package zb.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

enum TokenType {
    Number,
    BinaryOperator,
    OpenParenthesis,
    CloseParenthesis,
    EOF
}


public class Token {

    public TokenType type;
    public String value;

    public Token(TokenType type, String value) {
        this.type = type;
        this.value = value;
    }

    public static List<Token> tokenize(String sourceCode) {
        List<Token> tokens = new ArrayList<Token>();
        List<Character> src = sourceCode.chars().mapToObj(c -> (char) c).collect(Collectors.toList());

        while(src.size() > 0) {

            switch (Character.toString(src.get(0))) {
                case "(":
                tokens.add(new Token(TokenType.OpenParenthesis, String.valueOf(src.remove(0))));
                    break;

                case ")":
                tokens.add(new Token(TokenType.CloseParenthesis, String.valueOf(src.remove(0))));
                    break;

                case "+":
                case "-":
                case "*":
                case "/":
                case "%":
                case "^":
                case "~":
                tokens.add(new Token(TokenType.BinaryOperator, String.valueOf(src.remove(0))));
                    break;
                default:
                    // Multiline Token Resolution
                    if(Character.isDigit(src.get(0)) || src.get(0).equals('.')){
                        String num = "";
                        while(src.size() > 0 && (Character.isDigit(src.get(0)) || src.get(0).equals('.'))){
                            num += src.remove(0);
                        }

                        tokens.add(new Token(TokenType.Number, num));
                    } else if(Character.isWhitespace(src.get(0))) {
                        src.remove(0); //Skippable Characters
                    } else {
                        throw new RuntimeException("Unrecognized Character found in source: " + src.get(0));
                    }
                    break;
            }
        }

        tokens.add(new Token(TokenType.EOF, "EndOfFile"));

        return tokens;
    }
}