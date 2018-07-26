package pl.agh.edu.wiet.srlab2.distributedmap.exceptions;

public class ChannelMergeException extends RuntimeException {

    public ChannelMergeException() {
    }

    public ChannelMergeException(String message) {
        super(message);
    }

    public ChannelMergeException(String message, Throwable cause) {
        super(message, cause);
    }

    public ChannelMergeException(Throwable cause) {
        super(cause);
    }

    public ChannelMergeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
