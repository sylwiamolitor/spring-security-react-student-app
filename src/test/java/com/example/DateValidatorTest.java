package com.example;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DateValidatorTest {

    @ParameterizedTest
    @ValueSource(strings = {"2020-02-19", "1999-01-10", "2000-12-31", "2023-05-01"})
    public void givenValidDate_whenValidateDate_thenReturnTrue(String input) {
        assertTrue(DateValidator.isValid(input), "The date should be valid: " + input);
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "null"})
    public void givenEmptyOrNullDate_whenValidateDate_thenReturnFalse(String input) {
        assertFalse(DateValidator.isValid(input), "The date should be invalid: " + input);
    }

    @ParameterizedTest
    @ValueSource(strings = {"2020/02/19", "1999.01.10", "2021,13,01"})
    public void givenDateWithWrongDelimiter_whenValidateDate_thenReturnFalse(String input) {
        assertFalse(DateValidator.isValid(input), "The date should be invalid due to wrong delimiter: " + input);
    }
}
