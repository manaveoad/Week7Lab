<%-- 
    Document   : users
    Created on : 30-Oct-2022, 4:32:13 PM
    Author     : manav
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Users</title>
    </head>
    <body>
        <h1>Manage Users</h1>
        <c:choose>
            <c:when test="${isEmpty eq 'true'}">
                <h3>No users found. Please add a user.</h3>
            </c:when>
            <c:otherwise>
                <table border="1">
                <tr><th>Email</th><th>First Name</th><th>Last Name</th><th>Role</th><th></th><th></th></tr>
                <c:forEach items="${users}" var="user">
                <tr>
                    <td>${user.email}</td><td>${user.firstname}</td><td>${user.lastname}</td><td>${user.role.roleName}</td>
                    <td><a href="<c:url value="user">
                               <c:param name="action" value="edit"/>
                               <c:param name="userEmail" value="${user.email}"/>
                           </c:url>">Edit</a></td>
                    <td><a href="<c:url value="user">
                               <c:param name="action" value="delete"/>
                               <c:param name="userEmail" value="${user.email}"/>
                           </c:url>">Delete</a></td>
                </tr>
                </c:forEach>
            </table>
            </c:otherwise>
        </c:choose>
        <c:if test="${message eq null}">
        <h2>Add User</h2>
        <form action="user" method="post">
            Email: <input type="text" name="email" value="${user.email}"><br>
            First name: <input type="text" name="firstname" value="${user.firstname}"><br>
            Last name: <input type="text" name="lastname" value="${user.lastname}"><br>
            Password: <input type="password" name="password" value="${user.password}"><br>
            <label for="role">Role:</label>
                <select id="role" name="role">
                    <c:forEach items="${roles}" var="role">
                        <option value="${role.roleID}"
                                <c:if test="${user.role.roleID == role.roleID}"> selected</c:if>
                        >${role.roleName}</option>
                    </c:forEach>
                </select>
            <input type="hidden" name="action" value="add"><br>
            <input type="submit" value="Add user"><br>
            <div>${errorMessage}</div>
        </form>
        </c:if>
        <c:if test="${message eq 'edit'}">
        <h2>Edit User</h2>
        <form action="user" method="post">
            Email: ${user.email}<br>
            First name: <input type="text" name="firstname" value="${user.firstname}"><br>
            Last name: <input type="text" name="lastname" value="${user.lastname}"><br>
            Password: <input type="text" name="password" value=""><br>
            <label for="role">Role: </label>
                <select id="role" name="role">
                    <c:forEach items="${roles}" var="role">
                        <option value="${role.roleID}"
                                <c:if test="${user.role.roleID == role.roleID}"> selected</c:if>
                        >${role.roleName}</option>
                    </c:forEach>
                </select>
            <input type="hidden" name="action" value="update">
            <input type="hidden" name="email" value="${user.email}"><br>
            <input type="submit" value="Update">
        </form>
        <form action="user" method="post">
            <input type="hidden" name="action" value="cancel">
            <input type="submit" value="Cancel">
        </form>
        <div>${errorMessage}</div>
        </c:if>
    </body>
</html>