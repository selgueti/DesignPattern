package com.evilcorp.stphipster;

import java.util.Scanner;

public class ApplicationHipster {

    public static void main(String[] args) {
        var scan = new Scanner(System.in);
        var processor = STPCommandProcessor.createSTPCommandProcessor();
        while(scan.hasNextLine()){
            var line = scan.nextLine();
            if (line.equals("quit")){
                break;
            }
            STPParser.parse(line).ifPresentOrElse(processor::process, () -> System.out.println("Unrecognized command"));
        }
    }
}
