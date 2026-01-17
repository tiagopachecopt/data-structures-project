package dataStructures.exceptions;

/**
 * Exception thrown when an operation is performed on an empty collection.
 */
public class EmptyCollectionException extends Exception {

    /**
     * Constructs a new EmptyCollectionException with the specified detail message.
     *
     * @param message the detail message explaining the reason for the exception
     */
    public EmptyCollectionException(String message) {
        super(message);
    }
}
