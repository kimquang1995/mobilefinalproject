package mobile.atsm.atsm_wmp_finalproject;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import mobile.atsm.atsm_wmp_finalproject.Adapter.User;
import mobile.atsm.atsm_wmp_finalproject.Adapter.UserAdapter_Task;

public class AddTag extends AppCompatActivity implements android.widget.CompoundButton.OnCheckedChangeListener {
    ListView lv;
    ArrayList<User> userList;
    UserAdapter_Task userAdapter;
    String urlAddtag = "http://www.stsmteam.esy.es/insertTag.php";
    String urlAddDeliTag = "http://www.stsmteam.esy.es/insertdelitag.php";
    String urlGetallUser = "http://www.stsmteam.esy.es/getalluser.php";
    ArrayList<String> userChecked;
    String ID_USER = "";
    TextView tvKQ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tag);
        Intent intent = getIntent();
        final EditText edtName = (EditText) findViewById(R.id.edtNameTag);
        tvKQ = (TextView) findViewById(R.id.txtThongBao);
        ID_USER = intent.getStringExtra(Login.ID_USER);
        findViewById(R.id.btnBackTag).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddTag.this, ViewTags.class);
                intent.putExtra(Login.ID_USER, ID_USER);
                startActivity(intent);
                finish();
            }
        });
        lv = (ListView) findViewById(R.id.lstUser);
        displayUserList();
        findViewById(R.id.btnCreateTag).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userChecked.size() > 0) {
                    new exeInsertag().execute(urlAddtag, edtName.getText().toString().toUpperCase());
                } else {
                    Toast.makeText(getApplicationContext(), "please select stakeholder", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void displayUserList() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new exeViewUser().execute(urlGetallUser);
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
            Toast.makeText(getApplicationContext(),String.valueOf(userChecked.size()),Toast.LENGTH_LONG).show();
        }
    }

    class exeInsertag extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            return InsertTag(params[0], params[1]);
        }

        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
            try {
                for (int i = 0; i < userChecked.size(); i++) {
                    new exeInsertDeliTag().execute(urlAddDeliTag, userChecked.get(i).trim(), s.trim());
                }
                tvKQ.setTextColor(Color.BLUE);
                tvKQ.setText("Insert Successfull");
            } catch (Exception e) {
                tvKQ.setTextColor(Color.RED);
                tvKQ.setText("Insert Failure,Please try again");
                e.printStackTrace();
            }
        }
    }

    class exeInsertDeliTag extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            return InsertDeliTag(params[0], params[1], params[2]);
        }

        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
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
                    userAdapter = new UserAdapter_Task(userList, AddTag.this);
                    lv.setAdapter(userAdapter);
                } else {

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

    private String InsertTag(String url, String name) {
        HttpClient httpClient = new DefaultHttpClient();
        // URL cua trang nhan Request
        HttpPost httpPost = new HttpPost(url);
        //Cac tham so truyen
        List nameValuePair = new ArrayList(1);
        nameValuePair.add(new BasicNameValuePair("name", name));
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

    private String InsertDeliTag(String url, String id_user, String id_tag) {
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
