package com.talkdesk.pnc.boundary;

import com.talkdesk.pnc.controller.PhoneNumberParser;

import java.util.Map;

public class Main {

    public static void main(String[] args) {
        if (args.length == 1) {
            final PhoneNumberParser p = new PhoneNumberParser();
            p.parseFile(args[0]);
            for (final Map.Entry<Integer, Integer> entry : p.getAreaCodesCounter().entrySet()) {
                System.out.println(entry.getKey() + ":" + entry.getValue());
            }
        }
    }
}
