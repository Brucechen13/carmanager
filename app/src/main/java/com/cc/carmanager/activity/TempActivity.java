package com.cc.carmanager.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.cc.carmanager.R;
import com.cc.carmanager.comparisoncar.MainActivity;

/**
 * Created by chenc on 2017/11/11.
 */
public class TempActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        Button bt = (Button)findViewById(R.id.bt);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(TempActivity.this.getApplicationContext(), MainActivity.class);
                TempActivity.this.getApplicationContext().startActivity(i);
            }
        });
    }


}