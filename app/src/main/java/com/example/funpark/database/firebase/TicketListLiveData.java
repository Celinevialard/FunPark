package com.example.funpark.database.firebase;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.funpark.database.entity.TicketEntity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TicketListLiveData extends LiveData<List<TicketEntity>> {

    private final DatabaseReference reference;
    private final MyValueEventListener listener = new MyValueEventListener();

    public TicketListLiveData(DatabaseReference ref) {
        reference = ref;
    }

    @Override
    protected void onActive() {
        reference.addValueEventListener(listener);
    }

    private class MyValueEventListener implements ValueEventListener {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            setValue(toTickets(dataSnapshot));
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    }

    private List<TicketEntity> toTickets(DataSnapshot snapshot) {
        List<TicketEntity> tickets = new ArrayList<>();
        for (DataSnapshot childSnapshot : snapshot.getChildren()) {
            TicketEntity entity = childSnapshot.getValue(TicketEntity.class);
            entity.setId(childSnapshot.getKey());
            tickets.add(entity);
        }
        return tickets;
    }

}
