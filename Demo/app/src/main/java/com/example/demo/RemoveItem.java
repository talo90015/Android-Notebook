package com.example.demo;

public class RemoveItem {

    private int Position;
    private String string;

    public RemoveItem(int position, String string){
        this.Position = position;
        this.string = string;
    }
    public  int getPosition(){
        return Position;
    }
    public String getString(){
        return string;
    }
}
