package com.example.ankit.watsapplistner.utilities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.ankit.watsapplistner.R;
import com.example.ankit.watsapplistner.model.MessageData;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.CustomView> {

    Context context;

    List<MessageData> messageDataList;
    long facilityId;
    long docId;

    public MessageAdapter(Context mContext, List<MessageData> messageData) {
        this.context = mContext;

        this.messageDataList = messageData;

    }


    @Override
    public MessageAdapter.CustomView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_row_layout, parent, false);
        final CustomView viewHolder = new CustomView(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.CustomView holder, int position) {
        holder.txtName.setText(messageDataList.get(position).getName());
        holder.txtPhone.setText(messageDataList.get(position).getPhone());
        holder.txtMessage.setText(messageDataList.get(position).getMessage());
    }

    @Override
    public int getItemCount() {
        return messageDataList.size();
    }

    public class CustomView extends RecyclerView.ViewHolder  {
        private TextView txtDoctorInfo;
        private TextView txtDoctorSpeciality;
        private RelativeLayout rlMain;
        private TextView txtName;
        private TextView txtPhone;
        private TextView txtMessage;


        public CustomView(View itemView) {
            super(itemView);
            findViews(itemView);
        }

        private void findViews(View view) {
            txtName = (TextView)view.findViewById(R.id.txtName);
            txtPhone = (TextView)view.findViewById(R.id.txtPhone);
            txtMessage = (TextView)view.findViewById(R.id.txtMessage);

        }
    }





}