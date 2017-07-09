package me.philcali.http.api;

public interface IHttpClient {
    IRequest createRequest(HttpMethod method, String url);
}
