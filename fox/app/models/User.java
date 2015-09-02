package models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by quarles on 9/1/2015.
 */
public class User {
    public String loginName;
    public String password;
    public boolean isAdmin;
    public List<Site> sites;
    private static List<User> users;
    public static final String DEFAULT_PASSWORD = "P@$$word100";

    public User(String loginName, String password, boolean isAdmin) {
        this.loginName = loginName;
        this.password = password;
        this.isAdmin = isAdmin;
    }

    public User createUser(String username, String password, boolean admin) {
        if (isAdmin) {
            return new User(username, password, admin);
        }
        return null;
    }

    public static User findByLoginName(String login) {
        for (User u: users) {
            if (u.loginName.toLowerCase().equals(login.toLowerCase())) {
                return u;
            }
        }
        return null;
    }

    public  void resetUserPassword(User user) {
        if (isAdmin) {
            user.password = DEFAULT_PASSWORD;
        }
    }

    public List<User> findAll() {
        return new ArrayList<>(users);
    }
}
