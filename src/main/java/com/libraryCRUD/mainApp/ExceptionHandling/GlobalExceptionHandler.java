package com.libraryCRUD.mainApp.ExceptionHandling;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

// --> Exception handler for RestController. Multiple exceptions handled with personalized responses <-- \\
@ControllerAdvice
public class GlobalExceptionHandler {
    //  Handle exceptions for Library users that are not found in database
    @ExceptionHandler(LibraryUserNotFoundException.class)
    public ResponseEntity<GenericErrorResponse> handleNotFoundException(LibraryUserNotFoundException exc){
        // Create Error Response
        GenericErrorResponse errorResponse = new GenericErrorResponse();
        // Fill out fields for error response
        errorResponse.setStatus(HttpStatus.NOT_FOUND.value());
        errorResponse.setMessage(exc.getMessage());
        errorResponse.setTimeStamp(currentTime());
        // Return ResponseEntity with error response
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    //  Handle exceptions for arguments that are not valid to be inputted in database
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BadInputErrorResponse> handleValidationException(MethodArgumentNotValidException exc){
        //  Create Error response
        BadInputErrorResponse errorResponse = new BadInputErrorResponse();
        //  Fill out fields for error response
        errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        errorResponse.setMessage(exc.getFieldError().getDefaultMessage()); // Default message is set up to be a String so  null won't be returned
        errorResponse.setTimeStamp(currentTime());
        //  If null is passed in for field, we just set up Rejected value to null
        if(exc.getFieldError().getRejectedValue() == null){
            errorResponse.setRejectedValue("null");
        }else {// Otherwise set rejected value as String
            errorResponse.setRejectedValue(exc.getFieldError().getRejectedValue().toString());
        }
        errorResponse.setField(exc.getFieldError().getField());
        //  Return response entity with error response for exception
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    //  Handle exceptions for messages that are not readable when passed in a JSON. It handles DataTimeParseException and general HttpNotReadable exc.
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> handleHttpMessageNotReadableException(HttpMessageNotReadableException exc) {
        //  Create badInputErrorResponse and personalize depending on exception thrown
        BadInputErrorResponse badInputErrorResponse = new BadInputErrorResponse();
        //  Set constant fields
        badInputErrorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        badInputErrorResponse.setTimeStamp(currentTime());

        //  Check if the root cause is DateTimeParseException and fill it out accordingly
        if (exc.getRootCause() instanceof DateTimeParseException dateTimeParseException) {
            badInputErrorResponse.setMessage("Invalid date format. Enter format as YYYY-MM-DD");
            badInputErrorResponse.setRejectedValue(dateTimeParseException.getParsedString());
            badInputErrorResponse.setField("dateOfBirth");
            return new ResponseEntity<>(badInputErrorResponse, HttpStatus.BAD_REQUEST);
        }
        //  Check if the root cause is invalidFormatException that is thrown for eNum violations only in this app
        if (exc.getRootCause() instanceof InvalidFormatException invalidFormatException) {
            badInputErrorResponse.setMessage("Invalid value for userRole. Only LIBRARIAN and MEMBER are allowed");
            badInputErrorResponse.setField("userRole");
            badInputErrorResponse.setRejectedValue(invalidFormatException.getValue().toString());
            return new ResponseEntity<>(badInputErrorResponse, HttpStatus.BAD_REQUEST);
        }
        //  Handle general HttpNotReadableExceptions
        GenericErrorResponse errorResponse = new GenericErrorResponse();
        errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        errorResponse.setMessage(exc.getLocalizedMessage());
        errorResponse.setTimeStamp(currentTime());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    //  Handle exceptions for endpoints that are not found
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<GenericErrorResponse> handleNoResourceFoundException(NoResourceFoundException exc){
        GenericErrorResponse errorResponse = new GenericErrorResponse();
        errorResponse.setStatus(HttpStatus.NOT_FOUND.value());
        errorResponse.setMessage(exc.getMessage());
        errorResponse.setTimeStamp(currentTime());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);

    }

    //  Handle exceptions for wrong Role
    @ExceptionHandler(WrongRoleException.class)
    public ResponseEntity<GenericErrorResponse> handleWrongRoleException(WrongRoleException exc){
        // Create Error Response
        GenericErrorResponse errorResponse = new GenericErrorResponse();
        // Fill out fields to Error Response
        errorResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        errorResponse.setMessage(exc.getMessage());
        errorResponse.setTimeStamp(currentTime());
        //Return ResponseEntity with Error Response for exception.
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    //  Handle exceptions for duplicated emails in database
    @ExceptionHandler(DuplicatedEmailException.class)
    public ResponseEntity<GenericErrorResponse> handleDuplicatedRoleException(DuplicatedEmailException exc){
        GenericErrorResponse errorResponse = new GenericErrorResponse();
        errorResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        errorResponse.setMessage(exc.getMessage());
        errorResponse.setTimeStamp(currentTime());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    // Helper method to set a formatted current time
    private String currentTime(){
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

}
