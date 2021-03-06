package com.blackducksoftware.integration.hub.alert;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;

import com.blackducksoftware.integration.hub.alert.channel.SupportedChannels;
import com.blackducksoftware.integration.hub.alert.datasource.entity.CommonDistributionConfigEntity;
import com.blackducksoftware.integration.hub.alert.datasource.entity.repository.CommonDistributionRepositoryWrapper;
import com.blackducksoftware.integration.hub.alert.exception.AlertException;
import com.blackducksoftware.integration.hub.alert.mock.entity.MockCommonDistributionEntity;
import com.blackducksoftware.integration.hub.service.HubServicesFactory;
import com.blackducksoftware.integration.hub.service.PhoneHomeService;
import com.blackducksoftware.integration.phonehome.PhoneHomeRequestBody;
import com.blackducksoftware.integration.rest.connection.RestConnection;
import com.blackducksoftware.integration.test.annotation.HubConnectionTest;
import com.blackducksoftware.integration.test.tool.TestLogger;

public class PhoneHomeTest {

    @Test
    @Category(HubConnectionTest.class)
    public void testProductVersion() throws AlertException, IOException {
        final TestGlobalProperties globalProperties = new TestGlobalProperties();
        final PhoneHome phoneHome = new PhoneHome(null);

        try (final RestConnection restConnection = globalProperties.createRestConnection(new TestLogger())) {
            final HubServicesFactory hubServicesFactory = globalProperties.createHubServicesFactory(restConnection);
            final PhoneHomeService phoneHomeService = hubServicesFactory.createPhoneHomeService();
            final PhoneHomeRequestBody.Builder builder = phoneHome.createPhoneHomeBuilder(phoneHomeService, "test");

            Assert.assertNotNull(builder);
            Assert.assertEquals("test", builder.getArtifactVersion());
            Assert.assertEquals("blackduck-alert", builder.getArtifactId());
        }

    }

    @Test
    public void testChannelMetaData() {
        final CommonDistributionRepositoryWrapper commonDistributionRepositoryWrapper = Mockito.mock(CommonDistributionRepositoryWrapper.class);
        Mockito.when(commonDistributionRepositoryWrapper.findAll()).thenReturn(createConfigEntities());
        final PhoneHome phoneHome = new PhoneHome(commonDistributionRepositoryWrapper);
        final PhoneHomeRequestBody.Builder builder = new PhoneHomeRequestBody.Builder();

        phoneHome.addChannelMetaData(builder);
        final Map<String, String> metaData = builder.getMetaData();

        Assert.assertNull(metaData.get("channel." + SupportedChannels.EMAIL_GROUP));
        Assert.assertEquals(String.valueOf(2), metaData.get("channel." + SupportedChannels.HIPCHAT));
        Assert.assertEquals(String.valueOf(1), metaData.get("channel." + SupportedChannels.SLACK));
    }

    private List<CommonDistributionConfigEntity> createConfigEntities() {
        final MockCommonDistributionEntity mockCommonDistributionEntity = new MockCommonDistributionEntity();
        final CommonDistributionConfigEntity entity1 = mockCommonDistributionEntity.createEntity();
        final CommonDistributionConfigEntity entity2 = mockCommonDistributionEntity.createEntity();
        mockCommonDistributionEntity.setDistributionType(SupportedChannels.SLACK);
        final CommonDistributionConfigEntity entity3 = mockCommonDistributionEntity.createEntity();

        return Arrays.asList(entity1, entity2, entity3);
    }
}
