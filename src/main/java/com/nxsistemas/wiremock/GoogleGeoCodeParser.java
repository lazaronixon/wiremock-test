/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nxsistemas.wiremock;

import java.io.IOException;
import java.net.URL;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

/**
 *
 * @author Nixon
 */
public final class GoogleGeoCodeParser {

    private static final String GOOGLE_GEOCODE_URL = "http://localhost:8089/maps/api/geocode/json?sensor=false&region=br&address=";

    public static double[] getLatLng(String city, String uf) throws IOException {
        String sParams = city + ",+" + uf;
        String sUrl = GOOGLE_GEOCODE_URL + sParams;
        URL url = new URL(sUrl);
        JsonReader jsonReader = Json.createReader(url.openStream());
        JsonObject locationJson = jsonReader.readObject()
                .getJsonArray("results")
                .getJsonObject(0)
                .getJsonObject("geometry")
                .getJsonObject("location");

        double[] location = new double[2];
        location[0] = locationJson.getJsonNumber("lat").doubleValue();
        location[1] = locationJson.getJsonNumber("lng").doubleValue();
        return location;
    }

}
