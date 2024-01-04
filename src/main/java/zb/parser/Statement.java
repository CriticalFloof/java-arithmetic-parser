package zb.parser;

public abstract class Statement {
    public NodeType kind;

    Statement(NodeType kind) {
        this.kind = kind;
    }
}
