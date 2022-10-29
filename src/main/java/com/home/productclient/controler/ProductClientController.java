package com.home.productclient.controler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/productclient")
public class ProductClientController {

    @Autowired
    RestTemplate restTemplateTimeoutWithRequestFactory;

    @GetMapping("/getp")
    public ResponseEntity<String> getProducts() {

        String message = "initial";
        try {
            String output = restTemplateTimeoutWithRequestFactory.getForObject("http://localhost:8081/product/getProduct", String.class);

            if (output != null) {
                message = output;
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (e instanceof ResourceAccessException) {

                // return new ResponseEntity<String>("clientoutput", HttpStatus.OK);
            }

        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
