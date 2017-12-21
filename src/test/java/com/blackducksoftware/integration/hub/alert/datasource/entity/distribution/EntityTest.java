/*
 * Copyright (C) 2017 Black Duck Software Inc.
 * http://www.blackducksoftware.com/
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * Black Duck Software ("Confidential Information"). You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Black Duck Software.
 */
package com.blackducksoftware.integration.hub.alert.datasource.entity.distribution;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.ObjectStreamClass;

import org.junit.Test;

import com.blackducksoftware.integration.hub.alert.mock.MockUtils;

public abstract class EntityTest<E extends DistributionChannelConfigEntity> {
    protected final MockUtils<?, ?, E, ?> mockUtils;
    private final Class<E> entityClass;

    public EntityTest(final MockUtils<?, ?, E, ?> mockUtils, final Class<E> entityClass) {
        this.mockUtils = mockUtils;
        this.entityClass = entityClass;
    }

    @Test
    public void testEmptyEntity() {
        final E configEntity = mockUtils.createEmptyEntity();
        assertEquals(entitySerialId(), ObjectStreamClass.lookup(entityClass).getSerialVersionUID());

        assertEntityFieldsNull(configEntity);
        assertNull(configEntity.getId());

        final int configHash = configEntity.hashCode();
        assertEquals(emptyEntityHashCode(), configHash);

        final String expectedString = mockUtils.getEmptyEntityJson();
        assertEquals(expectedString, configEntity.toString());

        final E configEntityNew = mockUtils.createEmptyEntity();
        assertEquals(configEntity, configEntityNew);
    }

    public abstract void assertEntityFieldsNull(E entity);

    public abstract long entitySerialId();

    public abstract int emptyEntityHashCode();

    @Test
    public void testEntity() {
        final E configEntity = mockUtils.createEntity();

        assertEntityFieldsFull(configEntity);
        assertEquals(Long.valueOf(mockUtils.getId()), configEntity.getId());

        final int configHash = configEntity.hashCode();
        assertEquals(entityHashCode(), configHash);

        final String expectedString = mockUtils.getEntityJson();
        assertEquals(expectedString, configEntity.toString());

        final E configEntityNew = mockUtils.createEntity();
        assertEquals(configEntity, configEntityNew);
    }

    public abstract void assertEntityFieldsFull(E entity);

    public abstract int entityHashCode();

}