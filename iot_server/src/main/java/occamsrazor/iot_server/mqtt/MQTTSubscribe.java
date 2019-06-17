package occamsrazor.iot_server.mqtt;

import occamsrazor.iot_server.service.ClientMessageService;
import occamsrazor.iot_server.service.GatewayMessageService;
import occamsrazor.iot_server.service.impl.ClientMessageServiceImpl;
import occamsrazor.iot_server.service.impl.GatewayMessageServiceImpl;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;


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

    private ClientMessageService clientMessageService = new ClientMessageServiceImpl();
    private GatewayMessageService gatewayMessageService = new GatewayMessageServiceImpl();

    public MQTTSubscribe() {
    }

    public MQTTSubscribe(String topic) {
        try {
            subscribe(topic);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public MQTTSubscribe(String[] topics) {
        subscribe(topics);
    }

    public MqttMessage getMessage() {
        return message;
    }

    public void setMessage(MqttMessage message) {
        this.message = message;
    }


    @Override
    public void connectionLost(Throwable throwable) {
        System.out.println("Connect lost,do some thing to solve it");
    }

    @Override
    public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
        message = mqttMessage;
        System.out.println("From topic: " + s);
        System.out.println(new String(mqttMessage.getPayload()));
        switch (s) {
            case "gateway_conversation":
                gatewayMessageService.messageHandle(mqttMessage);
                break;
            case "client_conversation":
                clientMessageService.messageHandle(mqttMessage);
                break;
            default:
                break;
        }
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
        //System.out.println("message send success");
        MqttConnectOptions connectOptions = new MqttConnectOptions();
        connectOptions.setUserName(USERNAME);
        connectOptions.setPassword(PASSWORD.toCharArray());
        connectOptions.setCleanSession(true);
        System.out.println("Connecting to broker: " + BROKER);
        try {
            client.connect(connectOptions);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    private MqttClient getMqttClient() {
        try {
            MqttClient subClient = new MqttClient(BROKER, CLIENT_ID, new MemoryPersistence());
            MqttConnectOptions connectOptions = new MqttConnectOptions();
            connectOptions.setUserName(USERNAME);
            connectOptions.setPassword(PASSWORD.toCharArray());
            connectOptions.setCleanSession(true);
            System.out.println("Connecting to broker: " + BROKER);
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
