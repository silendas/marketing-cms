package com.cms.score.common.exception;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.cms.score.common.response.Message;
import com.cms.score.common.response.Response;
import com.cms.score.common.response.dto.GlobalDto;

@ControllerAdvice
public class ExceptionMethodNotValid {

     @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.toList());

        GlobalDto errorDetails = new GlobalDto();
        errorDetails.setMessage(Message.EXCEPTION_BAD_REQUEST.getMessage());
        errorDetails.setStatus(Message.EXCEPTION_BAD_REQUEST.getStatusCode());
        errorDetails.setDetails(errors);

        return Response.buildResponse(errorDetails, HttpStatus.BAD_REQUEST.value());
    }

    //    @Override
    // protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
    //     List<String> errors = ex.getBindingResult()
    //             .getFieldErrors()
    //             .stream()
    //             .map(FieldError::getDefaultMessage)
    //             .collect(Collectors.toList());

    //     GlobalDto errorDetails = new GlobalDto();
    //     errorDetails.setMessage(Message.EXCEPTION_BAD_REQUEST.getMessage(), null);
    //     errorDetails.setStatus(Message.EXCEPTION_BAD_REQUEST.getStatusCode());
    //     errorDetails.setDetails(errors);

    //     return Response.buildResponse(errorDetails, HttpStatus.BAD_REQUEST.value());
    // }
    
}
