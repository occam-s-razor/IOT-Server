package occamsrazor.iot_server.runnable;

import occamsrazor.iot_server.mqtt.MQTTPublish;
import org.eclipse.paho.client.mqttv3.MqttException;


/**
 * @author : 鱼摆摆
 * @date : Create at 2019-06-17
 * @time : 10:25
 */
public class MqttPublishRunnable implements Runnable {
    private MQTTPublish mqttPublish;

    private String topic;
    private String msg;

    public MqttPublishRunnable() {
    }

    public MqttPublishRunnable(MQTTPublish mqttPublish) {
        this.mqttPublish = mqttPublish;
    }

    public MqttPublishRunnable(MQTTPublish mqttPublish, String topic, String msg) {
        this.mqttPublish = mqttPublish;
        this.topic = topic;
        this.msg = msg;
    }

    @Override
    public void run() {
        try {
            mqttPublish.publish(topic, msg);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public MQTTPublish getMqttPublish() {
        return mqttPublish;
    }

    public void setMqttPublish(MQTTPublish mqttPublish) {
        this.mqttPublish = mqttPublish;
    }
}
