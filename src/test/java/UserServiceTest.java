package com.projectweb.ProjectWeb.service;

import com.projectweb.ProjectWeb.dao.UserDao;
import com.projectweb.ProjectWeb.model.User_Entity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserDao userDao;

    @BeforeEach
    public void setUp() {
        // Chuẩn bị dữ liệu trước khi mỗi test chạy
        User_Entity user = new User_Entity();
        user.setID_USER("12345");
        user.setEMAIL_ACC("test@example.com");
        user.setPASSWORD_ACC("password");
        user.setSALT("salt");
        user.setNAME_USER("Test User");
        user.setDATE_JOIN(LocalDateTime.now());
        user.setROLE_ACC("USER");
        userDao.createUser(user);
    }

    @Test
    public void testRegisterNewUser() {
        User_Entity newUser = new User_Entity();
        newUser.setEMAIL_ACC("newuser@example.com");
        newUser.setPASSWORD_ACC("newpassword");
        newUser.setNAME_USER("New User");

        User_Entity savedUser = userService.registerNewUser(newUser);
        assertNotNull(savedUser);
        assertEquals("newuser@example.com", savedUser.getEMAIL_ACC());
    }

    @Test
    public void testLogin() {
        boolean loginSuccess = userService.login("test@example.com", "passwordsalt");
        assertTrue(loginSuccess);
    }

    @Test
    public void testGetUsersByID() {
        User_Entity user = userService.getUsersByID("12345");
        assertNotNull(user);
        assertEquals("test@example.com", user.getEMAIL_ACC());
    }
}
