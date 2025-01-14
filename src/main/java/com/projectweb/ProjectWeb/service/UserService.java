package com.projectweb.ProjectWeb.service;

import com.projectweb.ProjectWeb.dao.UserDao;
import com.projectweb.ProjectWeb.model.User_Entity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserService {

    private final UserDao userDao;
    private final SecurityFunction securityFunction;

    @Autowired
    public UserService(UserDao userDao, SecurityFunction securityFunction) {
        this.userDao = userDao;
        this.securityFunction = securityFunction;
    }

    public User_Entity getUsersByID(String id) {
        return userDao.getUsersByID(id);
    }

    public User_Entity getUsersByEmail(String email) {
        try {
            String encryptedEmail = securityFunction.encrypt(email);
            return userDao.getUsersByMail(encryptedEmail);
        } catch (RuntimeException e) {
            throw new RuntimeException("Error getting user by email", e);
        }
    }

    public User_Entity registerNewUser(User_Entity user) {
        try {
            // Generate salt and hash password
            String salt = securityFunction.generateSalt();
            user.setSALT(salt);
            String pass = user.getPASSWORD_ACC() + salt;
            pass = securityFunction.hashString(pass);
            user.setPASSWORD_ACC(pass);

            // Generate user ID and set join date
            user.setID_USER(securityFunction.hashString(user.getEMAIL_ACC()));
            LocalDateTime dateJoin = LocalDateTime.now();
            user.setDATE_JOIN(dateJoin);

            // Encrypt sensitive user data
            encryptUserSensitiveData(user);

            // Save user to the database
            userDao.createUser(user);
            return user;
        } catch (RuntimeException e) {
            throw new RuntimeException("Error while registering new user", e);
        }
    }

    public boolean login(String email, String password) {
        try {
            String encryptedEmail = securityFunction.encrypt(email);
            if (userDao.isUserByMail(encryptedEmail)) {
                String salt = userDao.getUsersByMail(encryptedEmail).getSALT();
                password = securityFunction.hashString(password + salt);
                return userDao.loginValidate(encryptedEmail, password);
            }
            return false;
        } catch (RuntimeException e) {
            throw new RuntimeException("Login failed", e);
        }
    }

    public void updateUserInfo(User_Entity user) {
        try {
            // Encrypt sensitive data
            encryptUserSensitiveData(user);

            // Update user information
            userDao.updateUser(
                    user.getID_USER(),
                    user.getADDRESS(),
                    user.getEMAIL_ACC(),
                    user.getPHONE_ACC(),
                    user.getROLE_ACC(),
                    user.getNAME_USER()
            );
        } catch (RuntimeException e) {
            throw new RuntimeException("Error updating user information", e);
        }
    }

    public void changePassword(String email, String newPassword) {
        try {
            String encryptedEmail = securityFunction.encrypt(email);
            userDao.changePasswordByEmail(encryptedEmail, newPassword);
        } catch (RuntimeException e) {
            throw new RuntimeException("Error changing password", e);
        }
    }

    private void encryptUserSensitiveData(User_Entity user) {
        try {
            if (user.getADDRESS() != null) {
                user.setADDRESS(securityFunction.encrypt(user.getADDRESS()));
            }
            if (user.getPHONE_ACC() != null) {
                user.setPHONE_ACC(securityFunction.encrypt(user.getPHONE_ACC()));
            }
            if (user.getEMAIL_ACC() != null) {
                user.setEMAIL_ACC(securityFunction.encrypt(user.getEMAIL_ACC()));
            }
        } catch (RuntimeException e) {
            throw new RuntimeException("Error encrypting user sensitive data", e);
        }
    }
}
