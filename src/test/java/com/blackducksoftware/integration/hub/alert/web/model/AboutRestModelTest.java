package com.blackducksoftware.integration.hub.alert.web.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class AboutRestModelTest {

    @Test
    public void testWithValues() {
        final String version = "1.2.3";
        final String description = "description";
        final String gitHubUrl = "https://www.google.com";

        final AboutRestModel model = new AboutRestModel(version, description, gitHubUrl);

        assertEquals(version, model.getVersion());
        assertEquals(description, model.getDescription());
        assertEquals(gitHubUrl, model.getProjectUrl());
    }
}
