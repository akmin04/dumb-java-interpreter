package info.andrewmin.dji;

import info.andrewmin.dji.ast.ProgramNode;
import info.andrewmin.dji.exceptions.BaseUserException;
import info.andrewmin.dji.exceptions.InternalCompilerException;
import info.andrewmin.dji.lexer.FileCharIterator;
import info.andrewmin.dji.lexer.Lexer;
import info.andrewmin.dji.parser.ProgramParser;
import info.andrewmin.dji.tokens.Token;

import java.io.File;

public class CLI {

    public static void main(String[] args) {
        try {
            for (Lexer it = new Lexer(new FileCharIterator(new File("../test.djava"))); it.hasNext(); ) {
                Token t = it.next();
                System.out.println(t);
            }

            FileCharIterator iter = new FileCharIterator(new File("../test.djava"));
            Lexer lexer = new Lexer(iter);
            ProgramParser parser = new ProgramParser(lexer);
            ProgramNode node = parser.parse();
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
