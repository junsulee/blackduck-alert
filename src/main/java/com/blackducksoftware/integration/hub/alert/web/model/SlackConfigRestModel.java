/**
 * Copyright (C) 2017 Black Duck Software, Inc.
 * http://www.blackducksoftware.com/
 *
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
package com.blackducksoftware.integration.hub.alert.web.model;

public class SlackConfigRestModel extends ChannelRestModel {
    private static final long serialVersionUID = -2360827976516988339L;

    private String channelName;
    private String username;
    private String webhook;

    protected SlackConfigRestModel() {
    }

    public SlackConfigRestModel(final String channelName, final String username, final String webhook) {
        this.channelName = channelName;
        this.username = username;
        this.webhook = webhook;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(final String channelName) {
        this.channelName = channelName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public String getWebhook() {
        return webhook;
    }

    public void setWebhook(final String webhook) {
        this.webhook = webhook;
    }

}
