package com.merakianalytics.clinic;

import com.google.common.base.CaseFormat;

/**
 * Common data and utilities for clinic
 */
public abstract class Common {
    public static final String HELP_OPTION = "--help";

    /**
     * @param camelCase
     *        a camelCase string
     * @return the string in lower hyphen case
     */
    public static String toHypenCase(final String camelCase) {
        return CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_HYPHEN, camelCase);
    }

    private Common() {}
}
