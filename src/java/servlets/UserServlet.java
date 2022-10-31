/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.Role;
import models.User;
import services.RoleService;
import services.UserService;


/**
 *
 * @author manav
 */
public class UserServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        UserService us = new UserService();
        RoleService rs = new RoleService();
        
        String action = request.getParameter("action");
        
        String message = null;
        User user = null;
        String userEmail = null;
        List<User> users = null;
        List<Role> roles = null;
        
        try {
            
            users = us.getAll();
            roles = rs.getAll();
            
            request.setAttribute("users", users);
            request.setAttribute("roles", roles);

            switch (action) {
                case "edit":
                    message = "edit";
                    request.setAttribute("message", message);
                    userEmail = request.getParameter("userEmail");
                    user = us.get(userEmail);
                    request.setAttribute("user", user);
                    break;
                case "delete":
                    userEmail = request.getParameter("userEmail");
                    us.deleteUser(userEmail);
                    break;
            }

        } catch (NullPointerException e)    {
            
        } catch (Exception ex) {
            
            Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("message", "error");
            
        } finally   {
            
            try {    
                users = us.getAll();
            } catch (Exception ex) {
                Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            request.setAttribute("users", users);
            
            String isEmpty = ((users.isEmpty()) ? "true" : "false");
            request.setAttribute("isEmpty", isEmpty);
            
            getServletContext().getRequestDispatcher("/WEB-INF/users.jsp").forward(request, response);
        }

    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        UserService us = new UserService();
        RoleService rs = new RoleService();
        
        List<User> users = null;
        List<Role> roles = null;
        String action = request.getParameter("action");

        String message = null;
        String errorMessage = null;
        
        Role role = null;
        User user = null;
        
        try {
            
            String email = request.getParameter("email");
            String firstname = request.getParameter("firstname");
            String lastname = request.getParameter("lastname");
            String password = request.getParameter("password");
            int roleID = Integer.parseInt(request.getParameter("role"));

            role = rs.getRole(roleID);
            
            user = new User(email, firstname, lastname, password, role);
            
            if (email.isEmpty() || firstname.isEmpty() || lastname.isEmpty() || password.isEmpty()) {
                System.out.println("Input cannot be empty");
            }
            
            switch (action) {
                case "add":
                    us.addUser(user);
                    break;
                case "update":
                    us.updateUser(user);
                    break;
            }
            
        } catch (SQLException ex)   {
            
            errorMessage = "User already exists.";
            request.setAttribute("errorMessage", errorMessage);
            request.setAttribute("user", user);
                  
        } catch (Exception ex) {
            
            Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
            
        }  finally  {
            
            try {
                users = us.getAll();
                roles = rs.getAll();
                String isEmpty = ((users.isEmpty()) ? "true" : "false");

                request.setAttribute("isEmpty", isEmpty);
                request.setAttribute("users", users);
                request.setAttribute("roles", roles);
                
                getServletContext().getRequestDispatcher("/WEB-INF/users.jsp").forward(request, response);
            } catch (Exception ex) {
                Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        
        
    }

    
}
