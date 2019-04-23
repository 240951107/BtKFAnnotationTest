package com.atagotech.apt.api;

/**
 * Created by Tom on 2019/4/23.
 * 一句话描述：
 */
public interface Injector<T> {

  boolean checkData(T host);
}
