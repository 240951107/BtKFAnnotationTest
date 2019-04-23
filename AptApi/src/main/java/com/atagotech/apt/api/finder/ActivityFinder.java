package com.atagotech.apt.api.finder;

import android.app.Activity;
import android.content.Context;
import android.view.View;

/**
 * Created by Tom on 2019/4/23.
 * 一句话描述：
 */
public class ActivityFinder implements Finder {
  @Override public Context getContext(Object source) {
    return (Activity) source;
  }

  @Override public View findView(Object source, int id) {
    return ((Activity) source).findViewById(id);
  }
}
