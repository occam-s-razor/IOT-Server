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
}
