package com.example.funpark.database.firebase;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.funpark.database.entity.SalesTicketEntity;
import com.example.funpark.database.entity.TicketEntity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class SalesTicketLiveData extends LiveData<SalesTicketEntity> {

    private final DatabaseReference reference;
    private final SalesTicketLiveData.MyValueEventListener listener = new SalesTicketLiveData.MyValueEventListener();

    public SalesTicketLiveData(DatabaseReference ref) {
        reference = ref;
    }

    @Override
    protected void onActive() {
        reference.addValueEventListener(listener);
    }


    private class MyValueEventListener implements ValueEventListener {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            SalesTicketEntity entity = dataSnapshot.getValue(SalesTicketEntity.class);
            entity.setId(dataSnapshot.getKey());
            setValue(entity);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

     }


    }
}
