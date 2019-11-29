package com.freestyler.classnotebook.criss.helper;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.util.SparseArray;

/**
 * Created by Javad on 2018-04-08 at 11:06 PM.
 */

public class MyPermissionHandler {

  private static SparseArray<RequestListener> listeners = new SparseArray<>();

  private static int taskCounter;
  private static int taskSize;

  public interface RequestListener {
    void onGranted(String permission);

    void onDenied(String permission);

    void onTaskDone();
  }

  public void request(Activity activity, RequestListener listener) {
    try {
      PackageInfo packageInfo = activity.getPackageManager().getPackageInfo(activity.getPackageName(), PackageManager.GET_PERMISSIONS);
      request(activity, packageInfo.requestedPermissions, listener);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }


  public void request(Activity activity, String[] permissions, RequestListener listener) {
    taskSize = permissions.length;
    taskCounter = 0;
    int requestCode = 100;
    for (String permission : permissions) {
      request(activity, permission, listener, ++requestCode);
    }
  }

  public void request(Activity activity, String permission, RequestListener listener) {
    request(activity, permission, listener, 100);
  }

  private void request(Activity activity, String permission, RequestListener listener, int requestCode) {
    listeners.put(requestCode, listener);

    Log.i("LOG", "" + permission + " " + taskCounter + " " + taskSize);
    int granted = ActivityCompat.checkSelfPermission(activity, permission);
    if (granted == PackageManager.PERMISSION_DENIED) {
      ActivityCompat.requestPermissions(activity, new String[]{permission}, requestCode);
    } else {
      listener.onGranted(permission);
      taskCounter++;
    }
    Log.e("LOG", "" + permission + " " + taskCounter + " " + taskSize);
    taskCalculate(listener);
  }

  public static void onRequestPermissionResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    try {
      if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
        listeners.get(requestCode).onGranted(permissions[0]);
      } else {
        listeners.get(requestCode).onDenied(permissions[0]);
      }
    } catch (IndexOutOfBoundsException e) {
      e.printStackTrace();
    }
    taskCounter++;
    taskCalculate(listeners.get(requestCode));
  }

  private static void taskCalculate(RequestListener listener) {
    if (taskCounter == taskSize) {
      listener.onTaskDone();
    }
  }
}