package com.blackducksoftware.integration.hub.alert.web.controller;

import org.springframework.stereotype.Component;

import com.blackducksoftware.integration.hub.alert.web.actions.distribution.DistributionConfigActions;

@Component
public class ConfigActionsManager {

    public <CA extends DistributionConfigActions> CA getDistributionConfigActions(final Class<?> restModelClass) {
        return null;
    }
}
