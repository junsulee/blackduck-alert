package com.blackducksoftware.integration.hub.alert.channel.email.users;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.blackducksoftware.integration.exception.EncryptionException;
import com.blackducksoftware.integration.hub.alert.datasource.AbstractRepositoryWrapper;

@Component
public class EmailGroupUserOptOutRepositoryWrapper extends AbstractRepositoryWrapper<EmailGroupUserOptOutEntity, EmailGroupUserOptOutEntityPK, EmailGroupUserOptOutRepository> {

    @Autowired
    public EmailGroupUserOptOutRepositoryWrapper(final EmailGroupUserOptOutRepository repository) {
        super(repository);
    }

    public List<EmailGroupUserOptOutEntity> findByCommonDistributionConfigId(final Long commonDistributionConfigId) {
        return getRepository().findByCommonDistributionConfigId(commonDistributionConfigId);
    }

    @Override
    public EmailGroupUserOptOutEntity encryptSensitiveData(final EmailGroupUserOptOutEntity entity) throws EncryptionException {
        return entity;
    }

    @Override
    public EmailGroupUserOptOutEntity decryptSensitiveData(final EmailGroupUserOptOutEntity entity) throws EncryptionException {
        return entity;
    }

}
