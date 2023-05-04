package com.gznznzjsn.inventoryservice.core.model.exception;

public class NotEnoughResourcesException extends RuntimeException {

    /** Constructs a new 'not enough resources' exception with the specified
     * detail message. The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause}.
     *
     * @param   message   the detail message. The detail message is saved for
     *          later retrieval by the {@link #getMessage()} method.
     */
    public NotEnoughResourcesException(final String message) {
        super(message);
    }

}
