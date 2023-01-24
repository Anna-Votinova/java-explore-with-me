package ru.practicum.ewm_service.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.ewm_service.client.BaseClient;
import ru.practicum.ewm_service.entity.dto.hit.EndpointHitDto;


import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.time.Instant;


@Component
public class StatsClient extends BaseClient {
    private static final String APPLICATION_NAME = "ewm-main-service";

    @Autowired
    public StatsClient(@Value("${stats-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl))
                        .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                        .build()
        );
    }

    public void createHit(HttpServletRequest request) {
        final EndpointHitDto hit = EndpointHitDto.builder()
                .app(APPLICATION_NAME)
                .uri(request.getRequestURI())
                .ip(request.getRemoteAddr())
                .timestamp(Timestamp.from(Instant.now()))
                .build();
        post("/hit", hit);
    }
}
