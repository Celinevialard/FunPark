package com.example.funpark.database.firebase;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.funpark.database.entity.TicketEntity;
import com.example.funpark.database.entity.VisitorEntity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class VisitorLiveData extends LiveData<VisitorEntity> {

    private final DatabaseReference reference;
    private final VisitorLiveData.MyValueEventListener listener = new VisitorLiveData.MyValueEventListener();

    public VisitorLiveData(DatabaseReference ref) {
        reference = ref;
    }

    public VisitorLiveData(VisitorEntity value) {
        super(value);
        reference = null;
    }

    @Override
    protected void onActive() {
        if(reference != null)
            reference.addValueEventListener(listener);
    }


    private class MyValueEventListener implements ValueEventListener {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            VisitorEntity entity = dataSnapshot.getValue(VisitorEntity.class);
            entity.setId(dataSnapshot.getKey());
            setValue(entity);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

     }


    }
}
