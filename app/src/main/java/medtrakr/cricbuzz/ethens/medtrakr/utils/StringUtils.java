package medtrakr.cricbuzz.ethens.medtrakr.utils;

/**
 * Created by ethens on 02/09/17.
 */

public class StringUtils {

  /**
   * @param input Input String
   * @return Is String blank or null
   */
  public static boolean isBlank(String input) {
    int strLen;
    if (input == null || input.equals("null") || (strLen = input.length()) == 0) {
      return true;
    }
    for (int i = 0; i < strLen; i++) {
      if (!Character.isWhitespace(input.charAt(i))) {
        return false;
      }
    }
    return true;
  }
}
