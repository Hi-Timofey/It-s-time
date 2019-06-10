package com.hitim.android.itstime;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.Calendar;

import static android.provider.CalendarContract.EXTRA_EVENT_BEGIN_TIME;
import static android.provider.CalendarContract.EXTRA_EVENT_END_TIME;
import static android.provider.CalendarContract.Events;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailsFragment extends Fragment implements DialogInterface.OnClickListener, View.OnClickListener {

    private Task task;
    private Toolbar toolbar;
    private FragmentManager fm;
    private AlertDialog sureToDelete, congratulationsDialog;
    private TextView taskNameTextView, taskSphereTextView, taskDescTextView, taskDeadlineTextView, taskNeededTimeTextView;


    public DetailsFragment() {
    }

    public DetailsFragment(Task task) {
        this.task = task;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_details, container, false);


        taskNameTextView = v.findViewById(R.id.details_task_name);
        taskSphereTextView = v.findViewById(R.id.details_sphere_text);
        taskDescTextView = v.findViewById(R.id.details_task_description);
        taskDeadlineTextView = v.findViewById(R.id.details_task_deadline_time);
        taskNeededTimeTextView = v.findViewById(R.id.details_task_needed_time);
        ConstraintLayout googleSyncButton = v.findViewById(R.id.details_google_sync);


        ImageButton descImageButton = v.findViewById(R.id.details_edit_description);
        ImageButton deadlineImageButton = v.findViewById(R.id.details_edit_deadline_time);
        ImageButton neededTimeImageButton = v.findViewById(R.id.details_edit_needed_time);
        descImageButton.setOnClickListener(this);
        deadlineImageButton.setOnClickListener(this);
        googleSyncButton.setOnClickListener(this);
        neededTimeImageButton.setOnClickListener(this);


        toolbar = getActivity().findViewById(R.id.tool_bar);
        FloatingActionButton taskCompletedButton = v.findViewById(R.id.task_completed_fab);
        taskCompletedButton.setOnClickListener(this);
        AppBarLayout l = getActivity().findViewById(R.id.tool_bar_layout);
        l.setElevation(0);
        toolbar.setTitle("");
        toolbar.setElevation(100);
        toolbar.setNavigationIcon(R.drawable.ic_close_white_24dp);
        toolbar.setNavigationOnClickListener(v1 -> {
            FragmentManager fm = getActivity().getSupportFragmentManager();
            fm.beginTransaction().replace(R.id.fragment_container, fm.findFragmentByTag(getString(R.string.task_list_fragment)))
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
                    .addToBackStack(null)
                    .commit();
        });
        if (task != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.AlertDialogStyle_Light);
            builder.setMessage(getString(R.string.sure_want_delete) + " " + task.getName() + "?");
            builder.setPositiveButton(R.string.yes, this);
            builder.setNegativeButton(R.string.no, this);
            sureToDelete = builder.create();
            AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext(), R.style.AlertDialogStyle_Light);
            builder1.setMessage(getString(R.string.congratulations));
            builder1.setPositiveButton(R.string.yeah, (dialog, which) -> dialog.cancel());
            congratulationsDialog = builder1.create();
        } else {
            String tag = getString(R.string.sphere_fragment);
            SphereFragment sf = (SphereFragment) getFragmentManager().findFragmentByTag(tag);
            if (sf != null) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, sf, tag)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit();
            } else {
                getFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new SphereFragment(), tag)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit();
            }
        }
        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.details_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_delete_task) {
            sureToDelete.show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        super.onStart();
        fm = getFragmentManager();
        initTaskDetails();
    }

    private void initTaskDetails() {
        if (task != null) {
            String sphereOfTask = "";
            taskNameTextView.setText(task.getName());
            switch (task.getSphere()) {
                case "Work":
                    sphereOfTask = getString(R.string.work);
                    break;
                case "Health":
                    sphereOfTask = getString(R.string.health);
                    break;
                case "Routine":
                    sphereOfTask = getString(R.string.routine);
                    break;
                case "Yourself":
                    sphereOfTask = getString(R.string.yourself);
                    break;
            }
            taskSphereTextView.setText(sphereOfTask);
            if (task.getDescription().equals(""))
                taskDescTextView.setText(getString(R.string.emptyString));
            else taskDescTextView.setText(task.getDescription());
            taskDeadlineTextView.setText(task.getDatePicked().toString());
            DatePicked neededTime = task.getNeededTimePicked();
            if (neededTime.isNull()) {
                taskNeededTimeTextView.setText(task.getNeededTimePicked().toString());
            } else {
                taskNeededTimeTextView.setText(getString(R.string.empty_data));
            }
        } else {
            fm.beginTransaction().replace(R.id.fragment_container, fm.findFragmentByTag(getString(R.string.task_list_fragment)))
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE).commit();
        }
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        if (which == -1) {
            AsyncWorker worker = new AsyncWorker();
            worker.deleteTask(task);
            fm.beginTransaction()
                    .replace(R.id.fragment_container, fm.findFragmentByTag(getString(R.string.task_list_fragment)))
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
                    .commit();
        } else {
            dialog.dismiss();
        }
    }

    @Override
    public void onClick(View v) {
        DialogInterface.OnCancelListener onCancelListener;
        TimePickerDialog.OnTimeSetListener onTimeSetListener;
        DatePickerDialog.OnDateSetListener onDateSetListener;
        DatePickerDialog dpd;
        onCancelListener = dialog -> dialog.dismiss();
        Calendar now = Calendar.getInstance();

        switch (v.getId()) {
            case R.id.task_completed_fab:
                AsyncWorker worker = new AsyncWorker();
                worker.deleteTask(task);
                congratulationsDialog.show();
                fm.beginTransaction()
                        .replace(R.id.fragment_container, fm.findFragmentByTag(getString(R.string.task_list_fragment)))
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
                        .commit();
                break;
            case R.id.details_edit_description:
                AlertDialog.Builder builderForText = new AlertDialog.Builder(getActivity(), R.style.AlertDialogStyle_Light)
                        .setCancelable(false);
                View viewTextEdit = LayoutInflater.from(getContext()).inflate(R.layout.edit_dialog_layout, null);
                TextInputEditText textInputEditText = viewTextEdit.findViewById(R.id.edit_dialog_edit_text);
                viewTextEdit.findViewById(R.id.edit_dialog_edit_text);
                builderForText.setView(viewTextEdit);
                builderForText.setPositiveButton(getString(R.string.ok), (dialog, which) -> {
                    String newDesc = textInputEditText.getText().toString();
                    task.setDescription(newDesc);
                    taskDescTextView.setText(newDesc);
                    new AsyncWorker().updateTask(task);
                    dialog.dismiss();
                });
                builderForText.create().show();

                break;
            case R.id.details_edit_deadline_time:
                DatePicked deadTime = task.getDatePicked();
                onTimeSetListener = (view, hourOfDay, minute, second) -> {
                    deadTime.setHour(hourOfDay);
                    deadTime.setMinutes(minute);
                    task.setDatePicked(deadTime);
                    taskDeadlineTextView.setText(deadTime.toString());
                    new AsyncWorker().updateTask(task);
                };
                onDateSetListener = (view, year, monthOfYear, dayOfMonth) -> {
                    deadTime.setYear(year);
                    deadTime.setMonth(monthOfYear + 1);
                    deadTime.setDay(dayOfMonth);
                    TimePickerDialog tpd = TimePickerDialog.newInstance(
                            onTimeSetListener,
                            now.get(Calendar.HOUR),
                            now.get(Calendar.MINUTE),
                            DateFormat.is24HourFormat(getContext())
                    );
                    tpd.setTitle(getString(R.string.pick_time));
                    tpd.setVersion(TimePickerDialog.Version.VERSION_2);
                    tpd.setOnCancelListener(onCancelListener);
                    tpd.show(fm, getString(R.string.time_picker_dialog));
                };
                dpd = DatePickerDialog.newInstance(
                        onDateSetListener,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.setTitle(getString(R.string.pick_date));
                dpd.setMinDate(now);
                dpd.setOnCancelListener(onCancelListener);
                dpd.show(fm, getString(R.string.date_picker_dialog));
                break;

            case R.id.details_edit_needed_time:
                DatePicked neededTime = task.getNeededTimePicked();
                onTimeSetListener = (view, hourOfDay, minute, second) -> {
                    neededTime.setHour(hourOfDay);
                    neededTime.setMinutes(minute);
                    task.setNeededTimePicked(neededTime);
                    taskNeededTimeTextView.setText(neededTime.toString());
                    new AsyncWorker().updateTask(task);
                };
                onDateSetListener = (view, year, monthOfYear, dayOfMonth) -> {
                    neededTime.setYear(year);
                    neededTime.setMonth(monthOfYear + 1);
                    neededTime.setDay(dayOfMonth);
                    TimePickerDialog tpd = TimePickerDialog.newInstance(
                            onTimeSetListener,
                            now.get(Calendar.HOUR),
                            now.get(Calendar.MINUTE),
                            DateFormat.is24HourFormat(getContext())
                    );
                    tpd.setTitle(getString(R.string.pick_time));
                    tpd.setVersion(TimePickerDialog.Version.VERSION_2);
                    tpd.setOnCancelListener(onCancelListener);
                    tpd.show(fm, getString(R.string.time_picker_dialog));
                };
                dpd = DatePickerDialog.newInstance(
                        onDateSetListener,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.setTitle(getString(R.string.pick_date));
                dpd.setMinDate(now);
                dpd.setOnCancelListener(onCancelListener);
                dpd.show(fm, getString(R.string.date_picker_dialog));
                break;
            case R.id.details_google_sync:
                Intent intent = new Intent(Intent.ACTION_INSERT)
                        .setData(Events.CONTENT_URI)
                        .putExtra(EXTRA_EVENT_END_TIME, task.getDatePicked().getCalendarFormat().getTimeInMillis())
                        .putExtra(EXTRA_EVENT_BEGIN_TIME, task.getNeededTimePicked().getCalendarFormat().getTimeInMillis())
                        .putExtra(Events.TITLE, task.getName())
                        .putExtra(Events.DESCRIPTION, task.getTaskDescription())
                        .putExtra(Events.AVAILABILITY, Events.AVAILABILITY_BUSY);
                startActivity(intent);
                break;
        }
    }
}
