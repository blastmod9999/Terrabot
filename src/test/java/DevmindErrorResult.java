import com.fasterxml.jackson.annotation.JsonProperty;

public class DevmindErrorResult extends DevmindResult {
    @JsonProperty
    private final String error;

    public DevmindErrorResult(final String test, final int testScore, final String error) {
        super(test, "ERROR", testScore);
        this.error = error;
    }
}
