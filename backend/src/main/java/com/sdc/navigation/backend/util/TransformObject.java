package com.sdc.navigation.backend.util;

import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.util.Arrays;
import java.util.List;

/**
 * Created by a.shloma on 04.04.2016.
 */
@Slf4j
public class TransformObject {

    private static final String INPUT_SOURCE = "d:\\other-sto.txt";
    private static final String OUTPUT_SOURCE = "d:\\output.txt";

    private void convert() {
        JSONArray array = new JSONArray(getSource(INPUT_SOURCE));
        JSONArray result = new JSONArray();
        for (int i = 0; i < array.length(); i++) {
            JSONObject jsonObject = array.getJSONObject(i);
            result.put(transform(jsonObject));
        }
        writeJson(result.toString(), OUTPUT_SOURCE);
    }

    private JSONObject transform(JSONObject another) {
        JSONObject transform = new JSONObject();
        transform.put("city", "Kyiv");
        transform.put("address", another.get("address"));
        transform.put("title", another.get("title"));
        transform.put("supportsBrand", another.get("marks_serviced"));
        List<Double> location = Arrays.asList(Double.valueOf((String)another.get("lat")),
                Double.valueOf((String)another.get("lng")));
        transform.put("location", location);
        transform.put("phone", another.get("tel"));
        transform.put("vipSlot", 0);
        transform.put("rating", 0.0);
        transform.put("votes", 0);
        return transform;
    }

    private String getSource(String source) {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(source))) {
            br.lines().forEach(s -> sb.append(s));
        } catch(IOException e) {
            log.debug("File not found ", source);
        }
        return sb.toString().replaceAll("\\uFEFF", "");
    }

    private void writeJson(String source, String output) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(output))) {
            bw.write(source);
        } catch (IOException e) {
            log.debug("File not found ", output);
        }
    }
}
