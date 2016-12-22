package mobile.atsm.atsm_wmp_finalproject;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
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

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class ViewTasks extends AppCompatActivity {
    String id_user = "";
    String id_tag = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_tasks);
        Intent intent = getIntent();
        id_user = intent.getStringExtra(Login.ID_USER);
        id_tag = intent.getStringExtra(ViewTags.ID_TAG);
        Toast.makeText(this, "ID_User " + id_user
                + " ID_TAG " + id_tag, Toast.LENGTH_LONG).show();

        findViewById(R.id.btnAddTask).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewTasks.this, AddTask.class);
                startActivity(intent);
                finish();
            }
        });
    }

    class exeLoad extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            return makeLoad(params[0], params[1], params[2]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }

    private String makeLoad(String url, String id_user, String id_tag) {
        HttpClient httpClient = new DefaultHttpClient();
        // URL cua trang nhan Request
        HttpPost httpPost = new HttpPost(url);
        //Cac tham so truyen
        List nameValuePair = new ArrayList(2);
        nameValuePair.add(new BasicNameValuePair("id_user", id_user));
        nameValuePair.add(new BasicNameValuePair("id_tag", id_tag));
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
