package com.slack.api.bolt.http4k;

import com.slack.api.bolt.App;
import com.slack.api.bolt.request.RequestHeaders;
import com.slack.api.bolt.util.SlackRequestParser;
import kotlin.Pair;
import kotlin.jvm.functions.Function1;
import org.http4k.core.Request;
import org.http4k.core.Response;
import org.http4k.core.Status;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toMap;
import static org.http4k.core.ParametersKt.toParameters;
import static org.http4k.core.Status.INTERNAL_SERVER_ERROR;

public class Http4kSlackApp {
    public static Function1<Request, Response> create(App app) {
        final SlackRequestParser requestParser = new SlackRequestParser(app.config());

        return req -> {
            try {
                return toHttp4k(app.run(toHttp4k(requestParser, req)));
            } catch (Exception e) {
                return Response.Companion.create(INTERNAL_SERVER_ERROR)
                        .header("Content-type", "application/json")
                        .body("{\"error\":\"Something is wrong\"}");
            }
        };
    }

    private static com.slack.api.bolt.request.Request<?> toHttp4k(SlackRequestParser requestParser, Request httpRequest) {
        return requestParser.parse(SlackRequestParser.HttpRequest.builder()
                .requestUri(httpRequest.getUri().getPath())
                .queryString(
                        toParameters(httpRequest.getUri().getQuery()).stream()
                                .collect(toMap(Pair::component1, it -> singletonList(it.component2()))))
                .requestBody(httpRequest.bodyString())
                .headers(
                        new RequestHeaders(httpRequest.getHeaders().stream()
                                .collect(toMap(Pair::component1, it -> singletonList(it.component2())))))
                .remoteAddress(httpRequest.header("X-Forwarded-For"))
                .build());
    }

    private static Response toHttp4k(com.slack.api.bolt.response.Response boltResponse) {
        List<Pair<String, String>> headers = boltResponse.getHeaders()
                .entrySet().stream()
                .flatMap(it -> it.getValue().stream().map(it2 -> new Pair<String, String>(it.getKey(), it2)))
                .collect(Collectors.toList());
        return Response.Companion.create(new Status(boltResponse.getStatusCode(), ""))
                .headers(headers)
                .body(boltResponse.getBody());
    }
}
