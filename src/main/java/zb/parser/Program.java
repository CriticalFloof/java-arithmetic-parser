package zb.parser;

import java.util.List;

public class Program extends Statement{

    public List<Statement> body;

    Program(List<Statement> body) {
        super(NodeType.Program);
        this.body = body;
    }
    
}
