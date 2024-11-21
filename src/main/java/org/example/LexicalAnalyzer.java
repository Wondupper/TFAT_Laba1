package org.example;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LexicalAnalyzer {
    private List<Lexeme> lexemes = new ArrayList<>();

    public List<Lexeme> startAnalyze(String input) {
        clearLexemes();
        input = input + '\n';
        State currentState = State.START;
        State previousState;
        boolean canAdd;
        StringBuilder currentLexeme = new StringBuilder();
        StringBuilder nextLexeme = new StringBuilder();
        int index = 0;
        int lexemeStart = 0;
        int lexemeEnd = 0;
        while (currentState != State.ERROR) {
            previousState = currentState;
            canAdd = true;
            if (index == input.length()) {
                currentState = State.FINAL;
                break;
            }
            char currentSymbol = input.charAt(index);
            switch (currentState) {
                case START -> {
                    if (Character.isWhitespace(currentSymbol)) {
                        currentState = State.START;
                    } else if
                    (Character.isDigit(currentSymbol)) {
                        currentState = State.CONSTANT;
                    } else if
                    (Character.isLetter(currentSymbol)) {
                        currentState = State.IDENTIFIER;
                    } else if (currentSymbol == '>' ||
                            currentSymbol == '<') {
                        currentState = State.COMPARISON;
                    } else if (currentSymbol == '+' ||
                            currentSymbol == '-' || currentSymbol == '*' || currentSymbol
                            == '/') {
                        currentState = State.ARITHMETIC;
                    } else if (currentSymbol == '=') {
                        currentState = State.ASSIGMENT;
                    } else if (currentSymbol == ';') {
                        currentState = State.DELIMITER;
                    } else {
                        currentState = State.ERROR;
                    }
                    canAdd = false;
                    if (!Character.isWhitespace(currentSymbol)) {
                        currentLexeme.append(currentSymbol);
                        lexemeStart = index;
                    }
                }
                case COMPARISON -> {
                    if (Character.isWhitespace(currentSymbol)) {
                        currentState = State.START;
                    } else if (currentSymbol == '>') {
                        currentState = State.START;
                        currentLexeme.append(currentSymbol);
                    } else if (Character.isLetter(currentSymbol)) {
                        currentState = State.IDENTIFIER;
                        nextLexeme.append(currentSymbol);
                    } else if (Character.isDigit(currentSymbol)) {
                        currentState = State.CONSTANT;
                        nextLexeme.append(currentSymbol);
                    }else if (currentSymbol == ';') {
                        currentState = State.DELIMITER;
                        nextLexeme.append(currentSymbol);
                    } else {
                        currentState = State.ERROR;
                        if (currentSymbol == '=') {
                            nextLexeme.append(currentSymbol);
                        }
                        canAdd = false;
                    }
                }

                case ASSIGMENT -> {
                    if (currentSymbol == '=') {
                        currentState = State.COMPARISON;
                        currentLexeme.append(currentSymbol);
                    } else if (Character.isWhitespace(currentSymbol)) {
                        currentState = State.START;
                    } else if (Character.isLetter(currentSymbol)) {
                        currentState = State.IDENTIFIER;
                        nextLexeme.append(currentSymbol);
                    } else if (Character.isDigit(currentSymbol)) {
                        currentState = State.CONSTANT;
                        nextLexeme.append(currentSymbol);
                    }else if (currentSymbol == ';') {
                        currentState = State.DELIMITER;
                        nextLexeme.append(currentSymbol);
                    } else {
                        currentState = State.ERROR;
                        canAdd = false;
                    }
                }
                case CONSTANT -> {
                    if (Character.isWhitespace(currentSymbol)) {
                        currentState = State.START;
                    } else if (Character.isDigit(currentSymbol)) {
                        canAdd = false;
                        currentState = State.CONSTANT;
                        currentLexeme.append(currentSymbol);
                    } else if (currentSymbol == '<' ||
                            currentSymbol == '>') {
                        currentState = State.COMPARISON;
                        nextLexeme.append(currentSymbol);
                    } else if (currentSymbol == '=') {
                        currentState = State.ASSIGMENT;
                        nextLexeme.append(currentSymbol);
                    } else if (currentSymbol == '+' || currentSymbol == '-'
                            || currentSymbol == '*' || currentSymbol
                            == '/') {
                        currentState = State.ARITHMETIC;
                        nextLexeme.append(currentSymbol);
                    }else if (currentSymbol == ';') {
                        currentState = State.DELIMITER;
                        nextLexeme.append(currentSymbol);
                    } else {
                        currentState = State.ERROR;
                        canAdd = false;
                    }
                }
                case IDENTIFIER -> {
                    if (Character.isWhitespace(currentSymbol)) {
                        currentState = State.START;
                    } else if (Character.isDigit(currentSymbol)
                            || Character.isLetter(currentSymbol)) {
                        currentState = State.IDENTIFIER;
                        canAdd = false;
                        currentLexeme.append(currentSymbol);
                    } else if (currentSymbol == '>' ||
                            currentSymbol == '<') {
                        currentState = State.COMPARISON;
                        nextLexeme.append(currentSymbol);
                    } else if (currentSymbol == '=') {
                        currentState = State.ASSIGMENT;
                        nextLexeme.append(currentSymbol);
                    } else if (currentSymbol == '+' ||
                            currentSymbol == '-' || currentSymbol == '*' || currentSymbol
                            == '/') {
                        currentState = State.ARITHMETIC;
                        nextLexeme.append(currentSymbol);
                    }else if (currentSymbol == ';') {
                        currentState = State.DELIMITER;
                        nextLexeme.append(currentSymbol);
                    } else {
                        currentState = State.ERROR;
                        canAdd = false;
                    }
                }
                case ARITHMETIC -> {
                    if (Character.isWhitespace(currentSymbol)) {
                        currentState = State.START;
                    } else if (Character.isLetter(currentSymbol)) {
                        currentState = State.IDENTIFIER;
                        nextLexeme.append(currentSymbol);
                    } else if (Character.isDigit(currentSymbol)) {
                        currentState = State.CONSTANT;
                        nextLexeme.append(currentSymbol);
                    } else if (currentSymbol == '-' ||
                            currentSymbol == '+' || currentSymbol == '*' || currentSymbol
                            == '/') {
                        currentState = State.ARITHMETIC;
                        nextLexeme.append(currentSymbol);
                    }else if (currentSymbol == ';') {
                        currentState = State.DELIMITER;
                        nextLexeme.append(currentSymbol);
                    } else {
                        currentState = State.ERROR;
                        canAdd = false;
                    }
                }
                case DELIMITER -> {
                    if(Character.isWhitespace(currentSymbol)) {
                        currentState = State.START;
                    } else if (Character.isLetter(currentSymbol)) {
                        currentState = State.IDENTIFIER;
                        nextLexeme.append(currentSymbol);
                    } else if (Character.isDigit(currentSymbol)) {
                        currentState = State.CONSTANT;
                        nextLexeme.append(currentSymbol);
                    } else if (currentSymbol == ';') {
                        currentState = State.DELIMITER;
                        currentLexeme.append(currentSymbol);
                    } else if (currentSymbol == '=') {
                        currentState = State.COMPARISON;
                        nextLexeme.append(currentSymbol);
                    } else if (currentSymbol == '-' || currentSymbol == '+' || currentSymbol == '*' || currentSymbol == '/') {
                        currentState = State.ARITHMETIC;
                        nextLexeme.append(currentSymbol);
                    } else {
                        currentState = State.ERROR;
                        canAdd = false;
                    }
                }
            }
            if (currentState == State.ERROR) {
                throw new IllegalArgumentException("Ошибка при обработке лексемы:" + currentLexeme + nextLexeme + " Позиция начала ошибки "  + " " + index);
            }
            if (canAdd) {
                lexemeEnd = index - 1;
                addLexeme(previousState, currentLexeme.toString(), lexemeStart, lexemeEnd);
                currentLexeme = new StringBuilder(nextLexeme.toString());
                nextLexeme = new StringBuilder();
            }
            index++;
        }
        return lexemes;
    }

    private void clearLexemes() {
        lexemes.clear();
    }

    private void addLexeme(State prevState, String value, int startIndex, int endIndex) {
        LexemeType lexemeType = LexemeType.UNDEFINED;
        LexemeCategory lexemeCategory = LexemeCategory.UNDEFINED;
        if (prevState == State.ARITHMETIC) {
            lexemeType = LexemeType.ARITHMETIC;
            lexemeCategory = LexemeCategory.SPECIAL_SYMBOL;
        } else if (prevState == State.ASSIGMENT) {
            lexemeCategory = LexemeCategory.SPECIAL_SYMBOL;
            if (Objects.equals(value, "==")) {
                lexemeType = LexemeType.RELATION;
            } else {
                lexemeType = LexemeType.ASSIGMENT;
            }
        } else if (prevState == State.CONSTANT) {
            lexemeType = LexemeType.UNDEFINED;
            lexemeCategory = LexemeCategory.CONSTANT;
        } else if (prevState == State.COMPARISON) {
            lexemeType = LexemeType.RELATION;
            lexemeCategory = LexemeCategory.SPECIAL_SYMBOL;
        } else if (prevState == State.DELIMITER) {
            lexemeType = LexemeType.DELIMITER;
            lexemeCategory = LexemeCategory.SPECIAL_SYMBOL;
        }else if (prevState == State.IDENTIFIER) {
            boolean isKeyword = true;
            if (value.equalsIgnoreCase("if")) {
                lexemeType = LexemeType.IF;
            } else if (value.equalsIgnoreCase("then")) {
                lexemeType = LexemeType.THEN;
            } else if (value.equalsIgnoreCase("elseif")) {
                lexemeType = LexemeType.ELSEIF;
            } else if (value.equalsIgnoreCase("else")) {
                lexemeType = LexemeType.ELSE;
            } else if (value.equalsIgnoreCase("output")) {
                lexemeType = LexemeType.OUTPUT;
            } else if (value.equalsIgnoreCase("not")) {
                lexemeType = LexemeType.NOT;
            } else if (value.equalsIgnoreCase("end")) {
                lexemeType = LexemeType.END;
            } else if (value.equalsIgnoreCase("and")) {
                lexemeType = LexemeType.AND;
            } else if (value.equalsIgnoreCase("or")) {
                lexemeType = LexemeType.OR;
            } else {
                lexemeType = LexemeType.UNDEFINED;
                isKeyword = false;
            }
            if (isKeyword) {
                lexemeCategory = LexemeCategory.KEYWORD;
            } else {
                lexemeCategory = LexemeCategory.IDENTIFIER;
            }
        }
        Lexeme lexeme = new Lexeme(lexemeType, lexemeCategory, value, startIndex, endIndex);
        if (!lexeme.value().isEmpty()) {
            lexemes.add(lexeme);
        }

    }
}