package com.elcmal.service.impl;

import com.elcmal.model.Order;
import com.elcmal.payload.response.ShipmentResponse;
import com.elcmal.service.DHLService;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

//@Service
public class DHLServiceImpl implements DHLService {

//    private static final Logger logger = LoggerFactory.getLogger(DHLServiceImpl.class);
//
//    private final OkHttpClient dhlHttpClient;
//    private final ObjectMapper objectMapper;
//    private final String baseUrl;
//
//    @Value("${dhl.api.key}")
//    private String apiKey;
//
//    @Value("${dhl.api.secret}")
//    private String apiSecret;
//
//    public DHLServiceImpl(OkHttpClient dhlHttpClient, ObjectMapper objectMapper,
//                          @Value("${dhl.api.baseUrl}") String baseUrl) {
//        this.dhlHttpClient = dhlHttpClient;
//        this.objectMapper = objectMapper;
//        this.baseUrl = baseUrl;
//    }

//    @Override
//    public ShipmentResponse createShipment(Order order) {
//        try {
//            // Prepare shipment request body
//            Map<String, Object> shipmentRequest = createShipmentRequest(order);
//
//            // Convert request to JSON
//            String jsonRequest = objectMapper.writeValueAsString(shipmentRequest);
//
//            String credentials = apiKey + ":" + apiSecret;
//            String encodedAuth = new String(Base64.getEncoder().encode(credentials.getBytes()));
//
//            // Create HTTP request
//            Request request = new Request.Builder()
//                    .url(baseUrl + "shipments")
//                    .header("Authorization", "Basic " + encodedAuth)
//                    .header("Content-Type", "application/json")
//                    .post(RequestBody.create(jsonRequest, MediaType.parse("application/json")))
//                    .build();
//
//            // Execute request
//            try (Response response = dhlHttpClient.newCall(request).execute()) {
//                if (!response.isSuccessful()) {
//                    logger.error("DHL API error: {}", response);
//                    throw new IOException("Unexpected response code: " + response.code());
//                }
//
//                // Parse response
//                String jsonResponse = response.body().string();
//                return objectMapper.readValue(jsonResponse, ShipmentResponse.class);
//            }
//        } catch (IOException e) {
//            logger.error("I/O error creating DHL shipment", e);
//            throw new RuntimeException("Failed to communicate with DHL API", e);
//        }
//    }
//@Override
//public ShipmentResponse createShipment(Order order) {
//    try {
//        // Prepare shipment request body
//        Map<String, Object> shipmentRequest = createShipmentRequest(order);
//
//        // Convert request to JSON
//        String jsonRequest = objectMapper.writeValueAsString(shipmentRequest);
//
//        // Encode API credentials
//        String credentials = apiKey + ":" + apiSecret;
//        String encodedAuth = new String(Base64.getEncoder().encode(credentials.getBytes()));
//
//        // Create HTTP request
//        Request request = new Request.Builder()
//                .url(baseUrl + "shipments")
//                .header("Authorization", "Basic " + encodedAuth)
//                .header("Content-Type", "application/json")
//                .post(RequestBody.create(jsonRequest, MediaType.parse("application/json")))
//                .build();
//
//        // Execute request
//        try (Response response = dhlHttpClient.newCall(request).execute()) {
//            if (!response.isSuccessful()) {
//                logger.error("DHL API error: {}", response);
//                throw new IOException("Unexpected response code: " + response.code());
//            }
//
//            // Parse response
//            String jsonResponse = response.body().string();
//            return objectMapper.readValue(jsonResponse, ShipmentResponse.class);
//        }
//    } catch (IOException e) {
//        logger.error("I/O error creating DHL shipment", e);
//        throw new RuntimeException("Failed to communicate with DHL API", e);
//    }
//}
//
//
//    @Override
//    public String getShipmentStatus(String trackingNumber) {
//        try {
//            Request request = new Request.Builder()
//                    .url(baseUrl + "shipments/" + trackingNumber + "/tracking")
//                    .get()
//                    .build();
//
//            try (Response response = dhlHttpClient.newCall(request).execute()) {
//                if (!response.isSuccessful()) {
//                    throw new IOException("Unexpected response code: " + response);
//                }
//
//                String jsonResponse = response.body().string();
//                Map<String, Object> tracking = objectMapper.readValue(jsonResponse, Map.class);
//                return (String) ((Map<String, Object>) tracking.get("status")).get("description");
//            }
//        } catch (Exception e) {
//            logger.error("Error getting shipment status", e);
//            throw new RuntimeException("Failed to get shipment status", e);
//        }
//    }

//    @Override
//    public void cancelShipment(String shipmentId) {
//        try {
//            Request request = new Request.Builder()
//                    .url(baseUrl + "shipments/" + shipmentId)
//                    .delete()
//                    .build();
//
//            try (Response response = dhlHttpClient.newCall(request).execute()) {
//                if (!response.isSuccessful()) {
//                    throw new IOException("Failed to cancel shipment: " + response);
//                }
//            }
//        } catch (Exception e) {
//            logger.error("Error cancelling shipment", e);
//            throw new RuntimeException("Failed to cancel shipment", e);
//        }
//    }
//
//    private Map<String, Object> createShipmentRequest(Order order) {
//        Map<String, Object> shipmentRequest = new HashMap<>();
//
//        // Add customer information
//        Map<String, Object> customerInfo = new HashMap<>();
//        customerInfo.put("name", order.getCustomerName());
//        customerInfo.put("address", order.getAddress());
//        customerInfo.put("phone", order.getPhone());
//        customerInfo.put("email", order.getEmail());
//        shipmentRequest.put("customerInfo", customerInfo);
//
//        // Add shipment details
//        Map<String, Object> shipmentDetails = new HashMap<>();
//        shipmentDetails.put("weight", 1.0); // Default weight in kg
//        shipmentDetails.put("dimensions", Map.of(
//                "length", 10,
//                "width", 10,
//                "height", 10
//        ));
//        shipmentRequest.put("shipmentDetails", shipmentDetails);
//
//        // Add service options
//        shipmentRequest.put("service", "express");
//        shipmentRequest.put("currency", "USD");
//
//        return shipmentRequest;
//    }
}
