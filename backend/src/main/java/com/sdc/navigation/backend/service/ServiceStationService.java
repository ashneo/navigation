package com.sdc.navigation.backend.service;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.WriteResult;
import com.sdc.navigation.backend.domain.ServiceStation;
import com.sdc.navigation.backend.repository.ServiceStationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Circle;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

/**
 * Created by a.shloma on 01.04.2016.
 */
@Component
public class ServiceStationService {

    private static final Metrics METRIC_KILOMETERS = Metrics.KILOMETERS;
    private static final int SEARCHING_LIMIT = 50;

    @Autowired
    private ServiceStationRepository repository;
    @Autowired
    private MongoTemplate mongoTemplate;

    public List<ServiceStation> findByCity(String city) {
        List<ServiceStation> result = repository.findByCityIgnoreCaseOrderByRatingDesc(city);
        if (!result.isEmpty() && result.size() > SEARCHING_LIMIT) {
            return result.subList(0, SEARCHING_LIMIT);
        }
        return result;
    }

    public List<ServiceStation> findByLocation(double ltd, double lng, double distance) {
        Point point = new Point(ltd, lng);
        Circle circle = new Circle(point, new Distance(distance, METRIC_KILOMETERS));
        return mongoTemplate.find(new Query(Criteria.where("location").withinSphere(circle)), ServiceStation.class);
    }

    public List<ServiceStation> findByCityAndBrand(String city, String brand) {
        return repository.findByCityAndSupportsBrandIn(city, brand);
    }

    public boolean voteForStation(String serviceStationId, int vote) {
        ServiceStation station = mongoTemplate.findOne(query(where("id").is(serviceStationId)), ServiceStation.class);
        Double rating = Double.valueOf(station.getRating());
        int votes = station.getVotes();
        rating = (rating + vote * votes) / ++votes;
        station.setRating(roundValue(rating));
        station.setVotes(votes);

        DBObject obj = (DBObject) mongoTemplate.getConverter().convertToMongoType(station);
        Update setUpdate = Update.fromDBObject(new BasicDBObject("$set", obj));
        WriteResult wr = mongoTemplate.updateFirst(new Query(where("id").is(serviceStationId)),
                setUpdate, ServiceStation.class);
        return wr.isUpdateOfExisting();

    }

    private String roundValue(Double value) {
        String str = value.toString();
        if (str.length() > 4) {
            return str.substring(0, 3);
        }
        return str;
    }
}
