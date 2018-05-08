package com.blackducksoftware.integration.hub.alert.channel.email.users.controller;

import com.blackducksoftware.integration.hub.alert.model.Model;

public class EmailGroupUserOptOutRestModel extends Model {
    private String commonDistributionConfigId;
    private String userOptOutEmail;

    public EmailGroupUserOptOutRestModel() {
    }

    public EmailGroupUserOptOutRestModel(final String commonDistributionConfigId, final String userOptOutEmail) {
        super();
        this.commonDistributionConfigId = commonDistributionConfigId;
        this.userOptOutEmail = userOptOutEmail;
    }

    public String getCommonDistributionConfigId() {
        return commonDistributionConfigId;
    }

    public void setCommonDistributionConfigId(final String commonDistributionConfigId) {
        this.commonDistributionConfigId = commonDistributionConfigId;
    }

    public String getUserOptOutEmail() {
        return userOptOutEmail;
    }

    public void setUserOptOutEmail(final String userOptOutEmail) {
        this.userOptOutEmail = userOptOutEmail;
    }

}
