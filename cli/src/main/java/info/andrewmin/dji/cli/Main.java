package info.andrewmin.dji.cli;

import info.andrewmin.dji.core.ast.ProgramNode;
import info.andrewmin.dji.core.exceptions.BaseUserException;
import info.andrewmin.dji.core.lexer.FileCharIterator;
import info.andrewmin.dji.core.lexer.Lexer;
import info.andrewmin.dji.core.parser.ProgramParser;
import info.andrewmin.dji.core.runtime.Runtime;
import info.andrewmin.dji.core.tokens.Token;
import picocli.CommandLine;

import java.io.File;
import java.util.concurrent.Callable;
import java.util.logging.*;

@CommandLine.Command(name = "dji", mixinStandardHelpOptions = true, description = "Run the dumb java interpreter.")
public final class Main implements Callable<Integer> {

    @SuppressWarnings("unused")
    @CommandLine.Parameters(index = "0", description = "The source file.")
    private File file;

    @CommandLine.Option(names = "-v", description = "Verbosity level (-v, -vv).")
    boolean[] verbosity = new boolean[0];

    @CommandLine.ArgGroup
    OutputFormat outputFormat = new OutputFormat();

    @SuppressWarnings("FieldMayBeFinal")
    static class OutputFormat {
        @CommandLine.Option(names = "--tokens", description = "Print the tokens.")
        private boolean tokens = false;

        @CommandLine.Option(names = "--ast", description = "Print the abstract syntax tree.")
        private boolean ast = false;
    }

    @Override
    public Integer call() {
        // Setup logger
        LogManager.getLogManager().reset();
        Handler logHandler = new StreamHandler(System.out, new LogFormatter());
        logHandler.setLevel(Level.ALL);

        Logger rootLogger = LogManager.getLogManager().getLogger("");
        rootLogger.addHandler(logHandler);

        if (verbosity.length > 1) {
            rootLogger.setLevel(Level.ALL);
        } else if (verbosity.length > 0) {
            rootLogger.setLevel(Level.INFO);
        } else {
            rootLogger.setLevel(Level.OFF);
        }

        try {
            FileCharIterator iter = new FileCharIterator(file);
            Lexer lexer = new Lexer(iter);

            // Tokens only
            if (outputFormat.tokens) {
                logHandler.flush();
                while (lexer.hasNext()) {
                    Token t = lexer.next();
                    System.out.println(t);
                }
                return 0;
            }

            ProgramParser parser = new ProgramParser(lexer);
            ProgramNode node = parser.parse();

            // AST only
            if (outputFormat.ast) {
                logHandler.flush();
                System.out.println(node);
                return 0;
            }

            // Full
            Runtime runtime = new Runtime(node);
            String result = runtime.runProgram().toString();

            logHandler.flush();
            System.out.println("Main returned: " + result);
        } catch (BaseUserException e) {
            System.err.println("Error: " + e.getMessage());
            return 1;
        } catch (Exception e) {
            System.err.println("Internal error: " + e.getMessage());
            return 1;
        }
        return 0;
    }


    public static void main(String[] args) {
        int exitCode = new CommandLine(new Main()).execute(args);
        System.exit(exitCode);
    }
}
