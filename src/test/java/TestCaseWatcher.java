import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;

import java.util.Arrays;

public class TestCaseWatcher implements TestWatcher {
    public record TestCaseData(String testName, String input,  String output, String ref, int points) { }

    public static final StringBuilder stringBuilder = new StringBuilder();

    public static int totalPoints = 0;

    @Override
    public void testSuccessful(final ExtensionContext context) {
        try {
            final TestCaseData testCaseData = getTestCaseData(context);
            stringBuilder.append("CASE - %s - %s/%s = PASSED\n".formatted(testCaseData.testName(), testCaseData.points(), testCaseData.points()));
            totalPoints += testCaseData.points();
        }
        catch (final IllegalArgumentException e) {
            // Do nothing
        }
    }

    @Override
    public void testFailed(final ExtensionContext context, final Throwable cause) {
        try {
            final TestCaseData testCaseData = getTestCaseData(context);
            stringBuilder.append("CASE - %s - 0/%s = FAILED\n".formatted(testCaseData.testName(), testCaseData.points()));
        }
        catch (final IllegalArgumentException e) {
            // Do nothing
        }
    }

    private TestCaseData getTestCaseData(final ExtensionContext context) {
        final String[] testCaseSplit = context.getDisplayName().split(",");

        if (testCaseSplit.length != 5) {
            throw new IllegalArgumentException();
        }

        final String[] data = Arrays.stream(testCaseSplit)
                .map(String::strip)
                .toArray(String[]::new);

        return new TestCaseData(data[0], data[1], data[2], data[3], Integer.parseInt(data[4]));
    }
}
