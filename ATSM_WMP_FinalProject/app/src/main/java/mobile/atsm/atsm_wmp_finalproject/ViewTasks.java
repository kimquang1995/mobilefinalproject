package mobile.atsm.atsm_wmp_finalproject;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import mobile.atsm.atsm_wmp_finalproject.Adapter.Task;
import mobile.atsm.atsm_wmp_finalproject.Adapter.TaskAdapter;

public class ViewTasks extends AppCompatActivity {
    String id_user = "";
    String id_tag = "";
    ArrayList<Task> taskList;
    TaskAdapter Taskadapter;
    ListView lvTask;
    String url = "http://www.stsmteam.esy.es/gettask.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_tasks);
        lvTask = (ListView) findViewById(R.id.lstTask);
        Intent intent = getIntent();
        id_user = intent.getStringExtra(Login.ID_USER);
        id_tag = intent.getStringExtra(ViewTags.ID_TAG);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new ViewTask().execute(url, id_user, id_tag);
            }
        });
        findViewById(R.id.btnAddTask).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewTasks.this, AddTask.class);
                intent.putExtra(Login.ID_USER,id_user);
                intent.putExtra(ViewTags.ID_TAG,id_tag);
                startActivity(intent);
                finish();
            }
        });

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

    class ViewTask extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            return makeLoad(params[0], params[1], params[2]);
        }

        @Override
        protected void onPostExecute(String s) {
           // Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
            JSONObject object = null;
            try {
                object = new JSONObject(s);
                if (Integer.parseInt(object.getString("thanhcong")) == 1) {
                    taskList = new ArrayList<Task>();
                    JSONArray jsonArray = object.getJSONArray("task");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        taskList.add(new Task(jsonObject.getString("id"), jsonObject.getString("name"),
                                jsonObject.getString("level"), jsonObject.getString("start_date").toString(), jsonObject.getString("end_date").toString()));
                    }
                    Taskadapter = new TaskAdapter(taskList, ViewTasks.this);
                    lvTask.setAdapter(Taskadapter);
                } else {

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

}
