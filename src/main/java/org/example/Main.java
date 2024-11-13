package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("Введите путь к файлу");
        String path = System.console().readLine();
        String input = Files.readString(Path.of(path));
        List<Token> tokens = LexAnalyzer.lex(input);
        for(Token token : tokens) {
            System.out.println(token);
        }
    }
}