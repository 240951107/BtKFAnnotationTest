package com.atagotech.apt.compiler.model;

import com.atagotech.apt.compiler.CheckTypeUitls;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import java.util.ArrayList;
import java.util.List;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

/**
 * Created by tangpengfei on 2019/2/28.
 * 一句话描述：
 */
public class CheckAnnotatedClass {
  public TypeElement mClassElement; //类名
  public List<CheckWidgetFiled> mFields;//成员变量
  public Elements mElementUtils;

  public CheckAnnotatedClass(TypeElement classElement, Elements elementUtils) {
    this.mClassElement = classElement;
    this.mFields = new ArrayList<>();
    this.mElementUtils = elementUtils;
  }

  public String getFullClassName() {
    return mClassElement.getQualifiedName().toString();
  }

  public void addField(CheckWidgetFiled field) {
    mFields.add(field);
  }

  public JavaFile generateFinder() {
    // method inject(final T host, Object source, Provider provider)
    MethodSpec.Builder checkMethodBuilder = MethodSpec.methodBuilder("checkData")
        .addModifiers(Modifier.PUBLIC)
        .addAnnotation(Override.class)
        .returns(TypeName.BOOLEAN)
        .addParameter(TypeName.get(mClassElement.asType()), "host", Modifier.FINAL);

    //field
    for (CheckWidgetFiled field : mFields) {
      //checkMethodBuilder.addStatement("Toast.makeText(host, \"aaa\", Toast.LENGTH_SHORT).show();");
      if (field.getFieldType().toString().endsWith("TextView") || field.getFieldType()
          .toString()
          .endsWith("EditText")) {
        checkMethodBuilder.beginControlFlow("if($T.isEmpty(host.$N.getText().toString().trim()))",
            CheckTypeUitls.ANDROID_TEXTUTILS, field.getFieldName())
            .addStatement("$T.makeText(host,\"$L\", Toast.LENGTH_SHORT).show()",
                CheckTypeUitls.ANDROID_TOAST, field.getTips())
            .addStatement("return false")
            .endControlFlow();
      } else if (field.getFieldType().toString().endsWith("RadioGroup")) {
        checkMethodBuilder.beginControlFlow("if(!host.$N.isSelected())", field.getFieldName())
            .addStatement("$T.makeText(host,\"$L\", Toast.LENGTH_SHORT).show()",
                CheckTypeUitls.ANDROID_TOAST, field.getTips())
            .addStatement("return false")
            .endControlFlow();
      } else if (field.getFieldType().toString().endsWith("CheckBox")) {
        checkMethodBuilder.beginControlFlow("if(!host.$N.isChecked())", field.getFieldName())
            .addStatement("$T.makeText(host,\"$L\", Toast.LENGTH_SHORT).show()",
                CheckTypeUitls.ANDROID_TOAST, field.getTips())
            .addStatement("return false")
            .endControlFlow();
        ;
      }

      //injectMethodBuilder.addStatement("host.$N= ($T)(finder.findView(source,$L))",
      //    field.getFieldName(), ClassName.get(field.getFieldType()), field.getTips());
    }
    checkMethodBuilder.addStatement("return true");

    String packageName = getPackageName(mClassElement);
    String className = getClassName(mClassElement, packageName);
    ClassName bindingClassName = ClassName.get(packageName, className);

    System.out.println(
        "------mClassElement-getSimpleName------>>>>" + mClassElement.getSimpleName().toString());
    System.out.println(
        "-------bindingClassName-simpleName----->>>>" + bindingClassName.simpleName());

    // generate whole class
    TypeSpec finderClass = TypeSpec.classBuilder(bindingClassName.simpleName() + "$$Check")
        .addModifiers(Modifier.PUBLIC)
        .addSuperinterface(ParameterizedTypeName.get(CheckTypeUitls.INJECTOR,
            TypeName.get(mClassElement.asType())))
        .addMethod(checkMethodBuilder.build())
        .build();

    return JavaFile.builder(packageName, finderClass).build();
  }

  private String getPackageName(TypeElement type) {
    return mElementUtils.getPackageOf(type).getQualifiedName().toString();
  }

  private static String getClassName(TypeElement type, String packageName) {
    int packageLen = packageName.length() + 1;
    return type.getQualifiedName().toString().substring(packageLen).replace('.', '$');
  }
}
