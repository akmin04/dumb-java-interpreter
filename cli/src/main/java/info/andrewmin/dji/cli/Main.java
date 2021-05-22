package info.andrewmin.dji.cli;

import info.andrewmin.dji.core.ast.ProgramNode;
import info.andrewmin.dji.core.exceptions.BaseUserException;
import info.andrewmin.dji.core.exceptions.InternalException;
import info.andrewmin.dji.core.lexer.FileCharIterator;
import info.andrewmin.dji.core.lexer.Lexer;
import info.andrewmin.dji.core.parser.ProgramParser;
import info.andrewmin.dji.core.runtime.Runtime;

import java.io.File;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Main {

    public static void main(String[] args) {
        initLogger();

        try {
//            System.out.println("***** Tokens *****");
            FileCharIterator iter = new FileCharIterator(new File("../test.djava"));
            Lexer lexer = new Lexer(iter);
            ProgramParser parser = new ProgramParser(lexer);
            ProgramNode node = parser.parse();
//            System.out.println("\n\n***** Abstract Syntax Tree *****");
//            System.out.println(node);

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

    private static void initLogger() {
        LogManager.getLogManager().reset();
        Handler handler = new LogHandler();
        handler.setLevel(Level.ALL);
        handler.setFormatter(new LogFormatter());

        Logger rootLogger = LogManager.getLogManager().getLogger("");
        rootLogger.setLevel(Level.ALL);
        rootLogger.addHandler(handler);
    }
}
