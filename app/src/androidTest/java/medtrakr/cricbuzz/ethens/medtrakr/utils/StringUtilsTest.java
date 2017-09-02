package medtrakr.cricbuzz.ethens.medtrakr.utils;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by ethens on 02/09/17.
 */
public class StringUtilsTest {

  @Before public void setUp() {
  }

  @Test public void isBlankTest() {
    assertEquals(true, StringUtils.isBlank(null));
    assertEquals(false, StringUtils.isBlank("zbc"));
  }
}
