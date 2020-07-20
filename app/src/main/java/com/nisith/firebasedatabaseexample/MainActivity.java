package com.nisith.firebasedatabaseexample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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

public class MainActivity extends AppCompatActivity {

    private EditText nameEditText, ageEditText;
    private Button sendButton, updateButton;
    private TextView responseTextView;
    private DatabaseReference databaseReference;
    private DatabaseReference childRef;
    private ValueEventListener valueEventListener;
    private ChildEventListener childEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nameEditText = findViewById(R.id.name_edit_text);
        ageEditText = findViewById(R.id.age_edit_text);
        sendButton = findViewById(R.id.send_button);
        updateButton = findViewById(R.id.update_button);
        responseTextView = findViewById(R.id.response_text_view);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("message");
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });




    }


    @Override
    protected void onStart() {
        super.onStart();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String result = snapshot.getValue().toString();
                    responseTextView.setText(result);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void sendMessage(){
        String message = nameEditText.getText().toString();
        databaseReference.setValue(message);
    }


    @Override
    protected void onPause() {
        super.onPause();
      if (databaseReference != null){
          databaseReference.removeEventListener(valueEventListener);
      }
      if (databaseReference != null){
//          databaseReference.removeEventListener(childEventListener);
      }

    }


}