package com.developer.calllogmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.developer.calllogmanager.Models.SugarModel;
import com.developer.calllogmanager.dbHelper.DatabaseHelper;

public class UpdateNote extends AppCompatActivity {

    EditText updateNoteEditText;
    Button updateNoteBtn;

    DatabaseHelper helper ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_note);

        helper = new DatabaseHelper(this);
        updateNoteBtn = findViewById(R.id.btnUpdateNote);
        updateNoteEditText = findViewById(R.id.editTextUpdateNote);

        Bundle recievedNoteBundle = getIntent().getBundleExtra("NoteBundle");
        final SugarModel modelRecieved = (SugarModel) recievedNoteBundle.getSerializable("NoteToEdit");

        updateNoteEditText.setText(modelRecieved.getNote());

        updateNoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modelRecieved.setNote(updateNoteEditText.getText().toString());
                boolean isUpdatedNote = helper.updateNote( modelRecieved.getCurrentDate() , modelRecieved);
                if (isUpdatedNote){

                    Toast.makeText(UpdateNote.this, "Updated Note Successfully !", Toast.LENGTH_SHORT).show();
                    Intent toMain = new Intent(UpdateNote.this , MainActivity.class);
                    startActivity(toMain);
                }
                else{
                    Toast.makeText(UpdateNote.this, "Error Occured While updating the note", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
