package com.pyramidions.bajugali.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pyramidions.bajugali.R;
import com.pyramidions.bajugali.activities.NoticeBoard;
import com.pyramidions.bajugali.activities.NoticeBoardDetails;
import com.pyramidions.bajugali.dataModels.NoticeModel;

import java.util.ArrayList;

import static com.pyramidions.bajugali.R.color.colorBlack;

/**
 * Created by User on 12/17/2016.
 */

public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.ViewHolder> {

    ArrayList<NoticeModel> noticeModelsList = new ArrayList<NoticeModel>();
    Context context;
    public NoticeAdapter(ArrayList<NoticeModel> noticeModelsList, Context context){
        this.noticeModelsList = noticeModelsList;
        this.context = context;
    }

    @Override
    public NoticeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.notice_lists, parent, false);
        ViewHolder pvh = new ViewHolder(v);
        return pvh;
    }


    @Override
    public void onBindViewHolder(NoticeAdapter.ViewHolder holder, int position) {
        NoticeModel noticeModel = noticeModelsList.get(position);
        holder.noticeboardName.setText(noticeModel.getNoticeName());
        if (position %2 == 0){
            holder.notice_cardview.setCardBackgroundColor(context.getResources().getColor(R.color.colorWhite));
        }else{
            holder.notice_cardview.setCardBackgroundColor(context.getResources().getColor(R.color.colorGrey));
        }


//       holder.noticeboardDescription.setText(noticeModel.getNoticeSummary());
    }

    @Override
    public int getItemCount() {
        return noticeModelsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView noticeboardName,noticeboardDescription;
        private CardView notice_cardview;
        View view;
        public ViewHolder(View itemView) {
            super(itemView);
            noticeboardName = (TextView) itemView.findViewById(R.id.noticeboardName);
            notice_cardview = (CardView) itemView.findViewById(R.id.notice_cardview);
          //  noticeboardDescription = (TextView) itemView.findViewById(R.id.noticeboardDescription);
            itemView.setOnClickListener(this);
            view = itemView;

        }

        @Override
        public void onClick(View v) {
            NoticeModel noticeModel = noticeModelsList.get(getAdapterPosition());
            Intent intent = new Intent(context, NoticeBoardDetails.class);
            intent.putExtra("NoticeName",noticeModel.getNoticeName());
            intent.putExtra("NoticeSummary",noticeModel.getNoticeSummary());
            intent.putExtra("NoticeDescription",noticeModel.getNoticeDescription());
            intent.putExtra("NoticeContactUserId",noticeModel.getNoticeID());
            intent.putExtra("NoticeCreateDate",noticeModel.getNoticeCreateDate());
            intent.putExtra("NoticeExpiryDate",noticeModel.getNoticeExpiryDate());
            intent.putExtra("Contactpersonemail",noticeModel.getContactpersonemail());
            intent.putExtra("Contactpersonname",noticeModel.getContactpersonname());
            intent.putExtra("Contactpersonnumber",noticeModel.getContactpersonnumber());
            context.startActivity(intent);
            Activity activity = (Activity) context;
            activity.overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
        }
    }
}
