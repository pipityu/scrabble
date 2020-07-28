package com.sce.demo.model;

public class Token {
    private String name;
    private int value;

    public Token(){}
    public Token(TokenEnum t){
        this.name = t.toString();
        this.value = t.value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }


}
