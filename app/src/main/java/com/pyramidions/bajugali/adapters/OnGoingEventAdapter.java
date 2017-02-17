package com.pyramidions.bajugali.adapters;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.pyramidions.bajugali.R;
import com.pyramidions.bajugali.activities.DetailedeventsActivity;
import com.pyramidions.bajugali.dataModels.DealModel;
import com.pyramidions.bajugali.dataModels.OnGoingDataModel;

import java.util.ArrayList;

/**
 * Created by User on 1/2/2017.
 */

public class OnGoingEventAdapter extends RecyclerView.Adapter<OnGoingEventAdapter.ViewHolder>{

    ArrayList<OnGoingDataModel> onGoingDataModel = new ArrayList<OnGoingDataModel>();
    Context context;
    public OnGoingEventAdapter(Context context, ArrayList<OnGoingDataModel> onGoingDataModel) {
        this.context = context;
        this.onGoingDataModel = onGoingDataModel;

    }

    @Override
    public OnGoingEventAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.events_lists, parent, false);
        ViewHolder pvh = new ViewHolder(v);
        return pvh;
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        OnGoingDataModel dataModel = onGoingDataModel.get(position);
        holder.eventname.setText(dataModel.getEventName());
        holder.eventdate.setText(dataModel.getDate());
        holder.eventtime.setText(dataModel.getTime());

       if (position % 2 ==0){
           holder.eventscard.setCardBackgroundColor(context.getResources().getColor(R.color.colorWhite));
       }else {
           holder.eventscard.setCardBackgroundColor(context.getResources().getColor(R.color.colorGrey));
       }
    }

    @Override
    public int getItemCount() {
        return onGoingDataModel.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView eventname,eventdate,eventtime;
        private CardView eventscard;
        public ViewHolder(View itemView) {
            super(itemView);
            eventname = (TextView) itemView.findViewById(R.id.eventname);
            eventdate = (TextView) itemView.findViewById(R.id.eventdate);
            eventtime = (TextView) itemView.findViewById(R.id.eventtime);
            eventscard = (CardView) itemView.findViewById(R.id.eventscard);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();
            OnGoingDataModel dataModel = onGoingDataModel.get(pos);
            Intent intent = new Intent(context,DetailedeventsActivity.class);
            intent.putExtra("EventName",dataModel.getEventName());
            intent.putExtra("Date",dataModel.getDate());
            intent.putExtra("Time",dataModel.getTime());
            intent.putExtra("ChiefGuest",dataModel.getChiefguest());
            intent.putExtra("EventImage",dataModel.getEventImage());
            intent.putExtra("EventId",dataModel.getEventId());
            intent.putExtra("amount",dataModel.getAmount());
            intent.putExtra("paymentstatus",dataModel.getPaymentstatus());
            intent.putExtra("EventDescription",dataModel.getEventDescription());
            context.startActivity(intent);
            Activity activity = (Activity) context;
            activity.overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
        }
    }
}
