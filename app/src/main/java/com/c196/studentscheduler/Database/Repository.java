package com.c196.studentscheduler.Database;

import android.app.Application;
import com.c196.studentscheduler.DAO.AssessmentDAO;
import com.c196.studentscheduler.DAO.CourseDAO;
import com.c196.studentscheduler.DAO.TermDAO;
import com.c196.studentscheduler.Entity.Assessment;
import com.c196.studentscheduler.Entity.Course;
import com.c196.studentscheduler.Entity.Term;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Repository {
    private AssessmentDAO mAssessmentDAO;
    private CourseDAO mCourseDAO;
    private TermDAO mTermDAO;
    private List<Assessment> mAllAssessments;
    private List<Assessment> mAssociatedAssessments;
    private List<Course> mAllCourses;
    private List<Course> mAssociatedCourses;
    private List<Term> mAllTerms;

    private static int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    //Constructor
    public Repository(Application application) {
        SchedulerDatabaseBuilder db = SchedulerDatabaseBuilder.getDatabase(application);
        mAssessmentDAO = db.assessmentDAO();
        mCourseDAO = db.courseDAO();
        mTermDAO = db.termDAO();
    }

    public List<Assessment> getAllAssessments() {
        databaseExecutor.execute(() -> {
            mAllAssessments = mAssessmentDAO.getAllAssessments();
        });
        try {
            Thread.sleep(1000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllAssessments;
    }

    public List<Assessment> getAssociatedAssessments(int courseID) {
        databaseExecutor.execute(() -> {
            mAssociatedAssessments = mAssessmentDAO.getAssociatedAssessments(courseID);
        });
        try {
            Thread.sleep(1000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAssociatedAssessments;
    }


    public void insert(Assessment assessment) {
        databaseExecutor.execute(() -> {
            mAssessmentDAO.insert(assessment);
        });
        try {
            Thread.sleep(1000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void update(Assessment assessment) {
        databaseExecutor.execute(() -> {
            mAssessmentDAO.update(assessment);
        });
        try {
            Thread.sleep(1000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void delete(Assessment assessment) {
        databaseExecutor.execute(() -> {
            mAssessmentDAO.delete(assessment);
        });
        try {
            Thread.sleep(1000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public List<Course> getAllCourses() {
        databaseExecutor.execute(() -> {
            mAllCourses = mCourseDAO.getAllCourses();
        });
        try {
            Thread.sleep(1000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllCourses;
    }

    public List<Course> getAllAssociatedCourses(int termID) {
        databaseExecutor.execute(() -> {
            mAssociatedCourses = mCourseDAO.getAllAssociatedCourses(termID);
        });
        try {
            Thread.sleep(1000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAssociatedCourses;
    }


    public void insert(Course course) {
        databaseExecutor.execute(() -> {
            mCourseDAO.insert(course);
        });
        try {
            Thread.sleep(1000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void update(Course course) {
        databaseExecutor.execute(() -> {
            mCourseDAO.update(course);
        });
        try {
            Thread.sleep(1000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void delete(Course course) {
        databaseExecutor.execute(() -> {
            mCourseDAO.delete(course);
        });
        try {
            Thread.sleep(1000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public List<Term> getAllTerms() {
        databaseExecutor.execute(() -> {
            mAllTerms = mTermDAO.getAllTerms();
        });
        try {
            Thread.sleep(1000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllTerms;
    }

    public void insert(Term term) {
        databaseExecutor.execute(() -> {
            mTermDAO.insert(term);
        });
        try {
            Thread.sleep(1000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void update(Term term) {
        databaseExecutor.execute(() -> {
            mTermDAO.update(term);
        });
        try {
            Thread.sleep(1000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void delete(Term term) {
        databaseExecutor.execute(() -> {
            mTermDAO.delete(term);
        });
        try {
            Thread.sleep(1000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
