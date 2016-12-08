package mobile.atsm.atsm_wmp_finalproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;

import Controller.Account_Control;
import Ultilities.DatabaseConnection;

public class MainActivity extends AppCompatActivity {
    Account_Control ctr_account;
    DatabaseConnection db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new DatabaseConnection();
        Connection conn = db.CONN();
        ctr_account = new Account_Control(conn);
        final EditText etEmail = (EditText) findViewById(R.id.editEmail);
        final EditText etPass = (EditText) findViewById(R.id.editPassword);
        final String sMail = etEmail.getText().toString();
        final String sPass = etPass.getText().toString();
        findViewById(R.id.btnSignin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String sMail = etEmail.getText().toString();
                    String sPass = etPass.getText().toString();
                    if (ctr_account.CheckLogin(sMail, sPass)) {
                        Toast.makeText(MainActivity.this, "Login Thanh cong", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "Login That bai", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
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
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
