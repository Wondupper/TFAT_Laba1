package org.example;

public record Lexeme(LexemeType lexemeType,LexemeCategory lexemeCategory,
                     String value) {
    @Override
    public String toString() {
        return ("Lexeme Category: %s"+" ".repeat(15-lexemeCategory.toString().length())
                +"| Lexeme Type: %s"+" ".repeat(11-lexemeType.toString().length())
                +"| Lexeme Value: %s").formatted(lexemeCategory,lexemeType,value);
    }
}