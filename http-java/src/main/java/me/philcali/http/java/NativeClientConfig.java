package me.philcali.http.java;

import java.util.concurrent.TimeUnit;

public class NativeClientConfig {
    private int requestTimeout = (int) TimeUnit.SECONDS.toMillis(20);
    private int connectTimeont = (int) TimeUnit.SECONDS.toMillis(10);

    public int getConnectTimeont() {
        return connectTimeont;
    }

    public int getRequestTimeout() {
        return requestTimeout;
    }

    public void setConnectTimeont(int connectTimeont) {
        this.connectTimeont = connectTimeont;
    }

    public void setRequestTimeout(int requestTimeout) {
        this.requestTimeout = requestTimeout;
    }

    public NativeClientConfig withConnectTimeout(int connectTimeout) {
        this.connectTimeont = connectTimeout;
        return this;
    }

    public NativeClientConfig withRequestTimeout(int requestTimeout) {
        this.requestTimeout = requestTimeout;
        return this;
    }

}
