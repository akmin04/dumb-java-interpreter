package info.andrewmin.dji;

import info.andrewmin.dji.ast.StatementNode;
import info.andrewmin.dji.exceptions.BaseUserException;
import info.andrewmin.dji.exceptions.InternalCompilerException;
import info.andrewmin.dji.lexer.FileCharIterator;
import info.andrewmin.dji.lexer.Lexer;
import info.andrewmin.dji.parser.StatementParser;

import java.io.File;

public class App {

    public static void main(String[] args) {
        try {
            FileCharIterator iter = new FileCharIterator(new File("test.djava"));
            Lexer lexer = new Lexer(iter);
            StatementParser parser = new StatementParser(lexer);
            StatementNode node = parser.parse();

            System.out.println(node);
        } catch (BaseUserException e) {
            System.err.println("Error: " + e.getMessage());
            System.exit(1);
        } catch (InternalCompilerException e) {
            System.err.println("Internal error: " + e.getMessage());
            System.exit(1);
        }
    }
}
