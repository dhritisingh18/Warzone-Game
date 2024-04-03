package utils;

/**
 * Exception class for invalid execution of commands in the game stage
 *
 * @author Dhriti Singh
 */
public class InvalidExecutionFailure extends Exception {

    public InvalidExecutionFailure() {
        super("The command is not there in the game");
    }

    public InvalidExecutionFailure(String message) {
        super(message);
    }
}
