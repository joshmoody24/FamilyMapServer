package service;

public class DoesNotExistException extends Exception {
    DoesNotExistException(String message) {
        super(message);
    }
}
