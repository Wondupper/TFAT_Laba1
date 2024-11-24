package org.example.core;

public enum State {
    START,
    IDENTIFIER,
    CONSTANT,
    ERROR,
    COMPARISON,
    ARITHMETIC_LOW,
    ARITHMETIC_HIGH,
    ASSIGMENT,
    DELIMITER,
    FINAL
}