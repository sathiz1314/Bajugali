package com.pyramidions.bajugali.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.pyramidions.bajugali.R;
import com.pyramidions.bajugali.activities.Bills;
import com.pyramidions.bajugali.dataModels.BillModel;

import java.util.ArrayList;

/**
 * Created by User on 12/9/2016.
 */

public class BillAdapter extends RecyclerView.Adapter<BillAdapter.ViewHolder> {

    private ArrayList<BillModel> billModelsList = new ArrayList<BillModel>();
    Context context;

    public BillAdapter(ArrayList<BillModel> billModelsList, Context context) {
        this.billModelsList = billModelsList;
        this.context = context;

    }

    @Override
    public BillAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rowbill, parent, false);
        ViewHolder pvh = new ViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(BillAdapter.ViewHolder holder, int position) {
        BillModel billModel = billModelsList.get(position);
        holder.billmonth.setText(String.valueOf(billModel.getMonth()));
        holder.billamountdue.setText("Rs" + " "+ String.valueOf(billModel.getDueAmount()));
        holder.billduedate.setText(billModel.getDueDate());
        String StatusOfPayment = billModel.getStatusOfPayment();
        if (StatusOfPayment.equalsIgnoreCase("Paid")){
            holder.pay.setVisibility(View.VISIBLE);
        }
        else if (StatusOfPayment.equalsIgnoreCase("NotPaid")){
            holder.paid.setVisibility(View.VISIBLE);
        }



    }

    @Override
    public int getItemCount() {
        return billModelsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView billmonth,billamountdue,billduedate;
        private Button paid,pay;

        public ViewHolder(View itemView) {
            super(itemView);
            billmonth = (TextView) itemView.findViewById(R.id.billmonth);
            billamountdue = (TextView) itemView.findViewById(R.id.billamountdue);
            billduedate = (TextView)itemView.findViewById(R.id.billduedate);
            paid = (Button) itemView.findViewById(R.id.paid);
            pay = (Button) itemView.findViewById(R.id.pay);


        }
    }
}
