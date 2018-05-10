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
package com.blackducksoftware.integration.hub.alert.processor;

import com.blackducksoftware.integration.hub.alert.datasource.entity.NotificationCategoryEnum;
import com.blackducksoftware.integration.hub.api.view.CommonNotificationState;
import com.blackducksoftware.integration.hub.notification.content.NotificationContent;
import com.blackducksoftware.integration.hub.notification.content.NotificationContentDetail;
import com.blackducksoftware.integration.util.Stringable;

public class NotificationProcessingModel extends Stringable {
    private final NotificationContentDetail contentDetail;
    private final CommonNotificationState commonNotificationState;
    private final NotificationContent content;
    private final NotificationCategoryEnum notificationType;

    public NotificationProcessingModel(final NotificationContentDetail contentDetail, final CommonNotificationState commonNotificationState, final NotificationContent content, final NotificationCategoryEnum notificationType) {
        this.contentDetail = contentDetail;
        this.commonNotificationState = commonNotificationState;
        this.content = content;
        this.notificationType = notificationType;
    }

    public NotificationContentDetail getContentDetail() {
        return contentDetail;
    }

    public CommonNotificationState getCommonNotificationState() {
        return commonNotificationState;
    }

    public NotificationContent getContent() {
        return content;
    }

    public NotificationCategoryEnum getNotificationType() {
        return notificationType;
    }
}