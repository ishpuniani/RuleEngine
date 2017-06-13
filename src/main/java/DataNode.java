import com.google.gson.JsonObject;
import org.apache.maven.surefire.shade.org.apache.maven.shared.utils.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by Dhruv on 13/06/17.
 */
public class DataNode {

    private String signal;
    private String valueType;
    private Object value;

    public DataNode(JsonObject dataMap) {
        this.signal = dataMap.get("signal").getAsString();
        this.valueType = dataMap.get("value_type").getAsString();
        String jsonValue = dataMap.get("value").getAsString();
        try {
            this.value = getValueFromJsonValue(jsonValue);
        } catch (ParseException e) {
            System.out.println("Excepting parsing date from string: " + jsonValue);
            throw new RuntimeException(e);
        }
    }

    private Object getValueFromJsonValue(String jsonValue) throws ParseException {
        Object value;
        if(StringUtils.equalsIgnoreCase(jsonValue,"datetime")) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            value = formatter.parse(jsonValue);
        } else if(StringUtils.equalsIgnoreCase(jsonValue,"Integer")) {
            value = Float.parseFloat(jsonValue);
        } else {
            value = jsonValue;
        }
        return value;
    }


    public String getSignal() {
        return signal;
    }

    public String getValueType() {
        return valueType;
    }

    public Object getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "DataNode{" +
                "signal='" + signal + '\'' +
                ", valueType='" + valueType + '\'' +
                ", value=" + value +
                '}';
    }
}
