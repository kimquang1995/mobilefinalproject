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
import android.util.Log;
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


public class GetPassword extends Activity {
    private static final String username = "atsmteamgmail.com";
    private static final String password = "p123456p";
    private  String emailid = "";
    EditText edtMail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_password);
        edtMail = (EditText) findViewById(R.id.edtEmail);

        findViewById(R.id.btnSendMail).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailid = edtMail.getText().toString().trim();
                if(emailid.length()>0)
                {
                    new exeGetPass().execute("http://stsmteam.esy.es/getpass.php",emailid);
                }
                else
                {
                    Toast.makeText(GetPassword.this, "Please Input Your Email ", Toast.LENGTH_SHORT).show();
                }
                //new exeGetPass().execute("http://stsmteam.esy.es/getpass.php", emailid);

                        }
                    });
    }

    class exeGetPass extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            return getPass(params[0], params[1]);
        }

        @Override
        protected void onPostExecute(String s) {

          //  Toast.makeText(GetPassword.this, "Your Password is " + s.trim(), Toast.LENGTH_SHORT).show();
            if (s.trim().equals("khongcoemail")) {
                Toast.makeText(getApplicationContext(),"Email not exist !",Toast.LENGTH_SHORT).show();
            } else {
             //   Toast.makeText(getApplicationContext(),emailid,Toast.LENGTH_SHORT).show();
                Mail m = new Mail("teamatsm@gmail.com", "p123456p");

                String[] toArr = {emailid};
                m.set_to(toArr);
                m.set_from("teamatsm@gmail.com");
                m.set_subject("GET YOUR PASSWORD ");
                m.setBody("Hello ! your password is "+s +"\nThanks for using Ours App");

                try {

                    if(m.send()) {
                        edtMail.setText("");
                        Toast.makeText(GetPassword.this, "Your password was sent successfully. ! Please Check Email", Toast.LENGTH_LONG).show();

                    } else {
                        Toast.makeText(GetPassword.this, "Your password was not sent.", Toast.LENGTH_LONG).show();
                    }
                } catch(Exception e) {
                    //Toast.makeText(MailApp.this, "There was a problem sending the email.", Toast.LENGTH_LONG).show();
                    Log.e("GetPass", "Could not send email", e);
                }

            }


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