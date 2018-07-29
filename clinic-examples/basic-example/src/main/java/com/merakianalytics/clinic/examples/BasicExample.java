package com.merakianalytics.clinic.examples;

import com.merakianalytics.clinic.Clinic;
import com.merakianalytics.clinic.annotations.Command;
import com.merakianalytics.clinic.annotations.Option;

public class BasicExample {
    @Command
    public static void add(@Option(required = true) final int x, @Option(required = true) final int y) {
        System.out.println(x + y);
    }

    @Command
    public static void multiply(@Option(required = true) final int[] values) {
        int product = 1;
        for(final int value : values) {
            product *= value;
        }
        System.out.println(product);
    }

    @Command
    public static void print(@Option(defaultString = "Hello, world!") final String message) {
        System.out.println(message);
    }
    
    public static void main(final String[] args) {
        System.exit(Clinic.cli(BasicExample.class).args(args).executableName("basic-example").run());
    }
}
