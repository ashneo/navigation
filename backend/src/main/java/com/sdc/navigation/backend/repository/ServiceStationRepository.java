package com.sdc.navigation.backend.repository;

import com.sdc.navigation.backend.domain.ServiceStation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Created by a.shloma on 25.03.2016.
 */
public interface ServiceStationRepository extends MongoRepository<ServiceStation, String> {

    List<ServiceStation> findByCityIgnoreCaseOrderByRatingDesc(final String city);

    List<ServiceStation> findByCityAndSupportsBrandIn(final String city, final String supportBrand);
}
