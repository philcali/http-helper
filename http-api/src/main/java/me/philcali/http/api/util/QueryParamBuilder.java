package me.philcali.http.api.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

import me.philcali.http.api.exception.HttpException;

public class QueryParamBuilder {
    private Map<String, List<String>> params = new HashMap<>();

    public QueryParamBuilder add(String name, String value) {
        List<String> values = params.computeIfAbsent(name, p -> new ArrayList<>());
        try {
            values.add(URLEncoder.encode(value, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new HttpException(e);
        }
        return this;
    }

    public String build() {
        StringJoiner joiner = new StringJoiner("&");
        params.forEach((param, values) -> {
            values.forEach(value -> {
                StringJoiner binder = new StringJoiner("=");
                joiner.add(binder.add(param).add(value).toString());
            });
        });
        return joiner.toString();
    }

    public boolean isEmpty() {
        return params.isEmpty();
    }
}
