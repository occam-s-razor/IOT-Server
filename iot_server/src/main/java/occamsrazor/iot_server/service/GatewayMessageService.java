package occamsrazor.iot_server.service;

import com.alibaba.fastjson.JSONObject;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * @author : 鱼摆摆
 * @date : Create at 2019-06-17
 * @time : 14:58
 */
public interface GatewayMessageService {
    public void messageHandle(MqttMessage mqttMessage);
}
