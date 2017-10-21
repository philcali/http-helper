package me.philcali.http.api.util;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;

import me.philcali.http.api.exception.HttpException;

public class URLBuilder {
    private String protocol;
    private String host;
    private String path;
    private String baseUrl;
    private Integer port;
    private QueryParamBuilder queryParams = new QueryParamBuilder();

    public URLBuilder() {
    }

    public URLBuilder(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public URLBuilder addQueryParam(String param, Object value) {
        queryParams.add(param, value.toString());
        return this;
    }

    public URL build() {
        if (baseUrl == null || (host == null && protocol == null)) {
            throw new NullPointerException("URL build is missing params");
        }
        StringBuilder builder = new StringBuilder(Optional.ofNullable(baseUrl).orElseGet(this::composeParts));
        if (!queryParams.isEmpty()) {
            builder.append("?").append(queryParams.build());
        }
        try {
            return new URL(builder.toString());
        } catch (MalformedURLException e) {
            throw new HttpException(e);
        }
    }

    private String composeParts() {
        StringBuilder parts = new StringBuilder(protocol).append("://").append(host);
        Optional.ofNullable(port).map(p -> p.toString()).ifPresent(parts.append(":")::append);
        Optional.ofNullable(path).map(p -> {
            if (p.indexOf('/') == 0) {
                return p.replaceFirst("/", "");
            } else {
                return p;
            }
        }).ifPresent(parts.append("/")::append);
        return parts.toString();
    }

    public URLBuilder withBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
        return this;
    }

    public URLBuilder withHost(String host) {
        this.host = host;
        return this;
    }

    public URLBuilder withPath(String path) {
        this.path = path;
        return this;
    }

    public URLBuilder withPort(Integer port) {
        this.port = port;
        return this;
    }

    public URLBuilder withProtocol(String protocol) {
        this.protocol = protocol;
        return this;
    }
}
