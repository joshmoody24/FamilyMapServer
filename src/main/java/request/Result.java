package request;

/**
 * base class for all results
 */
public abstract class Result {

    /**
     * whether the requested operation was successful
     */
    boolean success;

    /**
     * the error message of an unsuccessful operation
     */
    String message;

    /**
     * Creates a new result
     * @param success whether the operation was successful
     * @param message if not successful, the error message
     */
    public Result(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

}
