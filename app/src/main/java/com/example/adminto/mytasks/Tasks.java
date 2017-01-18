package com.example.adminto.mytasks;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Tasks extends AppCompatActivity {
    TextView CurrentDate; // поле для сохранения выбранной даты
    Calendar dateAndTime=Calendar.getInstance(); // календарь
    Button Button4;
    ListView Task ;
    List<String> list = new ArrayList<>();
    ArrayAdapter<String> adapter;
    EditText input;
    CheckBox checkBox;
    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_tasks);
        DataBase db = new DataBase(this);
        // data/time choose {
        CurrentDate =(TextView)findViewById(R.id.CurrentDate);
        setInitialDate();
        // }
        Button4 = (Button) findViewById(R.id.button4);
        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, list);
        Task = (ListView) findViewById(R.id.Task);
        Task.setAdapter(adapter);


        Task.setLongClickable(true);
        Task.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> parent, View v, int position, long id) {
                deleteNote(position);
                return true;
            }});
    }

    public void deleteNote(final int position)
    {
        AlertDialog.Builder build = new AlertDialog.Builder(Tasks.this);
        checkBox = new CheckBox(this);
        build.setTitle("Удалить запись?")
                .setCancelable(false)
                .setPositiveButton("Да",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                list.remove(position);
                                adapter.notifyDataSetChanged();
                            }
                        })
                .setNegativeButton("Нет",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }

                        });

        AlertDialog a = build.create();
        a.show();
    }
    // create plans in window{
    public void onClick(final View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Tasks.this);
        input = new EditText(this);

        builder.setView(input);
        builder.setTitle("Введите описание плана")
                .setCancelable(false)

                .setNegativeButton("Указать время",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                Checkbox(v);
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    String everyday;
    public void Checkbox(final View v)
    {
        AlertDialog.Builder build = new AlertDialog.Builder(Tasks.this);
        checkBox = new CheckBox(this);
        build.setTitle("Сделать запись ежедневной?")
                .setCancelable(false)
                .setPositiveButton("Да",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                everyday = "Каждый день";
                                dialog.cancel();
                                showDialog1(v);

                            }
                        })
                .setNegativeButton("Нет",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                everyday = " ";
                                dialog.cancel();
                                showDialog1(v);
                            }

                        });

        AlertDialog a = build.create();
        a.show();
    }
    // }

    //Create date choose dialog {
    public void setDate(View v) {
        new DatePickerDialog(Tasks.this, d,
                dateAndTime.get(Calendar.YEAR),
                dateAndTime.get(Calendar.MONTH),
                dateAndTime.get(Calendar.DAY_OF_MONTH))
                .show();
    }

    private void setInitialDate() {

        CurrentDate.setText(DateUtils.formatDateTime(this,
                dateAndTime.getTimeInMillis(),
                DateUtils.FORMAT_NUMERIC_DATE| DateUtils.FORMAT_SHOW_YEAR
                        | DateUtils.FORMAT_ABBREV_ALL));

    }

    DatePickerDialog.OnDateSetListener d=new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dateAndTime.set(Calendar.YEAR, year);
            dateAndTime.set(Calendar.MONTH, monthOfYear);
            dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            setInitialDate();

        }
    };
    //  }

    // Create time choose dialog {
    public void showDialog1(View v)
    {
        new TimePickerDialog(Tasks.this, t,
                dateAndTime.get(Calendar.HOUR_OF_DAY),
                dateAndTime.get(Calendar.MINUTE), true)
                .show();

    }
        private void setInitialTime() {
            list.add(0,DateUtils.formatDateTime(this,
                dateAndTime.getTimeInMillis(),
                DateUtils.FORMAT_ABBREV_ALL | DateUtils.FORMAT_ABBREV_ALL
                        | DateUtils.FORMAT_SHOW_TIME) + "\n" + input.getText().toString() + "\n" + everyday);

            adapter.notifyDataSetChanged();
            Context context = getApplicationContext();
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, "Запись была добавлена", duration);
            toast.show();
    }
    TimePickerDialog.OnTimeSetListener t=new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            dateAndTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
            dateAndTime.set(Calendar.MINUTE, minute);
            setInitialTime();
        }
    };


    //  }

}



