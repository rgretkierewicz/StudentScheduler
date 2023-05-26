package com.c196.studentscheduler.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.c196.studentscheduler.Entity.Term;
import com.c196.studentscheduler.R;
import java.util.List;

public class TermAdapter extends RecyclerView.Adapter<TermAdapter.TermViewHolder>{
    class TermViewHolder extends RecyclerView.ViewHolder {
        private final TextView termItemView;

        TermViewHolder(View itemView) {
            super(itemView);
            termItemView = itemView.findViewById(R.id.textView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    final Term current =  mTerms.get(position);
                    Intent intent = new Intent(context, TermDetails.class);

                    intent.putExtra("id", current.getTermID());
                    intent.putExtra("title", current.getTitle());
                    intent.putExtra("start", current.getStart());
                    intent.putExtra("end", current.getEnd());

                    context.startActivity(intent);
                }
            });
        }
    }

    private  List<Term> mTerms;
    private final Context context;
    private final LayoutInflater mInflater;

    public TermAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }


    @NonNull
    @Override
    public TermViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.term_list_item, parent, false);
        return new TermViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TermViewHolder holder, int position) {
        if(mTerms != null) {
            Term current = mTerms.get(position);
            String title = current.getTitle();
            holder.termItemView.setText(title);

        }
        else {
            holder.termItemView.setText("No Term Name");
        }

    }

    @Override
    public int getItemCount() {
        return mTerms.size();
    }

    public void setTerms(List<Term> terms) {
        mTerms = terms;
        notifyDataSetChanged();
    }


}
