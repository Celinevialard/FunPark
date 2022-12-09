package com.example.funpark.database.firebase;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.funpark.database.entity.TicketTypeEntity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TicketTypeListLiveData extends LiveData<List<TicketTypeEntity>> {

    private final DatabaseReference reference;
    private final MyValueEventListener listener = new MyValueEventListener();

    public TicketTypeListLiveData(DatabaseReference ref){
        reference = ref;
    }

    @Override
    protected void onActive() {
        reference.addValueEventListener(listener);
    }

    private class MyValueEventListener implements ValueEventListener{
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
        setValue(toTicketTypes(snapshot));
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    }

    private List<TicketTypeEntity> toTicketTypes(DataSnapshot snapshot){
        List<TicketTypeEntity> ticketTypes = new ArrayList<>();
        for(DataSnapshot childSnapshot : snapshot.getChildren()){
            TicketTypeEntity entity = childSnapshot.getValue(TicketTypeEntity.class);
            entity.setId(childSnapshot.getKey());
            ticketTypes.add(entity);
        }
        return ticketTypes;
    }



}
