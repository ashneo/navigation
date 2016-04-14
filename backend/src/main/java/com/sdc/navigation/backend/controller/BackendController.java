package com.sdc.navigation.backend.controller;

import com.sdc.navigation.backend.domain.ServiceStation;
import com.sdc.navigation.backend.service.ServiceStationService;
import org.jsondoc.core.annotation.Api;
import org.jsondoc.core.annotation.ApiAuthNone;
import org.jsondoc.core.annotation.ApiMethod;
import org.jsondoc.core.annotation.ApiVersion;
import org.jsondoc.core.pojo.ApiStage;
import org.jsondoc.core.pojo.ApiVisibility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by a.shloma on 31.03.2016.
 */
@Api(name = "Service station services", description = "Methods for searching of service stations", group = "Geography",
        visibility = ApiVisibility.PUBLIC, stage = ApiStage.RC)
@ApiVersion(since = "1.0")
@ApiAuthNone
@Controller
public class BackendController {

    @Autowired
    private ServiceStationService repositoryFacade;

    @RequestMapping(value = "/findByCity", method = RequestMethod.GET)
    @ApiMethod(description = "Find service stations by city name (limit 50)")
    public @ResponseBody
    List<ServiceStation> findByCity(@RequestParam(value="city", required=false) String city) {
        return repositoryFacade.findByCity(city);
    }

    @RequestMapping(value = "/findByCityAndBrand", method = RequestMethod.GET)
    @ApiMethod(description = "Find service stations by city name and supported brand of car")
    public @ResponseBody
    List<ServiceStation> findByCityAndBrand(@RequestParam(value="city", required=true) String city,
                                    @RequestParam(value="brand", required=true) String brand) {
        return repositoryFacade.findByCityAndBrand(city, brand);
    }

    @RequestMapping(value = "/findByLocation", method = RequestMethod.GET)
    @ApiMethod(description = "Find service stations by latitude, longitude and distance in radius")
    public @ResponseBody
    List<ServiceStation> findByLocation(@RequestParam(value="ltd", required=true) double ltd,
                                        @RequestParam(value="lng", required=true) double lng,
                                        @RequestParam(value="distance", required=false , defaultValue="3.0") double distance) {
        return repositoryFacade.findByLocation(ltd, lng, distance);
    }

    @RequestMapping(value = "/voteForStation", method = RequestMethod.POST)
    @ApiMethod(description = "Vote for current Service Station")
    public @ResponseBody
    boolean voteForStation(@RequestParam(value="id", required=true) String id,
                           @RequestParam(value="vote", required=true) int vote) {
        return repositoryFacade.voteForStation(id, vote);
    }
}
