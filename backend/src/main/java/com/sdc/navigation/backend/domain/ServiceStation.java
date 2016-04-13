package com.sdc.navigation.backend.domain;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.List;

/**
 * Created by a.shloma on 25.03.2016.
 */
@Data
public class ServiceStation {

    @Id
    @Setter(AccessLevel.PRIVATE)
    private String id;
    private String title;
    private String city;
    private String address;
    private String phone;
    private double[] location;
    private List<String> supportsBrand;
    private String rating;
    private int votes;
    private int vipSlot;

    public ServiceStation(String title, String city, String address, String phone, double[] location,
                          List<String> supportsBrand) {
        this.title = title;
        this.city = city;
        this.location = location;
        this.supportsBrand = supportsBrand;
        this.address = address;
        this.phone = phone;
        this.rating = "0.0";
    }
}
