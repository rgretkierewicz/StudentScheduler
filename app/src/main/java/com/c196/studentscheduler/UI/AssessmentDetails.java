package com.c196.studentscheduler.UI;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.c196.studentscheduler.Database.Repository;
import com.c196.studentscheduler.Entity.Assessment;
import com.c196.studentscheduler.Entity.Course;
import com.c196.studentscheduler.R;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class AssessmentDetails extends AppCompatActivity {
    EditText editID;
    EditText editTitle;
    EditText editType;
    EditText editStart;
    EditText editEnd;
    EditText editCourseID;
    int id;
    String title;
    String type;
    String start;
    String end;
    int courseID;
    final Calendar myCalendarStart = Calendar.getInstance();
    final Calendar myCalendarEnd = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener startDate;
    DatePickerDialog.OnDateSetListener endDate;
    Repository repository;
    Assessment assessment;
    String dateFormat = "MM/dd/yyyy";
    SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);
    ArrayList<Course> courseArrayList = new ArrayList<>();
    Spinner coursesSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_details);
        repository = new Repository(getApplication());

        //EditTexts
        editID = findViewById(R.id.editAssessmentID);
        editTitle = findViewById(R.id.editAssessmentTitle);
        editType = findViewById(R.id.editAssessmentType);
        editStart = findViewById(R.id.editAssessmentStart);
        editEnd = findViewById(R.id.editAssessmentEnd);

        //Spinner
        coursesSpinner = (Spinner) findViewById(R.id.coursesSpinner);

        //Grabbing from adapter
        id = getIntent().getIntExtra("id", -1);
        title = getIntent().getStringExtra("title");
        type = getIntent().getStringExtra("type");
        start = getIntent().getStringExtra("start");
        end = getIntent().getStringExtra("end");
        courseID = getIntent().getIntExtra("courseID", -1);

        //If id exists, set text to id
        if (id != -1) {
            editID.setText(String.valueOf(id));
        }

        //EditText setup
        editTitle.setText(title);
        editType.setText(type);
        editStart.setText(start);
        editEnd.setText(end);

        startDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendarStart.set(Calendar.YEAR, year);
                myCalendarStart.set(Calendar.MONTH, month);
                myCalendarStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelStart();
            }
        };

        endDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendarEnd.set(Calendar.YEAR, year);
                myCalendarEnd.set(Calendar.MONTH, month);
                myCalendarEnd.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelEnd();
            }
        };

        editStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(AssessmentDetails.this, startDate, myCalendarStart
                        .get(Calendar.YEAR), myCalendarStart.get(Calendar.MONTH),
                        myCalendarStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        editEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(AssessmentDetails.this, endDate, myCalendarEnd
                        .get(Calendar.YEAR), myCalendarEnd.get(Calendar.MONTH),
                        myCalendarEnd.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        coursesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                courseID = courseArrayList.get(i).getCourseID();
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
                coursesSpinner.setSelection(0);
            }
        });

        Button saveButton = findViewById(R.id.saveAssessment);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editTitle.getText().toString().equals("") || editType.getText().toString().equals("") || editStart.getText().toString().equals("")
                        || editEnd.getText().toString().equals("")) {
                    Toast.makeText(AssessmentDetails.this, "Save failed. All fields must have values.", Toast.LENGTH_LONG).show();
                } else {
                    if (id == -1) {
                        assessment = new Assessment(0, editTitle.getText().toString(), editType.getText().toString(), editStart.getText().toString(), editEnd.getText().toString(), courseID);
                        repository.insert(assessment);
                    } else {
                        assessment = new Assessment(id, editTitle.getText().toString(), editType.getText().toString(), editStart.getText().toString(), editEnd.getText().toString(), courseID);
                        repository.update(assessment);
                    }

                    Toast.makeText(AssessmentDetails.this, "Assessment is saved", Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        });

    }

    private void updateLabelStart() {
        editStart.setText(sdf.format(myCalendarStart.getTime()));
    }

    private void updateLabelEnd() {
        editEnd.setText(sdf.format(myCalendarEnd.getTime()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_assessmentdetails, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;

            case R.id.notifyStart:
                if (!editStart.getText().toString().equals("")) {
                    String editDate = editStart.getText().toString();
                    Date thisDate = null;

                    try {
                        thisDate = sdf.parse(editDate);
                    }
                    catch (ParseException e) {
                        e.printStackTrace();
                    }

                    Long notifyTime = thisDate.getTime();
                    Intent intent = new Intent(AssessmentDetails.this, MyReceiver.class);
                    intent.putExtra("key", "Assessment \"" + editTitle.getText().toString() + "\" begins on " + editDate);
                    PendingIntent sender = PendingIntent.getBroadcast(AssessmentDetails.this, ++MainActivity.numAlert, intent, PendingIntent.FLAG_IMMUTABLE);
                    AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                    alarmManager.set(AlarmManager.RTC_WAKEUP, notifyTime, sender);
                    Toast.makeText(AssessmentDetails.this, "Start date notification is set.", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(AssessmentDetails.this, "A notification cannot be set without a valid start date.", Toast.LENGTH_LONG).show();
                }
                return true;


            case R.id.notifyEnd:
                if (!editEnd.getText().toString().equals("")) {
                    String editDate = editEnd.getText().toString();
                    Date thisDate = null;

                    try {
                        thisDate = sdf.parse(editDate);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    Long notifyTime = thisDate.getTime();
                    Intent intent = new Intent(AssessmentDetails.this, MyReceiver.class);
                    intent.putExtra("key", "Assessment \"" + editTitle.getText().toString() + "\" ends on " + editDate);
                    PendingIntent sender = PendingIntent.getBroadcast(AssessmentDetails.this, ++MainActivity.numAlert, intent, PendingIntent.FLAG_IMMUTABLE);
                    AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                    alarmManager.set(AlarmManager.RTC_WAKEUP, notifyTime, sender);
                    Toast.makeText(AssessmentDetails.this, "End date notification is set.", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(AssessmentDetails.this, "A notification cannot be set without a valid end date.", Toast.LENGTH_LONG).show();
                }
                return true;


            case R.id.delete:
                for (Assessment assessment : repository.getAllAssessments()) {
                    if (assessment.getAssessmentID() == id) {
                        repository.delete(assessment);
                        Toast.makeText(AssessmentDetails.this, assessment.getTitle() + " was deleted", Toast.LENGTH_LONG).show();
                    }
                    finish();
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();

        //Set all courses to an array list for course spinner
        for (Course course : repository.getAllCourses()) {
            courseArrayList.add(course);
        }

        //Spinner setup
        ArrayAdapter<Course> courseAdapter = new ArrayAdapter<Course>(this, android.R.layout.simple_spinner_item, courseArrayList);
        courseAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        coursesSpinner.setAdapter(courseAdapter);

        //If assessment exists, set spinner selection to it's associated course. Else, set spinner selection to 0.
        if(id != -1) {
            courseArrayList.add(0, new Course());

            for (int i = 0; i < courseArrayList.size(); i++) {
                if ((courseArrayList.get(i)).getCourseID() == courseID) {
                    coursesSpinner.setSelection(i);
                }
            }
        }

        else {
            courseArrayList.add(0, new Course());
            coursesSpinner.setSelection(0);
        }

    }

}