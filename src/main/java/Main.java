import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.Set;

/**
 * Created by Dhruv on 13/06/17.
 */
public class Main {

    public static void main(String[] args) {
        /*
            1. Generate Rules
            2. Import data and apply rules
            3. Print violators
         */
        try {
            long startTime = new Date().getTime();
            Map<String,Rule> ruleMap = GenerateRules.generateRulesFromFile();
            Set<String> violators = ImportData.importAndValidateData(ruleMap);
            writeToFile(violators);
            System.out.println("Time Taken = " + (new Date().getTime() - startTime) + "ms");

        } catch (Exception e) {
            System.out.println("Exception Caught");
            throw new RuntimeException(e);
        }
    }

    private static void writeToFile(Set<String> violators) {

        FileWriter fileWriter = null;
        BufferedWriter bufferedWriter = null;
        try {
            fileWriter = new FileWriter("/Users/Dhruv/Downloads/violators.txt");
            bufferedWriter = new BufferedWriter(fileWriter);
            for(String str : violators) {
                bufferedWriter.write(str + "\n");
            }
        } catch (IOException e) {
            System.out.println("Error writing violators to file");
            throw new RuntimeException(e);
        } finally {
            try {
                bufferedWriter.close();
                fileWriter.close();
            } catch (IOException e) {
                System.out.println("Exception closing writers");
                throw new RuntimeException(e);
            }
        }
    }
}
