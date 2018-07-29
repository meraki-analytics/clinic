package com.merakianalytics.clinic.exceptions;

/**
 * An exception relating to clinic
 *
 * @since 1.0.0
 */
public class ClinicException extends RuntimeException {
    private static final long serialVersionUID = 9222740639776898149L;

    /**
     * @param message
     *        the error message
     */
    public ClinicException(final String message) {
        super(message);
    }

    /**
     * @param message
     *        the error message
     * @param cause
     *        the cause of the error
     */
    public ClinicException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * @param cause
     *        the cause of the error
     */
    public ClinicException(final Throwable cause) {
        super(null, cause);
    }
}
