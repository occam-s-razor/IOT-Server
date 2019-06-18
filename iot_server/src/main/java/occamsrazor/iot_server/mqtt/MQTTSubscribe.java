package occamsrazor.iot_server.mqtt;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import occamsrazor.iot_server.service.MessageService;
import occamsrazor.iot_server.service.impl.MessageServiceImpl;
import occamsrazor.iot_server.utils.DateUtil;
import occamsrazor.iot_server.utils.JsonUtil;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.util.Arrays;
import java.util.Date;


/**
 * @author : 鱼摆摆
 * @date : Create at 2019-06-16
 * @time : 16:21
 */
public class MQTTSubscribe implements MqttCallback {
    private static final String BROKER = "tcp://119.23.61.148:61613";
    private static final String CLIENT_ID = "MQTT_SUB_CLIENT_SERVER";
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "password";

    private MqttClient client = null;
    private MqttMessage message = null;

    private MessageService messageService = null;

    public MQTTSubscribe() {
    }

    public MQTTSubscribe(String topic) {
        try {
            subscribe(topic);
            Thread.sleep(10);
            messageService = new MessageServiceImpl();
            Thread.sleep(10);
        } catch (MqttException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public MQTTSubscribe(String[] topics) throws InterruptedException {
        subscribe(topics);
        Thread.sleep(10);
        messageService = new MessageServiceImpl();
        Thread.sleep(10);
    }

    public MqttMessage getMessage() {
        return message;
    }

    public void setMessage(MqttMessage message) {
        this.message = message;
    }


    @Override
    public void connectionLost(Throwable throwable) {
        try {
            System.out.println("subscribe connect lost.reconnecting......");
            client.reconnect();
            if (client.isConnected()) {
                System.out.println("reconnected");
            } else {
                System.out.println("reconnect failed");
            }
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
        message = mqttMessage;
        System.out.println("Time:" + DateUtil.DateToString(new Date(), "yyyy-MM-dd HH:mm:ss") + " From topic: " + s);
        try {
            String msg = new String(mqttMessage.getPayload());
            System.out.println(msg);
            String type = new JSONObject(JSON.parseObject(msg)).getString("type");
            if (type == null) {
                System.out.println("data not valid");
                return;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return;
        }
        switch (s) {
            case "gateway_conversation":
                messageService.gatewayMessageHandle(mqttMessage);
                break;
            case "client_conversation":
                messageService.clientMessageHandle(mqttMessage);
                break;
            default:
                break;
        }
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
        //System.out.println("message send success");
    }

    private MqttClient getMqttClient() {
        try {
            MqttClient subClient = new MqttClient(BROKER, CLIENT_ID, new MemoryPersistence());
            MqttConnectOptions connectOptions = new MqttConnectOptions();
            connectOptions.setUserName(USERNAME);
            connectOptions.setPassword(PASSWORD.toCharArray());
            connectOptions.setCleanSession(true);
            System.out.println("subscribe connecting to broker: " + BROKER);
            subClient.connect(connectOptions);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (subClient.isConnected()) {
                subClient.setCallback(this);
                return subClient;
            }
        } catch (MqttException e) {
            e.printStackTrace();
        }
        return null;
    }


    public void subscribe(String topic) throws MqttException {
        if (client == null) {
            client = getMqttClient();
        }
        if (client != null) {
            client.subscribe(topic);
        } else {
            System.out.println("error");
        }
    }

    public void subscribe(String[] topics) {
        if (client == null) {
            client = getMqttClient();
        }
        if (client != null) {
            try {
                client.subscribe(topics);
            } catch (MqttException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("error");
        }
    }

    @Override
    public String toString() {
        return "MQTTSubscribe{" +
                "message=" + message +
                '}';
    }
}
