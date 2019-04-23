package com.atagotech.apt.compiler.model;

import com.atagotech.apt.annotation.CheckWidgetData;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Name;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;

/**
 * Created by tangpengfei on 2019/2/28.
 * 一句话描述：
 */
public class CheckWidgetFiled {
  private VariableElement mFieldElement;
  private String  tips;

  public CheckWidgetFiled(Element element) throws IllegalArgumentException {
    if (element.getKind() != ElementKind.FIELD) {
      throw new IllegalArgumentException(String.format("Only fields can be annotated with @%s", CheckWidgetData.class.getSimpleName()));
    }
    mFieldElement = (VariableElement) element;
    CheckWidgetData checkWidgetData = mFieldElement.getAnnotation(CheckWidgetData.class);
    tips = checkWidgetData.value();
  }

  public Name getFieldName() {
    return mFieldElement.getSimpleName();
  }



  public TypeMirror getFieldType() {
    return mFieldElement.asType();
  }

  public String getTips() {
    return tips;
  }
}
