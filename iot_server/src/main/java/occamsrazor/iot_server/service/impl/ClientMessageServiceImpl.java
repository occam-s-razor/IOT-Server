package occamsrazor.iot_server.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mysql.cj.xdevapi.JsonArray;
import occamsrazor.iot_server.dao.ClientUserDao;
import occamsrazor.iot_server.dao.GatewayUserDao;
import occamsrazor.iot_server.dao.UserDao;
import occamsrazor.iot_server.dao.impl.ClientUserDaoImpl;
import occamsrazor.iot_server.dao.impl.GatewayUserDaoImpl;
import occamsrazor.iot_server.dao.impl.UserDaoImpl;
import occamsrazor.iot_server.domain.ClientUser;
import occamsrazor.iot_server.domain.GatewayUser;
import occamsrazor.iot_server.domain.User;
import occamsrazor.iot_server.mqtt.MQTTPublish;
import occamsrazor.iot_server.runnable.MqttPublishRunnable;
import occamsrazor.iot_server.service.ClientMessageService;
import occamsrazor.iot_server.utils.EncryptionUtil;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

/**
 * @author : 鱼摆摆
 * @date : Create at 2019-06-16
 * @time : 15:24
 */
public class ClientMessageServiceImpl implements ClientMessageService {

    // 创建线程池
    ExecutorService service = new ThreadPoolExecutor(10, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>(), Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());

    // 创建MQTT消息发布对象
    MQTTPublish publish = new MQTTPublish();

    @Override
    public void messageHandle(MqttMessage mqttMessage) {
        String msg = new String(mqttMessage.getPayload());
        final JSONObject jsonObject = JSON.parseObject(msg);
        String type = jsonObject.getString("type");
        System.out.println("type:" + type);
        switch (type) {
            case "login":
                service.execute(new Runnable() {
                    @Override
                    public void run() {
                        loginHandle(jsonObject);
                    }
                });
                break;
            case "modify":
                service.execute(new Runnable() {
                    @Override
                    public void run() {
                        modifyHandle(jsonObject);
                    }
                });
                break;
            case "register":
                // registerHandle(jsonObject);
                break;
            case "gateway_control":
                // gatewayControlHandle(jsonObject);
                break;
            default:
                // errorHandle(jsonObject);
                break;
        }
    }

    @Override
    public void loginHandle(JSONObject jsonObject) {
        String clientId = jsonObject.getString("client_id");
        JSONObject object = jsonObject.getJSONObject("msg");
        String username = object.getString("username");
        String password = object.getString("password");

        UserDao userDao = new UserDaoImpl();
        ClientUserDao clientUserDao = new ClientUserDaoImpl();
        GatewayUserDao gatewayUserDao = new GatewayUserDaoImpl();

        GatewayUser gatewayUser = null;
        ClientUser clientUser = null;

        Map<String, String> resMsg = new HashMap<>();
        resMsg.put("type", "login_return");

        if (userDao.isCrrect(username, EncryptionUtil.getInstance().MD5(password))) {
            clientUser = clientUserDao.findByUsername(username);
            if (clientUser == null) {
                clientUser = new ClientUser(clientId, username);
                clientUserDao.insertClientUser(clientUser);
            }
            gatewayUser = gatewayUserDao.findByUsername(username);
            if (null != gatewayUser) {
                resMsg.put("status", "ok");
                resMsg.put("gateway_id", gatewayUser.getGateway_id());
            } else {
                resMsg.put("status", "error");
            }
        } else {
            resMsg.put("status", "error");
        }

        // 发布
        try {
            publish.publish(clientId + "_conversation", JSON.toJSONString(resMsg));
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void modifyHandle(JSONObject jsonObject) {
        String clientId = jsonObject.getString("client_id");
        JSONObject object = jsonObject.getJSONObject("msg");
        String username = object.getString("username");
        String password = object.getString("password");
        String newpassword = object.getString("newpassword");

        UserDao userDao = new UserDaoImpl();
        ClientUserDao clientUserDao = new ClientUserDaoImpl();

        ClientUser clientUser = null;

        Map<String, String> resMsg = new HashMap<>();
        resMsg.put("type", "modify_return");

        if (userDao.isCrrect(username, EncryptionUtil.getInstance().MD5(password))) {

            clientUser = clientUserDao.findByUsername(username);
            if (clientUser == null) {
                clientUser = new ClientUser(clientId, username);
                clientUserDao.insertClientUser(clientUser);
            }
            resMsg.put("client_id", clientId);

            if (newpassword != null && userDao.updateUser(username, EncryptionUtil.getInstance().MD5(newpassword))) {
                resMsg.put("status", "ok");
            } else {
                resMsg.put("status", "error");
            }

        } else {
            resMsg.put("status", "error");
        }

        // 发布
        try {
            publish.publish(clientId + "_conversation", JSON.toJSONString(resMsg));
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void registerHandle(JSONObject jsonObject) {

    }

    @Override
    public void gatewayControlHandle(JSONObject jsonObject) {

    }

    @Override
    public void errorHandle() {

    }
}
