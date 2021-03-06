package com.hitim.android.itstime;


import android.content.DialogInterface;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
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
import com.joooonho.SelectableRoundedImageView;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.Calendar;

import nl.dionsegijn.steppertouch.StepperTouch;

import static com.jaredrummler.android.colorpicker.ColorPickerDialog.newBuilder;

class CreateTaskFragment extends Fragment implements CompoundButton.OnCheckedChangeListener, View.OnClickListener {

    private FloatingActionMenu floatingActionMenu;
    private FloatingActionButton createTaskButton;
    private Toolbar toolbar;
    private AppBarLayout l;
    private TextView deadText, neededTimeText, priorityText, sphereCardText;
    private SelectableRoundedImageView roundedImageView;
    private CheckBox dateBox, neededTimeBox;
    private CardView sphereCard;
    private EditText taskNameEditText, taskDescriptionEditText;
    private StepperTouch stepperTouchPriority;

    private final String[] spheresOfLife = {
            "Work",
            "Health",
            "Routine",
            "Yourself"
    };
    private String taskName = "", taskDecsription = "", sphere = "";
    private final DatePicked datePicked = new DatePicked();
    private final DatePicked neededTimePicked = new DatePicked();
    private int taskColor = -1;
    private int priority = -1;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_create_task, container, false);
        getAllViews(v);
        createTaskButton.setOnClickListener(this);
        sphereCard.setOnClickListener(this);
        dateBox.setOnCheckedChangeListener(this);
        neededTimeBox.setOnCheckedChangeListener(this);
        roundedImageView.setOnClickListener(this);
        toolbar.setTitle(getString(R.string.create_task));
        toolbar.setNavigationIcon(R.drawable.ic_close_white_24dp);
        toolbar.setNavigationOnClickListener(v1 -> {
            FragmentManager fm = getActivity().getSupportFragmentManager();
            fm.beginTransaction().replace(R.id.fragment_container, fm.findFragmentByTag(getString(R.string.sphere_fragment)))
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
                    .addToBackStack(null)
                    .commit();
        });
        l = getActivity().findViewById(R.id.tool_bar_layout);
        stepperTouchPriority.setMinValue(1);
        stepperTouchPriority.setMaxValue(6);
        stepperTouchPriority.setSideTapEnabled(true);
        stepperTouchPriority.addStepCallback((value, positive) -> {
            priority = value;
            if (value <= 3) {
                priorityText.setText(R.string.high_priority);
            } else {
                priorityText.setText(R.string.low_priority);
            }
        });
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
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.date_check_box:
                enableDateCheckBoxDialog(isChecked);
                break;
            case R.id.needed_time_check_box:
                enableNeededTimeCheckBoxDialog(isChecked);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sphere_mini_card_view:
                changeSphereDialog();
                break;
            case R.id.makeToDoFloatingActionButton:
                if (isValid()) {
                    //Task arguments
                    Task task = new Task(taskName, taskDecsription, datePicked, sphere, taskColor, neededTimePicked, priority);
                    AsyncWorker worker = new AsyncWorker();
                    if (worker.insertTask(task)) {
                        Toast.makeText(getContext(), "Successful", Toast.LENGTH_SHORT).show();
                    }

                    getFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, getFragmentManager().findFragmentByTag(getString(R.string.sphere_fragment)))
                            .commit();
                } else {
                    Toast.makeText(getContext(), getString(R.string.wrong_task), Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.set_icon_button:
                changeIconAndColor();
                break;
        }
    }

    private boolean isValid() {
        taskName = taskNameEditText.getText().toString();
        taskDecsription = taskDescriptionEditText.getText().toString();
        return !taskName.trim().isEmpty() && !sphere.equals("") && datePicked.isNull() && taskColor != -1 && priority != -1;
    }
    //Начало огромных методов
    //===================================================================================================

    //Собирает все view элементы
    private void getAllViews(View v) {
        neededTimeText = v.findViewById(R.id.userToDoNeededTimeText);
        roundedImageView = v.findViewById(R.id.set_icon_button);
        deadText = v.findViewById(R.id.deadline_text_view);
        dateBox = v.findViewById(R.id.date_check_box);
        neededTimeBox = v.findViewById(R.id.needed_time_check_box);
        floatingActionMenu = getActivity().findViewById(R.id.floating_button_menu);
        toolbar = getActivity().findViewById(R.id.tool_bar);
        sphereCard = v.findViewById(R.id.sphere_mini_card_view);
        sphereCardText = v.findViewById(R.id.mini_card_text);
        createTaskButton = v.findViewById(R.id.makeToDoFloatingActionButton);
        taskDescriptionEditText = v.findViewById(R.id.edit_text_task_description);
        taskNameEditText = v.findViewById(R.id.edit_text_task_name);
        stepperTouchPriority = v.findViewById(R.id.stepperTouch);
        priorityText = v.findViewById(R.id.priority_level_text);
    }

    //Вызывает диалоги для выбора даты дэдлайна задачи
    private void enableDateCheckBoxDialog(boolean isChecked) {

        DialogInterface.OnCancelListener onCancelListener;
        TimePickerDialog.OnTimeSetListener onTimeSetListener;
        DatePickerDialog.OnDateSetListener onDateSetListener;
        DatePickerDialog dpd;

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
            dpd.show(getFragmentManager(),"DEADLINE");
        } else {
            deadText.setText(getString(R.string.pick_deadline_date));
            datePicked.resetAll();
        }
    }

    //Вызывает диалоги для выбора даты напоминания задачи
    private void enableNeededTimeCheckBoxDialog(boolean isChecked) {
        DialogInterface.OnCancelListener onCancelListener;
        TimePickerDialog.OnTimeSetListener onTimeSetListener;
        DatePickerDialog.OnDateSetListener onDateSetListener;
        DatePickerDialog dpd;
        if (isChecked) {
            Calendar now = Calendar.getInstance();
            onCancelListener = dialog -> {
                if (neededTimeBox.isChecked()) {
                    neededTimeBox.setChecked(false);
                    neededTimePicked.resetAll();
                }
            };
            onTimeSetListener = (view, hourOfDay, minute, second) -> {
                neededTimePicked.setHour(hourOfDay);
                neededTimePicked.setMinutes(minute);
                neededTimeText.setText(neededTimePicked.toString());
            };
            onDateSetListener = (view, year, monthOfYear, dayOfMonth) -> {
                neededTimePicked.setYear(year);
                neededTimePicked.setMonth(monthOfYear + 1);
                neededTimePicked.setDay(dayOfMonth);
                TimePickerDialog tpd = TimePickerDialog.newInstance(
                        onTimeSetListener,
                        now.get(Calendar.HOUR),
                        now.get(Calendar.MINUTE),
                        DateFormat.is24HourFormat(getContext())
                );
                tpd.setTitle(getString(R.string.pick_time));
                tpd.setVersion(TimePickerDialog.Version.VERSION_2);
                tpd.setOnCancelListener(onCancelListener);
                tpd.show(getFragmentManager(), "TIMELINE");
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
        } else neededTimeText.setText(getString(R.string.when_to_start_worrying));
    }

    private void changeSphereDialog() {
        //TODO: Пофиксить баг с переводом
        String[] spheresTranslated = {getString(R.string.work),getString(R.string.health),getString(R.string.routine),getString(R.string.yourself)};
        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(getContext(), R.style.AlertDialogStyle_Light));
        builder.setTitle(R.string.choose_sphere)
                .setCancelable(false)
                .setNeutralButton(getString(R.string.back_button), (dialog, id) -> dialog.cancel())
                .setSingleChoiceItems(spheresTranslated, -1,
                        (dialog, item) -> {
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
    }

    //Диалог для выбора цвета
    private void changeIconAndColor() {
        newBuilder().setColor(getResources().getColor(R.color.whiteColor)).show(getActivity());
    }

    public void setTaskColor(int color) {
        Drawable image = getContext().getResources().getDrawable(R.drawable.ic_check);
        image.setColorFilter(color, PorterDuff.Mode.XOR);
        roundedImageView.setImageDrawable(image);
        roundedImageView.setBorderColor(color);
        taskColor = color;
    }
}
