package zb.parser;

public class NumericLiteral extends Expression{

    public Double value;

    NumericLiteral(Double value) {
        super(NodeType.NumericLiteral);
        this.value = value;
    }
    
}
