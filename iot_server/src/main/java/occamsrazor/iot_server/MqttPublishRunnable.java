package occamsrazor.iot_server;

import occamsrazor.iot_server.mqtt.MQTTPublish;
import org.eclipse.paho.client.mqttv3.MqttException;

import java.util.Locale;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author : 鱼摆摆
 * @date : Create at 2019-06-17
 * @time : 10:25
 */
public class MqttPublishRunnable implements Runnable {
    private MQTTPublish mqttPublish;

    public MqttPublishRunnable() {
    }

    public MqttPublishRunnable(MQTTPublish mqttPublish) {
        this.mqttPublish = mqttPublish;
    }

    @Override
    public void run() {
        try {
            System.out.println("当前线程：" + Thread.currentThread().getName());
            System.out.println("publish client thread run");
            while (true) {
                Thread.sleep(1000);
                try {
                    System.out.println("pulish,topic:client_conversation,content:{\"test\":\"test\"}");
                    mqttPublish.publish("client_conversation", "{\"test\":\"test\"}");
                } catch (MqttException e) {
                    e.printStackTrace();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
