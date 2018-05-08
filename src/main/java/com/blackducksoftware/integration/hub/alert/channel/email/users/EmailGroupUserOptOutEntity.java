package com.blackducksoftware.integration.hub.alert.channel.email.users;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.blackducksoftware.integration.hub.alert.datasource.entity.BaseEntity;

@Entity
@Table(schema = "alert", name = "email_group_user_opt_out_entity")
public class EmailGroupUserOptOutEntity extends BaseEntity {

    @Id
    @Column
    private Long commonDistributionConfigId;

    @Id
    @Column
    private String userOptOutEmail;

    public EmailGroupUserOptOutEntity() {

    }

    public EmailGroupUserOptOutEntity(final Long commonDistributionConfigId, final String userOptOutEmail) {
        this.commonDistributionConfigId = commonDistributionConfigId;
        this.userOptOutEmail = userOptOutEmail;
    }

    public Long getCommonDistributionConfigId() {
        return commonDistributionConfigId;
    }

    public String getuserOptOutEmail() {
        return userOptOutEmail;
    }
}
