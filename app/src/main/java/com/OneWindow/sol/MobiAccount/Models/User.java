package com.OneWindow.sol.MobiAccount.Models;

/**
 * Created by waqarbscs on 9/9/2016.
 */
public class User {
    private String UserName;
    private String Password;
    public void setUserName(String u){
        UserName=u;
    }
    public String getUserName(){
        return UserName;
    }
    public void setPassword(String p){
        Password=p;
    }
    public String getPassword(){
        return Password;
    }
}
