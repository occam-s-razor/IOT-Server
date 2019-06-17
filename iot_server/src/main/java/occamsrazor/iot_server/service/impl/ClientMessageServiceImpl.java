package occamsrazor.iot_server.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import occamsrazor.iot_server.mqtt.MQTTPublish;
import occamsrazor.iot_server.service.ClientMessageService;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.concurrent.*;

/**
 * @author : 鱼摆摆
 * @date : Create at 2019-06-16
 * @time : 15:24
 */
public class ClientMessageServiceImpl implements ClientMessageService {

    // 创建线程池
    ExecutorService service = new ThreadPoolExecutor(10, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>(), Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());

    // 创建MQTT消息发布对象
    MQTTPublish publish = new MQTTPublish();

    @Override
    public void messageHandle(MqttMessage mqttMessage) {
        String msg = new String(mqttMessage.getPayload());
        JSONObject object = JSON.parseObject(msg);
        String type = object.getString("type");
        switch (type) {
            case "login":
                System.out.println("login");
//                loginHandle(object);
                break;
            case "modify":
                System.out.println("modify");
                break;
            case "gateway_control":
                System.out.println("gateway_control");
                break;
            default:
                break;
        }
    }

    @Override
    public void loginHandle(JSONObject json) {

    }
}
