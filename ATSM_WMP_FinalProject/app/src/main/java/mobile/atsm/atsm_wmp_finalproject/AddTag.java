package mobile.atsm.atsm_wmp_finalproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridLayout;
import android.widget.LinearLayout;

public class AddTag extends AppCompatActivity {
LinearLayout addTag;
GridLayout girdlayoutaddtag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tag);

        addTag = (LinearLayout)findViewById(R.id.activity_add_tag);
        addTag.setBackgroundResource(R.drawable.backgroundaddtag1);
        girdlayoutaddtag = (GridLayout) findViewById(R.id.gridlayout_addtag);
        girdlayoutaddtag.setBackgroundResource(R.drawable.backgroundaddtag);
    }

}
