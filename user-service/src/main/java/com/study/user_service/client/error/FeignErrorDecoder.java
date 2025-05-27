package com.study.user_service.client.error;

import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
@RequiredArgsConstructor
public class FeignErrorDecoder implements ErrorDecoder {

    private final Environment environment;

    @Override
    public Exception decode(String methodName, Response response) {
        switch (response.status()) {
            case 400:
                break;
            case 404:
                if (methodName.contains("getOrders")) {
                    return new ResponseStatusException(HttpStatus.valueOf(response.status()),
                            environment.getProperty("order-service.exception.not-found"));
                }
                break;
            default:
                return new Exception(response.reason());
        }

        return null;
    }
}
