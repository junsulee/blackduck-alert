/**
 * hub-alert
 *
 * Copyright (C) 2018 Black Duck Software, Inc.
 * http://www.blackducksoftware.com/
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.blackducksoftware.integration.hub.alert.processor.policy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.blackducksoftware.integration.hub.alert.config.GlobalProperties;
import com.blackducksoftware.integration.hub.alert.datasource.entity.NotificationCategoryEnum;
import com.blackducksoftware.integration.hub.alert.datasource.entity.NotificationEntity;
import com.blackducksoftware.integration.hub.alert.hub.model.NotificationModel;
import com.blackducksoftware.integration.hub.alert.processor.NotificationTypeProcessor;
import com.blackducksoftware.integration.hub.api.generated.enumeration.NotificationType;
import com.blackducksoftware.integration.hub.notification.NotificationDetailResult;
import com.blackducksoftware.integration.hub.notification.content.PolicyOverrideNotificationContent;
import com.blackducksoftware.integration.hub.notification.content.detail.NotificationContentDetail;
import com.blackducksoftware.integration.hub.service.bucket.HubBucket;

@Component
public class PolicyNotificationTypeProcessor extends NotificationTypeProcessor {
    private final Logger logger = LoggerFactory.getLogger(PolicyNotificationTypeProcessor.class);

    public PolicyNotificationTypeProcessor() {
        super(new LinkedHashSet<>(Arrays.asList(NotificationType.RULE_VIOLATION, NotificationType.RULE_VIOLATION_CLEARED, NotificationType.POLICY_OVERRIDE)));
    }

    @Override
    public List<NotificationModel> process(final GlobalProperties globalProperties, final NotificationDetailResult notificationDetailResult, final HubBucket bucket) {
        final List<NotificationContentDetail> detailList = notificationDetailResult.getNotificationContentDetails();
        final List<NotificationModel> modelList = new ArrayList<>(detailList.size());
        try {
            detailList.forEach(detail -> {
                final NotificationCategoryEnum notificationCategory = getNotificationCategory(notificationDetailResult.getType());
                modelList.add(new NotificationModel(createNotificationEntity(notificationDetailResult, detail, notificationCategory), Collections.emptyList()));
            });
        } catch (final Exception ex) {
            logger.error("Error processing policy violation {}", ex);
        }
        return modelList;
    }

    private NotificationCategoryEnum getNotificationCategory(final NotificationType notificationType) {
        if (NotificationType.RULE_VIOLATION == notificationType) {
            return NotificationCategoryEnum.POLICY_VIOLATION;
        } else if (NotificationType.RULE_VIOLATION_CLEARED == notificationType) {
            return NotificationCategoryEnum.POLICY_VIOLATION_CLEARED;
        } else if (NotificationType.POLICY_OVERRIDE == notificationType) {
            return NotificationCategoryEnum.POLICY_VIOLATION_OVERRIDE;
        } else {
            return NotificationCategoryEnum.POLICY_VIOLATION;
        }
    }

    private NotificationEntity createNotificationEntity(final NotificationDetailResult notificationDetailResult, final NotificationContentDetail notificationContentDetail, final NotificationCategoryEnum notificationCategory) {
        final Date createdAt = notificationDetailResult.getCreatedAt();

        final String contentKey = notificationContentDetail.getContentDetailKey();
        final String projectName = notificationContentDetail.getProjectName().get();
        final String projectUrl = null;
        final String projectVersion = notificationContentDetail.getProjectVersionName().get();
        final String projectVersionUrl = notificationContentDetail.getProjectVersion().get().uri;
        String componentName = null;
        if (notificationContentDetail.getComponentName().isPresent()) {
            componentName = notificationContentDetail.getComponentName().get();
        }
        String componentVersion = null;
        if (notificationContentDetail.getComponentVersionName().isPresent()) {
            componentVersion = notificationContentDetail.getComponentVersionName().get();
        }
        final String policyRuleName = notificationContentDetail.getPolicyName().get();
        String policyRuleUser = null;
        if (NotificationCategoryEnum.POLICY_VIOLATION_OVERRIDE.equals(notificationCategory)) {
            final PolicyOverrideNotificationContent content = (PolicyOverrideNotificationContent) notificationDetailResult.getNotificationContent();
            policyRuleUser = StringUtils.join(" ", content.firstName, content.lastName);
        }
        return new NotificationEntity(contentKey, createdAt, notificationCategory, projectName, projectUrl, projectVersion, projectVersionUrl, componentName, componentVersion, policyRuleName, policyRuleUser);
    }

}
