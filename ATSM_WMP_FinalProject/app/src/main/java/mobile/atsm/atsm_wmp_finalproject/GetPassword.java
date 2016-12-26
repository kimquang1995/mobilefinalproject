package mobile.atsm.atsm_wmp_finalproject;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
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


public class GetPassword extends Activity
{
    public String jobNo;
    public String teamNo;
    private static final String username = "atsmteamgmail.com";
    private static final String password = "p123456p";
    private static  String emailid ="";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_password);
        final EditText edtMail =(EditText) findViewById(R.id.edtEmail);
      //  Intent intent = getIntent();
       // jobNo = intent.getStringExtra("Job_No");
      //  teamNo = intent.getStringExtra("Team_No");

        findViewById(R.id.btnSendMail).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailid = edtMail.getText().toString().trim();
                new exeGetPass().execute("http://stsmteam.esy.es/getpass.php",emailid);
            }
        });

    }

    class exeGetPass extends AsyncTask<String, Integer,String>
    {

        @Override
        protected String doInBackground(String... params) {
            return getPass(params[0],params[1]);
        }

        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(GetPassword.this,"Your Password is "+s.trim(),Toast.LENGTH_SHORT).show();
        }
    }
    private String getPass(String url, String email) {
        HttpClient httpClient = new DefaultHttpClient();
        // URL cua trang nhan Request
        HttpPost httpPost = new HttpPost(url);
        //Cac tham so truyen
        List nameValuePair = new ArrayList(2);
        nameValuePair.add(new BasicNameValuePair("email", email));
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