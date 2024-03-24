package com.libraryCRUD.mainApp.ExceptionHandling;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(LibraryUserNotFoundException.class)
    public ResponseEntity<LibraryUserErrorResponse> handleNotFoundException(LibraryUserNotFoundException exc){

        //Create Error Response
        LibraryUserErrorResponse errorResponse = new LibraryUserErrorResponse();

        errorResponse.setStatus(HttpStatus.NOT_FOUND.value());
        errorResponse.setMessage(exc.getMessage());
        errorResponse.setTimeStamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        //Return ResponseEntity
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BadInputErrorResponse> handleValidationException(MethodArgumentNotValidException exc){

        //Create Error response
        BadInputErrorResponse errorResponse = new BadInputErrorResponse();

        errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        errorResponse.setMessage(exc.getFieldError().getDefaultMessage()); //Default message is set up to be a String so no null will be returned
        errorResponse.setTimeStamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        //If null is passed in for field, we just set up Rejected value to null
        if(exc.getFieldError().getRejectedValue() == null){
            errorResponse.setRejectedValue("null");
        }else { //Otherwise get rejected value as String
            errorResponse.setRejectedValue(exc.getFieldError().getRejectedValue().toString());
        }
        errorResponse.setField(exc.getFieldError().getField());

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }




    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> handleHttpMessageNotReadableException(HttpMessageNotReadableException exc) {

        //Create badInputErrorResponse and personalize depending on exception thrown
        BadInputErrorResponse badInputErrorResponse = new BadInputErrorResponse();
        //Set constant fields
        badInputErrorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        badInputErrorResponse.setTimeStamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        // Check if the root cause is DateTimeParseException
        if (exc.getRootCause() instanceof DateTimeParseException dateTimeParseException) {
            badInputErrorResponse.setMessage("Invalid date format. Enter format as YYYY-MM-DD");
            badInputErrorResponse.setRejectedValue(dateTimeParseException.getParsedString());
            badInputErrorResponse.setField("dateOfBirth");
            return new ResponseEntity<>(badInputErrorResponse, HttpStatus.BAD_REQUEST);
        }
        // Check if the root cause is invalidFormatException that is thrown for eNum violations only in this app
        if (exc.getRootCause() instanceof InvalidFormatException invalidFormatException) {
            badInputErrorResponse.setMessage("Invalid value for userRole. Only LIBRARIAN and MEMBER are allowed");
            badInputErrorResponse.setField("userRole");
            badInputErrorResponse.setRejectedValue(invalidFormatException.getValue().toString());
            return new ResponseEntity<>(badInputErrorResponse, HttpStatus.BAD_REQUEST);
        }

        // Handle general HttpNotReadableExceptions
        LibraryUserErrorResponse jsonResponse = new LibraryUserErrorResponse();
        jsonResponse.setMessage(exc.getLocalizedMessage());
        jsonResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        jsonResponse.setTimeStamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        return new ResponseEntity<>(jsonResponse, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(WrongRoleException.class)
    public ResponseEntity<LibraryUserErrorResponse> handleWrongRoleException(WrongRoleException exc){
        //Create Error Response
        LibraryUserErrorResponse errorResponse = new LibraryUserErrorResponse();

        errorResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        errorResponse.setMessage(exc.getMessage());
        errorResponse.setTimeStamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        //Return ResponseEntity
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);

    }




}
