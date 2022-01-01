package com.mariworld.userservice.error;

import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class FeignErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String method, Response response) {
        if(method.contains("getOrders")){
            return new RuntimeException("order is empty");
        }

        return new ResponseStatusException(HttpStatus.BAD_REQUEST, response.reason());
    }
}
