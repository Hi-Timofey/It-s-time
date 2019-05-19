package com.hitim.android.itstime;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.joooonho.SelectableRoundedImageView;

import java.util.List;

/**{@link TaskAdapter} - адаптер для списков задач
 * Благодаря нему выносится большее количество информации о {@link Task}
 * на экран, чем в обычном списке
 */

public class TaskAdapter extends BaseAdapter {

    private List<Task> taskArrayList;
    private Context context;

    public TaskAdapter(List<Task> list, Context cont) {
        this.taskArrayList = list;
        this.context = cont;
    }

    @Override
    public int getCount() {
        return this.taskArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return this.taskArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        if(convertView == null){
            LayoutInflater inf = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inf.inflate(R.layout.task_layout, null);

            holder = new ViewHolder();
            holder.name = convertView.findViewById(R.id.task_layout_name);
            holder.sphere = convertView.findViewById(R.id.task_layout_sphere);
            holder.deadline = convertView.findViewById(R.id.task_layout_deadline);
            holder.imageView = convertView.findViewById(R.id.task_layout_image);

            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder)convertView.getTag();
        }

        Task task = taskArrayList.get(position);
        int color = task.getColor();
        Drawable image = context.getResources().getDrawable(R.drawable.ic_check);
        image.setColorFilter(color, PorterDuff.Mode.XOR);

        holder.name.setText(task.getName());
        holder.sphere.setText(task.getSphere());
        holder.deadline.setText(task.getDatePicked().toString());
        holder.imageView.setImageDrawable(image);
        holder.imageView.setBorderColor(color);

        return convertView;
    }

    private static class ViewHolder{
        public TextView name;
        public TextView sphere;
        public TextView deadline;
        public SelectableRoundedImageView imageView;
    }
}
