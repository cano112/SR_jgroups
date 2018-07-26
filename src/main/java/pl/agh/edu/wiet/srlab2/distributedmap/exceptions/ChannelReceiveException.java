package pl.agh.edu.wiet.srlab2.distributedmap.exceptions;

public class ChannelReceiveException extends RuntimeException {

    public ChannelReceiveException() {
    }

    public ChannelReceiveException(String message) {
        super(message);
    }

    public ChannelReceiveException(String message, Throwable cause) {
        super(message, cause);
    }

    public ChannelReceiveException(Throwable cause) {
        super(cause);
    }

    public ChannelReceiveException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
