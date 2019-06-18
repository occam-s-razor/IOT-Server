package occamsrazor.iot_server.mqtt;

import occamsrazor.iot_server.domain.ClientUser;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

/**
 * @author : 鱼摆摆
 * @date : Create at 2019-06-16
 * @time : 16:21
 */
public class MQTTPublish implements MqttCallback {
    private static final String BROKER = "tcp://119.23.61.148:61613";
    private static final String CLIENT_ID = "MQTT_PUB_CLIENT_SERVER";
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "password";
    private static final int QOS = 1;

    private MqttClient client = null;
    private MqttMessage message = null;

    public MQTTPublish() {
        client = getMqttClient();
        if (client != null) {
            client.setCallback(this);
        }
    }

    @Override
    public void connectionLost(Throwable throwable) {
        System.out.println("publish connect lost.reconnecting......");
        try {
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
//        message = new String(mqttMessage.getPayload());
//        System.out.println("From topic: " + s);
//        System.out.println("Message content: " + message);
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
        System.out.println("message send success");
    }

    private MqttClient getMqttClient() {
        try {
            MqttClient pubClient = new MqttClient(BROKER, CLIENT_ID, new MemoryPersistence());
            MqttConnectOptions connectOptions = new MqttConnectOptions();
            connectOptions.setUserName(USERNAME);
            connectOptions.setPassword(PASSWORD.toCharArray());
            connectOptions.setWill("lwt", "this is a will message".getBytes(), 1, false);
            connectOptions.setCleanSession(true);
            System.out.println("publish connecting to broker: " + BROKER);
            pubClient.connect(connectOptions);
            return pubClient;
        } catch (MqttException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void publish(String topic, String content) throws MqttException {
        if (message == null) {
            message = new MqttMessage(content.getBytes());
            message.setQos(QOS);
        }
        if (client != null) {
//            if (!client.isConnected()) {
//                client.reconnect();
//            }
            client.publish(topic, message);
        }
    }
}
