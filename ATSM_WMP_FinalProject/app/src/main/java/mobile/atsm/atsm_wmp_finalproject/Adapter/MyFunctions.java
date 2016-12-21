package mobile.atsm.atsm_wmp_finalproject.Adapter;


import android.content.Context;
import android.content.SharedPreferences;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by JinGuang on 12/21/2016.
 */
public class MyFunctions {
    JSONParser jsonparser;
    String createXeurl="http://192.168.1.16:1234/Webxehoi/createXe.php";
    String loginurl="http://www.stsmteam.esy.es/index.php";
    String registerurl="http://192.168.56.1:1234/Webxehoi/index.php";

    String login_tag="login";
    String register_tag="register";

    Context context;

    //ham tao khoi ta doi tuong jsonparser
    public MyFunctions(Context context)
    {
        jsonparser=new JSONParser();
        this.context=context;
    }
    //doc tu shared neu da log neu chua log tra ve false, log roi tra ve true
    public boolean checkLogin()
    {
        SharedPreferences lay=
                context.getSharedPreferences(null,context.MODE_WORLD_READABLE);
        String emaillogined=lay.getString("emaillogined","chua login");
        if(emaillogined.equals("chua login"))
            return false;
        else
            return true;
    }

    //lay email da login
    public String getEmail()
    {
        SharedPreferences lay=
                context.getSharedPreferences(null,context.MODE_WORLD_READABLE);
        String emaillogined=lay.getString("emaillogined","chua login");
        return emaillogined;
    }

    //ghi du lieu lai cho emaillogined thanh "chua login"
    public boolean logOut()
    {
        SharedPreferences ghi=
                context.getSharedPreferences(null,context.MODE_WORLD_WRITEABLE);
        SharedPreferences.Editor editor=ghi.edit();
        editor.putString("emaillogined", "chua login");
        editor.commit();
        return true;
    }

    //khi da login thi luu lai email lenh shared de biet da log
    public boolean setemaillogin(String email)
    {
        SharedPreferences ghi=
                context.getSharedPreferences(null,context.MODE_WORLD_WRITEABLE);
        SharedPreferences.Editor editor=ghi.edit();
        editor.putString("emaillogined", email);
        editor.commit();
        return true;
    }
    public JSONObject loginUser(String email, String password)
    {
        List<NameValuePair> cacdoiso=new ArrayList<NameValuePair>();
        cacdoiso.add(new BasicNameValuePair("tag",login_tag) );
        cacdoiso.add(new BasicNameValuePair("email", email));
        cacdoiso.add(new BasicNameValuePair("password", password));

        JSONObject jobj=jsonparser.getJSONFromUrl(loginurl, cacdoiso);

        setemaillogin(email);//gan len share de nho da login roi

        return jobj;
    }
    public JSONObject registerUser(String name, String email, String password)
    {
        List<NameValuePair> cacdoiso=new ArrayList<NameValuePair>();
        cacdoiso.add(new BasicNameValuePair("tag",register_tag));
        cacdoiso.add(new BasicNameValuePair("name",name));
        cacdoiso.add(new BasicNameValuePair("email", email));
        cacdoiso.add(new BasicNameValuePair("password", password));

        JSONObject jobj=jsonparser.getJSONFromUrl(registerurl, cacdoiso);
        return jobj;
    }



}


