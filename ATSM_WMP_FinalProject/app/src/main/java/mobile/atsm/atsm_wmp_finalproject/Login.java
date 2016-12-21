package mobile.atsm.atsm_wmp_finalproject;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import org.json.JSONObject;

import mobile.atsm.atsm_wmp_finalproject.Adapter.MyFunctions;

public class Login extends AppCompatActivity {
RelativeLayout acitivitymain;
    String sMail,sPass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_login);
        // Set background
        acitivitymain = (RelativeLayout)findViewById(R.id.activity_main);
        acitivitymain.setBackgroundResource(R.drawable.background2);

        final EditText etEmail = (EditText) findViewById(R.id.edtEmail);
        final EditText etPass = (EditText) findViewById(R.id.edtPassword);
        findViewById(R.id.btnSignin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    sMail=etEmail.getText().toString();;
                    sPass=etPass.getText().toString();
                    new thucthilogin().execute();

                } catch (Exception e) {
                    e.printStackTrace();
                }
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
    class thucthilogin extends AsyncTask<Void,Void,String>
    {
        MyFunctions myfunctions;
        String email;
        String password;
        @Override
        protected String doInBackground(Void... arg0) {
            // TODO Auto-generated method stub
            email=sMail;
            password=sPass;
            String thanhcong=null;

            try{
                myfunctions=new MyFunctions(getApplicationContext());
                JSONObject jsonobject=myfunctions.loginUser(email, password);

                thanhcong=jsonobject.getString("thanhcong");

            }catch(Exception e)
            {
                Log.d("loi", "khong tao json duoc "+ e.toString());
            }
            return thanhcong;
        }

        @Override
        protected void onPostExecute(String thanhcong) {
            // TODO Auto-generated method stub
            super.onPostExecute(thanhcong);
            if(Integer.parseInt(thanhcong)==1) //dang nhap thanh cong
            {
                myfunctions.setemaillogin(email);//luu mail lai
                Intent i=new Intent(getApplicationContext(),AddTag.class);
                startActivity(i);
                finish();
            }
            else //dang nhap that bai
            {
                Toast.makeText(getApplicationContext(),
                        "khong dang nhap duoc email hoac pass sai",
                        Toast.LENGTH_SHORT).show();
            }
        }

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
