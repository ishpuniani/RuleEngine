import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Dhruv on 13/06/17.
 */
public class GenerateRules {

    private static final String RULE_FILE = "/Users/Dhruv/Downloads/Rules.txt";

    public static Map<String,List<Rule>> generateRulesFromFile() {
        Map<String, List<Rule>> ruleMap = new HashMap<String, List<Rule>>();
        BufferedReader br = null;

        try {

            String sCurrentLine;
            br = new BufferedReader(new FileReader(RULE_FILE));
            while ((sCurrentLine = br.readLine()) != null) {
                String[] ruleArr = sCurrentLine.split("~");
                if(!ruleMap.containsKey(ruleArr[0])) {
                    ruleMap.put(ruleArr[0],new ArrayList<Rule>());
                }
                ruleMap.get(ruleArr[0]).add(new Rule(ruleArr));
            }
            System.out.println("RuleMap = " + ruleMap);
            return ruleMap;

        } catch (FileNotFoundException e) {
            System.out.println("File Not Found while generating rules");
            throw new RuntimeException(e);
        } catch (IOException e) {
            System.out.println("IOException while generating rules");
            throw new RuntimeException(e);
        } finally {
            try {
                if (br != null)
                    br.close();
            } catch (IOException ex) {
                System.out.println("Unable to close Br and Fr");
            }
        }

    }
}
