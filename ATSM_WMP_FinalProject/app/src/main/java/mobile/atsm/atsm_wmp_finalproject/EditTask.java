package mobile.atsm.atsm_wmp_finalproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridLayout;
import android.widget.LinearLayout;

public class EditTask extends AppCompatActivity {
LinearLayout linearlayoutedittask;
    GridLayout gridlayoutedittask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);
        linearlayoutedittask = (LinearLayout)findViewById(R.id.LinearlayoutEdittask);
        linearlayoutedittask.setBackgroundResource(R.drawable.backgroundaddtag1);
        gridlayoutedittask = (GridLayout)findViewById(R.id.gridlayoutedittask);
        gridlayoutedittask.setBackgroundResource(R.drawable.backgroundaddtag);
    }
}
