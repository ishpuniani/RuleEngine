package Operators;

/**
     * Created by Dhruv on 13/06/17.
     */
public class ComparisonOperator {

    public enum Operator {

        GREATER_THAN(">"),
        LESSER_THAN("<"),
        GREATER_THAN_EQUAL(">="),
        LESS_THAN_EQUAL("<="),
        EQUAL("="),
        NOT_EQUAL("!=");

        private String op;

        Operator(String op) {
            this.op = op;
        }

        public String op() {
            return op;
        }
    }
}
