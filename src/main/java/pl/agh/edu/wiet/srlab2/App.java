package pl.agh.edu.wiet.srlab2;

import pl.agh.edu.wiet.srlab2.distributedmap.SimpleStringMap;
import pl.agh.edu.wiet.srlab2.distributedmap.impl.DistributedMap;

import java.util.Arrays;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.LogManager;

public class App {

    private static final String MENU =
            "---------------\n" +
            "[E] - EXIT\n" +
            "[A] - ADD ITEM\n" +
            "[R] - REMOVE ITEM\n" +
            "[P] - PRINT MAP\n" +
            "---------------";

    public static void main(String[] args) {
        System.setProperty("java.net.preferIPv4Stack", "true");
        System.setProperty("log4j.configurationFile", "classpath:log4j2.xml");


        SimpleStringMap map = new DistributedMap();

        Scanner scanner = new Scanner(System.in);

        while(true) {
            System.out.println(MENU);
            final String op = scanner.nextLine();
            switch(op) {
                case "E":
                    return;
                case "A":
                    addItem(scanner, map);
                    break;
                case "R":
                    removeItem(scanner, map);
                    break;
                case "P":
                    printMap(map);
                    break;
            }
        }
    }

    private static void addItem(Scanner scanner, SimpleStringMap map) {
        System.out.println("KEY: ");
        String key = scanner.nextLine();
        System.out.println("VALUE: ");
        String value= scanner.nextLine();
        map.put(key, value);
    }

    private static void removeItem(Scanner scanner, SimpleStringMap map) {
        System.out.println("KEY: ");
        String key = scanner.nextLine();
        map.remove(key);
    }

    private static void printMap(SimpleStringMap map) {
        System.out.println(map.toString());
    }
}
