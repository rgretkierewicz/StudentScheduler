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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.c196.studentscheduler.Database.Repository;
import com.c196.studentscheduler.Entity.Assessment;
import com.c196.studentscheduler.Entity.Course;
import com.c196.studentscheduler.Entity.Term;
import com.c196.studentscheduler.R;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CourseDetails extends AppCompatActivity {
    EditText editID;
    EditText editTitle;
    EditText editStart;
    EditText editEnd;
    EditText editStatus;
    EditText editInstructorInfo;
    EditText editNote;
    int id;
    String title;
    String start;
    String end;
    String status;
    String instructorInfo;
    String note;
    int termID;
    final Calendar myCalendarStart = Calendar.getInstance();
    final Calendar myCalendarEnd = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener startDate;
    DatePickerDialog.OnDateSetListener endDate;
    Course course;
    Repository repository;
    String dateFormat = "MM/dd/yyyy";
    SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);
    ArrayList<Term> termArrayList = new ArrayList<>();
    RecyclerView recyclerView;
    Spinner termsSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);
        repository = new Repository(getApplication());

        //EditTexts
        editID = findViewById(R.id.editCourseID);
        editTitle = findViewById(R.id.editCourseTitle);
        editStart = findViewById(R.id.editCourseStart);
        editEnd = findViewById(R.id.editCourseEnd);
        editStatus = findViewById(R.id.editCourseStatus);
        editInstructorInfo = findViewById(R.id.editInstructorInfo);
        editNote = findViewById(R.id.editNote);

        //RecyclerView and Spinner
        recyclerView = findViewById(R.id.assessmentRecyclerView);
        termsSpinner = findViewById(R.id.termsSpinner);

        //Grabbing from adapter
        id = getIntent().getIntExtra("id", -1);
        title = getIntent().getStringExtra("title");
        start = getIntent().getStringExtra("start");
        end = getIntent().getStringExtra("end");
        status = getIntent().getStringExtra("status");
        instructorInfo = getIntent().getStringExtra("instructorInfo");
        note = getIntent().getStringExtra("note");
        termID = getIntent().getIntExtra("termId", -1);

        //If id exists, set text to id
        if (id != -1) {
            editID.setText(String.valueOf(id));
        }

        //EditText setup
        editTitle.setText(title);
        editStart.setText(start);
        editEnd.setText(end);
        editStatus.setText(status);
        editInstructorInfo.setText(instructorInfo);
        editNote.setText(note);

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
                new DatePickerDialog(CourseDetails.this, startDate, myCalendarStart
                        .get(Calendar.YEAR), myCalendarStart.get(Calendar.MONTH),
                        myCalendarStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        editEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(CourseDetails.this, endDate, myCalendarEnd
                        .get(Calendar.YEAR), myCalendarEnd.get(Calendar.MONTH),
                        myCalendarEnd.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        termsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                termID = termArrayList.get(i).getTermID();
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
                termsSpinner.setSelection(0);
            }

        });

        Button saveButton = findViewById(R.id.saveCourse);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editTitle.getText().toString().equals("") || editStart.getText().toString().equals("") || editEnd.getText().toString().equals("")
                        || editStatus.getText().toString().equals("") || editInstructorInfo.getText().toString().equals("")) {

                }
                else {
                    if (id == -1) {
                        course = new Course(0, editTitle.getText().toString(), editStart.getText().toString(), editEnd.getText().toString(),
                                editStatus.getText().toString(), editInstructorInfo.getText().toString(), editNote.getText().toString(), termID);
                        repository.insert(course);
                    } else {
                        course = new Course(id, editTitle.getText().toString(), editStart.getText().toString(), editEnd.getText().toString(),
                                editStatus.getText().toString(), editInstructorInfo.getText().toString(), editNote.getText().toString(), termID);
                        repository.update(course);
                    }

                    Toast.makeText(CourseDetails.this, "Course is saved", Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        });

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_coursedetails, menu);
        return true;
    }

    private void updateLabelStart() {
        editStart.setText(sdf.format(myCalendarStart.getTime()));
    }

    private void updateLabelEnd() {
        editEnd.setText(sdf.format(myCalendarEnd.getTime()));
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

                    try {thisDate = sdf.parse(editDate); }
                    catch(ParseException e) { e.printStackTrace(); }

                    Long notifyTime = thisDate.getTime();
                    Intent intent = new Intent(CourseDetails.this, MyReceiver.class);
                    intent.putExtra("key", "Course \"" + editTitle.getText().toString() + "\" begins on " + editDate);
                    PendingIntent sender = PendingIntent.getBroadcast(CourseDetails.this, ++MainActivity.numAlert, intent, PendingIntent.FLAG_IMMUTABLE);
                    AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
                    alarmManager.set(AlarmManager.RTC_WAKEUP, notifyTime, sender);
                    Toast.makeText(CourseDetails.this, "Start date notification is set.", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(CourseDetails.this, "A notification cannot be set without a valid start date.", Toast.LENGTH_LONG).show();
                }
                return true;

            case R.id.notifyEnd:
                if (!editEnd.getText().toString().equals("")) {
                    String editDate = editEnd.getText().toString();
                    Date thisDate = null;

                    try {
                        thisDate = sdf.parse(editDate);
                    }
                    catch (ParseException e) {
                        e.printStackTrace();
                    }

                    Long notifyTime = thisDate.getTime();
                    Intent intent = new Intent(CourseDetails.this, MyReceiver.class);
                    intent.putExtra("key", "Course \"" + editTitle.getText().toString() + "\" ends on " + editDate);
                    PendingIntent sender = PendingIntent.getBroadcast(CourseDetails.this, ++MainActivity.numAlert, intent, PendingIntent.FLAG_IMMUTABLE);
                    AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                    alarmManager.set(AlarmManager.RTC_WAKEUP, notifyTime, sender);
                    Toast.makeText(CourseDetails.this, "End date notification is set.", Toast.LENGTH_LONG).show();
                }

                else {
                    Toast.makeText(CourseDetails.this, "A notification cannot be set without a valid end date.", Toast.LENGTH_LONG).show();
                }
            return true;

            case R.id.share:
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, editNote.getText().toString());
                sendIntent.putExtra(Intent.EXTRA_TITLE, editTitle.getText().toString() + " note");
                sendIntent.setType("text/plain");
                Intent shareIntent = Intent.createChooser(sendIntent, null);
                startActivity(shareIntent);
                return true;

            case R.id.delete:
                for (Course course : repository.getAllCourses()) {
                    if (course.getCourseID() == id) {
                        repository.delete(course);
                        Toast.makeText(CourseDetails.this, course.getTitle() + " was deleted", Toast.LENGTH_LONG).show();
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

        //RecyclerView setup
        final AssessmentAdapter assessmentAdapter = new AssessmentAdapter(this);
        recyclerView.setAdapter(assessmentAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<Assessment> filteredAssessments = new ArrayList<>();
        for (Assessment a : repository.getAllAssessments()) {
            if (a.getCourseID() == id) filteredAssessments.add(a);
        }
        assessmentAdapter.setAssessments(filteredAssessments);

        //Set all terms to an array list for term spinner
        for (Term term : repository.getAllTerms()) {
            termArrayList.add(term);
        }

        //Spinner setup
        ArrayAdapter<Term> termAdapter = new ArrayAdapter<Term>(this, android.R.layout.simple_spinner_item, termArrayList);
        termAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        termsSpinner.setAdapter(termAdapter);

        //If course exists, set spinner selection to it's associated term. Else, set spinner selection to 0.
        if(id != -1) {
            termArrayList.add(0, new Term());

            //Populate spinner
            for (int i = 0; i < termArrayList.size(); i++) {
                if ((termArrayList.get(i)).getTermID() == termID) {
                    termsSpinner.setSelection(i);
                }
            }
        }

        else {
            termArrayList.add(0, new Term());
            termsSpinner.setSelection(0);
        }

    }



}