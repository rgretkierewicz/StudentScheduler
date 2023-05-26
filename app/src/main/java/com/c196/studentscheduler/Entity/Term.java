package com.c196.studentscheduler.Entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "terms")
public class Term {
    @PrimaryKey(autoGenerate = true)
    private int termID;
    private String title;
    private String start;
    private String end;

    public Term() {
    }

    public Term(int termID, String title, String start, String end) {
        this.termID = termID;
        this.title = title;
        this.start = start;
        this.end = end;
    }

    public int getTermID() {
        return termID;
    }

    public void setTermID(int termID) {
        this.termID = termID;
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

    public String toString() {
        return this.title;
    }
}
