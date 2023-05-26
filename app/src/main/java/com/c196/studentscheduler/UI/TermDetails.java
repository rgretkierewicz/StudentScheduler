package com.c196.studentscheduler.UI;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.c196.studentscheduler.Database.Repository;
import com.c196.studentscheduler.Entity.Course;
import com.c196.studentscheduler.Entity.Term;
import com.c196.studentscheduler.R;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class TermDetails extends AppCompatActivity {
    EditText editID;
    EditText editTitle;
    EditText editStart;
    EditText editEnd;
    int id;
    String title;
    String start;
    String end;
    final Calendar myCalendarStart = Calendar.getInstance();
    final Calendar myCalendarEnd = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener startDate;
    DatePickerDialog.OnDateSetListener endDate;
    Term term;
    Repository repository;
    String dateFormat = "MM/dd/yyyy";
    SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_details);
        repository = new Repository(getApplication());

        //EditTexts
        editID = findViewById(R.id.editTermID);
        editTitle = findViewById(R.id.editTermTitle);
        editStart = findViewById(R.id.editTermStart);
        editEnd = findViewById(R.id.editTermEnd);

        //RecyclerView
        recyclerView = findViewById(R.id.courseRecyclerView);

        id = getIntent().getIntExtra("id", -1);
        title = getIntent().getStringExtra("title");
        start = getIntent().getStringExtra("start");
        end = getIntent().getStringExtra("end");

        //If id exists, set text to id
        if (id != -1) {
            editID.setText(String.valueOf(id));
        }

        //EditText setup
        editTitle.setText(title);
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
                // TODO Auto-generated method stub
                new DatePickerDialog(TermDetails.this, startDate, myCalendarStart
                        .get(Calendar.YEAR), myCalendarStart.get(Calendar.MONTH),
                        myCalendarStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        editEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(TermDetails.this, endDate, myCalendarEnd
                        .get(Calendar.YEAR), myCalendarEnd.get(Calendar.MONTH),
                        myCalendarEnd.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        Button saveButton = findViewById(R.id.saveTerm);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(editTitle.getText().toString().equals("") || editStart.getText().toString().equals("") || editEnd.getText().toString().equals("")) {
                    Toast.makeText(TermDetails.this, "Save failed. All fields must have values.", Toast.LENGTH_LONG).show();
                }

                else {
                    if (id == -1) {
                        term = new Term(0, editTitle.getText().toString(), editStart.getText().toString(), editEnd.getText().toString());
                        repository.insert(term);
                    }
                    else {
                        term = new Term(id, editTitle.getText().toString(), editStart.getText().toString(), editEnd.getText().toString());
                        repository.update(term);
                    }

                    finish();
                    Toast.makeText(TermDetails.this, "Term is saved", Toast.LENGTH_LONG).show();
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
        getMenuInflater().inflate(R.menu.menu_termdetails, menu);
        return true;
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;

            case R.id.delete:
                Term currentTerm = new Term();

                for (Term term : repository.getAllTerms()) {
                    if (term.getTermID() == id) {
                        currentTerm = term;
                    }
                }

                int numCourses = 0;

                for (Course course : repository.getAllCourses()) {
                    if (course.getTermID() == id) {
                        ++numCourses;
                    }
                }

                if (numCourses == 0) {
                    repository.delete(currentTerm);
                    Toast.makeText(TermDetails.this, currentTerm.getTitle() + " was deleted", Toast.LENGTH_LONG).show();
                    finish();
                }
                else {
                    Toast.makeText(TermDetails.this, "Cannot delete a term with courses", Toast.LENGTH_LONG).show();
                }
                return true;
        }
        return super.onOptionsItemSelected(item);

    }


    @Override
    protected void onResume() {
        super.onResume();

        //RecyclerView setup
        final CourseAdapter courseAdapter = new CourseAdapter(this);
        recyclerView.setAdapter(courseAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<Course> filteredCourses = new ArrayList<>();
        for (Course c : repository.getAllCourses()) {
            if (c.getTermID() == id) filteredCourses.add(c);
        }
        courseAdapter.setCourses(filteredCourses);
    }


}
