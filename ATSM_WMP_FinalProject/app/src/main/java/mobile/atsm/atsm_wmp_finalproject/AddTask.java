package mobile.atsm.atsm_wmp_finalproject;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
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

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;

import java.util.Calendar;

import mobile.atsm.atsm_wmp_finalproject.Adapter.User;
import mobile.atsm.atsm_wmp_finalproject.Adapter.UserAdapter_Task;

public class AddTask extends AppCompatActivity implements android.widget.CompoundButton.OnCheckedChangeListener {
    String urlGetallUser = "http://www.stsmteam.esy.es/getuserbyIDtag.php";
    ListView lv;
    ArrayList<User> userList;
    UserAdapter_Task userAdapter;
    ArrayList<String> userChecked = new ArrayList<String>();
    String id_user = "";
    String id_tag = "";
    TextView txtStartDate, txtEndDate;
    private int mYear, mMonth, mDay, mHour, mMinute,mSec;
    String urlAddtask = "http://www.stsmteam.esy.es/inserttask.php";
    String urlAddDeliTask = "http://www.stsmteam.esy.es/insertdelitask.php";
    TextView tvKQ,tvName ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        lv = (ListView) findViewById(R.id.lstUser_Task);
        Intent intent = getIntent();
        id_user = intent.getStringExtra(Login.ID_USER);
        id_tag = intent.getStringExtra(ViewTags.ID_TAG);
        txtStartDate = (TextView) findViewById(R.id.txvStartDate);
        txtEndDate = (TextView) findViewById(R.id.txvEnDate);
        tvKQ = (TextView) findViewById(R.id.txtThongBao);
        tvName=(TextView) findViewById(R.id.edtNameTask);
        final Spinner dropdown = (Spinner)findViewById(R.id.spinner);
        String[] items = new String[]{"High", "Medium", "Low"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);
        //  Toast.makeText(getApplicationContext(),id_tag + " "+id_user,Toast.LENGTH_SHORT).show();
        findViewById(R.id.btnBackTask).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddTask.this, ViewTasks.class);
                intent.putExtra(Login.ID_USER,id_user);
                intent.putExtra(ViewTags.ID_TAG,id_tag);
                startActivity(intent);
            }
        });
        displayUserList();

        findViewById(R.id.btnStartDate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get Current Date
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                // Get Current Time
                mHour = c.get(Calendar.HOUR);
                mMinute = c.get(Calendar.MINUTE);
                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(AddTask.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                txtStartDate.append(" "+hourOfDay + ":" + minute);
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddTask.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                txtStartDate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
        findViewById(R.id.btnEndDate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get Current Date
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                // Get Current Time
                mHour = c.get(Calendar.HOUR);
                mMinute = c.get(Calendar.MINUTE);
                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(AddTask.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                txtEndDate.append(" "+hourOfDay + ":" + minute);
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddTask.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                txtEndDate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
        findViewById(R.id.btnCreateTask).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = tvName.getText().toString().toUpperCase();
                String start_date = txtStartDate.getText().toString();
                String end_date = txtEndDate.getText().toString();
                String level = dropdown.getSelectedItem().toString();
                if(name.length()>0 && !start_date.equals("Start Date") && !end_date.equals("End Date") && level.length() >0 && id_tag.length()>0) {
                    new exeInsertask().execute(urlAddtask, name, id_tag, start_date, end_date, level);

                }else
                {
                    Toast.makeText(getApplicationContext(),"Please Input Fully",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void displayUserList() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new exeViewUser().execute(urlGetallUser, id_tag);
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
           //  Toast.makeText(getApplicationContext(),String.valueOf(userChecked.size()),Toast.LENGTH_LONG).show();
        }
    }

    class exeViewUser extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            return ViewAllUser(params[0], params[1]);
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
    class exeInsertask extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            return InsertTask(params[0], params[1],params[2],params[3],params[4],params[5]);
        }

        @Override
        protected void onPostExecute(String s) {
           // Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
            try {
                boolean check = true;
                for (int i = 0; i < userChecked.size(); i++) {
                    if(userChecked.get(i).equals(id_user))
                    {
                        check = false;
                    }
                    new exeInsertDeliTask().execute(urlAddDeliTask, userChecked.get(i).trim(), s.trim());
                }
                if(check)
                {
                    new exeInsertDeliTask().execute(urlAddDeliTask, id_user, s.trim());
                }
                tvKQ.setTextColor(Color.BLUE);
                tvKQ.setText("Insert Successfull");
                Intent intent = new Intent(AddTask.this, ViewTasks.class);
                intent.putExtra(Login.ID_USER,id_user);
                intent.putExtra(ViewTags.ID_TAG,id_tag);
                startActivity(intent);
            } catch (Exception e) {
                tvKQ.setTextColor(Color.RED);
                tvKQ.setText("Insert Failure,Please try again");
                e.printStackTrace();
            }
        }
    }
    private String ViewAllUser(String url, String id_tag) {
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
    class exeInsertDeliTask extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            return InsertDeliTask(params[0], params[1], params[2]);
        }

        @Override
        protected void onPostExecute(String s) {
          //  Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
        }
    }

    private String InsertDeliTask(String url, String id_user, String id_task) {
        HttpClient httpClient = new DefaultHttpClient();
        // URL cua trang nhan Request
        HttpPost httpPost = new HttpPost(url);
        //Cac tham so truyen
        List nameValuePair = new ArrayList(2);
        nameValuePair.add(new BasicNameValuePair("id_user", id_user));
        nameValuePair.add(new BasicNameValuePair("id_task", id_task));
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
    private String InsertTask(String url, String name,String id_tag,String start_date,String end_date,String level) {
        HttpClient httpClient = new DefaultHttpClient();
        // URL cua trang nhan Request
        HttpPost httpPost = new HttpPost(url);
        //Cac tham so truyen
        List nameValuePair = new ArrayList(1);
        nameValuePair.add(new BasicNameValuePair("name", name));
        nameValuePair.add(new BasicNameValuePair("id_tag", id_tag));
        nameValuePair.add(new BasicNameValuePair("start_date", start_date));
        nameValuePair.add(new BasicNameValuePair("end_date", end_date));
        nameValuePair.add(new BasicNameValuePair("level", level));
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
