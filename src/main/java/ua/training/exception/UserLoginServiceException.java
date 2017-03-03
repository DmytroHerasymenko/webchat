package ua.training.exception;

/**
 * Created by dima on 02.03.17.
 */
public class UserLoginServiceException extends RuntimeException {
    public UserLoginServiceException(String message){
        super(message);
    }
}
