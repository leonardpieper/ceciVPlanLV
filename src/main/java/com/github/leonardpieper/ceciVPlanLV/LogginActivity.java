package com.github.leonardpieper.ceciVPlanLV;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.leonard.ceciVPlan.R;

public class LogginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loggin);
        loginToCeci();
    }

    private void loginToCeci(){
        final EditText uNameTV = (EditText) findViewById(R.id.loggUser);
        final EditText pwdTV = (EditText) findViewById(R.id.loggPwd);
        final EditText lehrerKrzl = (EditText)findViewById(R.id.loggYear);
        Button subBtn = (Button)findViewById(R.id.loggSub);

        subBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(LogginActivity.this);
                SharedPreferences.Editor editor = sharedPref.edit();

                String user = uNameTV.getText().toString();
                String password = pwdTV.getText().toString();
                String lehrer = lehrerKrzl.getText().toString();

                editor.putString("prefUname", user).apply();
                editor.putString("prefPwd", password).apply();
                editor.putString("prefLehrer", lehrer).apply();

                MainActivity.theActivity.loginPost(user, password);



                Toast toast = Toast.makeText(getApplicationContext(), "Daten gespeichert", Toast.LENGTH_SHORT);
                toast.show();
                finish();


            }
        });
    }
}
