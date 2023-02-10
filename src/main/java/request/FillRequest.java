package request;

/**
 * Represents the JSON request for a fill operation
 */
public class FillRequest {
    String username;
    int generations;
    public FillRequest(String username, int generations){
        this.username = username;
        this.generations = generations;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getGenerations() {
        return generations;
    }

    public void setGenerations(int generations) {
        this.generations = generations;
    }
}
