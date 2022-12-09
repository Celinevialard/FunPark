package com.example.funpark.database.firebase;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.funpark.database.entity.TicketEntity;
import com.example.funpark.database.entity.VisitorEntity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class VisitorListLiveData extends LiveData<List<VisitorEntity>> {

    private final DatabaseReference reference;
    private final MyValueEventListener listener = new MyValueEventListener();

    public VisitorListLiveData(DatabaseReference ref) {
        reference = ref;
    }

    @Override
    protected void onActive() {
        reference.addValueEventListener(listener);
    }

    private class MyValueEventListener implements ValueEventListener {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            setValue(toVisitors(dataSnapshot));
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    }

    private List<VisitorEntity> toVisitors(DataSnapshot snapshot) {
        List<VisitorEntity> tickets = new ArrayList<>();
        for (DataSnapshot childSnapshot : snapshot.getChildren()) {
            VisitorEntity entity = childSnapshot.getValue(VisitorEntity.class);
            entity.setId(childSnapshot.getKey());
            tickets.add(entity);
        }
        return tickets;
    }

}
