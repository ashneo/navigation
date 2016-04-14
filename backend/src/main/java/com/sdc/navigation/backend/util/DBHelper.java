package com.sdc.navigation.backend.util;

import com.sdc.navigation.backend.domain.ServiceStation;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by a.shloma on 04.04.2016.
 */
@Slf4j
public class DBHelper {

    public static List<ServiceStation> getCollections() {
        List<ServiceStation> collection = new ArrayList<>();
        JSONArray array = new JSONArray(readDataFromFile());
        for (int i = 0; i < array.length(); i++) {
            JSONObject jsonObject = array.getJSONObject(i);
            collection.add(transformIntoObject(jsonObject));
        }
        return collection;
    }

    private static ServiceStation transformIntoObject(JSONObject jsonObject) {
        return new ServiceStation(
                (String)jsonObject.get("title"),
                (String)jsonObject.get("city"),
                (String)jsonObject.get("address"),
                (String)jsonObject.get("phone"),
                extractLocation(jsonObject.getJSONArray("location")),
                extratcSupportsBrand(jsonObject.getJSONArray("supportsBrand"))
        );
    }

    private static double[] extractLocation(JSONArray array) {
        double[] location = new double[2];
        location[0] = Double.valueOf(array.get(0).toString());
        location[1] = Double.valueOf(array.get(1).toString());
        return location;
    }

    private static List<String> extratcSupportsBrand(JSONArray array) {
        List<String> result = new ArrayList<>();
        array.forEach(s -> result.add((String)s));
        return result;
    }

    private static String readDataFromFile() {
        final StringBuilder sb = new StringBuilder();
        try (Stream<String> lines = Files.lines(new ClassPathResource("db.txt").getFile().toPath(), StandardCharsets.UTF_8)) {
            lines.forEach(line -> sb.append(line));
        } catch(IOException e) {
            log.debug("File not found db.txt");
        }
        return sb.toString();
    }
}
