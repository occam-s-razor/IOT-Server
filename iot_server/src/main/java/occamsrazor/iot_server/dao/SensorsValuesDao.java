package occamsrazor.iot_server.dao;

import occamsrazor.iot_server.domain.SensorsValue;

import java.util.List;

/**
 * @author : 鱼摆摆
 * @date : Create at 2019-06-18
 * @time : 9:37
 */
public interface SensorsValuesDao {
    public boolean insertSensorsValues(SensorsValue sensorsValue);

    public SensorsValue findNewest();

    public List<SensorsValue> findLately(Integer num);
}
