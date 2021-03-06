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
package com.blackducksoftware.integration.hub.alert.digest.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.assertj.core.util.Sets;
import org.junit.Test;

public class CategoryDataTest {

    @Test
    public void testDataNull() {
        final CategoryData categoryData = new CategoryData(null, null, 0);

        assertNull(categoryData.getCategoryKey());
        assertEquals(0, categoryData.getItemCount());
        assertNull(categoryData.getItems());

        assertEquals("{\"categoryKey\":null,\"items\":null,\"itemCount\":0}", categoryData.toString());
    }

    @Test
    public void testData() {
        final ItemData itemData = new ItemData(null);
        final CategoryData categoryData = new CategoryData("CategoryKey", Sets.newLinkedHashSet(itemData), 3);

        assertEquals("CategoryKey", categoryData.getCategoryKey());
        assertEquals(3, categoryData.getItemCount());
        assertEquals(itemData, categoryData.getItems().iterator().next());

        assertEquals("{\"categoryKey\":\"CategoryKey\",\"items\":[{\"dataSet\":null}],\"itemCount\":3}", categoryData.toString());
    }
}
