package com.atagotech.apt.api;

import android.app.Activity;
import com.atagotech.apt.api.finder.ActivityFinder;
import com.atagotech.apt.api.finder.Finder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by tangpengfei on 2019/2/28.
 * 一句话描述：
 */
public class CheckWidgetInjector {
  private static final ActivityFinder ACTIVITY_FINDER = new ActivityFinder();
  private static final Map<String, Injector> FINDER_MAP = new HashMap<>();

  public static boolean inject(Activity activity) {
    return inject(activity, activity, ACTIVITY_FINDER);
  }

  public static boolean inject(Object host, Object source, Finder finder) {
    String className = host.getClass().getName();
    try {
      Injector injector = FINDER_MAP.get(className);
      if (injector == null) {
        Class<?> finderClass = Class.forName(className + "$$Check");
        injector = (Injector) finderClass.newInstance();
        FINDER_MAP.put(className, injector);
      }
      return injector.checkData(host);
    } catch (Exception e) {
      throw new RuntimeException("Unable to Check for " + className, e);
    }
  }
}
