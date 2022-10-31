/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataaccess;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import models.Role;
import models.User;



/**
 *
 * @author manav
 */
public class UserDB {

    
    public List<User> getAll() throws Exception {
        
        List<User> users = new ArrayList<>();
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection con = cp.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String sql = "select email, first_name, last_name, password, role_id, role_name from user u, role r where (u.role = r.role_id)";
        
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                String email = rs.getString(1);        
                String firstname = rs.getString(2);
                String lastname = rs.getString(3);
                String password = rs.getString(4);
                Role role = new Role(rs.getInt(5), rs.getString(6));
                User user = new User(email, firstname, lastname, password, role);
                users.add(user);
            }
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            cp.freeConnection(con);
        }

        return users;
    }
    
    public User get(String email) throws Exception   {
        
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection con = cp.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        User user = null;
        
        String sql = "select first_name, last_name, password, role_id, role_name from user u join role r on (r.role_id = u.role) where email=?";
        
        ps = con.prepareStatement(sql);
        ps.setString(1, email);
        rs = ps.executeQuery();
        if (rs.next()) {
            
            String firstname = rs.getString(1);
            String lastname = rs.getString(2);
            String password = rs.getString(3);
            Role role = new Role(rs.getInt(4), rs.getString(5));
            user = new User(email, firstname, lastname, password, role);
        }

        DBUtil.closeResultSet(rs);
        DBUtil.closePreparedStatement(ps);
        cp.freeConnection(con);

        return user;
    }
    
    public void insert(User user) throws Exception {
        
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection con = cp.getConnection();
        PreparedStatement ps = null;
        String sql = "INSERT INTO user (email,first_name,last_name,password,role) VALUES (?, ?, ?, ?, ?)";
        
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getFirstname());
            ps.setString(3, user.getLastname());
            ps.setString(4, user.getPassword());
            ps.setInt(5, user.getRole().getRoleID());
            ps.executeUpdate();
        } finally {
            DBUtil.closePreparedStatement(ps);
            cp.freeConnection(con);
        }
    }

    public void update(User user) throws Exception {
        
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection con = cp.getConnection();
        PreparedStatement ps = null;
        String sql = "update user set first_name=?, last_name=?, password=?, role=? where email=?";
        
        try {
            ps = con.prepareStatement(sql);
            
            ps.setString(1, user.getFirstname());
            ps.setString(2, user.getLastname());
            ps.setString(3, user.getPassword());
            ps.setInt(4, user.getRole().getRoleID());
            ps.setString(5, user.getEmail());
            ps.executeUpdate();
            
        } finally {
            DBUtil.closePreparedStatement(ps);
            cp.freeConnection(con);
        }
    }
    
    public void delete(String email) throws Exception  {
        
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection con = cp.getConnection();
        PreparedStatement ps = null;
        String sql = "delete from user where email=?";
        
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, email);
            ps.executeUpdate();
            
        } finally {
            
            DBUtil.closePreparedStatement(ps);
            cp.freeConnection(con);
        }
    }
}

