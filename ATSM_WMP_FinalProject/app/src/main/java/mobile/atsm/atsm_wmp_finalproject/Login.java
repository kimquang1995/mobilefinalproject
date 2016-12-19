package mobile.atsm.atsm_wmp_finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

public class Login extends AppCompatActivity {
RelativeLayout acitivitymain;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_login);
        // Set background
        acitivitymain = (RelativeLayout)findViewById(R.id.activity_main);
        acitivitymain.setBackgroundResource(R.drawable.background2);

        final EditText etEmail = (EditText) findViewById(R.id.edtEmail);
        final EditText etPass = (EditText) findViewById(R.id.edtPassword);
        final String sMail = etEmail.getText().toString();
        final String sPass = etPass.getText().toString();
        findViewById(R.id.btnSignin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String sMail = etEmail.getText().toString();
                    String sPass = etPass.getText().toString();

                } catch (Exception e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(Login.this,ViewTags.class);
                startActivity(intent);
                finish();
            }
        });
        findViewById(R.id.btnSignup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this,ViewTasks.class);
                startActivity(intent);
                finish();
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent GUI_Add_a_Task in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
