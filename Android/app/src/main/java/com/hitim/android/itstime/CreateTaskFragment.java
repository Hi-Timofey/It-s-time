package com.hitim.android.itstime;


import android.content.DialogInterface;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.android.material.appbar.AppBarLayout;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.Calendar;

public class CreateTaskFragment extends Fragment implements CompoundButton.OnCheckedChangeListener, View.OnClickListener {

    private FloatingActionMenu floatingActionMenu;
    private FloatingActionButton createTaskButton;
    private Toolbar toolbar;
    private AppBarLayout l;
    private TextView deadText, reminderText, tagsText, sphereCardText;
    private ImageButton deadImg, remindImg, taskIcon;
    private CheckBox dateBox, remindBox;
    private CardView sphereCard;

    private final String[] spheresOfLife = {
            "Work",
            "Health",
            "Routine",
            "Yourself"
    };

    //Task arguments
    private String taskName, taskDecsription, sphere;
    private DatePicked datePicked = new DatePicked();
    private DatePicked reminderPicked = new DatePicked();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_create_task, container, false);
        reminderText = v.findViewById(R.id.userToDoRemindMeTextView);
        deadText = v.findViewById(R.id.deadline_text_view);
        dateBox = v.findViewById(R.id.date_check_box);
        remindBox = v.findViewById(R.id.reminder_check_box);
        floatingActionMenu = getActivity().findViewById(R.id.floating_button_menu);
        toolbar = getActivity().findViewById(R.id.tool_bar);
        sphereCard = v.findViewById(R.id.sphere_mini_card_view);
        sphereCardText = v.findViewById(R.id.mini_card_text);
        createTaskButton = v.findViewById(R.id.makeToDoFloatingActionButton);
        createTaskButton.setOnClickListener(this);
        sphereCard.setOnClickListener(this);
        dateBox.setOnCheckedChangeListener(this);
        remindBox.setOnCheckedChangeListener(this);

        toolbar.setTitle(getString(R.string.create_task));
        toolbar.setNavigationIcon(R.drawable.ic_close_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.beginTransaction().replace(R.id.fragment_container, fm.findFragmentByTag(getString(R.string.sphere_fragment)))
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
                        .addToBackStack(null)
                        .commit();
            }
        });
        l = getActivity().findViewById(R.id.tool_bar_layout);
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        l.setElevation(0);
        floatingActionMenu.hideMenuButton(true);
        floatingActionMenu.close(true);
        floatingActionMenu.setVisibility(View.INVISIBLE);
    }


    @Override
    public void onPause() {
        super.onPause();
        l.setElevation(2);
        floatingActionMenu.setVisibility(View.VISIBLE);
        floatingActionMenu.showMenuButton(true);

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        DialogInterface.OnCancelListener onCancelListener;
        TimePickerDialog.OnTimeSetListener onTimeSetListener;
        DatePickerDialog.OnDateSetListener onDateSetListener;
        DatePickerDialog dpd;
        //TODO: Вынести OnClick в переменные вот прям вот тута
        switch (buttonView.getId()) {
            case R.id.date_check_box:
                if (isChecked) {
                    Calendar now = Calendar.getInstance();
                    onCancelListener = dialog -> {
                        if (dateBox.isChecked()) {
                            dateBox.setChecked(false);
                            datePicked.resetAll();
                        }
                    };
                    onTimeSetListener = (view, hourOfDay, minute, second) -> {
                        datePicked.setHour(hourOfDay);
                        datePicked.setMinutes(minute);
                        deadText.setText(datePicked.toString());
                    };
                    onDateSetListener = (view, year, monthOfYear, dayOfMonth) -> {
                        datePicked.setYear(year);
                        datePicked.setMonth(monthOfYear + 1);
                        datePicked.setDay(dayOfMonth);
                        TimePickerDialog tpd = TimePickerDialog.newInstance(
                                onTimeSetListener,
                                now.get(Calendar.HOUR),
                                now.get(Calendar.MINUTE),
                                DateFormat.is24HourFormat(getContext())
                        );
                        tpd.setTitle(getString(R.string.pick_time));
                        tpd.setOnCancelListener(onCancelListener);
                        tpd.show(getFragmentManager(), getString(R.string.time_picker_dialog));
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
                    dpd.show(getFragmentManager(), getString(R.string.date_picker_dialog));
                } else deadText.setText(getString(R.string.pick_deadline_date));
                break;
            case R.id.reminder_check_box:
                if (isChecked) {
                    Calendar now = Calendar.getInstance();
                    onCancelListener = dialog -> {
                        if (remindBox.isChecked()) {
                            remindBox.setChecked(false);
                            reminderPicked.resetAll();
                        }
                    };
                    onTimeSetListener = (view, hourOfDay, minute, second) -> {
                        reminderPicked.setHour(hourOfDay);
                        reminderPicked.setMinutes(minute);
                        reminderText.setText(reminderPicked.toString());
                    };
                    onDateSetListener = (view, year, monthOfYear, dayOfMonth) -> {
                        reminderPicked.setYear(year);
                        reminderPicked.setMonth(monthOfYear + 1);
                        reminderPicked.setDay(dayOfMonth);
                        TimePickerDialog tpd = TimePickerDialog.newInstance(
                                onTimeSetListener,
                                now.get(Calendar.HOUR),
                                now.get(Calendar.MINUTE),
                                DateFormat.is24HourFormat(getContext())
                        );
                        tpd.setTitle(getString(R.string.pick_time));
                        tpd.setOnCancelListener(onCancelListener);
                        tpd.show(getFragmentManager(), getString(R.string.time_picker_dialog));
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
                    dpd.show(getFragmentManager(), getString(R.string.date_picker_dialog));
                } else reminderText.setText(getString(R.string.remind_me));
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sphere_mini_card_view:
                AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(getContext(), R.style.AlertDialogStyle_Light));
                builder.setTitle(R.string.choose_sphere)
                        .setCancelable(false)
                        .setNeutralButton(getString(R.string.back_button), (dialog, id) -> dialog.cancel())
                        .setSingleChoiceItems(spheresOfLife, -1,
                                (DialogInterface.OnClickListener) (dialog, item) -> {
                                    sphere = spheresOfLife[item];
                                    sphereCardText.setTextColor(getResources().getColor(R.color.whiteColor));
                                    switch (item) {
                                        case 0:
                                            sphereCardText.setText(getString(R.string.work));
                                            sphereCard.setBackground(getResources().getDrawable(R.drawable.work_background));
                                            break;
                                        case 1:
                                            sphereCardText.setText(getString(R.string.health));
                                            sphereCard.setBackground(getResources().getDrawable(R.drawable.health_background));
                                            break;
                                        case 2:
                                            sphereCardText.setText(getString(R.string.routine));
                                            sphereCard.setBackground(getResources().getDrawable(R.drawable.routine_background));
                                            break;
                                        case 3:
                                            sphereCardText.setText(getString(R.string.yourself));
                                            sphereCard.setBackground(getResources().getDrawable(R.drawable.yourself_background));
                                            break;
                                        default:
                                            sphereCardText.setText(getString(R.string.choose_the_right_one));
                                            sphereCard.setCardBackgroundColor(getResources().getColor(R.color.whiteColor));
                                    }
                                    dialog.cancel();
                                }
                        );
                AlertDialog dialog = builder.show();
                dialog.show();
                break;
            case R.id.makeToDoFloatingActionButton:

                break;
        }
    }
}
