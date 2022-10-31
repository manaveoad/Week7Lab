/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import dataaccess.UserDB;
import java.util.List;
import models.User;


/**
 *
 * @author manav
 */
public class UserService {
    
    public void addUser(User user) throws Exception {
        UserDB userDB = new UserDB();
        userDB.insert(user);
    }
    
    public User get(String email) throws Exception   {
        UserDB userDB = new UserDB();
        User user = userDB.get(email);
        return user;
    }
    
    public List<User> getAll() throws Exception {
        UserDB userDB = new UserDB();
        List<User> users = userDB.getAll();
        return users;
    }
    
    public void updateUser(User user) throws Exception  {
        UserDB userDB = new UserDB();
        userDB.update(user);
    }
    
    public void deleteUser(String email) throws Exception   {
        UserDB userDB = new UserDB();
        userDB.delete(email);
    }

    public void insert(String email, String firstName, String lastName, String password, int roleId) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    public void update(String email, String firstName, String lastName, String password, int roleId) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    public void delete(String email) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
    
}
