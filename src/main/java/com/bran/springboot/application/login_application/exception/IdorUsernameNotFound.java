package com.bran.springboot.application.login_application.exception;

public class IdorUsernameNotFound extends Exception{

    public IdorUsernameNotFound(){
        super("Id or user not found");
    }


    public IdorUsernameNotFound(String message){
        super(message);
    }
}
