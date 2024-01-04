package zb.parser;

public class BinaryExpression extends Expression {
    public Expression left;
    public Expression right;
    public String operator;

    BinaryExpression(Expression left, Expression right, String operator) {
        super(NodeType.BinaryExpression);
        this.left = left;
        this.right = right;
        this.operator = operator;
    }
    
}
