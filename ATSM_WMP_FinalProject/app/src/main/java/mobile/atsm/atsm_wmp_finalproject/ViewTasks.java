package mobile.atsm.atsm_wmp_finalproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class ViewTasks extends AppCompatActivity {
LinearLayout lnlViewtask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_tasks);
        lnlViewtask = (LinearLayout)findViewById(R.id.LinearlayoutViewtask);
        lnlViewtask.setBackgroundResource(R.drawable.backgroundaddtag1);

        findViewById(R.id.btnAddTask).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewTasks.this,AddTask.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
