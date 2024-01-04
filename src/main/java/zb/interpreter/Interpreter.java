package zb.interpreter;

import zb.parser.BinaryExpression;
import zb.parser.NumericLiteral;
import zb.parser.Program;
import zb.parser.Statement;

public class Interpreter {

    public RuntimeValue evaluate(Statement astNode) {
        switch (astNode.kind) {

            case NumericLiteral: 
                NumericLiteral numericLiteral = (NumericLiteral) astNode;
                return new NumberValue(numericLiteral.value);
            case BinaryExpression:
                BinaryExpression binaryExpression = (BinaryExpression) astNode;
                return this.evaluateBinaryExpression(binaryExpression);
            case Program: 
                Program program = (Program) astNode;
                return this.evaluateProgram(program);
            default:
                throw new RuntimeException("This AST Node has not yet been setup for interpretation: " + astNode.kind);
        }
    }

    private RuntimeValue evaluateBinaryExpression(BinaryExpression binaryExpression) {

        RuntimeValue leftHandSide = evaluate(binaryExpression.left);
        RuntimeValue rightHandSide = evaluate(binaryExpression.right);

        if(leftHandSide.type == ValueType.Number && rightHandSide.type == ValueType.Number ) {
            NumberValue numericLeftHandSide = (NumberValue) leftHandSide;
            NumberValue numericRightHandSide = (NumberValue) rightHandSide;
            return evaluateNumericBinaryExpression(numericLeftHandSide, numericRightHandSide, binaryExpression.operator);
        } 
        return new NumberValue(0.0);
    }

    private NumberValue evaluateNumericBinaryExpression(NumberValue leftHandSide, NumberValue rightHandSide, String operator) {
        Double result = 0.0;

        if (operator.equals("+")) {
            result = leftHandSide.value + rightHandSide.value;
        } else if (operator.equals("-")) {
            result = leftHandSide.value - rightHandSide.value;
        } else if (operator.equals("*")) {
            result = leftHandSide.value * rightHandSide.value;
        } else if (operator.equals("/")) {
            result = leftHandSide.value / rightHandSide.value;
        } else if (operator.equals("%")) {
            result = leftHandSide.value % rightHandSide.value;
        } else if (operator.equals("^")) {
            result = Math.pow(leftHandSide.value, rightHandSide.value);
        } else if (operator.equals("~")) {
            result = Math.pow(leftHandSide.value, 1 / rightHandSide.value);
        }

        return new NumberValue(result);
    }

    private RuntimeValue evaluateProgram(Program program) {
        RuntimeValue lastEvaluated = new NumberValue(0.0);

        for(Statement statement : program.body) {
            lastEvaluated = this.evaluate(statement);
        }

        return lastEvaluated;
    }
}
