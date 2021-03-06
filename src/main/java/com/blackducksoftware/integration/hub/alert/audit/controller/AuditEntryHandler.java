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
package com.blackducksoftware.integration.hub.alert.audit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.blackducksoftware.integration.exception.IntegrationException;
import com.blackducksoftware.integration.hub.alert.web.ObjectTransformer;
import com.blackducksoftware.integration.hub.alert.web.controller.handler.ControllerHandler;
import com.blackducksoftware.integration.hub.alert.web.model.AlertPagedRestModel;
import com.google.gson.Gson;

@Component
public class AuditEntryHandler extends ControllerHandler {
    private final Gson gson;
    private final AuditEntryActions auditEntryActions;

    @Autowired
    public AuditEntryHandler(final ObjectTransformer objectTransformer, final Gson gson, final AuditEntryActions auditEntryActions) {
        super(objectTransformer);
        this.gson = gson;
        this.auditEntryActions = auditEntryActions;
    }

    public AlertPagedRestModel<AuditEntryRestModel> get(final Integer pageNumber, final Integer pageSize) {
        return auditEntryActions.get(pageNumber, pageSize);
    }

    public AuditEntryRestModel get(final Long id) {
        return auditEntryActions.get(id);
    }

    public ResponseEntity<String> resendNotification(final Long id) {
        AlertPagedRestModel<AuditEntryRestModel> auditEntries = null;
        try {
            auditEntries = auditEntryActions.resendNotification(id);
            return createResponse(HttpStatus.OK, id, gson.toJson(auditEntries));
        } catch (final IntegrationException e) {
            return createResponse(HttpStatus.BAD_REQUEST, id, e.getMessage());
        } catch (final IllegalArgumentException e) {
            return createResponse(HttpStatus.GONE, id, e.getMessage());
        }
    }

}
