package com.libraryCRUD.mainApp.ExceptionHandling;

// --> Error response for different types of wrong input from user <-- \\
public class BadInputErrorResponse {
    private int status;
    private String message;
    private String timeStamp;
    private String field;
    private String rejectedValue;


    //  Constructor

    public BadInputErrorResponse() {}


    //  Getters and setters

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
