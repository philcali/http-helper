package me.philcali.http.api;

import java.io.InputStream;

import me.philcali.http.api.exception.HttpException;

public interface IResponse {
    InputStream body() throws HttpException;
    String header(String name);
    int status();
}
