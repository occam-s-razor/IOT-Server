package occamsrazor.iot_server.domain;

/**
 * @author : 鱼摆摆
 * @date : Create at 2019-06-16
 * @time : 13:38
 */
public class GatewayUser {
    private String gateway_id;
    private String username;

    public GatewayUser() {
    }

    public GatewayUser(String gateway_id, String username) {
        this.gateway_id = gateway_id;
        this.username = username;
    }

    public String getGateway_id() {
        return gateway_id;
    }

    public void setGateway_id(String gateway_id) {
        this.gateway_id = gateway_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "GatewayUser{" +
                "gateway_id='" + gateway_id + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
