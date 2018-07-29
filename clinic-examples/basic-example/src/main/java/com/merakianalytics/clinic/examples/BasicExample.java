package com.merakianalytics.clinic.examples;

import com.merakianalytics.clinic.Clinic;
import com.merakianalytics.clinic.annotations.Command;
import com.merakianalytics.clinic.annotations.Option;

public class BasicExample {
    @Command(help = "Adds two numbers")
    public static void add(@Option(required = true) final int x, @Option(required = true) final int y) {
        System.out.println(x + y);
    }

    @Command(help = "Multiplies a bunch of numbers")
    public static void multiply(@Option(help = "the numbers to multiply together", required = true) final int[] values) {
        int product = 1;
        for(final int value : values) {
            product *= value;
        }
        System.out.println(product);
    }

    @Command(help = "Prints a message")
    public static void print(@Option(help = "the message to print", defaultString = "Hello, world!") final String message) {
        System.out.println(message);
    }
    
    public static void main(final String[] args) {
        System.exit(Clinic.cli(BasicExample.class).executableName("basic-example").help("A basic example of using clinic").args(args).run());
    }
}
