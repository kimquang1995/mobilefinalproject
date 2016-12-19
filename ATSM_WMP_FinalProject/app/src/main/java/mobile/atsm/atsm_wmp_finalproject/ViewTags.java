package mobile.atsm.atsm_wmp_finalproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class ViewTags extends AppCompatActivity {
LinearLayout viewtag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_tags);
        viewtag = (LinearLayout)findViewById(R.id.activity_view_tags);
        viewtag.setBackgroundResource(R.drawable.backgroundaddtag1);

        findViewById(R.id.btnAddTag).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewTags.this,AddTag.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
