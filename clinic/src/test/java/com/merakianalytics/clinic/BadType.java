package com.merakianalytics.clinic;

public class BadType {
    private final int value;

    public BadType(final int value) {
        this.value = value;
    }

    @Override
    public boolean equals(final Object obj) {
        if(this == obj) {
            return true;
        }
        if(obj == null) {
            return false;
        }
        if(getClass() != obj.getClass()) {
            return false;
        }
        final BadType other = (BadType)obj;
        if(value != other.value) {
            return false;
        }
        return true;
    }

    /**
     * @return the value
     */
    public int getValue() {
        return value;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + value;
        return result;
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }
}
