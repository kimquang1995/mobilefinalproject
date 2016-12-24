package mobile.atsm.atsm_wmp_finalproject;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Text;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class SignUp extends AppCompatActivity {
String email,pass,name;
    EditText etEmai,etPass,etName;
    TextView tvStatus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        etEmai= (EditText) findViewById(R.id.edtInputEmail);
        etPass= (EditText) findViewById(R.id.edtInputPass);
        etName= (EditText) findViewById(R.id.edtInputName);
        tvStatus= (TextView) findViewById(R.id.txtStatus);
        findViewById(R.id.btnCancelSignUp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUp.this, Login.class);
                startActivity(intent);
                finish();
            }
        });
        findViewById(R.id.btnSignUp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email=etEmai.getText().toString().trim();
                name=etName.getText().toString().trim().toUpperCase();
                pass=etPass.getText().toString().trim();
                if(email.length()>0 && name.length()>0 && pass.length()>0) {
                    new exeSignUp().execute("http://www.stsmteam.esy.es/index.php", name, email, pass);
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Please input fully", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    class exeSignUp extends AsyncTask<String,Integer,String>{

        @Override
        protected String doInBackground(String... params) {
           return makeSignUp(params[0],params[1],params[2],params[3]);
        }

        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(getApplicationContext(),s, Toast.LENGTH_SHORT).show();
            if(s.trim().equals("success"))
            {
                tvStatus.setTextColor(Color.BLUE);
                tvStatus.setText("Congurations ! Register Successfully ");
                Intent intent = new Intent(SignUp.this, Login.class);
                startActivity(intent);
                finish();

            }
            else if(s.trim().equals("email is used"))
            {
                tvStatus.setTextColor(Color.RED);
                tvStatus.setText("Failure ! Email was used,Please Input another one ");

            }
            else
            {
                tvStatus.setTextColor(Color.RED);
                tvStatus.setText("Have Error ! Please try again");
            }
        }
    }
    private String makeSignUp(String url,String name,String email,String password) {
        HttpClient httpClient = new DefaultHttpClient();
        // URL cua trang nhan Request
        HttpPost httpPost = new HttpPost(url);
        //Cac tham so truyen
        List nameValuePair = new ArrayList(3);
        nameValuePair.add(new BasicNameValuePair("tag", "register"));
        nameValuePair.add(new BasicNameValuePair("name", name));
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

}
