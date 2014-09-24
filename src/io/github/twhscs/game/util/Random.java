package io.github.twhscs.game.util;

/**
 * Static class to generate a random number within a given range.
 * WHY IS THIS NOT BUILT IN?
 * @author Robert
 *
 */
public class Random {
  
  // I admit I am weak Mr. Smith
  
  public static int intRange(int min, int max) {
    return min + (int) (Math.random() * ((max - min) + 1));
  }
  
  public static double doubleRange(double min, double max) {
    return min + (Math.random() * ((max - min) + 1));
  }
}
