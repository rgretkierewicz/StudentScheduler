package com.c196.studentscheduler.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.c196.studentscheduler.Entity.Assessment;
import com.c196.studentscheduler.Entity.Course;
import com.c196.studentscheduler.Entity.Term;
import com.c196.studentscheduler.R;


public class MainActivity extends AppCompatActivity {
    public static int numAlert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button termsButton = findViewById(R.id.termsButton);
        Button coursesButton = findViewById(R.id.coursesButton);
        Button assessmentsButton = findViewById(R.id.assessmentsButton);

        termsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, TermList.class);
                startActivity(intent);
            }
        });

        coursesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CourseList.class);
                startActivity(intent);
            }
        });

        assessmentsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AssessmentList.class);
                startActivity(intent);
            }
        });

    }

}