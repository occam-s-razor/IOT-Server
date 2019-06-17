package occamsrazor.iot_server.dao;

import occamsrazor.iot_server.domain.User;

import java.util.List;

/**
 * @author : 鱼摆摆
 * @date : Create at 2019-06-16
 * @time : 10:52
 */
public interface UserDao {
    public User findByUserName(String username);

    public List<User> findAllUser();

    public boolean insertUser(User user);

    public boolean insertUser(String username, String password);

    public boolean isCrrect(String username, String password);

    public boolean updateUser(User user);

    public boolean updateUser(String username, String password);
}
