package com.zac4j.zwallet.service;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.SystemClock;
import android.util.Log;

/**
 * Poll coin price background service
 * Created by zac on 16-7-18.
 */

public class PollService extends IntentService {

  private static final String TAG = "PollService";

  private static final long POLL_INTERVAL = 1000L * 60; // 60 seconds

  /**
   * As a matter of convention, each component wants to start this service should use newIntent.
   *
   * @param context ui context
   * @return PollService instance.
   */
  public static Intent newIntent(Context context) {
    return new Intent(context, PollService.class);
  }

  /**
   * Start poll service with interval time.
   * @param context ui context
   * @param turnOn turn alarm manager on
   */
  public static void setServiceAlarm(Context context, boolean turnOn) {
    Intent i = PollService.newIntent(context);
    PendingIntent pi = PendingIntent.getService(context, 0, i, 0);

    AlarmManager alarmMgr = (AlarmManager) context.getSystemService(ALARM_SERVICE);

    if (turnOn) {
      alarmMgr.setInexactRepeating(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime(),
          POLL_INTERVAL, pi);
    } else {
      alarmMgr.cancel(pi);
      pi.cancel();
    }
  }

  public static boolean isServiceAlarmOn(Context context) {
    Intent i = PollService.newIntent(context);
    PendingIntent pi = PendingIntent.getService(context, 0, i, PendingIntent.FLAG_NO_CREATE);
    return pi != null;
  }

  public PollService() {
    super(TAG);
  }

  @Override protected void onHandleIntent(Intent intent) {
    if (!isNetworkConnected()) {
      return;
    }
    Log.i(TAG, "onHandleIntent: " + intent);
  }

  /**
   * Verify the network is connected.
   *
   * @return true if the network is connected.
   */
  private boolean isNetworkConnected() {
    ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
    boolean isNetworkAvailable = cm.getActiveNetworkInfo() != null;
    return isNetworkAvailable && cm.getActiveNetworkInfo().isConnected();
  }
}
