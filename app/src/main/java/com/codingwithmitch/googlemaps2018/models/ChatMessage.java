package com.codingwithmitch.googlemaps2018.models;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class ChatMessage {

    private User user;
    private String message;
    private String message_id;
    private @ServerTimestamp
    Date timestamp;

    public ChatMessage(User user, String message, String message_id, Date timestamp) {
        this.user = user;
        this.message = message;
        this.message_id = message_id;
        this.timestamp = timestamp;
    }

    public ChatMessage() {

    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage_id() {
        return message_id;
    }

    public void setMessage_id(String message_id) {
        this.message_id = message_id;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "ChatMessage{" +
                "user=" + user +
                ", message='" + message + '\'' +
                ", message_id='" + message_id + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
