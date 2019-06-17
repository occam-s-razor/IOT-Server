package occamsrazor.iot_server.runnable;

import occamsrazor.iot_server.mqtt.MQTTSubscribe;


/**
 * @author : 鱼摆摆
 * @date : Create at 2019-06-17
 * @time : 10:31
 */
public class MqttSubscribeRunnable implements Runnable {
    private MQTTSubscribe mqttSubscribe;

    public MqttSubscribeRunnable() {
    }

    public MqttSubscribeRunnable(MQTTSubscribe mqttSubscribe) {
        this.mqttSubscribe = mqttSubscribe;
    }

    @Override
    public void run() {
        try {
            System.out.println("当前线程：" + Thread.currentThread().getName());
            System.out.println("subscribe client thread run");
            while (true) {
                Thread.sleep(100);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
