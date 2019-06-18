package occamsrazor.iot_server.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import occamsrazor.iot_server.dao.ClientUserDao;
import occamsrazor.iot_server.dao.GatewayUserDao;
import occamsrazor.iot_server.dao.SensorsValuesDao;
import occamsrazor.iot_server.dao.UserDao;
import occamsrazor.iot_server.dao.impl.ClientUserDaoImpl;
import occamsrazor.iot_server.dao.impl.GatewayUserDaoImpl;
import occamsrazor.iot_server.dao.impl.SensorsValueDaoImpl;
import occamsrazor.iot_server.dao.impl.UserDaoImpl;
import occamsrazor.iot_server.domain.ClientUser;
import occamsrazor.iot_server.domain.GatewayUser;
import occamsrazor.iot_server.domain.SensorsValue;
import occamsrazor.iot_server.mqtt.MQTTPublish;
import occamsrazor.iot_server.service.MessageService;
import occamsrazor.iot_server.utils.DateUtil;
import occamsrazor.iot_server.utils.EncryptionUtil;
import occamsrazor.iot_server.utils.ReadWriteLock;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

/**
 * @author : 鱼摆摆
 * @date : Create at 2019-06-16
 * @time : 15:24
 */
public class MessageServiceImpl implements MessageService {

    // 创建线程池
    private ExecutorService service = new ThreadPoolExecutor(10, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>(), Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());

    // 创建MQTT消息发布对象
    private MQTTPublish publish = new MQTTPublish();

    // 读写锁
    private ReadWriteLock lock = new ReadWriteLock();

    @Override
    public void clientMessageHandle(MqttMessage mqttMessage) {
        String msg = new String(mqttMessage.getPayload());
        final JSONObject jsonObject = JSON.parseObject(msg);
        String type = jsonObject.getString("type");
        System.out.println("type:" + type);
        if (type == null) {
            errorHandle();
            return;
        }
        switch (type) {
            case "login":
                service.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            lock.lockRead();
                            lock.lockWrite();
                            loginHandle(jsonObject);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } finally {
                            lock.unlockRead();
                            try {
                                lock.unlockWrite();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                });
                break;
            case "modify":
                service.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            lock.lockRead();
                            lock.lockWrite();
                            modifyHandle(jsonObject);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } finally {
                            lock.unlockRead();
                            try {
                                lock.unlockWrite();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
                break;
            case "register":
                service.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            lock.lockRead();
                            lock.lockWrite();
                            registerHandle(jsonObject);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } finally {
                            lock.unlockRead();
                            try {
                                lock.unlockWrite();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
                break;
            case "gateway_control":
                service.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            lock.lockRead();
                            gatewayControlHandle(jsonObject);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } finally {
                            lock.unlockRead();
                        }
                    }
                });
                break;
            default:
                errorHandle();
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
                System.out.println("login error");
            }
        } else {
            resMsg.put("status", "error");
            System.out.println("login error");
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
                System.out.println("modify error");
            }

        } else {
            resMsg.put("status", "error");
            System.out.println("modify error");
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
        System.out.println("register");
    }

    @Override
    public void gatewayControlHandle(JSONObject jsonObject) {
        String clientId = jsonObject.getString("client_id");
        ClientUserDao clientUserDao = new ClientUserDaoImpl();
        GatewayUserDao gatewayUserDao = new GatewayUserDaoImpl();
        String gatewayId = gatewayUserDao.findGatewayIdByUsername(clientUserDao.findUsernameByClientId(clientId));
        jsonObject.remove("client_id");
        jsonObject.remove("type");
        jsonObject.put("type", "client_control");

        try {
            publish.publish(gatewayId + "_conversation", jsonObject.toJSONString());
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    @Override

    public void errorHandle() {
        System.out.println("error");
//        Map<String, String> resMsg = new HashMap<>();
//        resMsg.put("type", "error");
//        resMsg.put("status", "error");
//        publish.publish(clientId + "_conversation", JSON.toJSONString(resMsg));
    }

    @Override
    public void gatewayMessageHandle(MqttMessage mqttMessage) {
        String msg = new String(mqttMessage.getPayload());
        final JSONObject jsonObject = JSON.parseObject(msg);

        String type = jsonObject.getString("type");
        System.out.println("type:" + type);
        if ("dashboard".equals(type)) {
            service.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        lock.lockRead();
                        dashboardHandle(jsonObject);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        lock.unlockRead();
                    }
                }
            });
        } else {
            System.out.println("error");
        }
    }

    @Override
    public void dashboardHandle(JSONObject jsonObject) {
        String gatewayId = jsonObject.getString("gateway_id");
        JSONObject object = jsonObject.getJSONObject("sensors_value");

        SensorsValue sensorsValue = new SensorsValue();
        sensorsValue.setTimeStamp(DateUtil.DateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
        sensorsValue.setGatewayId(gatewayId);
        sensorsValue.setTemperature(object.getString("temperature"));
        sensorsValue.setHumidity(object.getString("humidity"));
        sensorsValue.setFan(object.getString("fan"));
        sensorsValue.setBeam(object.getString("beam"));
        sensorsValue.setLight1(object.getBoolean("light1"));
        sensorsValue.setLight2(object.getBoolean("light2"));

        SensorsValuesDao sensorsValuesDao = new SensorsValueDaoImpl();

        String topic = null;

        if (sensorsValuesDao.insertSensorsValues(sensorsValue)) {
            System.out.println("insert to database success");
            GatewayUserDao gatewayUserDao = new GatewayUserDaoImpl();
            ClientUserDao clientUserDao = new ClientUserDaoImpl();
            String clientId = clientUserDao.findClientIdByUsername(gatewayUserDao.findUsernameByGatewayId(gatewayId));

            topic = clientId + "_conversation";

            try {
                publish.publish(topic, JSON.toJSONString(sensorsValue));
            } catch (MqttException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("insert to database failed");
            topic = gatewayId + "_conversation";

            try {
                publish.publish(topic, "{\"type\":\"error\"}");
            } catch (MqttException e) {
                e.printStackTrace();
            }
        }

    }
}
