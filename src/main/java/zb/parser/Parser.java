package zb.parser;

import java.util.ArrayList;
import java.util.List;

public class Parser {
    private List<Token> tokens;

    public Program produceAST (String sourceCode) {

        this.tokens = Token.tokenize(sourceCode);

        Program program = new Program(new ArrayList<Statement>());

        while (!this.isEOF()) {
            program.body.add(this.parseStatement());
        }

        return program;
        
    }

    private Token at() {
        return this.tokens.get(0);
    }

    private Token eat() {
        return this.tokens.remove(0);
    }

    private Token expect(TokenType type, String err) {
        Token prev = this.tokens.remove(0);
        if(prev == null || prev.type != type) {
            throw new RuntimeException("Parser Error:\n" + err + "\n" + prev + "\n" + " - Expecting: " + type);
        }

        return prev;
    }

    private boolean isEOF() {
        return this.tokens.get(0).type == TokenType.EOF;
    }

    private Statement parseStatement() {
        return this.parseExpression();
    }

    private Expression parseExpression() {
        return parseAdditiveExpression();
    }

    

    private Expression parseAdditiveExpression() {
        Expression left = this.parseMultiplicativeExpression();
        
        while (this.at().value.equals("+") || this.at().value.equals("-")) {
            String operator = this.eat().value;
            Expression right = this.parseMultiplicativeExpression();

            left = new BinaryExpression(left, right, operator);
        }

        return left;
    }

    private Expression parseMultiplicativeExpression() {
        Expression left = this.parseExponentialExpression();
        
        while (this.at().value.equals("*") || this.at().value.equals("/") || this.at().value.equals("%")) {
            String operator = this.eat().value;
            Expression right = this.parseExponentialExpression();

            left = new BinaryExpression(left, right, operator);
        }

        return left;
    }

    private Expression parseExponentialExpression() {
        Expression left = this.parsePrimaryExpression();
        
        while (this.at().value.equals("^") || this.at().value.equals("~")) {
            String operator = this.eat().value;
            Expression right = this.parsePrimaryExpression();

            left = new BinaryExpression(left, right, operator);
        }

        return left;
    }

    private Expression parsePrimaryExpression() {
        TokenType tk = this.at().type;

        switch (tk) {
            case Number:
                return new NumericLiteral(Double.parseDouble(this.eat().value));
            case OpenParenthesis:
                this.eat();
                Expression value = this.parseExpression();
                this.expect(TokenType.CloseParenthesis, "Unexpected token found. Expected closing parenthesis.");
                return value;
            default: 
                throw new RuntimeException("Unexpected token found during parsing."+ this.at().type);
        }
    }

}
