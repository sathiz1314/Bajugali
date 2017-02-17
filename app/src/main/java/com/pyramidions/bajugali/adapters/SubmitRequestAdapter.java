package com.pyramidions.bajugali.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.pyramidions.bajugali.R;
import com.pyramidions.bajugali.activities.SubmitRequest;
import com.pyramidions.bajugali.activities.SubmitRequestDetails;
import com.pyramidions.bajugali.dataModels.SubmitRequestModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by User on 1/10/2017.
 */

public class SubmitRequestAdapter extends RecyclerView.Adapter<SubmitRequestAdapter.ViewHolder> {

    Context context;
    private ArrayList<SubmitRequestModel> submitRequestModelList = new ArrayList<SubmitRequestModel>();

    public SubmitRequestAdapter(Context context, ArrayList<SubmitRequestModel> submitRequestModelList) {
        this.context = context;
        this.submitRequestModelList = submitRequestModelList;
    }

    @Override
    public SubmitRequestAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.submitrequest_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(SubmitRequestAdapter.ViewHolder holder, int position) {
        /*Collections.sort(submitRequestModelList, new Comparator<SubmitRequestModel>() {
            @Override
            public int compare(SubmitRequestModel lhs, SubmitRequestModel rhs) {

                return lhs.getSubmitdate().compareTo(rhs.getSubmitdate());
            }
        });*/

        SubmitRequestModel requestModel = submitRequestModelList.get(position);

        holder.submitrrequest_Tv_Subject.setText(requestModel.getDescription());
        holder.submitrrequest_Tv_Date.setText(requestModel.getSubmitdate());
        String status = requestModel.getStatus();

        if (status.equalsIgnoreCase("not started") ){
           holder.submitrrequest_Tv_Status.setText(requestModel.getStatus());
          // holder.submitrrequest_Tv_Status.setTextColor(context.getResources().getColor(R.color.main_color));
          //  holder.submitrrequest_Tv_Status.setBackground(context.getDrawable(R.drawable.textview_red));
            holder.submitrrequest_Tv_Status.setBackground(context.getResources().getDrawable(R.drawable.textview_red));

            //  holder.submitrrequest_Tv_Status.setBackgroundColor(context.getResources().getColor(R.color.main_color));

        }else if (requestModel.getStatus().equalsIgnoreCase("in progress")){
            holder.submitrrequest_Tv_Status.setText(requestModel.getStatus());
          //  holder.submitrrequest_Tv_Status.setBackground(context.getDrawable(R.drawable.textview_yellow));
            holder.submitrrequest_Tv_Status.setBackground(context.getResources().getDrawable(R.drawable.textview_yellow));

            // holder.submitrrequest_Tv_Status.setTextColor(context.getResources().getColor(R.color.colorYellow));

        }else if (requestModel.getStatus().equalsIgnoreCase("Resolved")){
            holder.submitrrequest_Tv_Status.setText(requestModel.getStatus());
          //  holder.submitrrequest_Tv_Status.setBackground(context.getDrawable(R.drawable.textview_green));
            holder.submitrrequest_Tv_Status.setBackground(context.getResources().getDrawable(R.drawable.textview_green));
           // holder.submitrrequest_Tv_Status.setTextColor(context.getResources().getColor(R.color.colorGreen));
        }

        if (position %2 ==0){
            holder.submitrequestcard.setCardBackgroundColor(context.getResources().getColor(R.color.colorWhite));
        }else {
            holder.submitrequestcard.setCardBackgroundColor(context.getResources().getColor(R.color.colorGrey));
        }

    }

    @Override
    public int getItemCount() {
        return submitRequestModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView submitrrequest_Tv_Subject, submitrrequest_Tv_Date, submitrrequest_Tv_Status;
        private CardView submitrequestcard;

        public ViewHolder(View itemView) {
            super(itemView);
            submitrrequest_Tv_Subject = (TextView) itemView.findViewById(R.id.submitrrequest_Tv_Subject);
            submitrrequest_Tv_Date = (TextView) itemView.findViewById(R.id.submitrrequest_Tv_Date);
            submitrrequest_Tv_Status = (TextView) itemView.findViewById(R.id.submitrrequest_Tv_Status);
            submitrequestcard = (CardView) itemView.findViewById(R.id.submitrequestcard);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            SubmitRequestModel submitRequestModel = submitRequestModelList.get(getAdapterPosition());
            Intent intent = new Intent(context, SubmitRequestDetails.class);
            intent.putExtra("getSubject",submitRequestModel.getSubject());
            intent.putExtra("getTicketNum",submitRequestModel.getTicketNum());
            intent.putExtra("getDescription",submitRequestModel.getDescription());
            intent.putExtra("getStatus",submitRequestModel.getStatus());
            intent.putExtra("getSubmitdate",submitRequestModel.getSubmitdate());
            intent.putExtra("getAssignedto",submitRequestModel.getAssignedto());
            intent.putExtra("getResolutioncomment",submitRequestModel.getResolutioncomment());
            intent.putExtra("getResolvedOnDate",submitRequestModel.getResolvedOnDate());
            context.startActivity(intent);
            Activity activity = (Activity) context;
            activity.overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);

        }
    }
}
