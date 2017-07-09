package me.philcali.http.java;

import me.philcali.http.api.HttpMethod;
import me.philcali.http.api.IHttpClient;
import me.philcali.http.api.IRequest;

public class NativeHttpClient implements IHttpClient {
    private final NativeClientConfig config;

    public NativeHttpClient() {
        this(new NativeClientConfig());
    }

    public NativeHttpClient(NativeClientConfig config) {
        this.config = config;
    }

    public IRequest createRequest(HttpMethod method, String url) {
        return new NativeHttpRequest(method, url, config);
    }

}
