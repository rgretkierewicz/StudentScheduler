package com.c196.studentscheduler.Database;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.c196.studentscheduler.DAO.AssessmentDAO;
import com.c196.studentscheduler.DAO.CourseDAO;
import com.c196.studentscheduler.DAO.TermDAO;
import com.c196.studentscheduler.Entity.Assessment;
import com.c196.studentscheduler.Entity.Course;
import com.c196.studentscheduler.Entity.Term;

// Increment version when changes to entities occur
@Database(entities = {Assessment.class, Course.class, Term.class}, version=6, exportSchema = false)
public abstract class SchedulerDatabaseBuilder extends RoomDatabase {
    public abstract AssessmentDAO assessmentDAO();//Instance of AssessmentDAO
    public abstract CourseDAO courseDAO(); //Instance of CourseDAO
    public abstract TermDAO termDAO(); //Instance of TermDAO

    private static volatile SchedulerDatabaseBuilder INSTANCE; //Instance of Database

    //Method from room
    static SchedulerDatabaseBuilder  getDatabase(final Context context) {
        //Check that database does not already exist, is instance null?
        if(INSTANCE == null) {
            synchronized (SchedulerDatabaseBuilder.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), SchedulerDatabaseBuilder .class, "StudentSchedulerDatabase.db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }

        }
        //Instance of database is returned whether database is already built or newly built
        return INSTANCE;

    }
}
