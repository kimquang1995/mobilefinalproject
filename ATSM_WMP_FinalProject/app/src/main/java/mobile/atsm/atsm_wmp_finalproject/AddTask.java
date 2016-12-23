package mobile.atsm.atsm_wmp_finalproject;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
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

import mobile.atsm.atsm_wmp_finalproject.Adapter.User;
import mobile.atsm.atsm_wmp_finalproject.Adapter.UserAdapter_Task;

public class AddTask extends AppCompatActivity implements android.widget.CompoundButton.OnCheckedChangeListener{
    String urlGetallUser = "http://www.stsmteam.esy.es/getuserbyIDtag.php";
    ListView lv;
    ArrayList<User> userList;
    UserAdapter_Task userAdapter;
    ArrayList<String> userChecked;
    String id_user = "";
    String id_tag = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        lv = (ListView) findViewById(R.id.lstUser_Task);
        Intent intent = getIntent();
        id_user = intent.getStringExtra(Login.ID_USER);
        id_tag = intent.getStringExtra(ViewTags.ID_TAG);
      //  Toast.makeText(getApplicationContext(),id_tag + " "+id_user,Toast.LENGTH_SHORT).show();
       displayUserList();
    }
    private void displayUserList() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new exeViewUser().execute(urlGetallUser,id_tag);
            }
        });
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        int pos = lv.getPositionForView(buttonView);
        if (pos != ListView.INVALID_POSITION) {
            User u = userList.get(pos);
            userChecked = new ArrayList<String>();
            u.setSelected(isChecked);
            // Toast.makeText(this, "Click " + u.getId() + " Is checked " + isChecked, Toast.LENGTH_SHORT).show();
            for (int i = 0; i < userList.size(); i++) {
                if (userList.get(i).isSelected()) {
                    userChecked.add(userList.get(i).getId());
                }
            }
            // Toast.makeText(getApplicationContext(),String.valueOf(userChecked.size()),Toast.LENGTH_LONG).show();
        }
    }
    class exeViewUser extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            return ViewAllUser(params[0],params[1]);
        }

        @Override
        protected void onPostExecute(String s) {
            //  Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();
            try {
                JSONObject object = new JSONObject(s);
                if (Integer.parseInt(object.getString("thanhcong")) == 1) {
                    userList = new ArrayList<User>();
                    JSONArray array = object.getJSONArray("user");
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject jsonObject = array.getJSONObject(i);
                        userList.add(new User(jsonObject.getString("name").trim(), jsonObject.getString("id").trim(), jsonObject.getString("email").trim()));
                    }
                    userAdapter = new UserAdapter_Task(userList, AddTask.this);
                    lv.setAdapter(userAdapter);
                } else {

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    private String ViewAllUser(String url,String id_tag) {
        HttpClient httpClient = new DefaultHttpClient();
        // URL cua trang nhan Request
        HttpPost httpPost = new HttpPost(url);
        //Cac tham so truyen
        List nameValuePair = new ArrayList(1);
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
