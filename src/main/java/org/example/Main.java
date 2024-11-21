package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        String input = Files.readString(Path.of("src/main/resources/test.txt"));
        LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer();
        List<Lexeme> lexemes = lexicalAnalyzer.startAnalyze(input);
        for(Lexeme lexeme : lexemes) {
            System.out.println(lexeme);
        }
    }
}