package info.andrewmin.dji;

import info.andrewmin.dji.lexer.Lexer;
import info.andrewmin.dji.tokens.Token;

public class App {

    public static void main(String[] args) {
        Logger.init(true);
        Lexer lexer = new Lexer("test.djava");
        while (lexer.hasNext()) {
            Token t = lexer.next();
            System.out.println(t);
        }

    }
}
