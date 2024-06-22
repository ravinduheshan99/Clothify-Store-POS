package edu.icet.coursework.bo.custom.impl;

import edu.icet.coursework.bo.custom.UserBo;
import edu.icet.coursework.dao.DaoFactory;
import edu.icet.coursework.dao.custom.UserDao;
import edu.icet.coursework.dto.User;
import edu.icet.coursework.entity.UserEntity;
import edu.icet.coursework.util.DaoType;
import javafx.collections.ObservableList;
import org.modelmapper.ModelMapper;

public class UserBoImpl implements UserBo {

    private UserDao userDaoImpl = DaoFactory.getInstance().getDao(DaoType.USER);

    @Override
    public boolean addUser(User dto) {
        return userDaoImpl.addUser(new ModelMapper().map(dto, UserEntity.class));
    }

    @Override
    public ObservableList<User> searchUser() {
        return userDaoImpl.searchUser();
    }

    @Override
    public User searchUserById(String uid) {
        return userDaoImpl.searchUserById(uid);
    }

    @Override
    public boolean updateUser(User dto) {
        return userDaoImpl.updateUser(new ModelMapper().map(dto, UserEntity.class));
    }

    @Override
    public boolean removeUser(String uid) {
        return userDaoImpl.removeUser(uid);
    }

}
