package com.atagotech.apt.compiler;

import com.squareup.javapoet.ClassName;

/**
 * Created by tangpengfei on 2019/2/28.
 * 一句话描述：
 */
public class CheckTypeUitls {
  public static final ClassName FINDER = ClassName.get("com.atagotech.apt.api.finder", "Finder");
  public static final ClassName INJECTOR = ClassName.get("com.atagotech.apt.api", "Injector");
  public static final ClassName ANDROID_TOAST = ClassName.get("android.widget", "Toast");
  public static final ClassName ANDROID_TEXTUTILS = ClassName.get("android.text", "TextUtils");
}
