package com.zac4j.zwallet.util;

import java.security.MessageDigest;

/**
 * Utilities
 * Created by zac on 16-7-3.
 */

public class Utils {

  public static final String ACCESS_KEY = "0afd3df5-bc3dfe76-e749a82f-78c61";

  private static final String SECRET_KEY = "47922755-48ad16d8-6099bf62-46f15";

  public static String generateSign(String methodName, String currentTime) {
    String originalText = "access_key=" + ACCESS_KEY +
        "&created=" + currentTime + "&method=" + methodName +
        "&secret_key=" + SECRET_KEY;
    return md5(originalText);
  }

  public static String generateSign(String methodName,String coinType,String currentTime) {
    String originalText = "access_key=" + ACCESS_KEY + "&coin_type=" + coinType +
        "&created=" + currentTime + "&method=" + methodName +
        "&secret_key=" + SECRET_KEY;
    return md5(originalText);
  }

  /**
   * Transform delta time millis to x min ago style
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

}
