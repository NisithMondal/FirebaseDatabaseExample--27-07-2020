package com.nisith.firebasedatabaseexample;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private EditText nameEditText, ageEditText;
    private Button sendButton, updateButton;
    private TextView responseTextView;
    private DatabaseReference databaseReference;
    private DatabaseReference childRef;
    private ValueEventListener valueEventListener;
    private ChildEventListener childEventListener;
    private FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (firebaseDatabase == null) {
            firebaseDatabase =FirebaseDatabase.getInstance();
        }
        setContentView(R.layout.activity_main);
        nameEditText = findViewById(R.id.name_edit_text);
        ageEditText = findViewById(R.id.age_edit_text);
        sendButton = findViewById(R.id.send_button);
        updateButton = findViewById(R.id.update_button);
        responseTextView = findViewById(R.id.response_text_view);
        databaseReference = firebaseDatabase.getReference("users");
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addUser();
            }
        });




    }


    @Override
    protected void onStart() {
        super.onStart();
        if (databaseReference != null){
            childEventListener = databaseReference.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    Log.d("ABCD","onChildAdded() :: "+snapshot);
                    Log.d("ABCD","previousChildName :: "+previousChildName);
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    Log.d("ABCD","onChildChanged() :: "+snapshot);
                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                    Log.d("ABCD","onChildRemoved() :: "+snapshot);
                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
//            valueEventListener = databaseReference.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot snapshot) {
//                   Log.d("ABCD", "valueEventListener is trigger");
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError error) {
//                    Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
//                }
//            });
        }
    }


    private void addUser(){
        String name = nameEditText.getText().toString().trim();
        String age = ageEditText.getText().toString();
        User user = new User(name, age);
        if (databaseReference != null){
            databaseReference.child(databaseReference.push().getKey()).setValue(user)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(MainActivity.this, "Data Send Successfully", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(MainActivity.this, "Error: "+e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
        }

    }


    @Override
    protected void onPause() {
        super.onPause();
      if (databaseReference != null){
//          databaseReference.removeEventListener(valueEventListener);
      }
      if (databaseReference != null){
          databaseReference.removeEventListener(childEventListener);
      }

    }


}