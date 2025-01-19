package com.elcmal.service;

import com.elcmal.payload.request.SignupRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.util.HashSet;
import java.util.Set;

public class Test {

    public static void main(String[] args) {

        SignupRequest object = new SignupRequest();
        object.setUsername("abdo");
        object.setEmail("abdoabdelsabour355@gmail.com");
        object.setPassword("abdelsabour22");
        Set <String> roles = new HashSet<>();
        roles.add("admin");
       // roles.add("user") ;
        object.setRoles(roles);
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        try {
            String json = ow.writeValueAsString(object);
            System.out.println(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
