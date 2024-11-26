package com.uzu.exception.advice;

import com.uzu.exception.DuplicateException;
import com.uzu.exception.NotFoundException;
import com.uzu.exception.advice.models.ErrorResponse;
import com.uzu.exception.advice.models.FieldErrorResponse;
import com.uzu.exception.advice.models.enums.ErrorCode;
import jakarta.validation.ConstraintViolationException;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GenericControllerAdvice {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFoundException(NotFoundException e) {
        log.error("NotFoundException", e);

        String errorMessage = e.getMessage();
        errorMessage = ObjectUtils.isEmpty(errorMessage) ? ErrorCode.NOT_FOUND.getCode() : errorMessage;

        return new ErrorResponse(ErrorCode.NOT_FOUND.getCode(), errorMessage);
    }

    @ExceptionHandler(DuplicateException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleDuplicateException(DuplicateException e) {
        log.error("NotFoundException", e);

        String errorMessage = e.getMessage();
        errorMessage = ObjectUtils.isEmpty(errorMessage) ? ErrorCode.DUPLICATE_RECORD.getCode() : errorMessage;

        return new ErrorResponse(ErrorCode.DUPLICATE_RECORD.getCode(), errorMessage);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public FieldErrorResponse handleConstraintViolationErrors(ConstraintViolationException ex) {
        log.error(ex.getMessage(), ex);
        FieldErrorResponse response = new FieldErrorResponse();
        response.setCode(ErrorCode.BAD_REQUEST.getCode());
        response.setMessage("Invalid request");
        response.setFieldErrors(
                ex.getConstraintViolations()
                        .stream()
                        .map(fe -> new FieldErrorResponse.FieldError(fe.getPropertyPath().toString(),
                                fe.getMessageTemplate(), fe.getMessage()))
                        .collect(Collectors.toList())
        );
        return response;
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public FieldErrorResponse handleMethodArgumentNotValidErrors(MethodArgumentNotValidException ex) {
        log.error(ex.getMessage(), ex);
        FieldErrorResponse response = new FieldErrorResponse();
        response.setCode(ErrorCode.BAD_REQUEST.getCode());
        response.setMessage("Invalid request");
        response.setFieldErrors(
                ex.getBindingResult().getFieldErrors()
                        .stream()
                        .map(fe -> new FieldErrorResponse.FieldError(fe.getField(),
                                fe.getCode(), fe.getDefaultMessage()))
                        .collect(Collectors.toList())
        );
        return response;
    }

}