package medtrakr.cricbuzz.ethens.medtrakr.common.widget;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import medtrakr.cricbuzz.ethens.medtrakr.R;

/**
 * Created by ethens on 02/09/17.
 */

public class TwoButtonSimpleDialog {
  public TwoButtonSimpleDialog(AppCompatActivity activity, String title, String message,
      final TwoButtonDialogListener twoButtonDialogListener) {

    this(activity, title, message, "OK", "Cancel", twoButtonDialogListener);
  }

  public TwoButtonSimpleDialog(AppCompatActivity activity, String title, String message,
      String positiveMessage, String negativeMessage,
      final TwoButtonDialogListener twoButtonDialogListener) {

    AlertDialog dialog = getDialog(activity, title, message).setPositiveButton(positiveMessage,
        (dialog1, which) -> twoButtonDialogListener.onAccept())
        .setNegativeButton(negativeMessage, (dialog12, which) -> twoButtonDialogListener.onReject())
        .create();
    dialog.show();
  }

  private AlertDialog.Builder getDialog(AppCompatActivity activity, String title, String message) {
    return new AlertDialog.Builder(activity).setTitle(
        Html.fromHtml("<font color='#000000'>" + title + "</font>"))
        .setMessage(Html.fromHtml("<font color='#000000'>" + message + "</font>"));
  }

}
