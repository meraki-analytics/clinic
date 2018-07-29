package com.merakianalytics.clinic.exceptions;

/**
 * Thrown when the command line arguments passed to clinic are invalid
 *
 * @since 1.0.0
 */
public class ClinicParseException extends ClinicException {
    private static final long serialVersionUID = -522148842063720469L;

    /**
     * @param message
     *        the error message
     */
    public ClinicParseException(final String message) {
        super(message);
    }

    /**
     * @param message
     *        the error message
     * @param cause
     *        the cause of the error
     */
    public ClinicParseException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
