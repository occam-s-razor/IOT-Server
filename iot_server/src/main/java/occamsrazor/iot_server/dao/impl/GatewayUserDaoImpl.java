package occamsrazor.iot_server.dao.impl;

import occamsrazor.iot_server.dao.GatewayUserDao;
import occamsrazor.iot_server.domain.GatewayUser;
import occamsrazor.iot_server.utils.DruidUtil;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.List;

/**
 * @author : 鱼摆摆
 * @date : Create at 2019-06-16
 * @time : 13:45
 */
public class GatewayUserDaoImpl implements GatewayUserDao {
    private QueryRunner runner = new QueryRunner(DruidUtil.getDataSource());

    @Override
    public GatewayUser findByGatewayId(String gateway_id) {
        String sql = "select * from gateway_user where gateway_id = ?;";

        GatewayUser user = null;

        try {
            user = runner.query(sql, new BeanHandler<>(GatewayUser.class), gateway_id);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    @Override
    public GatewayUser findByUsername(String username) {

        String sql = "select * from gateway_user where username = ?;";

        GatewayUser user = null;

        try {
            user = runner.query(sql, new BeanHandler<>(GatewayUser.class), username);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    @Override
    public List<GatewayUser> findAllGatewayUser() {
        String sql = "select * from gateway_user;";

        List<GatewayUser> users = null;

        try {
            users = runner.query(sql, new BeanListHandler<>(GatewayUser.class));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    @Override
    public boolean insertGatewayUser(GatewayUser gatewayUser) {
        String sql = "insert into gateway_user(gateway_id, username) value(?, ?);";

        int res = 0;

        try {
            res = runner.update(sql, gatewayUser.getGateway_id(), gatewayUser.getUsername());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return res > 0;
    }
}
