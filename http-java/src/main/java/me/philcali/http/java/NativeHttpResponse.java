package me.philcali.http.java;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

import me.philcali.http.api.IResponse;
import me.philcali.http.api.exception.HttpException;

public class NativeHttpResponse implements IResponse {
    private final HttpURLConnection connection;

    public NativeHttpResponse(HttpURLConnection connection) {
        this.connection = connection;
    }

    @Override
    public InputStream body() throws HttpException {
        try {
            return connection.getInputStream();
        } catch (IOException e) {
            throw new HttpException(e);
        }
    }

    @Override
    public String header(String name) {
        return connection.getHeaderField(name);
    }

    @Override
    public int status() {
        try {
            return connection.getResponseCode();
        } catch (IOException e) {
            return 500;
        }
    }

}
