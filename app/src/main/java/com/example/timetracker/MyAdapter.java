package com.example.timetracker;

import android.content.Context;
import android.content.Intent;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    List<String> data1;
    Context context;
    private SparseBooleanArray mCheckedItems = new SparseBooleanArray();
    List<String> checkedItems = new ArrayList<String>();

    public MyAdapter(Context ct, List<String> s1){
        context = ct;
        data1 = s1;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row,parent,false);
        return new MyViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position){
        holder.checkBox.setOnCheckedChangeListener(null);
        holder.checkBox.setText(data1.get(position));
        if(holder.checkBox.isChecked()){
            System.out.println("THIS CHECK BOX VALUE IS: ");
            System.out.println(holder.checkBox.getText().toString());
        }
        holder.checkBox.setChecked(mCheckedItems.get(position));

        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.checkBox.isChecked()){
                    checkedItems.add(holder.checkBox.getText().toString());
                }
                else{
                    if(checkedItems.contains(holder.checkBox.getText().toString())){
                        checkedItems.removeAll(Arrays.asList(holder.checkBox.getText().toString()));
                    }
                }
                Snackbar snackbar = Snackbar.make(v, "app added", Snackbar.LENGTH_SHORT);
                snackbar.show();
            }
        });
    }
    public List<Integer> getSelectedItemPositions(){
        List<Integer> selected = new ArrayList<>();
        for (int i = 0; i < mCheckedItems.size(); i++) {
            final boolean checked = mCheckedItems.valueAt(i);
            if (checked) {
                selected.add(mCheckedItems.keyAt(i));
            }
        }
        return selected;
    }

    public void restoreSelectedItems(List<Integer> positions){
        for (Integer position : positions) {
            mCheckedItems.put(position, true);
        }
    }
    @Override
    public int getItemCount(){
        return data1.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkBox;
        ConstraintLayout mainLayout;
        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            checkBox = itemView.findViewById(R.id.checkBox3);
            mainLayout = itemView.findViewById(R.id.mainActivity);

        }


    }
}
