package com.evilcorp.stp;

import java.io.IOException;
import java.util.Scanner;

public class Trivial {

    public static void main(String[] args) throws IOException {

        Scanner clavier = new Scanner(System.in);
        Visitor v = new Visitor();

        while(clavier.hasNextLine()){
            String input = clavier.nextLine();

            var command = STPParser.parse(input);

            if (command.isPresent()) {
                if (command.get() instanceof HelloCmd) {
                    command.get().accept(v);
                } else {
                    command.get().accept(v);
                }
            } else {
                System.out.println("Don't understood");
            }
        }
    }
}
