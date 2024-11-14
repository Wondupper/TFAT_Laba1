package org.example;

import java.util.ArrayList;
import java.util.List;

public class LexAnalyzer {
    public static List<Token> lex(String input) {
        List<Token> tokens = new ArrayList<>();
        StringBuilder currentToken = new StringBuilder();
        TokenType currentState = null;

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);

            switch (currentState) {
                case null:
                    if (Character.isWhitespace(c)) {
                        continue;
                    } else if (Character.isLetter(c)) {
                        currentToken.append(c);
                        currentState = TokenType.IDENTIFIER;
                    } else if (Character.isDigit(c)) {
                        currentToken.append(c);
                        currentState = TokenType.CONSTANT;
                    } else if ("<>=".indexOf(c) != -1) {
                        currentToken.append(c);
                        currentState = TokenType.COMPARISON_OPERATOR;
                    } else if ("+-".indexOf(c) != -1) {
                        tokens.add(new Token(TokenType.ARITHMETIC_OPERATION_LOW_PRIORITY, String.valueOf(c)));
                    } else if ("*/".indexOf(c) != -1) {
                        tokens.add(new Token(TokenType.ARITHMETIC_OPERATION_HIGH_PRIORITY, String.valueOf(c)));
                    } else {
                        tokens.add(new Token(TokenType.UNKNOWN, String.valueOf(c)));
                    }
                    break;

                case IDENTIFIER:
                    if (Character.isLetterOrDigit(c)) {
                        currentToken.append(c);
                    } else {
                        tokens.add(createKeywordOrIdentifierToken(currentToken.toString()));
                        currentToken.setLength(0);
                        currentState = null;
                        i--;
                    }
                    break;

                case CONSTANT:
                    if (Character.isDigit(c)) {
                        currentToken.append(c);
                    } else {
                        tokens.add(new Token(TokenType.CONSTANT, currentToken.toString()));
                        currentToken.setLength(0);
                        currentState = null;
                        i--;
                    }
                    break;

                case COMPARISON_OPERATOR:
                    if (currentToken.length() == 1 && currentToken.charAt(0) == '<' && c == '>') {
                        currentToken.append(c);
                        tokens.add(new Token(TokenType.COMPARISON_OPERATOR, currentToken.toString()));
                        currentToken.setLength(0);
                        currentState = null;
                    } else {
                        tokens.add(new Token(TokenType.COMPARISON_OPERATOR, currentToken.toString()));
                        currentToken.setLength(0);
                        currentState = null;
                        i--;
                    }
                    break;

                default:
                    throw new IllegalStateException("Unexpected state: " + currentState);
            }
        }

        if (currentToken.length() > 0) {
            if (currentState == TokenType.IDENTIFIER) {
                tokens.add(createKeywordOrIdentifierToken(currentToken.toString()));
            } else if (currentState == TokenType.CONSTANT) {
                tokens.add(new Token(TokenType.CONSTANT, currentToken.toString()));
            } else if (currentState == TokenType.COMPARISON_OPERATOR) {
                tokens.add(new Token(TokenType.COMPARISON_OPERATOR, currentToken.toString()));
            }
        }

        return tokens;
    }

    private static Token createKeywordOrIdentifierToken(String text) {
        switch (text) {
            case "if": return new Token(TokenType.IF, text);
            case "then": return new Token(TokenType.THEN, text);
            case "else": return new Token(TokenType.ELSE, text);
            case "elseif": return new Token(TokenType.ELSEIF, text);
            case "end": return new Token(TokenType.END, text);
            case "output": return new Token(TokenType.OUTPUT, text);
            case "not": return new Token(TokenType.NOT, text);
            case "and": return new Token(TokenType.AND, text);
            case "or": return new Token(TokenType.OR, text);
            default: return new Token(TokenType.IDENTIFIER, text);
        }
    }
}
