package com.blackducksoftware.integration.hub.alert.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;

import com.blackducksoftware.integration.hub.alert.exception.AlertException;
import com.blackducksoftware.integration.hub.alert.web.model.ConfigRestModel;

@RequestMapping(FlexibleDistributionConfigController.DISTRIBUTION_PATH)
public class FlexibleDistributionConfigController extends FlexibleConfigController {
    public static final String DISTRIBUTION_PATH = ConfigController.CONFIGURATION_PATH + "/distribution";

    private final ConfigActionsManager configActionsManager;

    @Autowired
    public FlexibleDistributionConfigController(final ConfigActionsManager configActionsManager) {
        this.configActionsManager = configActionsManager;
    }

    @Override
    public <R extends ConfigRestModel> List<R> getConfig(final Long id, final Class<R> restModelClass) throws AlertException {
        return configActionsManager.getDistributionConfigActions(restModelClass).getConfig(id);
    }

    @Override
    public <R extends ConfigRestModel> ResponseEntity<String> postConfig(final R restModel) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public <R extends ConfigRestModel> ResponseEntity<String> putConfig(final R restModel) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public <R extends ConfigRestModel> ResponseEntity<String> deleteConfig(final R restModel) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public <R extends ConfigRestModel> ResponseEntity<String> validateConfig(final R restModel) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public <R extends ConfigRestModel> ResponseEntity<String> testConfig(final R restModel) {
        // TODO Auto-generated method stub
        return null;
    }

}
