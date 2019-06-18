package occamsrazor.iot_server.dao.impl;

import occamsrazor.iot_server.dao.SensorsValuesDao;
import occamsrazor.iot_server.domain.SensorsValue;
import occamsrazor.iot_server.utils.DruidUtil;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.List;

/**
 * @author : 鱼摆摆
 * @date : Create at 2019-06-18
 * @time : 11:43
 */
public class SensorsValueDaoImpl implements SensorsValuesDao {

    private QueryRunner runner = new QueryRunner(DruidUtil.getDataSource());

    @Override
    public boolean insertSensorsValues(SensorsValue sensorsValue) {
        String sql = "insert into gateway_info(time_stamp, gateway_id, temperature_value, humidity_value, fan_value, beam_value, light1_value, light2_value) VALUE (?, ?, ?, ?, ?, ?, ?, ?);";

        int res = 0;

        try {
            res = runner.update(sql, sensorsValue.getTimeStamp(), sensorsValue.getGatewayId(), sensorsValue.getTemperature(), sensorsValue.getHumidity()
                    , sensorsValue.getFan(), sensorsValue.getBeam(), sensorsValue.getLight1(), sensorsValue.getLight2());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return res > 0;
    }

    @Override
    public SensorsValue findNewest() {
        String sql = "select * from gateway_info order by time_stamp desc limit 1;";

        SensorsValue value = null;

        try {
            value = runner.query(sql, new BeanHandler<>(SensorsValue.class));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return value;
    }

    @Override
    public List<SensorsValue> findLately(Integer num) {
        String sql = "select * from gateway_info order by time_stamp desc limit ?;";

        List<SensorsValue> values = null;

        try {
            values = runner.query(sql, new BeanListHandler<>(SensorsValue.class), num);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return values;
    }
}
