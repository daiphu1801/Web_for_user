package com.projectweb.ProjectWeb.service;

import com.projectweb.ProjectWeb.dao.UserDao;
import com.projectweb.ProjectWeb.model.User_Entity;
import com.projectweb.ProjectWeb.service.SecurityFunction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserService {

    private final UserDao userDao;

    @Autowired
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public User_Entity getUsersByID(String id) {
        return userDao.getUsersByID(id);
    }

    public User_Entity getUsersByEmail(String email) {
        return userDao.getUsersByMail(SecurityFunction.encrypt(email));
    }

    public void registerNewUser(User_Entity user) throws Exception {
        String salt = SecurityFunction.generateSalt();
        user.setSALT(salt);
        String pass = user.getPASSWORD_ACC() + salt;
        pass = SecurityFunction.hashString(pass);
        user.setPASSWORD_ACC(pass);
        user.setID_USER(SecurityFunction.hashString(user.getEMAIL_ACC()));
        LocalDateTime dateJoin = LocalDateTime.now();
        user.setDATE_JOIN(dateJoin);
        encryptUserSensitiveData(user);
        userDao.createUser(user);
    }

    public boolean login(String email, String password) throws Exception {
        String mail = SecurityFunction.encrypt(email);
        if (userDao.isUserByMail(mail)) {
            password += userDao.getUsersByMail(mail).getSALT();
            password = SecurityFunction.hashString(password);
            return userDao.loginValidate(mail, password);
        }
        return false;
    }

    public void updateUserInfo(User_Entity user) throws Exception {
        encryptUserSensitiveData(user);
        userDao.updateUser(
                user.getID_USER(),
                user.getADDRESS(),
                user.getEMAIL_ACC(),
                user.getPHONE_ACC(),
                user.getROLE_ACC(),
                user.getNAME_USER()
        );
    }

    public void changePassword(String email, String newPassword) throws Exception {
        userDao.changePasswordByEmail(SecurityFunction.encrypt(email), newPassword);
    }

    public void encryptUserSensitiveData(User_Entity user) throws Exception {
        if (user.getADDRESS() != null) {
            user.setADDRESS(SecurityFunction.encrypt(user.getADDRESS()));
        }
        if (user.getPHONE_ACC() != null) {
            user.setPHONE_ACC(SecurityFunction.encrypt(user.getPHONE_ACC()));
        }
        if (user.getEMAIL_ACC() != null) {
            user.setEMAIL_ACC(SecurityFunction.encrypt(user.getEMAIL_ACC()));
        }
    }
}
