package zb.interpreter;

enum ValueType {
    Number,
}

public class RuntimeValue {
    public ValueType type;

    RuntimeValue(ValueType type) {
        this.type = type;
    }
}
