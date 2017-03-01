package ua.training.exception;

/**
 * Created by dima on 28.02.17.
 */
public class LoginServiceException extends RuntimeException {
    public LoginServiceException(String message) {
        super(message);
    }
}
