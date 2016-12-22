package mobile.atsm.atsm_wmp_finalproject;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.GridLayout;
import android.widget.LinearLayout;
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
import mobile.atsm.atsm_wmp_finalproject.Adapter.UserAdapter;

public class AddTag extends AppCompatActivity implements android.widget.CompoundButton.OnCheckedChangeListener {
    ListView lv;
    ArrayList<User> userList;
    UserAdapter userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tag);
        findViewById(R.id.btnBackTag).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddTag.this, ViewTags.class);
                startActivity(intent);
                finish();
            }
        });
        lv = (ListView) findViewById(R.id.lstUser);
        displayUserList();
    }

    private void displayUserList() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new exeViewUser().execute("http://www.stsmteam.esy.es/getalluser.php");
            }
        });
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        int pos = lv.getPositionForView(buttonView);
        if (pos != ListView.INVALID_POSITION) {
            User u = userList.get(pos);
            u.setSelected(isChecked);
            Toast.makeText(this, "Click " + u.getId() + " Is checked " + isChecked, Toast.LENGTH_SHORT).show();
        }
    }

    class exeViewUser extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            return ViewAllUser(params[0]);
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
                    userAdapter = new UserAdapter(userList, AddTag.this);
                    lv.setAdapter(userAdapter);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    private String ViewAllUser(String url) {
        HttpClient httpClient = new DefaultHttpClient();
        // URL cua trang nhan Request
        HttpPost httpPost = new HttpPost(url);
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
