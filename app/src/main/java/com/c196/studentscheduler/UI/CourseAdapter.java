package com.c196.studentscheduler.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.c196.studentscheduler.Entity.Course;
import com.c196.studentscheduler.R;

import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder>{
    class CourseViewHolder extends RecyclerView.ViewHolder {
        private final TextView courseItemView;

        CourseViewHolder(View itemView) {
            super(itemView);
            courseItemView = itemView.findViewById(R.id.textView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    final Course current =  mCourses.get(position);
                    Intent intent = new Intent(context, CourseDetails.class);

                    intent.putExtra("id", current.getCourseID());
                    intent.putExtra("title", current.getTitle());
                    intent.putExtra("start", current.getStart());
                    intent.putExtra("end", current.getEnd());
                    intent.putExtra("status", current.getStatus());
                    intent.putExtra("instructorInfo", current.getInstructorInfo());
                    intent.putExtra("note", current.getNote());
                    intent.putExtra("termId", current.getTermID());

                    context.startActivity(intent);
                }
            });
        }
    }

    private List<Course> mCourses;
    private final Context context;
    private final LayoutInflater mInflater;

    public CourseAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.course_list_item, parent, false);
        return new CourseViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
        if(mCourses != null) {
            Course current = mCourses.get(position);
            String title = current.getTitle();
            holder.courseItemView.setText(title);
        }
        else {
            holder.courseItemView.setText("No Course Name");
        }

    }

    @Override
    public int getItemCount() {
        return mCourses.size();
    }

    public void setCourses(List<Course> courses) {
        mCourses = courses;
        notifyDataSetChanged();
    }
}
