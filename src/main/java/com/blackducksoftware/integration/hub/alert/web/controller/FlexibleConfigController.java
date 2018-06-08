package com.blackducksoftware.integration.hub.alert.web.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.blackducksoftware.integration.hub.alert.web.model.ConfigRestModel;

//This must be an abstract class for the security to work
@RequestMapping(FlexibleConfigController.CONFIGURATION_PATH)
public abstract class FlexibleConfigController extends BaseController {
    public static final String CONFIGURATION_PATH = BaseController.BASE_PATH + "/configuration";

    @GetMapping
    public abstract <R extends ConfigRestModel> List<R> getConfig(final Long id, Class<R> restModelClass);

    @PostMapping
    public abstract <R extends ConfigRestModel> ResponseEntity<String> postConfig(final R restModel);

    @PutMapping
    public abstract <R extends ConfigRestModel> ResponseEntity<String> putConfig(final R restModel);

    @DeleteMapping
    public abstract <R extends ConfigRestModel> ResponseEntity<String> deleteConfig(final R restModel);

    @PostMapping(value = "/validate")
    public abstract <R extends ConfigRestModel> ResponseEntity<String> validateConfig(final R restModel);

    @PostMapping(value = "/test")
    public abstract <R extends ConfigRestModel> ResponseEntity<String> testConfig(final R restModel);
}
