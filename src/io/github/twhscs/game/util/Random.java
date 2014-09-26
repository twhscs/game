package io.github.twhscs.game.util;

/**
 * Static class to generate a random number within a given range.
 * WHY IS THIS NOT BUILT IN?
 * @author Robert
 *
 */
public class Random {
  
  // I admit I am weak Mr. Smith
  
  /**
   * Return a random integer within the given range.
   * @param min The minimum value as an integer.
   * @param max The maximum value as an integer.
   * @return A random integer between the minimum and maximum.
   */
  public static int intRange(int min, int max) {
    return min + (int) (Math.random() * ((max - min) + 1));
  }
  
  /**
   * Return a random double within the given range.
   * @param min The minimum value as a double.
   * @param max The maximum value as a double.
   * @return A random double between the minimum and maximum.
   */
  public static double doubleRange(double min, double max) {
    return min + (Math.random() * ((max - min) + 1));
  }
}
