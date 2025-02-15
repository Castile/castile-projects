package com.castile.exception;

import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.Response;
import org.apache.servicecomb.core.Invocation;
import org.apache.servicecomb.core.SCBEngine;
import org.apache.servicecomb.core.exception.ExceptionConverter;
import org.apache.servicecomb.core.exception.converter.ValidateDetail;
import org.apache.servicecomb.swagger.invocation.exception.CommonExceptionData;
import org.apache.servicecomb.swagger.invocation.exception.InvocationException;

import java.util.List;
import java.util.stream.Collectors;

import static jakarta.ws.rs.core.Response.Status.BAD_REQUEST;
import static org.apache.servicecomb.core.exception.ExceptionCodes.DEFAULT_VALIDATE;
import static org.apache.servicecomb.core.exception.converter.ConstraintViolationExceptionConverter.KEY_CODE;

/**
 * @author castile
 * @date 2025-02-14 22:36
 */
public class ConstraintViolationExceptionConverter implements ExceptionConverter<ConstraintViolationException> {
    @Override
    public boolean canConvert(Throwable throwable) {
        return throwable instanceof ConstraintViolationException;
    }

    @Override
    public InvocationException convert(Invocation invocation, ConstraintViolationException throwable, Response.StatusType genericStatus) {
        List<ValidateDetail> details = throwable.getConstraintViolations().stream()
                .map(violation -> new ValidateDetail(violation.getPropertyPath().toString(), violation.getMessage()))
                .collect(Collectors.toList());

        CommonExceptionData exceptionData = new CommonExceptionData(SCBEngine.getInstance().getEnvironment().
                getProperty(KEY_CODE, String.class, DEFAULT_VALIDATE), "invalid parameters.");
        exceptionData.putDynamic("validateDetail", details);
        return new InvocationException(BAD_REQUEST, exceptionData);
    }
}
