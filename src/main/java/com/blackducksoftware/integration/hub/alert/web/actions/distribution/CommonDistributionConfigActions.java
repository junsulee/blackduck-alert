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
package com.blackducksoftware.integration.hub.alert.web.actions.distribution;

import org.springframework.stereotype.Component;

import com.blackducksoftware.integration.exception.IntegrationException;
import com.blackducksoftware.integration.hub.alert.datasource.entity.CommonDistributionConfigEntity;
import com.blackducksoftware.integration.hub.alert.datasource.entity.repository.CommonDistributionRepository;
import com.blackducksoftware.integration.hub.alert.exception.AlertException;
import com.blackducksoftware.integration.hub.alert.web.ObjectTransformer;
import com.blackducksoftware.integration.hub.alert.web.model.distribution.CommonDistributionConfigRestModel;

@Component
public class CommonDistributionConfigActions extends DistributionConfigActions<CommonDistributionConfigEntity, CommonDistributionConfigRestModel> {

    public CommonDistributionConfigActions(final CommonDistributionRepository commonDistributionRepository, final ObjectTransformer objectTransformer) {
        super(CommonDistributionConfigEntity.class, CommonDistributionConfigRestModel.class, commonDistributionRepository, commonDistributionRepository, objectTransformer);
    }

    @Override
    public CommonDistributionConfigEntity saveConfig(final CommonDistributionConfigRestModel restModel) throws AlertException {
        if (restModel != null) {
            try {
                CommonDistributionConfigEntity createdEntity = objectTransformer.configRestModelToDatabaseEntity(restModel, databaseEntityClass);
                if (createdEntity != null) {
                    createdEntity = commonDistributionRepository.save(createdEntity);
                    return createdEntity;
                }
            } catch (final Exception e) {
                throw new AlertException(e.getMessage(), e);
            }
        }
        return null;
    }

    @Override
    public void deleteConfig(final Long id) {
        if (id != null) {
            commonDistributionRepository.delete(id);
        }
    }

    @Override
    public String channelTestConfig(final CommonDistributionConfigRestModel restModel) throws IntegrationException {
        // TODO test config
        return "Not implemented.";
    }

    @Override
    public CommonDistributionConfigRestModel constructRestModel(final CommonDistributionConfigEntity entity) throws AlertException {
        final CommonDistributionConfigEntity foundEntity = commonDistributionRepository.findOne(entity.getId());
        if (foundEntity != null) {
            return constructRestModel(foundEntity, null);
        }
        return null;
    }

    @Override
    public CommonDistributionConfigRestModel constructRestModel(final CommonDistributionConfigEntity commonEntity, final CommonDistributionConfigEntity distributionEntity) throws AlertException {
        return objectTransformer.databaseEntityToConfigRestModel(commonEntity, CommonDistributionConfigRestModel.class);
    }

    @Override
    public String getDistributionName() {
        // This does not have a distribution name
        return null;
    }

}