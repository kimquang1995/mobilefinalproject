package mobile.atsm.atsm_wmp_finalproject;

import android.content.Entity;
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

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class Login extends AppCompatActivity {
    String sMail, sPass;
    public  static final String ID_USER ="ID_USER";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_login);
        final EditText etEmail = (EditText) findViewById(R.id.edtEmail);
        final EditText etPass = (EditText) findViewById(R.id.edtPassword);
        Intent intent = getIntent();
        sMail =intent.getStringExtra("EMAIL");
        sPass =intent.getStringExtra("PASS");
        if(sMail.length()>0 && sPass.length()>0)
        {
            etEmail.setText(sMail);
            etPass.setText(sPass);
        }
        findViewById(R.id.btnSignin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    sMail = etEmail.getText().toString();
                    sPass = etPass.getText().toString();
                    if (sMail.length() >0 && sPass.length()>0) {
                        new exe_Login().execute("http://www.stsmteam.esy.es/index.php", sMail, sPass);
                    }else {
                        Toast.makeText(getApplicationContext(), "Please Input Email or Password", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        findViewById(R.id.btnSignup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, SignUp.class);
                startActivity(intent);
                finish();
            }
        });

    }

    class exe_Login extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            return makeLogin(params[0],params[1],params[2]);
        }

        @Override
        protected void onPostExecute(String s) {
          //  Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
            try {
               JSONObject object = new JSONObject(s);
                if (Integer.parseInt(object.getString("thanhcong"))==1)
                {
                    JSONObject objectUser = new JSONObject(object.getString("user"));
                    Intent intent = new Intent(Login.this, ViewTags.class);
                    intent.putExtra(ID_USER,objectUser.getString("id"));
                    startActivity(intent);
                    finish();
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Email or Password is wrong", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private String makeLogin(String url,String email,String password) {
        HttpClient httpClient = new DefaultHttpClient();
        // URL cua trang nhan Request
        HttpPost httpPost = new HttpPost(url);
        //Cac tham so truyen
        List nameValuePair = new ArrayList(3);
        nameValuePair.add(new BasicNameValuePair("tag", "login"));
        nameValuePair.add(new BasicNameValuePair("email", email));
        nameValuePair.add(new BasicNameValuePair("password", password));
        //Encode Post data
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String kq = "";
        try {
            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            kq = EntityUtils.toString(entity);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return kq;
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
