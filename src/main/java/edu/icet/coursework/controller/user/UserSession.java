package edu.icet.coursework.controller.user;

import edu.icet.coursework.dto.User;

public class UserSession{
    private UserSession(){}
    private static UserSession instance;
    public static UserSession getInstance(){
        if (instance==null){
            instance = new UserSession();
            return instance;
        }
        return instance;
    }

    private User user;

    public void CurrentSession(User user){
        this.user=user;
    }

    public User getCurrentSession(){
        return user;
    }
}
