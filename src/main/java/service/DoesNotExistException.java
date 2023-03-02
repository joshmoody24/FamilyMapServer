package service;

/**
 * Raised when the database does not find the requested object
 */
public class DoesNotExistException extends Exception {
    DoesNotExistException(String message) {
        super(message);
    }
}
