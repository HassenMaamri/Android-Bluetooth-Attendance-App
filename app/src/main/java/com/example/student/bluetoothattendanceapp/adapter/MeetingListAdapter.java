package com.example.student.bluetoothattendanceapp.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.student.bluetoothattendanceapp.R;
import com.example.student.bluetoothattendanceapp.listener.RecyclerItemClickListener;
import com.example.student.bluetoothattendanceapp.model.Meeting;
import com.example.student.bluetoothattendanceapp.widget.LetterTile;

import java.util.ArrayList;
import java.util.List;

public class MeetingListAdapter extends RecyclerView.Adapter<MeetingListAdapter.ContactHolder>{

    private List<Meeting> meetingList;
    private Context context;

    private RecyclerItemClickListener recyclerItemClickListener;

    public MeetingListAdapter(Context context) {
        this.context = context;
        this.meetingList = new ArrayList<>();
    }

    private void add(Meeting item) {
        meetingList.add(item);
        notifyItemInserted(meetingList.size() - 1);
    }

    public void addAll(List<Meeting> meetingList) {
        for (Meeting meeting : meetingList) {
            add(meeting);
        }
    }

    public void remove(Meeting item) {
        int position = meetingList.indexOf(item);
        if (position > -1) {
            meetingList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }

    public Meeting getItem(int position) {
        return meetingList.get(position);
    }

    @Override
    public ContactHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_contact_item, parent, false);

        final ContactHolder contactHolder = new ContactHolder(view);

        contactHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPos = contactHolder.getAdapterPosition();
                if (adapterPos != RecyclerView.NO_POSITION) {
                    if (recyclerItemClickListener != null) {
                        recyclerItemClickListener.onItemClick(adapterPos, contactHolder.itemView);
                    }
                }
            }
        });

        return contactHolder;
    }

    @Override
    public void onBindViewHolder(ContactHolder holder, int position) {
        final Meeting meeting = meetingList.get(position);

        final Resources res = context.getResources();
        final int tileSize = res.getDimensionPixelSize(R.dimen.letter_tile_size);

        LetterTile letterTile = new LetterTile(context);

        Bitmap letterBitmap = letterTile.getLetterTile(meeting.getName(),
                String.valueOf(meeting.getId()), tileSize, tileSize);

        holder.thumb.setImageBitmap(letterBitmap);
        holder.name.setText(meeting.getName());
        holder.phone.setText(meeting.getPhone());
    }

    @Override
    public int getItemCount() {
        return meetingList.size();
    }

    public void setOnItemClickListener(RecyclerItemClickListener recyclerItemClickListener) {
        this.recyclerItemClickListener = recyclerItemClickListener;
    }

    static class ContactHolder extends RecyclerView.ViewHolder {

        ImageView thumb;
        TextView name;
        TextView phone;

        public ContactHolder(View itemView) {
            super(itemView);

            thumb = (ImageView) itemView.findViewById(R.id.thumb);
            name = (TextView) itemView.findViewById(R.id.name);
            phone = (TextView) itemView.findViewById(R.id.phone);

        }
    }
}
