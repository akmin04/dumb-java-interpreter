package info.andrewmin.dji;

import info.andrewmin.dji.exceptions.BaseUserException;
import info.andrewmin.dji.exceptions.InternalCompilerError;
import info.andrewmin.dji.lexer.Lexer;
import info.andrewmin.dji.tokens.Token;

public class App {

    public static void main(String[] args) {
        try {
            Lexer lexer = new Lexer("test.djava");
            while (lexer.hasNext()) {
                Token t = lexer.next();
                System.out.println(t);
            }
        } catch (BaseUserException e) {
            System.err.println("Error: " + e.getMessage());
            System.exit(1);
        } catch (InternalCompilerError e) {
            System.err.println("Internal error: " + e.getMessage());
            System.exit(1);
        }
    }
}
