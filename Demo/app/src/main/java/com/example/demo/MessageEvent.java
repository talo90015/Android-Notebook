package com.example.demo;

public class MessageEvent {
    private  String Message;

    public  MessageEvent(String message){
        this.Message = message;
    }
    public String getMessage(){
        return Message;
    }
}
