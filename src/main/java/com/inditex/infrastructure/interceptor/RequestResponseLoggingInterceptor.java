package com.inditex.infrastructure.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Slf4j
@Component
public class RequestResponseLoggingInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        logRequestParams(request);

        if (HttpMethod.POST.matches(request.getMethod()) || HttpMethod.PUT.matches(request.getMethod())) {
            logRequestBody(request);
        }

        return true;
    }

     @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
         log.info("response status: {}", response.getStatus());
        if (ex != null) {
            log.error("Request failed: {}", ex.getMessage());
        }
    }


    private void logRequestParams(HttpServletRequest request) {
        StringBuilder params = new StringBuilder();
        params.append("?");
        request.getParameterMap().forEach((key, value) -> {
            for (String v : value) {
                params.append(key).append("=").append(v).append("&");
            }
        });

        if (params.length() > 0) {
            params.setLength(params.length() - 1);
        }

        log.info("Request : {}{}",request.getRequestURL(), params);
    }

    private void logRequestBody(HttpServletRequest request) throws IOException {
        String requestBody = Optional.of(StreamUtils.copyToString(request.getInputStream(), StandardCharsets.UTF_8)).orElse("{}");
        log.info("Request Body: {}", requestBody);
    }
}