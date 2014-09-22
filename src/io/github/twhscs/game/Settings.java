package io.github.twhscs.game;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class Settings {
  public static HashMap<String, String> List = new HashMap<String, String>();
  public static void grabSettings() throws FileNotFoundException {
    Scanner sc = new Scanner(new File("resources/mainSettings.dat"));
    while (sc.hasNextLine()) {
      String[] curLine = sc.nextLine().split(":");
      List.put(curLine[0], curLine[1]);
    }
    sc.close();
  }
}