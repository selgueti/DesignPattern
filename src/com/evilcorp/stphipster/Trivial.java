package com.evilcorp.stphipster;

import java.io.IOException;
import java.util.Scanner;

public class Trivial {

    public static void main(String[] args) throws IOException {

        Scanner clavier = new Scanner(System.in);
        var processor = STPCommandProcessor.createSTPCommandProcessor();

        while(clavier.hasNextLine()){
            String input = clavier.nextLine();

            var command = STPParser.parse(input);

            command.ifPresentOrElse(processor::process, () -> System.out.println("Pas compris"));

        }
    }
}
