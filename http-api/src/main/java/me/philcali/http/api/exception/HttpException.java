package me.philcali.http.api.exception;

public class HttpException extends RuntimeException {
    /**
     *
     */
    private static final long serialVersionUID = -7505393376881159790L;

    public HttpException(Throwable t) {
        super(t);
    }
}
