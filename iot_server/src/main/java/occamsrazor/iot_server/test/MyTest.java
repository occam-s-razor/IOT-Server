package occamsrazor.iot_server.test;

import occamsrazor.iot_server.dao.Impl.UserDaoImpl;
import occamsrazor.iot_server.dao.UserDao;
import occamsrazor.iot_server.domain.User;
import occamsrazor.iot_server.utils.EncryptionUtil;
import org.junit.jupiter.api.Test;

/**
 * @author : 鱼摆摆
 * @date : Create at 2019-06-16
 * @time : 13:25
 */
public class MyTest {

    @Test
    public void Test1() {
        UserDao userDao = new UserDaoImpl();

        boolean x = userDao.insertUser(new User("FisherCloud", EncryptionUtil.getInstance().MD5("woshiyuxin")));

        if (x) {
            System.out.println("success");
        } else {
            System.out.println("failed");
        }
    }
}
