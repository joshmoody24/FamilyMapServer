package request;

/**
 * Represents the JSON response for a fill request
 */
public class FillResult extends Result {

    /**
     * creates a new FillResult
     * @param success whether the Fill operation was successful
     * @param message if Fill operation failed, the error message
     */
    public FillResult(boolean success, String message) {
        super(success, message);
    }
}
