import com.puppycrawl.tools.checkstyle.api.AuditEvent;
import com.puppycrawl.tools.checkstyle.api.AuditListener;

public class CheckstyleAuditListener implements AuditListener {
    private final StringBuilder errors = new StringBuilder();

    @Override
    public void auditStarted(final AuditEvent auditEvent) {
    }

    @Override
    public void auditFinished(final AuditEvent auditEvent) {
    }

    @Override
    public void fileStarted(final AuditEvent auditEvent) {
    }

    @Override
    public void fileFinished(final AuditEvent auditEvent) {
    }

    @Override
    public void addError(final AuditEvent auditEvent) {
        errors.append(auditEvent.getFileName())
                .append(":")
                .append(auditEvent.getLine())
                .append(" - ")
                .append(auditEvent.getMessage())
                .append("\n");
    }

    @Override
    public void addException(final AuditEvent auditEvent, final Throwable throwable) {
    }

    @Override
    public String toString() {
        return errors.toString();
    }
}
