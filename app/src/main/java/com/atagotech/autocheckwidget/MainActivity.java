package com.atagotech.autocheckwidget;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.atagotech.apt.annotation.CheckWidgetData;
import com.atagotech.apt.api.CheckWidgetInjector;

public class MainActivity extends AppCompatActivity {
  @CheckWidgetData(value = "TextView不能为空") TextView tv;
  @CheckWidgetData(value = "EditText1不能为空") EditText et1;
  @CheckWidgetData(value = "EditText2不能为空") EditText et2;
  @CheckWidgetData(value = "EditText3不能为空") EditText et3;
  @CheckWidgetData(value = "EditText4不能为空") EditText et4;
  @CheckWidgetData(value = "请选择Radiobutton") RadioGroup rg;
  @CheckWidgetData(value = "请选择cb") CheckBox cb;

  Button bt;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    tv = findViewById(R.id.tv);
    et1 = findViewById(R.id.et1);
    et2 = findViewById(R.id.et2);
    et3 = findViewById(R.id.et3);
    et4 = findViewById(R.id.et4);

    rg = findViewById(R.id.rg);
    cb = findViewById(R.id.cb);
    bt = findViewById(R.id.bt);
    bt.setOnClickListener(view -> {
      if (!CheckWidgetInjector.inject(MainActivity.this)) {
        return;
      }
      Toast.makeText(MainActivity.this, "检测通过", Toast.LENGTH_LONG).show();
    });
  }
}
