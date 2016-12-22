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

public class UserAdapter extends ArrayAdapter<User> {
    private List<User> userList;
    private Context context;

    public UserAdapter(List<User> userList, Context context) {
        super(context, R.layout.single_listuser_item, userList);
        this.userList = userList;
        this.context = context;
    }

    public static class TaskViewHolder {
        public TextView username;
        public TextView email;
        public CheckBox chkbox;
        public TextView id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        TaskViewHolder holder = new TaskViewHolder();
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.single_listuser_item, null);
            holder.username = (TextView) v.findViewById(R.id.tvName);
            holder.email = (TextView) v.findViewById(R.id.tvEmail);
            holder.id = (TextView) v.findViewById(R.id.tvID);
            holder.chkbox = (CheckBox) v.findViewById(R.id.checkBox);
            holder.chkbox.setOnCheckedChangeListener((AddTag) context);
            v.setTag(holder);
        } else {
            holder = (TaskViewHolder) v.getTag();
        }
        User u = userList.get(position);
        holder.username.setText(u.getName());
        holder.email.setText(u.email);
        holder.chkbox.setChecked(u.isSelected());
        holder.id.setText("ID " + u.id);
        holder.chkbox.setTag(u);
        return v;
    }
}
