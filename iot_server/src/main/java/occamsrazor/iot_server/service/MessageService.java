package occamsrazor.iot_server.service;

import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * @author : 鱼摆摆
 * @date : Create at 2019-06-16
 * @time : 15:24
 */
public interface MessageService {
    public MqttMessage messageHandle(MqttMessage mqttMessage);
}
