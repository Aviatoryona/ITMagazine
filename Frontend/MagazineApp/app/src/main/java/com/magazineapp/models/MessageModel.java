package com.magazineapp.models;

import java.io.Serializable;

public class MessageModel implements Serializable {
    private boolean error;
    private String message;
    private Object data;

    public MessageModel() {
    }

    public MessageModel(boolean error, String message, Object data) {
        this.setError(error);
        this.setMessage(message);
        this.setData(data);
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
