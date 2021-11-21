package com.example.socialmediaappsal.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.socialmediaappsal.R;
import com.example.socialmediaappsal.adapter.NotificationAdapter;
import com.example.socialmediaappsal.model.Notification;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class Notification2Fragment extends Fragment {

    RecyclerView notificationRV;
    ArrayList<Notification> list;
    FirebaseDatabase database;
    FirebaseAuth auth;

    public Notification2Fragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notification2, container, false);

        notificationRV = view.findViewById(R.id.notification2RV);

        list = new ArrayList<>();
//        list.add(new Notification(R.drawable.hrk,"<b> Hritisk </b> Hello I am interested We are friends and close friends","Just Now"));
//        list.add(new Notification(R.drawable.hrk,"<b> Hritisk </b>Hello I am interested","Just Now"));
//        list.add(new Notification(R.drawable.hrk,"<b> Salman </b>Hello I am interested","Just Now"));
//        list.add(new Notification(R.drawable.hrk,"Hello I am interested","Just Now"));
//        list.add(new Notification(R.drawable.hrk,"Hello I am interested","Just Now"));
//        list.add(new Notification(R.drawable.hrk,"<b> Salman </b>Hello I am interested","Just Now"));
//        list.add(new Notification(R.drawable.hrk,"<b> Salman </b>Hello I am interested","Just Now"));
//        list.add(new Notification(R.drawable.hrk,"<b> Salman </b>Hello I am interested","Just Now"));
//        list.add(new Notification(R.drawable.hrk,"<b> Salman </b>Hello I am interested","Just Now"));

        NotificationAdapter adapter = new NotificationAdapter(list,getContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        notificationRV.setLayoutManager(layoutManager);
        notificationRV.setAdapter(adapter);

        database.getReference()
                .child("notification")
                .child(FirebaseAuth.getInstance().getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                            Notification notification = dataSnapshot.getValue(Notification.class);
                            notification.setNotificationID(dataSnapshot.getKey());
                            list.add(notification);
                        }
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        return view;
    }
}