package zb;

import java.io.*;

import zb.interpreter.Interpreter;
import zb.interpreter.NumberValue;
import zb.parser.Parser;
import zb.parser.Program;

public class App {
    public static void main(String[] args) throws IOException {
        final Parser parser = new Parser();
        final Interpreter interpreter = new Interpreter();
        final BufferedReader buffReader = new BufferedReader(new InputStreamReader(System.in));

        System.out.print("\033[2J");
        System.out.println("Entering Calculator.");
        while(true) {
            System.out.print("Please enter a mathematical expression: ");
            String source = buffReader.readLine();

            if(source.toLowerCase().equals("exit")) {
                break;
            };

            try {
                Program program = parser.produceAST(source);
                NumberValue result = (NumberValue) interpreter.evaluate(program);
                System.out.println("Result: " + result.value + "\n");
            } catch (Exception error) {
                if(error.getMessage().contains("Unrecognized Character found in source")){
                    System.out.println("You can only use numbers and these operators: +, -, *, /, %, ^.");
                } else {
                    throw error;
                }
            }
        }  
        System.out.println("Exiting Calculator."); 
    }
}