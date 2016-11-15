package ch.heigvd.amt.prl.lab1.utils;

/**
 * String utilities
 * 
 * @author Laurent Prevost, laurent.prevost@heig-vd.ch
 */
public class StringUtils {
  /**
   * Check if a string is null or empty
   * 
   * @param str The string to check
   * @return True if the string is null or empty
   */
  public static boolean isEmpty(String str) {
    return str == null || str.isEmpty();
  }
  
  /**
   * Quick solution to replace the last occurrence of a pattern in a string.
   * 
   * Solution from: http://stackoverflow.com/a/2282998
   * 
   * @param text The text where to replace the last occurrence
   * @param regex The pattern to find
   * @param replacement The replacing string
   * @return The new string
   */
  public static String replaceLast(String text, String regex, String replacement) {
    return text.replaceFirst("(?s)(.*)" + regex, "$1" + replacement);
  }
}
