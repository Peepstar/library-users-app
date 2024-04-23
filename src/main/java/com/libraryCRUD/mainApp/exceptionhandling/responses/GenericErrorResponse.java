package com.libraryCRUD.mainApp.exceptionhandling.responses;

// --> Error response for general cases or when no extra information is needed for an exception triggered <-- \\
public class GenericErrorResponse {
    private int status;
    private String message;
    private String timeStamp;


    // Constructors

    public GenericErrorResponse() {
    }


    //  Getters and setters

    public int getStatus() {
        return status;
    }
    public void setStatus(int status) { this.status = status; }
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

}
