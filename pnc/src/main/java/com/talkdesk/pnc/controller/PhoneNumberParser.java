package com.talkdesk.pnc.controller;

import java.io.*;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class PhoneNumberParser {

    private Map<Integer, Integer> areaCodesCounter;

    public PhoneNumberParser() {
        loadAreaCodes();
    }

    private void loadAreaCodes() {
        final InputStream in = getClass().getClassLoader().getResourceAsStream("area_codes.txt");
        try (BufferedReader br = new BufferedReader(new InputStreamReader(in))) {
            String line;
            areaCodesCounter = new TreeMap<>();
            while ((line = br.readLine()) != null) {
                areaCodesCounter.put(Integer.parseInt(line), Integer.valueOf(0));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get only the areacodes that have one or more occurrence
     */
    public Map<Integer, Integer> getAreaCodesCounter() {
        return areaCodesCounter.entrySet().stream()
                .filter(p -> p.getValue() > 0)
                .sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (k1, k2) -> k1, TreeMap::new));
    }

    public void parseFile(final String fileName) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                parsePhoneNumber(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Parse a phone number. First check if its valid and then increment its area code if it exists
     *
     * @param phoneNumber
     */
    void parsePhoneNumber(final String phoneNumber) {
        if (isValidPhoneNumber(phoneNumber)) {
            incrementAreaCode(cleanPhoneNumber(phoneNumber));
        }
    }

    /**
     * First check if the phone number is valid (only numbers and correct placement of 00/+ and spaces
     * Afterwards, clean up the phoneNumber to have only the actual phoneNumber
     * Then, check the length of the phoneNumber.
     * Afterwards, return a simplified phoneNumber if the number is valid, null otherwise
     */
    boolean isValidPhoneNumber(final String phoneNumber) {
        final boolean isValidFormat = phoneNumber != null ? phoneNumber.matches("^\\s*(\\+[0-9][0-9 ]+|[0-9 ]+)\\s*$") : false;

        if (isValidFormat) {
            final String cleanedPhoneNumber = cleanPhoneNumber(phoneNumber);
            final int length = cleanedPhoneNumber.length();
            if (length == 3 || 7 <= length && length <= 12) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns a "clean" phone Number.
     * A clean number is a phone number without spaces, + and 00
     *
     * @param phoneNumber
     * @return
     */
    String cleanPhoneNumber(final String phoneNumber) {
        String cleanedPhoneNumber = phoneNumber.replaceAll(" ", "");
        if (cleanedPhoneNumber.startsWith("+")) {
            cleanedPhoneNumber = cleanedPhoneNumber.substring(1);
        }
        if (cleanedPhoneNumber.startsWith("00")) {
            cleanedPhoneNumber = cleanedPhoneNumber.substring(2);
        }
        return cleanedPhoneNumber;
    }

    /**
     * Update the areaCodesCounter if the areaCode of the cleanedPhoneNumber exists
     *
     * @param cleanPhoneNumber
     */
    void incrementAreaCode(final String cleanPhoneNumber) {
        final Integer areaCode3Digit;
        if (cleanPhoneNumber.length() == 3) {
            areaCode3Digit = Integer.parseInt(cleanPhoneNumber);
        } else {
            areaCode3Digit = Integer.parseInt(cleanPhoneNumber.substring(0, 3));
        }
        final Integer areaCode2Digit = Integer.parseInt(cleanPhoneNumber.substring(0, 2));
        final Integer areaCode1Digit = Integer.parseInt(cleanPhoneNumber.substring(0, 1));
        if (areaCodesCounter.containsKey(areaCode3Digit)) {
            areaCodesCounter.put(areaCode3Digit, areaCodesCounter.get(areaCode3Digit) + 1);
        } else if (areaCodesCounter.containsKey(areaCode2Digit)) {
            areaCodesCounter.put(areaCode2Digit, areaCodesCounter.get(areaCode2Digit) + 1);
        } else if (areaCodesCounter.containsKey(areaCode1Digit)) {
            areaCodesCounter.put(areaCode1Digit, areaCodesCounter.get(areaCode1Digit) + 1);
        }
    }
}
