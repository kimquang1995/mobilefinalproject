package mobile.atsm.atsm_wmp_finalproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridLayout;
import android.widget.LinearLayout;

public class AddTask extends AppCompatActivity {
LinearLayout lnladdtag;
    GridLayout gridlayoutaddtask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        lnladdtag = (LinearLayout)findViewById(R.id.linearlayoutaddtask);
        lnladdtag.setBackgroundResource(R.drawable.backgroundaddtag1);
        gridlayoutaddtask = (GridLayout)findViewById(R.id.gridlayout_addtag);
        gridlayoutaddtask.setBackgroundResource(R.drawable.backgroundaddtag1);

    }
}
