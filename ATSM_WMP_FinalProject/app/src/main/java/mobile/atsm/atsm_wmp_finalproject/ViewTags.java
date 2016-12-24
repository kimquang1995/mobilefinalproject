package mobile.atsm.atsm_wmp_finalproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
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

import mobile.atsm.atsm_wmp_finalproject.Adapter.Tag;

public class ViewTags extends AppCompatActivity {

    String ID_USER = "";
    String url = "http://www.stsmteam.esy.es/gettagbyID.php";
    String urlDeletetag ="http://stsmteam.esy.es/deletetag.php";
    ArrayList<Tag> arr_Tag = new ArrayList<Tag>();
    ArrayList<String> arr_nameTag = new ArrayList<String>();
    ListView lvTag;
    ArrayAdapter adapter;
    public static final String ID_TAG = "ID_TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_tags);

        lvTag = (ListView) findViewById(R.id.lvTags);

        Intent intent = getIntent();
        ID_USER = intent.getStringExtra(Login.ID_USER);
        //     Toast.makeText(getApplicationContext(), intent.getStringExtra(Login.ID_USER), Toast.LENGTH_SHORT).show();
        Load();
        findViewById(R.id.btnAddTag).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewTags.this, AddTag.class);
                intent.putExtra(Login.ID_USER, ID_USER);
                startActivity(intent);
                finish();
            }
        });
        lvTag.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Tag tag = arr_Tag.get(position);
                Intent intent = new Intent(ViewTags.this, ViewTasks.class);
                intent.putExtra(ID_TAG, tag.getId_tag());
                intent.putExtra(Login.ID_USER, ID_USER);
                startActivity(intent);
            }
        });

        lvTag.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
              //  Toast.makeText(getApplicationContext(), ID_TAG + ID_USER, Toast.LENGTH_SHORT).show();
                Tag tag = arr_Tag.get(position);
               final String id_tag = tag.id_tag;
                final AlertDialog.Builder mydialog = new AlertDialog.Builder(ViewTags.this);
                mydialog.setTitle("Review Action");
                mydialog.setMessage("Are you want delete this ?");
                mydialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        //  Toast.makeText(getApplicationContext(), "YES", Toast.LENGTH_SHORT).show();

                        new exedeletetag().execute(urlDeletetag,ID_USER,id_tag);

                    }
                });
                mydialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        //  Toast.makeText(getApplicationContext(), "NO", Toast.LENGTH_SHORT).show();

                    }
                });
                mydialog.show();
                return true;
            }
        });
    }

    private void Load() {
                new exeLoad().execute(url, ID_USER);
    }

    class exeLoad extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            return makeLoad(params[0], params[1]);
        }

        @Override
        protected void onPostExecute(String s) {
            //  Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
            try {

                JSONObject object = new JSONObject(s);
                if (Integer.parseInt(object.getString("thanhcong")) == 1) {
                    JSONArray arrJson = object.getJSONArray("tag");
                    for (int i = 0; i < arrJson.length(); i++) {
                        JSONObject jsObject = new JSONObject(arrJson.getString(i));
                        arr_Tag.add(new Tag(jsObject.getString("id"), jsObject.getString("NameTag"), jsObject.getString("create_date")));
                        arr_nameTag.add(jsObject.getString("NameTag"));
                    }
                    adapter = new ArrayAdapter(
                            getApplicationContext(),
                            android.R.layout.simple_list_item_1,
                            arr_nameTag) {
                        @NonNull
                        @Override
                        public View getView(int position, View convertView, ViewGroup parent) {
                            View view = super.getView(position, convertView, parent);
                            TextView text = (TextView) view.findViewById(android.R.id.text1);
                            text.setTextColor(Color.RED);
                            return view;
                        }
                    };
                    lvTag.setAdapter(adapter);
                } else {
                    Toast.makeText(getApplicationContext(), "Email or Password is wrong", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    class exedeletetag extends AsyncTask<String,Integer,String>
    {

        @Override
        protected String doInBackground(String... params) {
            return deleteTag(params[0],params[1],params[2]);
        }

        @Override
        protected void onPostExecute(String s) {
            if(s.trim().equals("success"))
            {
                finish();
                startActivity(getIntent());
                Load();
            }
            else
            {
                Toast.makeText(getApplicationContext(), "Can't Delete", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private String deleteTag(String url, String id_user,String id_tag) {
        HttpClient httpClient = new DefaultHttpClient();
        // URL cua trang nhan Request
        HttpPost httpPost = new HttpPost(url);
        //Cac tham so truyen
        List nameValuePair = new ArrayList(2);
        nameValuePair.add(new BasicNameValuePair("id_user",id_user ));
        nameValuePair.add(new BasicNameValuePair("id_tag",id_tag ));
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
    private String makeLoad(String url, String id) {
        HttpClient httpClient = new DefaultHttpClient();
        // URL cua trang nhan Request
        HttpPost httpPost = new HttpPost(url);
        //Cac tham so truyen
        List nameValuePair = new ArrayList(1);
        nameValuePair.add(new BasicNameValuePair("id", id));
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