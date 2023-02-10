package request;

/**
 * Represents the JSON response for a clear request
 */
public class ClearResult extends Result {

    /**
     * creates a new ClearResult
     * @param success whether the clear operation was successful
     * @param message if clear operation failed, the error message
     */
    public ClearResult(boolean success, String message) {
        super(success, message);
    }
}
