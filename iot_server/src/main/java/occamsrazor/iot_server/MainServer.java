package occamsrazor.iot_server;

import occamsrazor.iot_server.mqtt.MQTTSubscribe;

import java.util.Date;
import java.util.concurrent.*;

/**
 * @author : 鱼摆摆
 * @date : Create at 2019-06-16
 * @time : 9:59
 */
public class MainServer {

    public static void main(String[] args) throws InterruptedException {
        // 创建MQTT消息订阅对象
        MQTTSubscribe subscribe = new MQTTSubscribe(new String[]{"gateway_conversation", "client_conversation"});

        while (true) {
            Thread.sleep(5000);
            // System.out.println("Time:" + new Date());
        }
    }
}
