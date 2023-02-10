package dao;

/**
 * Thrown when an error occurs while accessing information stored in the SQLite database
 */
public class DataAccessException extends Exception {

    /**
     * creates an exception
     * @param message the error message
     */
    DataAccessException(String message) {
        super(message);
    }
}
