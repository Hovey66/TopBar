package com.ysu.hongvs.topbar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
TopBar mBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBar= (TopBar) findViewById(R.id.topbar);
        mBar.setOnclickTopBar(new TopBar.TopBarListener() {
            @Override
            public void onClickLeftButton() {
                Toast.makeText(getApplicationContext(),"left" , Toast.LENGTH_LONG).show();
            }

            @Override
            public void onClickRightButton() {
                Toast.makeText(getApplicationContext(),"right" , Toast.LENGTH_LONG).show();
            }
        });

    }
}
