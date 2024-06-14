package edu.icet.coursework.bo.custom;

import edu.icet.coursework.bo.SuperBo;
import edu.icet.coursework.dto.User;

public interface UserBo extends SuperBo {
    public boolean addUser(User user);
}
