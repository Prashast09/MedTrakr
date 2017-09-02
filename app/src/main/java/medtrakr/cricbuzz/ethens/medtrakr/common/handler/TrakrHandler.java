package medtrakr.cricbuzz.ethens.medtrakr.common.handler;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import medtrakr.cricbuzz.ethens.medtrakr.common.lambda.VoidCallback;
import medtrakr.cricbuzz.ethens.medtrakr.utils.MathUtils;

/**
 * Created by ethens on 02/09/17.
 */

public class TrakrHandler {


  public TrakrHandler(VoidCallback voidCallback) {
    getHandler(false).post(voidCallback::onResponse);
  }


  private Handler getHandler(boolean runOnUiThread) {
    Looper looper;
    int randomId = MathUtils.randomWithRange(0, 100);
    if (!runOnUiThread) {
      HandlerThread handlerThread = new HandlerThread("trakrThread" + randomId);
      handlerThread.start();
      looper = handlerThread.getLooper();
    } else {
      looper = Looper.getMainLooper();
    }

    return new Handler(looper) {
      @Override public void handleMessage(Message msg) {
        // Process received messages here!
      }
    };
  }

}
