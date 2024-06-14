package edu.icet.coursework.dao;

public interface CrudDao <T> extends SuperDao{

    public boolean addUser(T entity);
}
