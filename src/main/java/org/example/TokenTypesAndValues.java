package org.example;

import java.util.Map;
import java.util.Set;

public class TokenTypesAndValues {
    static Map<TokenType, Set<String>> mapOfComparsionOperators = Map.of(
            TokenType.COMPARISON_OPERATOR,Set.of("<",">","=","<>")
    );

    static Map<TokenType, Set<String>> mapOfUnaryLogicalOperators = Map.of(
            TokenType.UNARY_LOGICAL_OPERATION,Set.of("not")
    );

    static Map<TokenType, Set<String>> mapOfBinaryLogicalOperators = Map.of(
            TokenType.BINARY_LOGICAL_OPERATION,Set.of("and","or")
    );

    static Map<TokenType, Set<String>> mapOfArithmeticOperations = Map.of(
            TokenType.ARITHMETIC_OPERATION,Set.of("+","-","*","/")
    );
}
