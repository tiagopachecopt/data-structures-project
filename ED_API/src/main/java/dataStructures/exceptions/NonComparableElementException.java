package dataStructures.exceptions;

/**
 * Exception thrown when a comparison happens between incomparable items.
 */
public class NonComparableElementException extends RuntimeException {

    /**
     * Constructs a new NonComparableElementException with the specified detail message.
     *
     * @param message the detail message explaining the reason for the exception
     */
    public NonComparableElementException(String message) {
        super(message);
    }
}
