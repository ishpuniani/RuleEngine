import Operators.ComparisonOperator.Operator;
import org.apache.maven.surefire.shade.org.apache.maven.shared.utils.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Dhruv on 13/06/17.
 */
public class Rule {

    Operator operator;
    List<String> comparatorValue = new ArrayList<String>();

    public Rule(String [] ruleRowArr) {
        operator = Operator.valueOf(ruleRowArr[1]);

        //In case of multiple values [Eg between]
        for(int i = 2; i<ruleRowArr.length; i++) {
            comparatorValue.add(ruleRowArr[i]);
        }
    }

    /*
        Returns true if Rule is not violated
        Returns true if Rule cannot be applied
     */

    public boolean applyRule(DataNode dataNode) throws ParseException {

        boolean res = true;
        if(StringUtils.equalsIgnoreCase(dataNode.getValueType(),"String") && StringUtils.isAlpha(comparatorValue.get(0))) {
            switch (operator) {
                case EQUAL: res =  StringUtils.equalsIgnoreCase((String)dataNode.getValue(),comparatorValue.get(0)); break;
                case NOT_EQUAL: res =  StringUtils.equalsIgnoreCase((String)dataNode.getValue(),comparatorValue.get(0)); break;
            }
        } else if(StringUtils.equalsIgnoreCase(dataNode.getValueType(),"Datetime") && comparatorValue.get(0).contains("-")) {

            Date signalDate = (Date)dataNode.getValue();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Date comparatorDate = formatter.parse(comparatorValue.get(0));
            //System.out.println("Applying date rule to signal:" + signalDate + " and comp: " + comparatorDate);
            switch (operator) {
                case GREATER_THAN: res =  signalDate.after(comparatorDate); break;
                case LESSER_THAN: res =  signalDate.before(comparatorDate); break;
                case EQUAL: res =  signalDate.equals(comparatorDate); break;
                case NOT_EQUAL: res =  !signalDate.equals(comparatorDate); break;
                case LESS_THAN_EQUAL: res =  signalDate.before(comparatorDate) || signalDate.equals(comparatorDate); break;
                case GREATER_THAN_EQUAL: res =  signalDate.after(comparatorDate) || signalDate.equals(comparatorDate); break;
            }
        } else if(StringUtils.equalsIgnoreCase(dataNode.getValueType(),"Integer") && StringUtils.isNumeric(comparatorValue.get(0).replace(".",""))) {

            float signalInt = (Float)dataNode.getValue();
            float comparatorInt = Float.parseFloat(comparatorValue.get(0));
            //System.out.println("Applying float rule to signal:" + signalInt + " and comp: " + comparatorInt);
            switch (operator) {
                case GREATER_THAN: res =  signalInt > comparatorInt; break;
                case LESSER_THAN: res =  signalInt < comparatorInt; break;
                case EQUAL: res =  signalInt == comparatorInt; break;
                case NOT_EQUAL: res =  signalInt != comparatorInt; break;
                case LESS_THAN_EQUAL: res =  signalInt <= comparatorInt; break;
                case GREATER_THAN_EQUAL: res =  signalInt >= comparatorInt; break;
            }
        }
        return res;
    }

    @Override
    public String toString() {
        return "Rule{" +
                "operator=" + operator +
                ", comparatorValue=" + comparatorValue +
                '}';
    }
}
