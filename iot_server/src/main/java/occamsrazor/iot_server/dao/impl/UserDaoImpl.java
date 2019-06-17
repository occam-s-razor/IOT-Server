package occamsrazor.iot_server.dao.impl;

import occamsrazor.iot_server.dao.UserDao;
import occamsrazor.iot_server.domain.User;
import occamsrazor.iot_server.utils.DruidUtil;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.List;

/**
 * @author : 鱼摆摆
 * @date : Create at 2019-06-16
 * @time : 10:52
 */
public class UserDaoImpl implements UserDao {

    private QueryRunner runner = new QueryRunner(DruidUtil.getDataSource());

    @Override
    public User findByUserName(String username) {
        String sql = "select * from user where username=?;";

        User user = null;

        try {
            user = runner.query(sql, new BeanHandler<>(User.class), username);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    @Override
    public List<User> findAllUser() {
        String sql = "select * from user;";

        List<User> users = null;

        try {
            users = runner.query(sql, new BeanListHandler<>(User.class));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    @Override
    public boolean insertUser(User user) {
        String sql = "insert into user (username, password) values (?, ?);";

        int res = 0;

        try {
            res = runner.update(sql, user.getUsername(), user.getPassword());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return res > 0;
    }
}
