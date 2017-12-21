/*
 * Copyright (C) 2017 Black Duck Software Inc.
 * http://www.blackducksoftware.com/
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * Black Duck Software ("Confidential Information"). You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Black Duck Software.
 */
package com.blackducksoftware.integration.hub.alert.web.controller.handler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.sql.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import com.blackducksoftware.integration.hub.alert.Application;
import com.blackducksoftware.integration.hub.alert.MockUtils;
import com.blackducksoftware.integration.hub.alert.config.DataSourceConfig;
import com.blackducksoftware.integration.hub.alert.datasource.entity.AuditEntryEntity;
import com.blackducksoftware.integration.hub.alert.datasource.entity.CommonDistributionConfigEntity;
import com.blackducksoftware.integration.hub.alert.datasource.entity.NotificationEntity;
import com.blackducksoftware.integration.hub.alert.datasource.entity.repository.AuditEntryRepository;
import com.blackducksoftware.integration.hub.alert.datasource.entity.repository.CommonDistributionRepository;
import com.blackducksoftware.integration.hub.alert.datasource.entity.repository.NotificationRepository;
import com.blackducksoftware.integration.hub.alert.enumeration.StatusEnum;
import com.blackducksoftware.integration.hub.alert.web.model.AuditEntryRestModel;
import com.blackducksoftware.integration.hub.alert.web.model.NotificationRestModel;
import com.github.springtestdbunit.DbUnitTestExecutionListener;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { Application.class, DataSourceConfig.class })
@Transactional
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class, TransactionalTestExecutionListener.class, DbUnitTestExecutionListener.class })
public class AuditEntryHandlerTestIT {
    private final MockUtils mockUtils = new MockUtils();
    @Autowired
    private AuditEntryHandler auditEntryHandler;
    @Autowired
    public AuditEntryRepository auditEntryRepository;
    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private CommonDistributionRepository commonDistributionRepository;

    @After
    public void cleanup() {
        auditEntryRepository.deleteAll();
        notificationRepository.deleteAll();
        commonDistributionRepository.deleteAll();
    }

    @Test
    public void getTestIT() {
        final NotificationEntity savedNotificationEntity = notificationRepository.save(mockUtils.createNotificationEntity());
        final CommonDistributionConfigEntity savedConfigEntity = commonDistributionRepository.save(mockUtils.createCommonDistributionConfigEntity());
        final AuditEntryEntity savedAuditEntryEntity = auditEntryRepository
                .save(new AuditEntryEntity(savedNotificationEntity.getId(), savedConfigEntity.getId(), new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()), StatusEnum.SUCCESS, null, null));

        final List<AuditEntryRestModel> auditEntries = auditEntryHandler.get();
        assertEquals(1, auditEntries.size());

        final AuditEntryRestModel auditEntry = auditEntryHandler.get(savedAuditEntryEntity.getId());
        assertNotNull(auditEntry);
        assertEquals(auditEntry, auditEntries.get(0));

        assertEquals(savedAuditEntryEntity.getId().toString(), auditEntry.getId());
        assertEquals(savedConfigEntity.getDistributionType(), auditEntry.getEventType());
        assertEquals(savedConfigEntity.getName(), auditEntry.getName());

        final NotificationRestModel notification = auditEntry.getNotification();
        assertEquals(savedNotificationEntity.getEventKey(), notification.getEventKey());
        assertEquals(savedNotificationEntity.getCreatedAt().toString(), notification.getCreatedAt());
        assertEquals(savedNotificationEntity.getNotificationType(), notification.getNotificationType());
        assertEquals(savedNotificationEntity.getProjectName(), notification.getProjectName());
        assertEquals(savedNotificationEntity.getProjectVersion(), notification.getProjectVersion());
        assertEquals(savedNotificationEntity.getProjectUrl(), notification.getProjectUrl());
        assertEquals(savedNotificationEntity.getProjectVersionUrl(), notification.getProjectVersionUrl());
        assertEquals(savedNotificationEntity.getComponentName(), notification.getComponentName());
        assertEquals(savedNotificationEntity.getComponentVersion(), notification.getComponentVersion());
        assertEquals(savedNotificationEntity.getPolicyRuleName(), notification.getPolicyRuleName());
        assertEquals(savedNotificationEntity.getPerson(), notification.getPerson());
    }

    @Test
    public void resendNotificationTestIt() {
        final NotificationEntity savedNotificationEntity = notificationRepository.save(mockUtils.createNotificationEntity());
        final CommonDistributionConfigEntity savedConfigEntity = commonDistributionRepository.save(mockUtils.createCommonDistributionConfigEntity());
        final AuditEntryEntity savedAuditEntryEntity = auditEntryRepository
                .save(new AuditEntryEntity(savedNotificationEntity.getId(), savedConfigEntity.getId(), new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()), StatusEnum.SUCCESS, null, null));
        final AuditEntryEntity badAuditEntryEntity_1 = auditEntryRepository
                .save(new AuditEntryEntity(savedNotificationEntity.getId(), -1L, new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()), StatusEnum.FAILURE, "Failed: stuff happened", ""));
        final AuditEntryEntity badAuditEntryEntity_2 = auditEntryRepository
                .save(new AuditEntryEntity(-1L, savedConfigEntity.getId(), new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()), StatusEnum.FAILURE, "Failed: stuff happened", ""));
        final AuditEntryEntity badAuditEntryEntityBoth = auditEntryRepository
                .save(new AuditEntryEntity(-1L, -1L, new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()), StatusEnum.FAILURE, "Failed: stuff happened", ""));

        final ResponseEntity<String> invalidIdResponse = auditEntryHandler.resendNotification(-1L);
        assertEquals(HttpStatus.BAD_REQUEST, invalidIdResponse.getStatusCode());

        final ResponseEntity<String> invalidReferenceResponse_1 = auditEntryHandler.resendNotification(badAuditEntryEntity_1.getId());
        assertEquals(HttpStatus.GONE, invalidReferenceResponse_1.getStatusCode());

        final ResponseEntity<String> invalidReferenceResponse_2 = auditEntryHandler.resendNotification(badAuditEntryEntity_2.getId());
        assertEquals(HttpStatus.GONE, invalidReferenceResponse_2.getStatusCode());

        final ResponseEntity<String> invalidReferenceResponseBoth = auditEntryHandler.resendNotification(badAuditEntryEntityBoth.getId());
        assertEquals(HttpStatus.GONE, invalidReferenceResponseBoth.getStatusCode());

        final ResponseEntity<String> validResponse = auditEntryHandler.resendNotification(savedAuditEntryEntity.getId());
        assertEquals(HttpStatus.OK, validResponse.getStatusCode());
    }

}