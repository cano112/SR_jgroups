package pl.agh.edu.wiet.srlab2.distributedmap.exceptions;

public class ChannelSendException extends RuntimeException {
    public ChannelSendException() {
    }

    public ChannelSendException(String message) {
        super(message);
    }

    public ChannelSendException(String message, Throwable cause) {
        super(message, cause);
    }

    public ChannelSendException(Throwable cause) {
        super(cause);
    }

    public ChannelSendException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
