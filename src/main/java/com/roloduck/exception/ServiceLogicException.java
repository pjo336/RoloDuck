package com.roloduck.exception;

/**
 * @author Andrew Ertell
 * @author Peter Johnston
 * @since 3/7/14
 * RoloDuck
 */

public class ServiceLogicException extends Exception implements RoloDuckException {

    public ServiceLogicException(){};

    public ServiceLogicException(String message) {
        super(message);
    }
    public ServiceLogicException(Throwable cause) {
        super(cause.getMessage(), cause);
    }

    public ServiceLogicException(String message, Throwable cause) {
        super(message, cause);
    }
}
