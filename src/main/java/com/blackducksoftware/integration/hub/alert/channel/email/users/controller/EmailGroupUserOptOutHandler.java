package com.blackducksoftware.integration.hub.alert.channel.email.users.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.blackducksoftware.integration.hub.alert.channel.email.users.EmailGroupUserOptOutRepositoryWrapper;
import com.blackducksoftware.integration.hub.alert.web.ObjectTransformer;
import com.blackducksoftware.integration.hub.alert.web.controller.handler.ControllerHandler;

public class EmailGroupUserOptOutHandler extends ControllerHandler {
    private final EmailGroupUserOptOutRepositoryWrapper emailGroupUserOptOutRepositoryWrapper;

    @Autowired
    public EmailGroupUserOptOutHandler(final ObjectTransformer objectTransformer, final EmailGroupUserOptOutRepositoryWrapper emailGroupUserOptOutRepositoryWrapper) {
        super(objectTransformer);
        this.emailGroupUserOptOutRepositoryWrapper = emailGroupUserOptOutRepositoryWrapper;
    }

    public List<EmailGroupUserOptOutRestModel> getRestModels(final Long commonConfigId) {
        return emailGroupUserOptOutRepositoryWrapper.findByCommonDistributionConfigId(commonConfigId);
    }

}
