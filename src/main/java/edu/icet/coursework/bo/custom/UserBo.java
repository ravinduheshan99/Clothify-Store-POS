package edu.icet.coursework.bo.custom;

import edu.icet.coursework.bo.SuperBo;
import edu.icet.coursework.dto.Product;
import edu.icet.coursework.dto.User;
import javafx.collections.ObservableList;

public interface UserBo extends SuperBo {
    public boolean addUser(User user);
    public ObservableList<User> searchUser();
}
