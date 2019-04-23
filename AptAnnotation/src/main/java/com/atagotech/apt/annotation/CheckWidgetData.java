package com.atagotech.apt.annotation;

/**
 * Created by Tom on 2019/4/23.
 * 一句话描述：
 */
public @interface CheckWidgetData {
  String value() default "数据不能为空";
}
