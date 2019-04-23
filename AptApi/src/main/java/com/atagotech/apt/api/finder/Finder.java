package com.atagotech.apt.api.finder;

import android.content.Context;
import android.view.View;

/**
 * Created by Tom on 2019/4/23.
 * 一句话描述：
 */
public interface Finder {
  Context getContext(Object source);

  View findView(Object source, int id);
}
