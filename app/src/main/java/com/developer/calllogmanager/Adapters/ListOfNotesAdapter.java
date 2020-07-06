package com.developer.calllogmanager.Adapters;

import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.databinding.adapters.DatePickerBindingAdapter;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.developer.calllogmanager.EditNoteActivity;
import com.developer.calllogmanager.ListOfNotes;
import com.developer.calllogmanager.Models.SugarModel;
import com.developer.calllogmanager.R;
import com.developer.calllogmanager.UpdateNote;
import com.developer.calllogmanager.databinding.RowListOfNotesRecyclerViewBinding;
import com.developer.calllogmanager.dbHelper.DatabaseHelper;

import java.sql.Array;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ListOfNotesAdapter extends RecyclerView.Adapter<ListOfNotesAdapter.ListOfNotesViewHolder>{
    RowListOfNotesRecyclerViewBinding itemBinding;
    Context context;
    ArrayList<SugarModel> listOfNotesRecieved;
    ArrayList<SugarModel> records;
    int listIndex = 0;
    Cursor cursor ;

    DatabaseHelper databaseHelper ;

    public ListOfNotesAdapter(String Date, Context context ) {
        this.context = context;
        listOfNotesRecieved = new ArrayList<>();

        databaseHelper = new DatabaseHelper(context);

        cursor = databaseHelper.GETNOTES(Date);
        SugarModel model = new SugarModel();
        records = new ArrayList<>();

        if(cursor.moveToFirst()){
            while (!cursor.isAfterLast()) {
                String DateRecieved = String.valueOf(cursor.getString(cursor.getColumnIndex("DATE")));
                String Note = String.valueOf(cursor.getString(cursor.getColumnIndex("NOTE")));
                cursor.moveToNext();
            }
        }
        else
        {
            Toast.makeText(context, "Cursor Length : " + String.valueOf(cursor.getCount()), Toast.LENGTH_SHORT).show();
        }
    }

    @NonNull
    @Override
    public ListOfNotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        itemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),R.layout.row_list_of_notes_recycler_view,parent,false);
//        color = context.getResources().getColor(R.color.colorPrimaryDark);
//        filePath = Environment.getExternalStorageDirectory() + "/recorded_audio_notes";
        return new ListOfNotesViewHolder(itemBinding);
    }


    @Override
    public void onBindViewHolder(@NonNull ListOfNotesViewHolder holder, final int position) {
        // TODO notesModel is presenting the same data at the view find out why and resolve it when you are back to work

        Log.d("BindView Position : ", String.valueOf(position));
        
        if(cursor.moveToFirst()) {
            if (!cursor.isAfterLast()) {
                SugarModel model = new SugarModel();
                cursor.move(listIndex);
                String DateRecieved = String.valueOf(cursor.getString(cursor.getColumnIndex("DATE")));
                String Note = String.valueOf(cursor.getString(cursor.getColumnIndex("NOTE")));
                String id = String.valueOf(cursor.getString(cursor.getColumnIndex("ID")));
                Date dateObject = new Date(Long.parseLong(DateRecieved));
                SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy hh:mm a");
                int pos = cursor.getPosition();
                itemBinding.textViewNameId.setText(Note);
                itemBinding.textViewStatus.setText(formatter.format(dateObject));
                model.setNote(Note);
                model.setDate(DateRecieved);
                model.setUid(id);
                model.setCurrentDate(String.valueOf(cursor.getString(cursor.getColumnIndex("CURRENTDATE"))));
                model.setHours(Integer.parseInt(String.valueOf(cursor.getString(cursor.getColumnIndex("HOURS")))));
                model.setMinutes(Integer.parseInt(String.valueOf(cursor.getString(cursor.getColumnIndex("MINUTES")))));
                model.setDayOfMonth(Integer.parseInt(String.valueOf(cursor.getString(cursor.getColumnIndex("DAY_OF_MONTH")))));
                model.setMonth(Integer.parseInt(String.valueOf(cursor.getString(cursor.getColumnIndex("MONTH")))));
                model.setYear(Integer.parseInt(String.valueOf(cursor.getString(cursor.getColumnIndex("YEAR")))));
                records.add(model);
                // TODO The cursor is not moving to the next position so the list of notes being initiazed by the same object in each row [FIXED NOW]
                 // TODO *   And then edit a specific note [DONE]
                 // TODO *   Delete the specific note
                // TODO *   Add reminder to each activitY
                listIndex++;
                cursor.moveToNext();
            }
        }

        /*String note = records.get(listIndex).getNote();
        final String date = records.get(listIndex).getDate();
        itemBinding.textViewNameId.setText(note);
        itemBinding.textViewStatus.setText(date);
        listIndex++;*/
        itemBinding.editNoteImageViewId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
/*                String currentDate =  records.get(position).getCurrentDate();
                String note =  records.get(position).getNote();
                int hours = records.get(position).getHours();*/
                String Id = records.get(position).getUid();

//                Toast.makeText(context, "ID : " + Id , Toast.LENGTH_SHORT).show();

                SugarModel specificNote = databaseHelper.GetSpecificNote(Id);
                Intent toUpdateNote = new Intent(context , UpdateNote.class);
                Bundle noteBundle = new Bundle();
                noteBundle.putSerializable("NoteToEdit" , specificNote);
                toUpdateNote.putExtra("NoteBundle" , noteBundle);
                context.startActivity(toUpdateNote);
                Toast.makeText(context, specificNote.getNote(), Toast.LENGTH_SHORT).show();




            }
        });
        itemBinding.deleteNoteImageViewId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

    public class ListOfNotesViewHolder extends RecyclerView.ViewHolder {

        TextView nameTv , statusTv;
        View mView ;
        RowListOfNotesRecyclerViewBinding rowListOfNotesRecyclerViewBinding ;
        public ListOfNotesViewHolder(RowListOfNotesRecyclerViewBinding itemView ) {
          /*  super(itemView);
            nameTv = itemView.findViewById(R.id.textViewNameId);
            statusTv = itemView.findViewById(R.id.textViewStatus);
            onNoteClickListenerViewHOlder = onNoteClickListener;
            mView = itemView;*/
//            itemView.setOnClickListener(this);
            super(itemView.getRoot());
            rowListOfNotesRecyclerViewBinding = itemView;



        }


    }

}
