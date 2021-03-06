package occamsrazor.iot_server.dao;

import occamsrazor.iot_server.domain.GatewayUser;

import java.util.List;

/**
 * @author : 鱼摆摆
 * @date : Create at 2019-06-16
 * @time : 13:41
 */
public interface GatewayUserDao {

    public GatewayUser findByGatewayId(String gateway_id);

    public GatewayUser findByUsername(String username);

    public String findGatewayIdByUsername(String username);

    public String findUsernameByGatewayId(String gatewayId);

    public List<GatewayUser> findAllGatewayUser();

    public boolean insertGatewayUser(GatewayUser gatewayUser);
}
