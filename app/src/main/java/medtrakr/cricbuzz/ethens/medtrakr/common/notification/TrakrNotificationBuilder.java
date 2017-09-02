package medtrakr.cricbuzz.ethens.medtrakr.common.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import java.io.InputStream;
import java.net.URL;
import medtrakr.cricbuzz.ethens.medtrakr.R;
import medtrakr.cricbuzz.ethens.medtrakr.utils.StringUtils;

/**
 * Created by ethens on 02/09/17.
 */

public class TrakrNotificationBuilder {

  //public static final String EXTRA_NOTIFICATION_ID = "NotificationId";

  /**
   * @param context context
   * @param targetIntent Target Intent
   * @param title Title of Notification
   * @param message Message in Notification
   * @param notificationId Notification ID
   * pass
   * null.
   */
  public static void buildNotification(Context context, Intent targetIntent, String title,
      String message, int notificationId,
      boolean isAnActivityAction) {
    NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
    inboxStyle.setBigContentTitle(title);
    inboxStyle.addLine(message);
    Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

    Bitmap largeIcon = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher);
    NotificationCompat.Builder mBuilder =
        new NotificationCompat.Builder(context).setLargeIcon(largeIcon)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(title)
            .setColor(ContextCompat.getColor(context, R.color.trakr))
            .setAutoCancel(true)
            .setTicker(message)
            .setContentText(message)
            .setPriority(2)
            .setWhen(0)
            .setSound(defaultSoundUri)
            .setStyle(
                new NotificationCompat.BigTextStyle().bigText(message).setBigContentTitle(title));

    PendingIntent contentIntent;

    if (isAnActivityAction) {
      contentIntent =
          PendingIntent.getActivity(context, (int) System.currentTimeMillis(), targetIntent,
              PendingIntent.FLAG_UPDATE_CURRENT);
    } else {
      contentIntent =
          PendingIntent.getBroadcast(context, (int) System.currentTimeMillis(), targetIntent,
              PendingIntent.FLAG_UPDATE_CURRENT);
    }

    mBuilder.setContentIntent(contentIntent);
    mBuilder.setDefaults(Notification.DEFAULT_SOUND);
    NotificationManager mNotificationManager = (NotificationManager) context.
        getSystemService(Context.NOTIFICATION_SERVICE);
    mNotificationManager.notify(notificationId, mBuilder.build());
  }


}