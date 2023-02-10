package request;


/**
 * Represents the JSON response for a "load" request
 */
public class LoadResult extends Result {

    /**
     * creates a new LoadResult
     * @param success whether the operation was successful
     * @param message if not successful, the error message
     */
    public LoadResult(boolean success, String message) {
        super(success, message);
    }


}
