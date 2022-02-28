package com.evilcorp.stp;

import java.util.Scanner;

public class Application {

    public static void main(String[] args) {
        var scan = new Scanner(System.in);
        var visitor = new Visitor();
        var timeObserver = new TimeObserver();
        visitor.register(timeObserver);
        while (scan.hasNextLine()) {
            var line = scan.nextLine();
            if (line.equals("quit")) {

                break;
            }
            STPParser.parse(line).ifPresentOrElse(v -> v.accept(visitor),
                    () -> System.out.println("Unrecognized command"));
        }
        timeObserver.displayInfo();
    }
}
