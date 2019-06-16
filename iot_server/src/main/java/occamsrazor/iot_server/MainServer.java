package occamsrazor.iot_server;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MqttDefaultFilePersistence;

/**
 * @author : 鱼摆摆
 * @date : Create at 2019-06-16
 * @time : 9:59
 */
public class MainServer {
    private static String host = "tcp://119.23.61.148:61613";
    private static String userName = "admin";
    private static String passWord = "password";
    private static MqttClient client;
    //本次测试订阅的主题：test
    private static String topicStr = "test";

    public static void main(String[] args) throws MqttException {
        init();
        while (true) {
            try {
                Thread.sleep(5000);
                pushMsg("客户端发送：" + System.currentTimeMillis());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void init() throws MqttException {
        //host为主机名，test为clientid即连接MQTT的客户端ID，一般以客户端唯一标识符表示，
        //MemoryPersistence设置clientid的保存形式，默认为以内存保存
        if (client == null) {
            client = new MqttClient(host, "CallbackClient", new MqttDefaultFilePersistence());
        }
        MqttConnectOptions options = new MqttConnectOptions();
        //设置是否清空session,这里如果设置为false表示服务器会保留客户端的连接记录，
        //这里设置为true表示每次连接到服务器都以新的身份连接
        options.setCleanSession(false);
        //设置连接的用户名
        options.setUserName(userName);
        //设置连接的密码
        options.setPassword(passWord.toCharArray());
        // 设置超时时间 单位为秒
        options.setConnectionTimeout(10);
        // 设置会话心跳时间 单位为秒 服务器会每隔1.5*20秒的时间向客户端发送个消息判断客户端是否在线，但这个方法并没有重连的机制
        options.setKeepAliveInterval(20);
        //回调
        client.setCallback(new MqttCallback() {
            public void messageArrived(String topicName, MqttMessage message) throws Exception {
                //subscribe后得到的消息会执行到这里面
                System.out.println("messageArrived----------");
                System.out.println(topicName + "---" + message.toString());
            }

            public void deliveryComplete(IMqttDeliveryToken token) {
                //publish后会执行到这里
                System.out.println("deliveryComplete---------"
                        + token.isComplete());
            }

            public void connectionLost(Throwable cause) {
                // //连接丢失后，一般在这里面进行重连
                System.out.println("connectionLost----------");
                try {
                    init();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        //链接
        client.connect(options);
        //订阅
        client.subscribe(topicStr, 1);
        //取消订阅
        //client.unsubscribe(topicStr);

    }

    //自己写发布消息的方法，然后循环调用。
    public static void pushMsg(String msg) {
        MqttMessage m = new MqttMessage();
        m.setRetained(true);
        m.setPayload(msg.getBytes());
        try {
            client.publish("test", m);
        } catch (Exception e) {
            System.out.println("发布消息失败-->" + msg);
            e.printStackTrace();
        }

    }
}

