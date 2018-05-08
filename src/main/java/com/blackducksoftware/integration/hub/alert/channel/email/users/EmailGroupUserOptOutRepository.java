package com.blackducksoftware.integration.hub.alert.channel.email.users;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

@Transactional
public interface EmailGroupUserOptOutRepository extends JpaRepository<EmailGroupUserOptOutEntity, EmailGroupUserOptOutEntityPK> {
    public List<EmailGroupUserOptOutEntity> findByCommonDistributionConfigId(final Long commonDistributionConfigId);

    public List<EmailGroupUserOptOutEntity> findByUserOptOutEmail(String email);
}
