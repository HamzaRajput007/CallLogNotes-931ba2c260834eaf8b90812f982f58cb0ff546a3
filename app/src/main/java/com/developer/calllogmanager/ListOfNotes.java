package com.developer.calllogmanager;

import android.content.Intent;
import android.database.Cursor;
import android.speech.RecognizerIntent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.developer.calllogmanager.Adapters.ListOfNotesAdapter;
import com.developer.calllogmanager.Models.ListOfNotesModel;
import com.developer.calllogmanager.Models.SugarModel;
import com.developer.calllogmanager.dbHelper.DatabaseHelper;

import java.util.ArrayList;
import java.util.Locale;


public class ListOfNotes extends AppCompatActivity {
    private static final int REQUEST_CODE_SPEECH_INPUT = 1000;


    DatabaseHelper databaseHelper;

    RecyclerView recyclerViewListOfNotes;

    private void speak() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak Something");
        try {
            startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT);

        }catch (Exception e){
            Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case REQUEST_CODE_SPEECH_INPUT:
            {
                if (resultCode == RESULT_OK && null != data){
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
//                    editText.setText(result.get(0));




                    if(result.get(0).equals("save note")) {

                        Toast.makeText(this, ""+result, Toast.LENGTH_SHORT).show();

//                        Intent intent = new Intent(MainActivity.this, Main2Activity.class);
//                        startActivityForResult(intent, 2);
                    }
//                    else {
//                        Toast.makeText(this, " sorry"+result, Toast.LENGTH_SHORT).show();
//
//                    }
                }
                break;
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id==android.R.id.home) {
            Intent toMain = new Intent(getApplicationContext() , MainActivity.class);
            startActivity(toMain);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
    String Date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_notes);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Date = getIntent().getStringExtra("DATE");
        Log.d("DATE_VALUE", Date);
        databaseHelper = new DatabaseHelper(getBaseContext());
        ArrayList<SugarModel> records = new ArrayList<>();
        SugarModel model = new SugarModel();

        Cursor cursor = databaseHelper.GETNOTES(Date);

       /* if(cursor.moveToFirst()){
            while (!cursor.isAfterLast()) {

                String DateRecieved = String.valueOf(cursor.getString(cursor.getColumnIndex("DATE")));
                String Note = String.valueOf(cursor.getString(cursor.getColumnIndex("NOTE")));

                model.setDate(DateRecieved);
                model.setNote(Note);
                model.setExtra(String.valueOf(cursor.getString(cursor.getColumnIndex("EXTRA"))));
                model.setNumber(String.valueOf(cursor.getString(cursor.getColumnIndex("NUMBER"))));
                model.setHours(Integer.valueOf(String.valueOf(cursor.getString(cursor.getColumnIndex("HOURS")))));
                model.setMinutes(Integer.valueOf(String.valueOf(cursor.getString(cursor.getColumnIndex("MINUTES")))));
                model.setDayOfMonth(Integer.valueOf(String.valueOf(cursor.getString(cursor.getColumnIndex("DAY_OF_MONTH")))));
                model.setMonth(Integer.valueOf(String.valueOf(cursor.getString(cursor.getColumnIndex("MONTH")))));
                model.setYear(Integer.valueOf(String.valueOf(cursor.getString(cursor.getColumnIndex("YEAR")))));
                cursor.moveToNext();
                records.add(model);
            }
        }
        else
        {
            Toast.makeText(this, "Cursor Length : " + String.valueOf(cursor.getCount()), Toast.LENGTH_SHORT).show();
        }*/
        recyclerViewListOfNotes = findViewById(R.id.listOfNotesRecyclerView);
        recyclerViewListOfNotes.setHasFixedSize(true);
        recyclerViewListOfNotes.setLayoutManager(new LinearLayoutManager(this));
        ListOfNotesAdapter adapter = new ListOfNotesAdapter(Date , this);
        recyclerViewListOfNotes.setAdapter(adapter);

    }

}
