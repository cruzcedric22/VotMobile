package com.example.voting;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class PositionAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private ArrayList<Candidates> candidatesList;

    public PositionAdapter(Context context, int layout, ArrayList<Candidates> candidatesList) {
        super();
        this.context = context;
        this.layout = layout;
        this.candidatesList = candidatesList;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return candidatesList.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return candidatesList.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    private class ViewHolder{
        ImageView imgCan;
        TextView nameText,posText;
    }


    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        // TODO Auto-generated method stub
        View row = view;
        ViewHolder holder = new ViewHolder();
        if(row == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layout, null);
            holder.posText = (TextView) row.findViewById(R.id.posText);
            row.setTag(holder);
        }
        else {
            holder = (ViewHolder) row.getTag();
        }
        Candidates candidates = candidatesList.get(position);
        holder.posText.setText(candidates.getPosition());
        return row;
    }
}


