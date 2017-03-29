package ua.training.exception;

/**
 * Created by dima on 28.03.17.
 */
public class BanUserException extends RuntimeException {
    public BanUserException(String message) {
        super(message);
    }
}
