package com.example.funpark.database.firebase;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.funpark.database.entity.SalesTicketEntity;
import com.example.funpark.database.entity.TicketEntity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SalesTicketListLiveData extends LiveData<List<SalesTicketEntity>> {

    private final DatabaseReference reference;
    private final MyValueEventListener listener = new MyValueEventListener();

    public SalesTicketListLiveData(DatabaseReference ref) {
        reference = ref;
    }

    @Override
    protected void onActive() {
        reference.addValueEventListener(listener);
    }

    private class MyValueEventListener implements ValueEventListener {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            setValue(toSalesTickets(dataSnapshot));
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    }

    private List<SalesTicketEntity> toSalesTickets(DataSnapshot snapshot) {
        List<SalesTicketEntity> tickets = new ArrayList<>();
        for (DataSnapshot childSnapshot : snapshot.getChildren()) {
            SalesTicketEntity entity = childSnapshot.getValue(SalesTicketEntity.class);
            entity.setId(childSnapshot.getKey());
            tickets.add(entity);
        }
        return tickets;
    }

}
