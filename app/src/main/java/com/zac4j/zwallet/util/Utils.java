package com.zac4j.zwallet.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.support.v4.util.Pair;
import android.view.inputmethod.InputMethodManager;
import java.io.Closeable;
import java.io.IOException;
import java.security.MessageDigest;
import java.util.List;

/**
 * Utilities
 * Created by zac on 16-7-3.
 */

public class Utils {

  public static final String ACCESS_KEY = "2e0be253-3d1f9e86-977870bf-26124";

  private static final String SECRET_KEY = "2547271c-28a8ee11-f1d9a301-cf4fc";

  public static String generateSign(List<Pair<String, String>> pairs) {
    String result = "access_key=" + ACCESS_KEY;
    for (int i = 0; i < pairs.size(); i++) {
      result += pairs.get(i).first + pairs.get(i).second;
    }
    result += "&secret_key=" + SECRET_KEY;

    System.out.println("result: " + result);

    return md5(result);
  }

  /**
   * Transform delta time millis to x min ago style
   *
   * @param deltaTime delta time
   * @return new style time
   */
  public static String generateTime(long deltaTime) {
    String result;
    if (deltaTime < 60) {
      result = "Just Now!";
    } else if (deltaTime < 3600) {
      result = (deltaTime / 60) + " min ago";
    } else if (deltaTime < 24 * 3600) {
      result = (deltaTime / 3600) + " hour ago";
    } else {
      result = (deltaTime / 24 / 3600) + " day ago";
    }
    return result;
  }

  public static String md5(final String originalText) {
    try {
      final MessageDigest digest = MessageDigest.getInstance("md5");
      digest.update(originalText.getBytes());
      final byte[] bytes = digest.digest();
      final StringBuilder sb = new StringBuilder();
      for (byte aByte : bytes) {
        sb.append(String.format("%02X", aByte));
      }
      return sb.toString().toLowerCase();
    } catch (Exception exc) {
      return "";
    }
  }

  public static void close(Closeable closeable) {
    if (closeable != null) {
      try {
        closeable.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  public static float pxToDp(float px) {
    float densityDpi = Resources.getSystem().getDisplayMetrics().densityDpi;
    return px / (densityDpi / 160f);
  }

  public static int dpToPx(int dp) {
    float density = Resources.getSystem().getDisplayMetrics().density;
    return Math.round(dp * density);
  }

  public static void hideKeyboard(Activity activity) {
    InputMethodManager imm =
        (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
    imm.hideSoftInputFromWindow(activity.getWindow().getDecorView().getWindowToken(), 0);
  }

}
