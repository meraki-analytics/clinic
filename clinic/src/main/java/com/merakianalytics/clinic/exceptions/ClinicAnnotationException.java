package com.merakianalytics.clinic.exceptions;

/**
 * Thrown when a clinic annotation has been configured improperly
 *
 * @since 1.0.0
 */
public class ClinicAnnotationException extends ClinicException {
    private static final long serialVersionUID = -2449484134338627799L;

    /**
     * @param message
     *        the error message
     */
    public ClinicAnnotationException(final String message) {
        super(message);
    }

    /**
     * @param message
     *        the error message
     * @param cause
     *        the cause of the error
     */
    public ClinicAnnotationException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
