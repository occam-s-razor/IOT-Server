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
