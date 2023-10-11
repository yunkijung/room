package com.yun.room.api.house.service;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class GeocodingAPIService {
    private final GeoApiContext context;

    public GeocodingAPIService(@Value("${geocoding.api-key}") String apiKey) {
        this.context = new GeoApiContext.Builder().apiKey(apiKey).build();
    }

    public LatLng getLatLngFromAddress(String address) throws Exception {
        GeocodingResult[] results = GeocodingApi.geocode(context, address).await();
        if (results != null && results.length > 0) {
            return results[0].geometry.location;
        }
        throw new Exception("Unable to get coordinates for the provided address.");
    }
}
