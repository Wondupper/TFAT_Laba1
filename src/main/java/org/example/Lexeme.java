package org.example;

public record Lexeme(LexemeType lexemeType,LexemeCategory lexemeCategory,
                     String value, int startIndex, int endIndex) {
    @Override
    public String toString() {
        return ("Lexeme Category: %s"+" ".repeat(15-lexemeCategory.toString().length())
                +"| Lexeme Type: %s"+" ".repeat(11-lexemeType.toString().length())
                +"| Lexeme Value: %s"+" ".repeat(10-value.length())
                +"| start:end (%d:%d)").formatted(lexemeCategory,lexemeType,value,startIndex, endIndex);
    }
}