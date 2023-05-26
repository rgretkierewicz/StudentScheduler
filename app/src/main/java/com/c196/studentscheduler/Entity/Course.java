package com.c196.studentscheduler.Entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "courses")
public class Course {
    @PrimaryKey(autoGenerate = true)
    private int courseID;
    private String title;
    private String start;
    private String end;
    private String status;
    private String instructorInfo;
    private String note;
    private int termID;

    public Course() {
    }

    public Course(int courseID, String title, String start, String end, String status, String instructorInfo, String note, int termID) {
        this.courseID = courseID;
        this.title = title;
        this.start = start;
        this.end = end;
        this.status = status;
        this.instructorInfo = instructorInfo;

        if (note == null) {
            this.note = "";
        }
        else {
            this.note = note;
        }

        this.termID = termID;
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getInstructorInfo() {
        return instructorInfo;
    }

    public void setInstructorInfo(String instructorInfo) {
        this.instructorInfo = instructorInfo;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getTermID() {
        return termID;
    }

    public void setTermID(int termID) {
        this.termID = termID;
    }

    public String toString() {
        return this.title;
    }
}
