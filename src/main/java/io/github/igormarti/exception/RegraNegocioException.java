package io.github.igormarti.exception;

import java.util.function.Supplier;

public class RegraNegocioException extends  RuntimeException {

    public RegraNegocioException(String message) {
        super(message);
    }

}
