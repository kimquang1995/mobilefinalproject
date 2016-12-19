package mobile.atsm.atsm_wmp_finalproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.LinearLayout;

public class EditTag extends AppCompatActivity {
LinearLayout lnlEdittag;
    GridLayout gridlayoutedittag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_tag);
        lnlEdittag = (LinearLayout)findViewById(R.id.LinearlayoutEdittag);
        lnlEdittag.setBackgroundResource(R.drawable.backgroundaddtag1);
        gridlayoutedittag = (GridLayout)findViewById(R.id.gridlayoutedittag);
        gridlayoutedittag.setBackgroundResource(R.drawable.backgroundaddtag);

        findViewById(R.id.btnAddTag).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditTag.this,AddTag.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
