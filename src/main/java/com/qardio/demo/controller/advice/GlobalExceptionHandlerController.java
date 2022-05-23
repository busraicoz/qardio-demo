package com.qardio.demo.controller.advice;

import com.qardio.demo.exception.CustomException;
import com.qardio.demo.model.error.ErrorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandlerController {

    private final MessageSource messageSource;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception exception) {
        ErrorResponse errorResponse = new ErrorResponse(
                "GenericException",
                LocalDateTime.now(),
                Collections.singletonList(exception.getMessage())
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handle(MethodArgumentNotValidException exception) {
        List<String> errorMessages = exception.getBindingResult().getFieldErrors().stream()
                .map(this::getMessage)
                .collect(Collectors.toList());

        ErrorResponse errorResponse = new ErrorResponse(
                "MethodArgumentNotValidException",
                LocalDateTime.now(),
                errorMessages
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(CustomException exception) {
        return ResponseEntity
                .status(exception.getHttpStatus())
                .body(ErrorResponse.builder()
                        .exception("CustomException")
                        .error(getMessage(exception.getKey(), (Object) exception.getArgs()))
                        .build());
    }
    private String getMessage(String messageKey, Object... args) {
        return messageSource.getMessage(messageKey, args, messageKey, Locale.ENGLISH);
    }

    private String getMessage(FieldError error) {
        return getMessage(error.getDefaultMessage(), error.getArguments());
    }
}
