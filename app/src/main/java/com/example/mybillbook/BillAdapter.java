package com.example.mybillbook;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BillAdapter extends RecyclerView.Adapter<BillAdapter.ViewHolder>{
    private ArrayList<BillModal> billModalArrayList;
    private Context context;
    private DBHandler myDB;

    // constructor

    public BillAdapter(ArrayList<BillModal> billModalArrayList, Context context,DBHandler myDB) {
        this.billModalArrayList = billModalArrayList;
        this.context = context;
        this.myDB=myDB;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_bill_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BillModal model = billModalArrayList.get(position);
        holder.billname.setText(model.getBillname());
        holder.billnumber.setText(model.getBillnumber());
        holder.category.setText(model.getCategary());
        String amt= String.valueOf(model.amountpaid);
        holder.amtpaid.setText(amt);
        holder.date.setText(model.getDate());
        holder.status.setText(model.getSatus());
    }
    public Context getContext(){
        return context;
    }

    public void deleteTask(int position){
        BillModal item = billModalArrayList.get(position);
        myDB.deleteTask(item.getId());
        billModalArrayList.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public int getItemCount() {
        return billModalArrayList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView billname,billnumber,category,amtpaid,date,status;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            billname=itemView.findViewById(R.id.billname);
            billnumber=itemView.findViewById(R.id.billnumber);
            category=itemView.findViewById(R.id.category);
            amtpaid=itemView.findViewById(R.id.amtpaid);
            date=itemView.findViewById(R.id.date);
            status=itemView.findViewById(R.id.status);
        }
    }
}
