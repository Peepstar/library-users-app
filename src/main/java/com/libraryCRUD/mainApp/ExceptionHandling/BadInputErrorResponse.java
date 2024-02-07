package com.libraryCRUD.mainApp.ExceptionHandling;

public class BadInputErrorResponse {

    //Fields

    private int status;

    private String message;

    private String timeStamp;

    private String field;

    private String rejectedValue;

    //Constructors
    public BadInputErrorResponse() {}

    public BadInputErrorResponse(int status, String message, String timeStamp, String field, String rejectedValue) {
        this.status = status;
        this.message = message;
        this.timeStamp = timeStamp;
        this.field = field;
        this.rejectedValue = rejectedValue;
    }

    //Getters and setters


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getRejectedValue() {
        return rejectedValue;
    }

    public void setRejectedValue(String rejectedValue) {
        this.rejectedValue = rejectedValue;
    }
}
