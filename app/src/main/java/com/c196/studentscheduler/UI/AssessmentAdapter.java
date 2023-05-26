package com.c196.studentscheduler.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.c196.studentscheduler.Entity.Assessment;
import com.c196.studentscheduler.R;

import java.util.List;

public class AssessmentAdapter extends RecyclerView.Adapter<AssessmentAdapter.AssessmentViewHolder>{
    class AssessmentViewHolder extends RecyclerView.ViewHolder {
        private final TextView assessmentItemView;

        AssessmentViewHolder(View itemView) {
            super(itemView);
            assessmentItemView = itemView.findViewById(R.id.textView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    final Assessment current =  mAssessments.get(position);
                    Intent intent = new Intent(context, AssessmentDetails.class);

                    intent.putExtra("id", current.getAssessmentID());
                    intent.putExtra("title", current.getTitle());
                    intent.putExtra("type", current.getType());
                    intent.putExtra("start", current.getStart());
                    intent.putExtra("end", current.getEnd());
                    intent.putExtra("courseID", current.getCourseID());

                    context.startActivity(intent);
                }
            });
        }
    }

    private List<Assessment> mAssessments;
    private final Context context;
    private final LayoutInflater mInflater;

    public AssessmentAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }


    @NonNull
    @Override
    public AssessmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.assessment_list_item, parent, false);
        return new AssessmentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AssessmentViewHolder holder, int position) {
        if(mAssessments != null) {
            Assessment current = mAssessments.get(position);
            String title = current.getTitle();
            holder.assessmentItemView.setText(title);

        }
        else {
            holder.assessmentItemView.setText("No Assessment Name");
        }

    }

    @Override
    public int getItemCount() {
        return mAssessments.size();
    }

    public void setAssessments(List<Assessment> assessments) {
        mAssessments = assessments;
        notifyDataSetChanged();
    }
}
