package occamsrazor.iot_server.dao;

import occamsrazor.iot_server.domain.ClientUser;

import java.util.List;

/**
 * @author : 鱼摆摆
 * @date : Create at 2019-06-16
 * @time : 14:41
 */
public interface ClientUserDao {
    public ClientUser findByClientId(String client_id);

    public ClientUser findByUsername(String username);

    public String findUsernameByClientId(String client_id);

    public String findClientIdByUsername(String username);

    public List<ClientUser> findAllClientUser();

    public boolean insertClientUser(ClientUser clientUser);
}
