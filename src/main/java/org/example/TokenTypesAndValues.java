package org.example;

import java.util.Map;
import java.util.Set;

public class TokenTypesAndValues {
    static Map<TokenType, Set<String>> mapOfComparsionOperators = Map.of(
            TokenType.COMPARISON_OPERATOR,Set.of("<",">","=","<>")
    );

    static Map<TokenType, Set<String>> mapOfArithmeticOperationsLow = Map.of(
            TokenType.ARITHMETIC_OPERATION_LOW_PRIORITY,Set.of("+","-")
    );

    static Map<TokenType, Set<String>> mapOfArithmeticOperationsHigh = Map.of(
            TokenType.ARITHMETIC_OPERATION_HIGH_PRIORITY,Set.of("*","/")
    );
}
