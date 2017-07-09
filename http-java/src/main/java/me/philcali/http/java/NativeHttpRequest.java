package me.philcali.http.java;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import me.philcali.http.api.HttpMethod;
import me.philcali.http.api.IRequest;
import me.philcali.http.api.IResponse;
import me.philcali.http.api.exception.HttpException;
import me.philcali.http.api.util.QueryParamBuilder;

public class NativeHttpRequest implements IRequest {
    private final HttpMethod method;
    private final String url;
    private final NativeClientConfig config;
    private Map<String, String> headers = new HashMap<>();
    private QueryParamBuilder queryParams = new QueryParamBuilder();
    private QueryParamBuilder postParams = new QueryParamBuilder();
    private String body;

    public NativeHttpRequest(HttpMethod method, String url, NativeClientConfig config) {
        this.method = method;
        this.url = url;
        this.config = config;
    }

    @Override
    public IRequest body(String body) {
        this.body = body;
        return this;
    }

    private String buildUrl() {
        StringBuilder builder = new StringBuilder(url);
        if (!queryParams.isEmpty()) {
            builder.append("?").append(queryParams.build());
        }
        return builder.toString();
    }

    @Override
    public IRequest header(String name, String value) {
        headers.put(name, value);
        return this;
    }

    @Override
    public IRequest post(String name, String value) {
        postParams.add(name, value);
        return this;
    }

    private boolean pushingData() {
        switch (method) {
        case POST:
        case PUT:
        case PATCH:
            return true;
        default:
            return false;
        }
    }

    @Override
    public IRequest query(String name, String value) {
        queryParams.add(name, value);
        return this;
    }

    @Override
    public IResponse respond() throws HttpException {
        try {
            URL url = new URL(buildUrl());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(config.getRequestTimeout());
            connection.setConnectTimeout(config.getConnectTimeont());
            connection.setRequestMethod(method.name());
            connection.setDoInput(true);
            headers.forEach((name, value) -> {
                connection.setRequestProperty(name, value);
            });
            if (pushingData()) {
                if (!postParams.isEmpty()) {
                    body = postParams.build();
                    connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                }
                Optional.ofNullable(body).ifPresent(content -> {
                    connection.setRequestProperty("Content-Length", Integer.toString(body.length()));
                    connection.setDoOutput(true);
                    try {
                        OutputStream output = connection.getOutputStream();
                        output.write(body.getBytes(StandardCharsets.UTF_8));
                        output.close();
                    } catch (IOException e) {
                        throw new HttpException(e);
                    }
                });
            }
            return new NativeHttpResponse(connection);
        } catch (IOException e) {
            throw new HttpException(e);
        }
    }
}
