package utils;

/**
 * It shows the invalid console commands
 * @author Simran Simran
 */
public class ValidationFailure extends Exception {

    /**
     * Validation Exception Constructor
     */
    public ValidationFailure() {
        super("Invalid command. Type help ");
    }

    /**
     * Exception message
     * @param message exception message
     */
    public ValidationFailure(String message) {
        super(message);
    }
}
