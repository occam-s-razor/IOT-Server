package occamsrazor.iot_server.dao.impl;

import occamsrazor.iot_server.dao.ClientUserDao;
import occamsrazor.iot_server.domain.ClientUser;
import occamsrazor.iot_server.utils.DruidUtil;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.List;

/**
 * @author : 鱼摆摆
 * @date : Create at 2019-06-16
 * @time : 14:42
 */
public class ClientUserDaoImpl implements ClientUserDao {
    private QueryRunner runner = new QueryRunner(DruidUtil.getDataSource());

    @Override
    public ClientUser findByClientId(String client_id) {
        String sql = "select * from client_user where client_id = ?;";

        ClientUser user = null;

        try {
            user = runner.query(sql, new BeanHandler<>(ClientUser.class), client_id);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    @Override
    public ClientUser findByUsername(String username) {
        String sql = "select * from client_user where username = ?;";

        ClientUser user = null;

        try {
            user = runner.query(sql, new BeanHandler<>(ClientUser.class), username);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    @Override
    public String findUsernameByClientId(String client_id) {
        String sql = "select username from client_user where client_id = ? limit 1;";

        String username = null;

        try {
            username = runner.query(sql, new BeanHandler<>(String.class), client_id);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return username;
    }

    @Override
    public String findClientIdByUsername(String username) {
        String sql = "select client_id from client_user where username = ? limit 1;";

        String clientId = null;

        try {
            clientId = runner.query(sql, new BeanHandler<>(String.class), username);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return clientId;
    }

    @Override
    public List<ClientUser> findAllClientUser() {
        String sql = "select * from client_user;";

        List<ClientUser> users = null;

        try {
            users = runner.query(sql, new BeanListHandler<>(ClientUser.class));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    @Override
    public boolean insertClientUser(ClientUser clientUser) {
        String sql = "insert into client_user(client_id, username) VALUE(?, ?);";

        int res = 0;

        try {
            res = runner.update(sql, clientUser.getClient_id(), clientUser.getUsername());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return res > 0;
    }
}
