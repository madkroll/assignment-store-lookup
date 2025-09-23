package store.lookup.repository;

import store.lookup.ApplicationException;

public class RepositoryOperationException extends ApplicationException {

    public RepositoryOperationException(String message, Throwable cause) {
        super(message, cause);
    }
}
