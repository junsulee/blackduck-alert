package com.blackducksoftware.integration.hub.alert.channel.email.users;

import java.io.Serializable;

@SuppressWarnings("serial")
public class EmailGroupUserOptOutEntityPK implements Serializable {
    private Long commonDistributionConfigId;
    private String userOptOutEmail;

    public EmailGroupUserOptOutEntityPK() {
    }

    public Long getCommonDistributionConfigId() {
        return commonDistributionConfigId;
    }

    public void setCommonDistributionConfigId(final Long commonDistributionConfigId) {
        this.commonDistributionConfigId = commonDistributionConfigId;
    }

    public String getUserOptOutEmail() {
        return userOptOutEmail;
    }

    public void setUserOptOutEmail(final String userOptOutEmail) {
        this.userOptOutEmail = userOptOutEmail;
    }

}
