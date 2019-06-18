package occamsrazor.iot_server.test;

import occamsrazor.iot_server.dao.ClientUserDao;
import occamsrazor.iot_server.dao.GatewayUserDao;
import occamsrazor.iot_server.dao.impl.ClientUserDaoImpl;
import occamsrazor.iot_server.dao.impl.GatewayUserDaoImpl;
import occamsrazor.iot_server.dao.impl.UserDaoImpl;
import occamsrazor.iot_server.dao.UserDao;
import occamsrazor.iot_server.domain.ClientUser;
import occamsrazor.iot_server.domain.GatewayUser;
import occamsrazor.iot_server.domain.User;
import occamsrazor.iot_server.mqtt.MQTTSubscribe;
import occamsrazor.iot_server.utils.EncryptionUtil;
import org.junit.jupiter.api.Test;

import java.util.Date;

/**
 * @author : 鱼摆摆
 * @date : Create at 2019-06-16
 * @time : 13:25
 */
public class MyTest {

    @Test
    public void Test1() {
        UserDao userDao = new UserDaoImpl();
        GatewayUserDao gatewayUserDao = new GatewayUserDaoImpl();
        ClientUserDao clientUserDao = new ClientUserDaoImpl();

//        boolean x = userDao.insertUser(new User("FisherCloud", EncryptionUtil.getInstance().MD5("woshiyuxin")));
//
//        if (x) {
//            System.out.println("success");
//        } else {
//            System.out.println("failed");
//        }

        User user = userDao.findByUserName("FisherCloud");
        System.out.println(user + "\t" + EncryptionUtil.getInstance().MD5("woshiyuxin").equals(user.getPassword()));
        GatewayUser gatewayUser = gatewayUserDao.findByUsername("FisherCloud");
        System.out.println(gatewayUser);
        ClientUser clientUser = clientUserDao.findByUsername("FisherCloud");
        System.out.println(clientUser);
    }

    @Test
    public void Test2() {
        MQTTSubscribe subscribe = new MQTTSubscribe("gateway_conversation");
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void Test3() {
        UserDao userDao = new UserDaoImpl();
        if (userDao.updateUser("FisherCloud", EncryptionUtil.getInstance().MD5("123456"))) {
            System.out.println("success");
        } else {
            System.out.println("failed");
        }
    }

    @Test
    public void Test4() {
        Date date = new Date();
        System.out.println(date.getTime());
    }
}
