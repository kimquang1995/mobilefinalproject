package mobile.atsm.atsm_wmp_finalproject.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

import mobile.atsm.atsm_wmp_finalproject.AddTag;
import mobile.atsm.atsm_wmp_finalproject.R;

/**
 * Created by JinGuang on 12/22/2016.
 */

public class TaskAdapter extends ArrayAdapter<Task> {
    private List<Task> taskList;
    private Context context;

    public TaskAdapter(List<Task> taskList, Context context) {
        super(context, R.layout.single_listtask_item, taskList);
        this.taskList = taskList;
        this.context = context;
    }

    public static class TaskViewHolder {
        public TextView id_task;
        public TextView name_task;
        public TextView start_date;
        public TextView end_date;
        public TextView level;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
       TaskViewHolder holder = new TaskViewHolder();
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.single_listuser_item, null);
            holder.id_task = (TextView) v.findViewById(R.id.txtID);
            holder.name_task = (TextView) v.findViewById(R.id.txtName);
            holder.start_date = (TextView) v.findViewById(R.id.txtStartdate);
            holder.end_date = (TextView) v.findViewById(R.id.txtEnddate);
            holder.level = (TextView) v.findViewById(R.id.txtLevel);
            v.setTag(holder);
        } else {
            holder = (TaskViewHolder) v.getTag();
        }
        Task t = taskList.get(position);
        holder.id_task.setText("ID "+t.id_task);
        holder.name_task.setText(t.name_task);
        holder.start_date.setText(t.start_date);
        holder.start_date.setText(t.end_date);
        holder.level.setText(t.level);
        return v;
    }
}

