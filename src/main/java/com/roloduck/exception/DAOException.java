package com.roloduck.exception;

/**
 * @author Andrew Ertell
 * @author Peter Johnston
 * @since 3/16/14
 * RoloDuck
 */

/**
 * This is the exception type that will be wrapped around any exception thrown inside a DAO layer
 */
public class DAOException extends Exception {

    public DAOException() {}

    public DAOException(String message) {
        super(message);
    }

    public DAOException(String message, Throwable cause) {
        super(message, cause);
    }
}
