package com.hitim.android.itstime;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.joooonho.SelectableRoundedImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link TaskAdapter} - адаптер для списков задач
 * Благодаря нему выносится большее количество информации о {@link Task}
 * на экран, чем в обычном списке
 */

public class TaskAdapter extends BaseAdapter implements android.widget.Filterable {

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
        ViewHolder holder;

        if (convertView == null) {
            LayoutInflater inf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inf.inflate(R.layout.task_layout, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Task task = taskArrayList.get(position);
        int color = task.getColor();
        Drawable image = context.getResources().getDrawable(R.drawable.ic_check);
        image.setColorFilter(color, PorterDuff.Mode.XOR);

        holder.name.setText(task.getName());
        holder.deadline.setText(task.getDatePicked().toString());
        holder.priority.setText(String.valueOf(task.getPriority()));
        holder.imageView.setImageDrawable(image);
        holder.imageView.setBorderColor(color);
        int resId = 0;
        int sphereColor = 0;
        switch (task.getSphere()) {
            case "Work":
                resId = R.string.work;
                sphereColor = R.color.orange_10;
                break;
            case "Health":
                resId = R.string.health;
                sphereColor = R.color.red_10;
                break;
            case "Routine":
                resId = R.string.routine;
                sphereColor = R.color.teal_10;
                break;
            case "Yourself":
                resId = R.string.yourself;
                sphereColor = R.color.blue_10;
                break;
        }
        holder.sphere.setText(context.getString(resId));
        holder.sphere.setTextColor(context.getResources().getColor(sphereColor));
        return convertView;
    }

    public void filter(String newText) {
        /*newText = newText.toLowerCase(Locale.getDefault());
        TaskListFragment.taskArrayList.clear();
        if (newText.length() == 0) {
            TaskListFragment.taskArrayList.addAll(taskArrayList);
        } else {
            for (Task wp : taskArrayList) {
                if (wp.getName().toLowerCase(Locale.getDefault()).contains(newText)) {
                    TaskListFragment.taskArrayList.add(wp);
                }
            }
        }
        notifyDataSetChanged();*/
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults result = new FilterResults();
                if(constraint == null || constraint.length() == 0){
                    result.values = taskArrayList;
                    result.count = taskArrayList.size();
                }else{
                    List<Task> newTasks = new ArrayList<>();
                    for(Task task: taskArrayList){
                        if(task.getName().toLowerCase().contains(constraint))
                            newTasks.add(task);
                    }
                    result.values = newTasks;
                    result.count = newTasks.size();
                }
                return result;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                notifyDataSetChanged();
            }
        };
    }

    private static class ViewHolder {
        public TextView name;
        public TextView sphere;
        public TextView deadline;
        public SelectableRoundedImageView imageView;
        public TextView priority;

        public ViewHolder(View v) {

            this.name = v.findViewById(R.id.task_layout_name);
            this.sphere = v.findViewById(R.id.task_layout_sphere);
            this.deadline = v.findViewById(R.id.task_layout_deadline);
            this.imageView = v.findViewById(R.id.task_layout_image);
            this.priority = v.findViewById(R.id.priority_number);
        }
    }
}
