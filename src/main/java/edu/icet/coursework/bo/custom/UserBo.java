package edu.icet.coursework.bo.custom;

import edu.icet.coursework.bo.SuperBo;
import edu.icet.coursework.dto.User;
import javafx.collections.ObservableList;

public interface UserBo extends SuperBo {
    public boolean addUser(User user);
    public ObservableList<User> searchUser();
    public User searchUserById(String uid);
    public boolean updateUser(User user);
    public boolean removeUser(String uid);
}
