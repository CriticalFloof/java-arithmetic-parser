package zb.interpreter;

public class NumberValue extends RuntimeValue {

    public Double value;
    NumberValue(Double value) {
        super(ValueType.Number);
        this.value = value;
    }
    
}
