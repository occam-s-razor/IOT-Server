package occamsrazor.iot_server.domain;

/**
 * @author : 鱼摆摆
 * @date : Create at 2019-06-18
 * @time : 9:25
 */
public class SensorsValue {
    private String timeStamp;
    private String temperature;
    private String humidity;
    private String fan;
    private String beam;
    private String light1;
    private String light2;

    public SensorsValue() {
    }

    public SensorsValue(String timeStamp, String temperature, String humidity, String fan, String beam, String light1, String light2) {
        this.timeStamp = timeStamp;
        this.temperature = temperature;
        this.humidity = humidity;
        this.fan = fan;
        this.beam = beam;
        this.light1 = light1;
        this.light2 = light2;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getFan() {
        return fan;
    }

    public void setFan(String fan) {
        this.fan = fan;
    }

    public String getBeam() {
        return beam;
    }

    public void setBeam(String beam) {
        this.beam = beam;
    }

    public String getLight1() {
        return light1;
    }

    public void setLight1(String light1) {
        this.light1 = light1;
    }

    public String getLight2() {
        return light2;
    }

    public void setLight2(String light2) {
        this.light2 = light2;
    }

    @Override
    public String toString() {
        return "SensorsValue{" +
                "temperature='" + temperature + '\'' +
                ", humidity='" + humidity + '\'' +
                ", fan='" + fan + '\'' +
                ", beam='" + beam + '\'' +
                ", light1='" + light1 + '\'' +
                ", light2='" + light2 + '\'' +
                '}';
    }
}
