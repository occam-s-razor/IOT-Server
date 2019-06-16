package occamsrazor.iot_server.domain;

/**
 * @author : 鱼摆摆
 * @date : Create at 2019-06-16
 * @time : 13:40
 */
public class ClientUser {
    private String client_id;
    private String username;

    public ClientUser() {
    }

    public ClientUser(String client_id, String username) {
        this.client_id = client_id;
        this.username = username;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "ClientUser{" +
                "client_id='" + client_id + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
