import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.ParseException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by Dhruv on 13/06/17.
 */
public class ImportData {

    public static Set<String> importAndValidateData(Map<String,Rule> ruleMap) throws FileNotFoundException, ParseException {
        //Read file and get data
        Set <String> violators = new HashSet<String>();
        JsonParser jsonParser = new JsonParser();
        JsonArray jsonArray = jsonParser.parse(new FileReader("/Users/Dhruv/Downloads/raw_data.json")).getAsJsonArray();
        for(JsonElement jsonElement : jsonArray) {
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            DataNode element = new DataNode(jsonObject);
            //System.out.println("parsed element = " + element);
            if(ruleMap.containsKey(element.getSignal())) {
                if(ruleMap.get(element.getSignal()).applyRule(element)) {
                    System.out.println("Add violator: " + element);
                    violators.add(element.getSignal());
                }
            }
        }
        return violators;
    }

}
