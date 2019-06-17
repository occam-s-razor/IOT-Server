package occamsrazor.iot_server;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mysql.cj.x.protobuf.MysqlxDatatypes;
import occamsrazor.iot_server.mqtt.MQTTPublish;
import occamsrazor.iot_server.mqtt.MQTTSubscribe;

import java.util.concurrent.*;

/**
 * @author : 鱼摆摆
 * @date : Create at 2019-06-16
 * @time : 9:59
 */
public class MainServer {

    public static void main(String[] args) throws InterruptedException {
        // 创建线程池
        ExecutorService service = new ThreadPoolExecutor(2, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>(), Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());

        // 创建MQTT消息订阅对象
        MQTTSubscribe subscribe = new MQTTSubscribe(new String[]{"gateway_conversation", "client_conversation"});

        // 将消息订阅加入线程
        service.execute(new MqttSubscribeRunnable(subscribe));

        while (true) {
            Thread.sleep(1000);
        }
    }
}

