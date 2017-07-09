package me.philcali.http.api;

import me.philcali.http.api.exception.HttpException;

public interface IRequest {
    IRequest body(String body);
    IRequest header(String name, String value);
    IRequest post(String name, String value);
    IRequest query(String name, String value);
    IResponse respond() throws HttpException;
}
