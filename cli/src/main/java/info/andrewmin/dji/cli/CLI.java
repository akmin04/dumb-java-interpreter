package info.andrewmin.dji.cli;

import info.andrewmin.dji.core.ast.ProgramNode;
import info.andrewmin.dji.core.exceptions.BaseUserException;
import info.andrewmin.dji.core.exceptions.InternalException;
import info.andrewmin.dji.core.lexer.FileCharIterator;
import info.andrewmin.dji.core.lexer.Lexer;
import info.andrewmin.dji.core.parser.ProgramParser;
import info.andrewmin.dji.core.runtime.Runtime;
import info.andrewmin.dji.core.tokens.Token;

import java.io.File;

public class CLI {

    public static void main(String[] args) {
        try {
            System.out.println("***** Tokens *****");
            for (Lexer it = new Lexer(new FileCharIterator(new File("../test.djava"))); it.hasNext(); ) {
                Token t = it.next();
                System.out.println(t);
            }

            FileCharIterator iter = new FileCharIterator(new File("../test.djava"));
            Lexer lexer = new Lexer(iter);
            ProgramParser parser = new ProgramParser(lexer);
            ProgramNode node = parser.parse();
            System.out.println("\n\n***** Abstract Syntax Tree *****");
            System.out.println(node);

            System.out.println("\n\n***** Runtime *****");
            Runtime runtime = new Runtime(node);
            System.out.println("Main returned: " + runtime.runProgram());
        } catch (BaseUserException e) {
            System.err.println("Error: " + e.getMessage());
            System.exit(1);
        } catch (InternalException e) {
            System.err.println("Internal error: " + e.getMessage());
            System.exit(1);
        }
    }
}
