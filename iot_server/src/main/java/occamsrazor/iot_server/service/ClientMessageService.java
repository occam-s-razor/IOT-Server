package occamsrazor.iot_server.service;

import com.alibaba.fastjson.JSONObject;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * @author : 鱼摆摆
 * @date : Create at 2019-06-16
 * @time : 15:24
 */
public interface ClientMessageService {
    public void messageHandle(MqttMessage mqttMessage);

    public void loginHandle(JSONObject json);
}
