import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.ParseException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Dhruv on 13/06/17.
 */
public class ImportData {

    public static Set<String> importAndValidateData(Map<String,List<Rule>> ruleMap) throws FileNotFoundException, ParseException {
        //Read file and get data
        Set <String> violators = new HashSet<String>();
        JsonParser jsonParser = new JsonParser();
        JsonArray jsonArray = jsonParser.parse(new FileReader("/Users/Dhruv/Downloads/raw_data.json")).getAsJsonArray();
        for(JsonElement jsonElement : jsonArray) {
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            DataNode element = new DataNode(jsonObject);
            //System.out.println("parsed element = " + element);
            if(ruleMap.containsKey(element.getSignal())) {
                boolean flag = false;
                for(Rule r : ruleMap.get(element.getSignal())) {
                    if (!r.applyRule(element) && !flag) {
                        System.out.println("Add violator: " + element);
                        violators.add(element.getSignal());
                        flag = true;
                    }
                }
            }
        }
        return violators;
    }

}
