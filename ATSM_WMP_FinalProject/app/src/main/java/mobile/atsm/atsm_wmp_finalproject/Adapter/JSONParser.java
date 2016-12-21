package mobile.atsm.atsm_wmp_finalproject.Adapter;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Created by US on 12/5/2016.
 */
public class JSONParser {
    static InputStream is=null;
    static JSONObject jobj=null;
    static String json="";

    public JSONParser()
    {

    }

    //ham nhan vao duong dan va doi so, tra ve doi tuong json
    public JSONObject getJSONFromUrl(String url, List<NameValuePair> cacdoiso)
    {
        try{
            //ket noi goi yeu cau
            DefaultHttpClient httpclient=new DefaultHttpClient();
            HttpPost httppost=new HttpPost(url);
            httppost.setEntity(new UrlEncodedFormEntity(cacdoiso));

            //nhan ve du lieu dua vao inputstream
            HttpResponse httpresponse =httpclient.execute(httppost);
            HttpEntity httpentity=httpresponse.getEntity();
            is=httpentity.getContent();

            //doc tu inputstream Ä‘ua vao doi tuong Json
            BufferedReader reader=new BufferedReader(new InputStreamReader(is,"utf-8"),8);
            StringBuilder sb=new StringBuilder();
            String line=null;
            while((line=reader.readLine())!=null)
            {
                sb.append(line+"\n");
            }
            is.close();
            json=sb.toString();
            jobj =new JSONObject(json);

        }
        catch(Exception e)
        {

        }
        return jobj;
    }
}
