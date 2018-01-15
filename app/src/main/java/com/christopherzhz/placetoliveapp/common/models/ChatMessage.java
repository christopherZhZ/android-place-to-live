package com.christopherzhz.placetoliveapp.common.models;

public class ChatMessage {

    private String message;
    private String author;

    public ChatMessage(String message, String author) {
        this.message = message;
        this.author = author;
    }

    public ChatMessage() {
    }

    public String getMessage() {
        return message;
    }

    public String getAuthor() {
        return author;
    }

}
