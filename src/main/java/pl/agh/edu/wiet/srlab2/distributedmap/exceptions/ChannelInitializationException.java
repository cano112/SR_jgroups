package pl.agh.edu.wiet.srlab2.distributedmap.exceptions;

public class ChannelInitializationException extends RuntimeException {

    public ChannelInitializationException() {
    }

    public ChannelInitializationException(String message) {
        super(message);
    }

    public ChannelInitializationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ChannelInitializationException(Throwable cause) {
        super(cause);
    }

    public ChannelInitializationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
