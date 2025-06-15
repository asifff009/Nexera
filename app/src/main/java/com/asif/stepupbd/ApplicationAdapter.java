package com.asif.stepupbd;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ApplicationAdapter extends RecyclerView.Adapter<ApplicationAdapter.AppViewHolder> {

    Context context;
    List<ApplicationModel> list;

    public ApplicationAdapter(Context context, List<ApplicationModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public AppViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AppViewHolder(LayoutInflater.from(context).inflate(R.layout.item_application, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AppViewHolder holder, int position) {
        ApplicationModel app = list.get(position);
        holder.email.setText("Email: " + app.getEmail());
        holder.skill.setText("Skill: " + app.getSkill());
        holder.exp.setText("Experience: " + app.getExperience());
        holder.intst.setText("Interest: " + app.getInterest());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class AppViewHolder extends RecyclerView.ViewHolder {
        TextView email, skill, exp, intst;

        public AppViewHolder(@NonNull View itemView) {
            super(itemView);
            email = itemView.findViewById(R.id.emailText);
            skill = itemView.findViewById(R.id.skillText);
            exp = itemView.findViewById(R.id.expText);
            intst = itemView.findViewById(R.id.intText);
        }
    }
}
