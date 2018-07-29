package com.merakianalytics.clinic.exceptions;

/**
 * Thrown when a clinic component that doesn't face I/O sends a help prompt to the user
 *
 * @since 1.0.0
 */
public class ClinicGetHelpException extends ClinicException {
    private static final long serialVersionUID = -4178207499152986507L;

    /**
     * @param message
     *        the error message
     */
    public ClinicGetHelpException(final String message) {
        super(message);
    }

    /**
     * @param message
     *        the error message
     * @param cause
     *        the cause of the error
     */
    public ClinicGetHelpException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
