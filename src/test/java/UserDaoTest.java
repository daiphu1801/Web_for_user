import com.projectweb.ProjectWeb.dao.UserDao;
import com.projectweb.ProjectWeb.model.User_Entity;
import com.projectweb.ProjectWeb.service.SecurityFunction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import({UserDao.class, SecurityFunction.class})
public class UserDaoTest {

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
    public void testGetUsersByID() {
        User_Entity user = userDao.getUsersByID("12345");
        assertNotNull(user);
        assertEquals("test@example.com", user.getEMAIL_ACC());
    }

    @Test
    public void testGetUsersByMail() {
        User_Entity user = userDao.getUsersByMail("test@example.com");
        assertNotNull(user);
        assertEquals("Test User", user.getNAME_USER());
    }

    @Test
    public void testIsUserByMail() {
        boolean exists = userDao.isUserByMail("test@example.com");
        assertTrue(exists);
    }
}
