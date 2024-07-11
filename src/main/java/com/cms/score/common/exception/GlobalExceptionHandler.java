package com.cms.score.common.exception;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


import com.cms.score.common.response.Message;
import com.cms.score.common.response.Response;
import com.cms.score.common.response.dto.GlobalDto;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage().toString());

        GlobalDto errorDetails = new GlobalDto();
        errorDetails.setMessage(Message.EXCEPTION_INTERNAL_SERVER_ERROR.getMessage());
        errorDetails.setStatus(Message.EXCEPTION_INTERNAL_SERVER_ERROR.getStatusCode());
        errorDetails.setDetails(details);

        return Response.buildResponse(errorDetails, 3);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public final ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage().toString());


        GlobalDto errorDetails = new GlobalDto();
        errorDetails.setMessage(Message.EXCEPTION_BAD_REQUEST.getMessage());
        errorDetails.setStatus(Message.EXCEPTION_BAD_REQUEST.getStatusCode());
        errorDetails.setDetails(details);

        return Response.buildResponse(errorDetails, 3);
    }

    @ExceptionHandler(NullPointerException.class)
    public final ResponseEntity<Object> handleNullException(NullPointerException ex, WebRequest request) {
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage().toString());

        GlobalDto errorDetails = new GlobalDto();
        errorDetails.setMessage(Message.EXCEPTION_BAD_REQUEST.getMessage());
        errorDetails.setStatus(Message.EXCEPTION_BAD_REQUEST.getStatusCode());
        errorDetails.setDetails(details);

        return Response.buildResponse(errorDetails, 3);
    }

    // @Override
    // protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, 
    //                                                                HttpHeaders headers, 
    //                                                                HttpStatus status, 
    //                                                                WebRequest request) {
    //     List<String> errors = ex.getBindingResult()
    //                             .getFieldErrors()
    //                             .stream()
    //                             .map(FieldError::getDefaultMessage)
    //                             .collect(Collectors.toList());
    
    //     GlobalDto errorDetails = new GlobalDto();
    //     errorDetails.setMessage(Message.EXCEPTION_BAD_REQUEST.getMessage(), null);
    //     errorDetails.setStatus(Message.EXCEPTION_BAD_REQUEST.getStatusCode());
    //     errorDetails.setDetails(errors);
    
    //     return Response.buildResponse(errorDetails, HttpStatus.BAD_REQUEST.value());
    // }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public final ResponseEntity<Object> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex, WebRequest request) {
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage().toString());

        GlobalDto errorDetails = new GlobalDto();
        errorDetails.setMessage(Message.EXCEPTION_BAD_REQUEST.getMessage());
        errorDetails.setStatus(Message.EXCEPTION_BAD_REQUEST.getStatusCode());
        errorDetails.setDetails(details);

        return Response.buildResponse(errorDetails, 3);
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<Object> handleDataAccessException(DataAccessException ex, WebRequest request) {
        log.error("Database access error: {}", ex.getMessage());

        GlobalDto errorDetails = new GlobalDto();
        errorDetails.setMessage(Message.EXCEPTION_BAD_REQUEST.getMessage());
        errorDetails.setStatus(Message.EXCEPTION_BAD_REQUEST.getStatusCode());
        errorDetails.setDetails(List.of(ex.getLocalizedMessage()));

        return Response.buildResponse(errorDetails, 3);
    }

}