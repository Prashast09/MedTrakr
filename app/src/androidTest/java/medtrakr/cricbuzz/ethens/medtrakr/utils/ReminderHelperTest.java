package medtrakr.cricbuzz.ethens.medtrakr.utils;

import android.content.Context;
import java.util.List;
import medtrakr.cricbuzz.ethens.medtrakr.config.ReminderConfig;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;

/**
 * Created by ethens on 02/09/17.
 */
public class ReminderHelperTest {

  private Context context = Mockito.mock(Context.class);

  private List<ReminderConfig> reminderConfigList;
  ReminderHelper reminderUtilss = new ReminderHelper();

  @Before public void setup() {

    //reminderConfigList = NotificationTestBuilder.getRhsReminderConfigList(context);

  }

  @Test public void getReminderRepeatTextTest() {

    int test1 = reminderConfigList.get(0).getRecurringType();
    int test2 = reminderConfigList.get(2).getRecurringType();
    assertEquals(1, reminderUtilss.getReminderRepeatText(test1));
    assertEquals(3, reminderUtilss.getReminderRepeatText(test2));
  }

  @Test public void validateDateTest() {
    ReminderConfig reminderConfig = reminderConfigList.get(3);
    ReminderConfig reminderConfig1 = reminderConfigList.get(0);
    assertEquals(true, reminderUtilss.validateData(reminderConfig));
    assertEquals(false, reminderUtilss.validateData(reminderConfig1));
  }
}
