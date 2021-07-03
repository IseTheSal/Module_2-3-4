package com.epam.esm.error.exception;

import com.epam.esm.error.RestErrorStatusCode;

public class TagNotFoundException extends ApplicationRuntimeException {

    public TagNotFoundException(String tagValue) {
        super(tagValue, RestErrorStatusCode.ENTITY_NOT_FOUND);
    }

    public TagNotFoundException() {
        super(null, RestErrorStatusCode.ENTITY_NOT_FOUND);
    }
}
