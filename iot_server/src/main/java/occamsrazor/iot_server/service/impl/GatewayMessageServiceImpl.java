package occamsrazor.iot_server.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import occamsrazor.iot_server.mqtt.MQTTPublish;
import occamsrazor.iot_server.service.GatewayMessageService;
import occamsrazor.iot_server.utils.ReadWriteLock;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.concurrent.*;

/**
 * @author : 鱼摆摆
 * @date : Create at 2019-06-17
 * @time : 14:58
 */
public class GatewayMessageServiceImpl implements GatewayMessageService {

    // 创建线程池
    ExecutorService service = new ThreadPoolExecutor(10, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>(), Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());

    // 创建MQTT消息发布对象
    MQTTPublish publish = new MQTTPublish();

    // 读写锁
    ReadWriteLock lock = new ReadWriteLock();

    @Override
    public void messageHandle(MqttMessage mqttMessage) {
        String msg = new String(mqttMessage.getPayload());
        final JSONObject jsonObject = JSON.parseObject(msg);
        String type = jsonObject.getString("type");
        System.out.println("type:" + type);
        if ("dashboard".equals(type)) {
            service.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        lock.lockRead();
                        dashboardHandle(jsonObject);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        lock.unlockRead();
                    }
                }
            });
        } else {
            System.out.println("error");
        }
    }

    @Override
    public void dashboardHandle(JSONObject jsonObject) {
        String gatewayId = jsonObject.getString("gateway_id");
        JSONObject object = jsonObject.getJSONObject("values");
        
    }
}
