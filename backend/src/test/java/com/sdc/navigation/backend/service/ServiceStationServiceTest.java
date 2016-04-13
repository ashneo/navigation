package com.sdc.navigation.backend.service;

import com.sdc.navigation.backend.config.TestMongoConfig;
import com.sdc.navigation.backend.domain.ServiceStation;
import com.sdc.navigation.backend.repository.ServiceStationRepository;
import com.sdc.navigation.backend.util.DBHelper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by a.shloma on 04.04.2016.
 */
@ContextConfiguration(classes={TestMongoConfig.class})
@RunWith(SpringJUnit4ClassRunner.class)
public class ServiceStationServiceTest {

    private static final String TITLE = "title";
    private static final String CITY = "city";
    private static final String PHONE = "phone";
    private static final String ADDRESS = "address";
    private static final double[] LOCATION = new double[]{0.0, 0.0};
    private static final List<String> SUPPORTS_BRAND = Arrays.asList("brandOne", "anotherBrand");

    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private ServiceStationRepository serviceStationRepository;
    @Autowired
    private ServiceStationService repositoryFacade;

    @Test
    public void initEmptyCollection() {
        List<ServiceStation> result = mongoTemplate.findAll(ServiceStation.class);
        assertEquals(0, result.size());
    }

    @Test
    public void shouldInsertDataInCollection() {
        int inputCount = 10;
        for (int i = 0; i < inputCount; i++) {
            mongoTemplate.insert(createServiceStation());
        }
        List<ServiceStation> result = mongoTemplate.findAll(ServiceStation.class);
        assertEquals(inputCount, result.size());
    }

    @Test
    public void shouldFindByCity() {
        serviceStationRepository.insert(DBHelper.getCollections());
        List<ServiceStation> result = repositoryFacade.findByCity("Kyiv");
        assertTrue(!result.isEmpty());
    }

    private ServiceStation createServiceStation() {
        return new ServiceStation(TITLE, CITY, PHONE, ADDRESS, LOCATION, SUPPORTS_BRAND);
    }

    @Before
    public void init() {
        serviceStationRepository.deleteAll();
    }
}
