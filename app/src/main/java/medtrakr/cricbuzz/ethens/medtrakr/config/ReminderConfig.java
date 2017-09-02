package medtrakr.cricbuzz.ethens.medtrakr.config;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import java.util.Date;

/**
 * Created by ethens on 01/09/17.
 */

public class ReminderConfig implements Parcelable {
  /**
   * name, dosage, date, time
   */

  private String reminderText;
  private String reminderId;
  private String reminderType;
  private Date reminderTime;
  private int recurring;
  private String reminderDisplayDay;
  private String reminderDisplayMonth;
  private String intentId;
  private String dosage;
  private Boolean medTaken;

  public ReminderConfig(){}

  public ReminderConfig(Parcel in) {
    reminderText = in.readString();
    reminderId = in.readString();
    reminderType = in.readString();
    long tmpReminderTime = in.readLong();
    this.reminderTime = tmpReminderTime == -1 ? null : new Date(tmpReminderTime);
    recurring = in.readInt();
    reminderDisplayDay = in.readString();
    reminderDisplayMonth = in.readString();
    intentId = in.readString();
    dosage = in.readString();
    this.medTaken = in.readByte() != 0;

  }

  public static final Creator<ReminderConfig> CREATOR = new Creator<ReminderConfig>() {
    @Override public ReminderConfig createFromParcel(Parcel in) {
      return new ReminderConfig(in);
    }

    @Override public ReminderConfig[] newArray(int size) {
      return new ReminderConfig[size];
    }
  };

  public String getReminderText() {
    return reminderText;
  }

  public void setReminderText(String reminderText) {
    this.reminderText = reminderText;
  }

  public String getReminderId() {
    return reminderId;
  }

  public void setReminderId(String reminderId) {
    this.reminderId = reminderId;
  }

  public String getReminderType() {
    return reminderType;
  }

  public void setReminderType(String reminderType) {
    this.reminderType = reminderType;
  }

  public Date getReminderTime() {
    return reminderTime;
  }

  public void setReminderTime(Date reminderTime) {
    this.reminderTime = reminderTime;
  }

  public int getRecurring() {
    return recurring;
  }

  public void setRecurring(int recurring) {
    this.recurring = recurring;
  }

  public String getReminderDisplayDay() {
    return reminderDisplayDay;
  }

  public void setReminderDisplayDay(String reminderDisplayDay) {
    this.reminderDisplayDay = reminderDisplayDay;
  }

  public String getReminderDisplayMonth() {
    return reminderDisplayMonth;
  }

  public void setReminderDisplayMonth(String reminderDisplayMonth) {
    this.reminderDisplayMonth = reminderDisplayMonth;
  }

  public String getIntentId() {
    return intentId;
  }

  public void setIntentId(String intentId) {
    this.intentId = intentId;
  }

  public String getDosage() {
    return dosage;
  }

  public void setDosage(String dosage) {
    this.dosage = dosage;
  }

  public Boolean getMedTaken() {
    return medTaken;
  }

  public void setMedTaken(Boolean medTaken) {
    this.medTaken = medTaken;
  }

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel parcel, int i) {
    parcel.writeString(reminderText);
    parcel.writeString(reminderId);
    parcel.writeString(reminderType);
    parcel.writeLong(this.reminderTime != null ? this.reminderTime.getTime() : -1);
    parcel.writeInt(recurring);
    parcel.writeString(reminderDisplayDay);
    parcel.writeString(reminderDisplayMonth);
    parcel.writeString(intentId);
    parcel.writeString(dosage);
    parcel.writeByte(this.medTaken ? (byte) 1 : (byte) 0);
  }

  public int getRecurringType() {
    return recurring;
  }

}
