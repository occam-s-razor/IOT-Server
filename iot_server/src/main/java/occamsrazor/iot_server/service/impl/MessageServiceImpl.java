package occamsrazor.iot_server.service.impl;

import occamsrazor.iot_server.mqtt.MQTTPublish;
import occamsrazor.iot_server.service.MessageService;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.concurrent.*;

/**
 * @author : 鱼摆摆
 * @date : Create at 2019-06-16
 * @time : 15:24
 */
public class MessageServiceImpl implements MessageService {

    // 创建线程池
    ExecutorService service = new ThreadPoolExecutor(10, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>(), Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());

    // 创建MQTT消息发布对象
    MQTTPublish publish = new MQTTPublish();

    @Override
    public MqttMessage messageHandle(MqttMessage mqttMessage) {
        return null;
    }
}
