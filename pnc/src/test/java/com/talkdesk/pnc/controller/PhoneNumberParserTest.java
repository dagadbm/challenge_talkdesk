package com.talkdesk.pnc.controller;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PhoneNumberParserTest {

    private PhoneNumberParser underTest;

    @Before
    public void setUp() {
        underTest = new PhoneNumberParser();
    }

    @Test
    public void phoneNumberWithLetters() throws Exception {
        //given
        final String phoneNumber = "123sb23   ZZASXBSAXAS";

        //when
        final boolean result = underTest.isValidPhoneNumber(phoneNumber);

        //then
        assertFalse(result);
    }

    @Test
    public void phoneNumberWithLettersStartingWithPlus() throws Exception {
        //given
        final String phoneNumber = "+123sb23   ZZASXBSAXAS";

        //when
        final boolean result = underTest.isValidPhoneNumber(phoneNumber);

        //then
        assertFalse(result);
    }

    @Test
    public void phoneNumberWithLettersStartingWithDoubleZero() throws Exception {
        //given
        final String phoneNumber = "0 012 3sb23   ZZA SXBSAXAS";

        //when
        final boolean result = underTest.isValidPhoneNumber(phoneNumber);

        //then
        assertFalse(result);
    }

    @Test
    public void phoneNumberWithWrongSymbols() throws Exception {
        //given
        final String phoneNumber = " 1 23s b23   ZZASXBSAXA-ç+º´~º´+º´~º,,.;_:;_.,-.,-*/-*+S";

        //when
        final boolean result = underTest.isValidPhoneNumber(phoneNumber);

        //then
        assertFalse(result);
    }

    @Test
    public void phoneNumberWithWrongSymbolsStartingWithPlus() throws Exception {
        //given
        final String phoneNumber = " +123sb23   ZZAS XBSAXA-ç+º´~º´+º ´~º,,.;_:;_., -.,-*/-*+S";

        //when
        final boolean result = underTest.isValidPhoneNumber(phoneNumber);

        //then
        assertFalse(result);
    }

    @Test
    public void phoneNumberWithWrongSymbolsStartingWithDoubleZero() throws Exception {
        //given
        final String phoneNumber = "0 091 23sb23   ZZA SXBSAXA-ç+º´~º´+º´~º,,.;_:;_.,-.,-*/-*+S";

        //when
        final boolean result = underTest.isValidPhoneNumber(phoneNumber);

        //then
        assertFalse(result);
    }

    @Test
    public void phoneNumberWithSpacesCorrectLengthEqual7() throws Exception {
        //given
        final String phoneNumber = " 123 456     7";

        //when
        final boolean result = underTest.isValidPhoneNumber(phoneNumber);

        //then
        assertTrue(result);
    }

    @Test
    public void phoneNumberWithSpacesCorrectLengthEqual12() throws Exception {
        //given
        final String phoneNumber = " 123 4  5  6     7 8 9 0  1 2";

        //when
        final boolean result = underTest.isValidPhoneNumber(phoneNumber);

        //then
        assertTrue(result);
    }

    @Test
    public void phoneNumberWithSpacesCorrectLengthBetween7And12() throws Exception {
        //given
        final String phoneNumber = " 1  2 3 4  5  6     7 8 9 ";

        //when
        final boolean result = underTest.isValidPhoneNumber(phoneNumber);

        //then
        assertTrue(result);
    }

    @Test
    public void phoneNumberWithSpacesIncorrectLengthLess7() throws Exception {
        //given
        final String phoneNumber = " 123 456     ";

        //when
        final boolean result = underTest.isValidPhoneNumber(phoneNumber);

        //then
        assertFalse(result);
    }

    @Test
    public void phoneNumberWithSpacesIncorrectLengthLess3() throws Exception {
        //given
        final String phoneNumber = "   1   2     ";

        //when
        final boolean result = underTest.isValidPhoneNumber(phoneNumber);

        //then
        assertFalse(result);
    }

    @Test
    public void phoneNumberWithDoubleZeroCorrectLengthEqual7() throws Exception {
        //given
        final String phoneNumber = " 00 123 456     7";

        //when
        final boolean result = underTest.isValidPhoneNumber(phoneNumber);

        //then
        assertTrue(result);
    }

    @Test
    public void phoneNumberWithDoubleZeroCorrectLengthEqual12() throws Exception {
        //given
        final String phoneNumber = "00123 4  5  6     7 8 9 0  1 2";

        //when
        final boolean result = underTest.isValidPhoneNumber(phoneNumber);

        //then
        assertTrue(result);
    }

    @Test
    public void phoneNumberWithDoubleZeroCorrectLengthBetween7And12() throws Exception {
        //given
        final String phoneNumber = "00123456789";

        //when
        final boolean result = underTest.isValidPhoneNumber(phoneNumber);

        //then
        assertTrue(result);
    }

    @Test
    public void phoneNumberWithDoubleZeroIncorrectLengthLess7() throws Exception {
        //given
        final String phoneNumber = "  0 0   123 456     ";

        //when
        final boolean result = underTest.isValidPhoneNumber(phoneNumber);

        //then
        assertFalse(result);
    }

    @Test
    public void phoneNumberWithSpacesBeforePlusCorrectLengthEqual3() throws Exception {
        //given
        final String phoneNumber = "    +1   2   3     ";

        //when
        final boolean result = underTest.isValidPhoneNumber(phoneNumber);

        //then
        assertTrue(result);
    }

    @Test
    public void phoneNumberWithSpacesBeforePlusCorrectLengthEqual7() throws Exception {
        //given
        final String phoneNumber = "    +1   2 3 4567 ";

        //when
        final boolean result = underTest.isValidPhoneNumber(phoneNumber);

        //then
        assertTrue(result);
    }

    @Test
    public void phoneNumberWithIncorrectPlusAfterPlus() throws Exception {
        //given
        final String phoneNumber = "    +1   2 3 4+567 ";

        //when
        final boolean result = underTest.isValidPhoneNumber(phoneNumber);

        //then
        assertFalse(result);
    }

    @Test
    public void phoneNumberWithIncorrectPlusAfterNoPlus() throws Exception {
        //given
        final String phoneNumber = "     1   2 3 4+567 ";

        //when
        final boolean result = underTest.isValidPhoneNumber(phoneNumber);

        //then
        assertFalse(result);
    }

    @Test
    public void phoneNumberWithIncorrectSymbolsAfterInitialPlus() throws Exception {
        //given
        final String phoneNumber = "     +1   2 3 4+5_+´´º~*-//*--*-+/-+/-.-,.-;:_;:_,.-,ª0}()/()79806&12$#%!&)/67 ";

        //when
        final boolean result = underTest.isValidPhoneNumber(phoneNumber);

        //then
        assertFalse(result);
    }

    @Test
    public void emptyPhoneNumber() throws Exception {
        //given
        final String phoneNumber = "";

        //when
        final boolean result = underTest.isValidPhoneNumber(phoneNumber);

        //then
        assertFalse(result);
    }

    @Test
    public void nullPhoneNumber() throws Exception {
        //given
        final String phoneNumber = null;

        //when
        final boolean result = underTest.isValidPhoneNumber(phoneNumber);

        //then
        assertFalse(result);
    }

    @Test
    public void smallestValidAreaCode() throws Exception {
        //given
        final String phoneNumber = "    2   3   2 ";

        //when
        underTest.incrementAreaCode(underTest.cleanPhoneNumber(phoneNumber));

        //then
        assertTrue(underTest.getAreaCodesCounter().size() == 1);
    }
}