package service;

/**
 * Raised when the database does not find the requested object
 */
public class AuthorizationException extends Exception {
    AuthorizationException(String message) {
        super(message);
    }
}
