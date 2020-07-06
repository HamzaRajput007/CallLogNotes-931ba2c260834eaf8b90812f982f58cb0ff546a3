package com.developer.calllogmanager.dbHelper;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;
import android.support.annotation.VisibleForTesting;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;


import com.developer.calllogmanager.Models.SugarModel;
import com.orm.dsl.Table;

import java.util.ArrayList;

import javax.net.ssl.SSLProtocolException;

public class DatabaseHelper extends SQLiteOpenHelper {

    SQLiteDatabase sqLiteDatabase;
    public static final String DATABASE_NAME = "QuickNotes2.db";

    public static final String TABLE_NAME = "callernotes";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "DATE";
    public static final String COL_3 = "NOTE";
    public static final String COL_4 = "EXTRA";
    public static final String COL_5 = "NUMBER";
    public static final String COL_6= "CURRENTDATE";
//    public static final String COL_7 = "CURRENTTIME";
    public static final String HOURS = "HOURS";
    public static final String MINUTES = "MINUTES";
    public static final String DAY_OF_MONTH = "DAY_OF_MONTH";
    public static final String MONTH = "MONTH";
    public static final String YEAR = "YEAR";
    public static final String AMPM = "AMPM";

    public static final String TABLE_VOICE_NOTES = "TABLEVoiceNotes";
    public static final String COL_1_voice_ID = "ID";
    public static final String COL_2_FILENOTESNAME = "FILENOTESNAME";
    public static final String COL_3_VOICENOTETIME = "VOICENOTETIME";

    public static final String TABLE_STATUS = "TABLESTATUS";
    public static final String COL_1_STATUS_ID = "ID";
    public static final String COL_2_STATUS_DATE = "StatusDate";
    public static final String COL_3_STATUS_VALUE = "StatusValue";


    public static final String TABLE_CALLRECORDING = "CallRecording";
    public static final String COL_1_ID = "ID";
    public static final String COL_2_Filename = "FileName";
    public static final String COL_3_CallTime = "CallTime";

    public static final String TABLE_NOTES_OF_CALL_LOG = "NotesOfCallLog";
    public static final String NOTE_ID = "ID";
    public static final String NOTE_CONTENT = "NoteContent";
    public static final String NOTE_TIME_STAMP = "NoteTimeStamp";
    public static final String NOTE_CALL_LOG_ID = "NoteCallLogId";

    public static final String REMINDERS_TABLE  = "REMINDERS_TABLE";
    public static final String REMINDER_ID = "ID";

    public static final String REMINDER_NOTE_ID = "NOTE_ID";

    Context context;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 6);
        sqLiteDatabase = this.getWritableDatabase();
        this.context = context;
//        onCreate(sqLiteDatabase);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query_voice_notes = (" CREATE TABLE IF NOT EXISTS " + TABLE_VOICE_NOTES + " (" +
                COL_1_voice_ID + " INTEGER  PRIMARY KEY AUTOINCREMENT," +
                COL_2_FILENOTESNAME + " VARCHAR," +
                COL_3_VOICENOTETIME + " VARCHAR );"
        );
        
        sqLiteDatabase.execSQL(query_voice_notes);


        String query_signup = (" CREATE TABLE " + TABLE_NAME + " (" +
                COL_1 + " INTEGER  PRIMARY KEY AUTOINCREMENT," +
                COL_2 + " VARCHAR," +
                COL_3 + " VARCHAR," +
                COL_4 + " VARCHAR,"+
                COL_5 + " VARCHAR," +
                COL_6 + " INTEGER, " +
                HOURS + " INTEGER," +
                MINUTES + " INTEGER," +
                DAY_OF_MONTH + " INTEGER," +
                MONTH + " INTEGER ," +
                YEAR + " INTEGER," +
                AMPM + " VARCHAR" +
                ");"
        );
        sqLiteDatabase.execSQL(query_signup);

        String query_status = (" CREATE TABLE IF NOT EXISTS " + TABLE_STATUS + " (" +
                COL_1_STATUS_ID + " INTEGER  PRIMARY KEY AUTOINCREMENT," +
                COL_2_STATUS_DATE + " VARCHAR," +
                COL_3_STATUS_VALUE + " VARCHAR );"
        );
        sqLiteDatabase.execSQL(query_status);

        String query_customer = (" CREATE TABLE  IF NOT EXISTS " + TABLE_CALLRECORDING + " (" +
                COL_1_ID + " INTEGER  PRIMARY KEY AUTOINCREMENT," +
                COL_2_Filename + " VARCHAR," +
                COL_3_CallTime + " VARCHAR );"
        );
        sqLiteDatabase.execSQL(query_customer);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME);
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS " + TABLE_VOICE_NOTES);
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS " + TABLE_CALLRECORDING);
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS " + TABLE_STATUS);
        onCreate(sqLiteDatabase);

    }
    public boolean SaveStatus(String date,String value) {
        sqLiteDatabase = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_2_STATUS_DATE,date);
        cv.put(COL_3_STATUS_VALUE, value);

        long ins = sqLiteDatabase.insert(TABLE_STATUS, null, cv);
        if (ins == -1) {
            return false;
        } else
            return true;

    }

    public String getStatus(String date) {
        sqLiteDatabase = this.getReadableDatabase();

        String[] columns = {COL_1_STATUS_ID, COL_2_STATUS_DATE,COL_3_STATUS_VALUE};
        String selection = COL_2_STATUS_DATE+" = ?";
        String[] selectionArgs = {date};
        Cursor cursor = sqLiteDatabase.query(TABLE_STATUS, columns, selection, selectionArgs, null, null, null);
        if (cursor.moveToFirst()) {
            String value= String.valueOf(cursor.getString(cursor.getColumnIndex(COL_3_STATUS_VALUE)));

            return value;
            //   cursor.moveToNext();
        }
        return "";
    }


    public boolean update_status(String date,String value) {
        sqLiteDatabase = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_2_STATUS_DATE,date);
        cv.put(COL_3_STATUS_VALUE,value);

        long ins = sqLiteDatabase.update(TABLE_STATUS, cv,"StatusDate="+date,null);
        if (ins == -1) {
            return false;
        } else
            return true;
    }



    public boolean SaveVoiceNOTE(String fileName,String number) {
        sqLiteDatabase = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_2_FILENOTESNAME,fileName);
        cv.put(COL_3_VOICENOTETIME, number);


        long ins = sqLiteDatabase.insert(TABLE_VOICE_NOTES, null, cv);
        if (ins == -1) {
            return false;
        } else
            return true;

    }


    public String GETVoiceNOTE(String fileName) {
        sqLiteDatabase = this.getReadableDatabase();

        String[] columns = {COL_1_voice_ID, COL_2_FILENOTESNAME,COL_3_VOICENOTETIME};
        String selection = COL_2_FILENOTESNAME+" = ?";
        String[] selectionArgs = {fileName};
        Cursor cursor = sqLiteDatabase.query(TABLE_VOICE_NOTES, columns, selection, selectionArgs, null, null, null);
        if (cursor.moveToFirst()) {
            String file= String.valueOf(cursor.getString(cursor.getColumnIndex(COL_2_FILENOTESNAME)));

            return file;
            //   cursor.moveToNext();
        }
        return "";
    }
    public boolean GETTextNOTE(String fileName) {
        sqLiteDatabase = this.getReadableDatabase();

        String[] columns = {COL_1, COL_2,COL_3,COL_4,COL_5};
        String selection = COL_2+" = ?";
        String[] selectionArgs = {fileName};
        Cursor cursor = sqLiteDatabase.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);
        if (cursor.moveToFirst()) {
            String file= String.valueOf(cursor.getString(cursor.getColumnIndex(COL_2)));

            return true;
            //   cursor.moveToNext();
        }
        return false;
    }

    public boolean SAVENOTE(SugarModel model , String date , String note) {
        sqLiteDatabase = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        String currentDate = model.getCurrentDate();

        cv.put(COL_2,date);
        cv.put(COL_3, note);
        cv.put(COL_4, model.getExtra());
        cv.put(COL_5, model.getNumber());
        cv.put(COL_6, model.getCurrentDate());
        cv.put(HOURS , model.getHours());
        cv.put(MINUTES , model.getMinutes());
        cv.put(DAY_OF_MONTH , model.getDayOfMonth());
        cv.put(MONTH , model.getMonth());
        cv.put(YEAR , model.getYear());
// todo table callernotes has no column named DATE [SOLVED]
        long ins = sqLiteDatabase.insert(TABLE_NAME, null, cv);
        if (ins == -1) {
            return false;
        } else
            return true;

    }
    public Cursor GETNOTES(String date) {
        String[] columns = {COL_1 , COL_2 , COL_3 , COL_4 , COL_5 , COL_6 , HOURS , MINUTES , DAY_OF_MONTH , MONTH , YEAR};
        String selection = COL_2+" = ?";
        String[] selectionArgs = {date};
        sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);
        int length = cursor.getCount();
        if(cursor.moveToFirst()){
            while (!cursor.isAfterLast()) {
                String Date = String.valueOf(cursor.getString(cursor.getColumnIndex("DATE")));
                String Note = String.valueOf(cursor.getString(cursor.getColumnIndex("NOTE")));
                cursor.moveToNext();
            }
        }
        sqLiteDatabase.close();
        return cursor;
    }

    public SugarModel GetSpecificNote(String id ){
        String[] columns = {COL_2 , COL_3 , COL_4 , COL_5 , COL_6 , HOURS , MINUTES , DAY_OF_MONTH , MONTH , YEAR};
        String selection = COL_1 + " = ?";
        String[] selectionArgs = {id};
        sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);
//        sqLiteDatabase.close();
        int length = cursor.getCount();
        SugarModel specificNote = new SugarModel();
        /*String Uid,date,note,number,extra;
        String currentDate, currentTime, ampm ;
        int hours , minutes , dayOfMonth , month , year ;*/

        if(cursor.moveToFirst()){
            while (!cursor.isAfterLast()) {
                String Date = String.valueOf(cursor.getString(cursor.getColumnIndex("DATE")));
                String Note = String.valueOf(cursor.getString(cursor.getColumnIndex("NOTE")));
                String NUMBER = String.valueOf(cursor.getString(cursor.getColumnIndex("NUMBER")));
                String EXTRA = String.valueOf(cursor.getString(cursor.getColumnIndex("EXTRA")));
                String CURRENT_TIME = String.valueOf(cursor.getString(cursor.getColumnIndex("CURRENTDATE")));
//                String ampm = String.valueOf(cursor.getString(cursor.getColumnIndex("AMPM")));
                int Hours = cursor.getInt(cursor.getColumnIndex("HOURS")) ;
                int Minutes = cursor.getInt(cursor.getColumnIndex("MINUTES")) ;
                int day_of_month = cursor.getInt(cursor.getColumnIndex("DAY_OF_MONTH")) ;
                int month = cursor.getInt(cursor.getColumnIndex("MONTH")) ;
                int year = cursor.getInt(cursor.getColumnIndex("YEAR")) ;
//                long noteId = cursor.getLong(cursor.getColumnIndex("ID")) ;

                specificNote.setDate(Date);
                specificNote.setNote(Note);
                specificNote.setNumber(NUMBER);
                specificNote.setExtra(EXTRA);
                specificNote.setCurrentDate(CURRENT_TIME);
//                specificNote.setAmpm(ampm);
                specificNote.setHours(Hours);
                specificNote.setMinutes(Minutes);
                specificNote.setDayOfMonth(day_of_month);
                specificNote.setMonth(month);
                specificNote.setYear(year);
//                specificNote.setId(noteId);


                cursor.moveToNext();
            }
        }
        return specificNote;
    }

    public boolean updateNote( String note_id ,SugarModel model){
      /*  sqLiteDatabase = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_2_STATUS_DATE,date);
        cv.put(COL_3_STATUS_VALUE,value);

        long ins = sqLiteDatabase.update(TABLE_STATUS, cv,"StatusDate="+date,null);
        if (ins == -1) {
            return false;
        } else
            return true;*/

      sqLiteDatabase = this.getReadableDatabase();
      ContentValues cv = new ContentValues();
        cv.put(COL_2,model.getDate());
        cv.put(COL_3, model.getNote());
        cv.put(COL_4, model.getExtra());
        cv.put(COL_5, model.getNumber());
        cv.put(COL_6, model.getCurrentDate());
        cv.put(HOURS , model.getHours());
        cv.put(MINUTES , model.getMinutes());
        cv.put(DAY_OF_MONTH , model.getDayOfMonth());
        cv.put(MONTH , model.getMonth());
        cv.put(YEAR , model.getYear());

        String whereClause = COL_6 + " = ?";
//        String noteId = model.getUid();
        String[] selectionArgs = {note_id};

        long isUpdated = sqLiteDatabase.update(TABLE_NAME , cv , whereClause , selectionArgs  );
        if(isUpdated == -1){
            return false;
        }
        else{
            return true ;
        }
    }

    public  Cursor GetReminders(){
        String[] columns = {COL_2 , COL_3 , COL_4 , COL_5 , HOURS , MINUTES , DAY_OF_MONTH , MONTH , YEAR};
        sqLiteDatabase = this.getReadableDatabase();
        return sqLiteDatabase.query(TABLE_NAME, columns, null, null, null, null, null);
    }
public ArrayList<SugarModel> FetchData() {
        sqLiteDatabase = this.getReadableDatabase();
        ArrayList <SugarModel> arrayList=new ArrayList<SugarModel>();
        Cursor cursor = sqLiteDatabase.rawQuery(" SELECT * FROM " + TABLE_NAME,null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                SugarModel model = new SugarModel();
                model.setDate(String.valueOf(cursor.getString(cursor.getColumnIndex(COL_2))));
                model.setNote(String.valueOf(cursor.getString(cursor.getColumnIndex(COL_3))));
                model.setExtra(String.valueOf(cursor.getString(cursor.getColumnIndex(COL_4))));
                model.setNumber(String.valueOf(cursor.getString(cursor.getColumnIndex(COL_5))));
                arrayList.add(model);
                cursor.moveToNext();
            }
        }
        return arrayList;
    }

public ArrayList<SugarModel> FetchVoiceData() {
        sqLiteDatabase = this.getReadableDatabase();
        ArrayList <SugarModel> arrayList=new ArrayList<SugarModel>();
        Cursor cursor = sqLiteDatabase.rawQuery(" SELECT * FROM " + TABLE_VOICE_NOTES,null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                SugarModel model = new SugarModel();
                model.setDate(String.valueOf(cursor.getString(cursor.getColumnIndex(COL_2_FILENOTESNAME))));
                model.setNumber(String.valueOf(cursor.getString(cursor.getColumnIndex(COL_3_VOICENOTETIME))));
                arrayList.add(model);
                cursor.moveToNext();
            }
        }
        return arrayList;
    }
    public String select_File(String time) {
        sqLiteDatabase = this.getReadableDatabase();

        String[] columns = {COL_1_ID, COL_2_Filename,COL_3_CallTime};
        String selection = COL_3_CallTime+" = ?";
        String[] selectionArgs = {time};
        Cursor cursor = sqLiteDatabase.query(TABLE_CALLRECORDING, columns, selection, selectionArgs, null, null, null);
        if (cursor.moveToFirst()) {
           time= String.valueOf(cursor.getString(cursor.getColumnIndex(COL_2_Filename)));

            return time;
         //   cursor.moveToNext();
        }
        return "";
    }

    /*public int delete_Entry(String ID) {
        String where="ID=?";
        sqLiteDatabase=this.getReadableDatabase();
        int numberOFEntriesDeleted= sqLiteDatabase.delete(TABLE_CUS_NAME, where, new String[]{ID}) ;


        return numberOFEntriesDeleted;
    }*/
    public int deleteEntry(String row) {
        sqLiteDatabase = this.getReadableDatabase();
        int var = sqLiteDatabase.delete(TABLE_NAME, COL_2 + "=" + row, null);
        if (var > 0) {
            return var;
        }
        return -1;
    }
    public int deleteVoiceEntry(String row) {
        sqLiteDatabase = this.getReadableDatabase();
        int var = sqLiteDatabase.delete(TABLE_VOICE_NOTES, COL_2_FILENOTESNAME + "=" + row, null);
        if (var > 0) {
            return var;
        }
        return -1;
    }
    public int deletestatus(String row) {
        sqLiteDatabase = this.getReadableDatabase();
        int var = sqLiteDatabase.delete(TABLE_STATUS, COL_2_STATUS_DATE + "=" + row, null);
        if (var > 0) {
            return var;
        }
        return -1;
    }

    public boolean insertfile(String name,String time){

        sqLiteDatabase = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_2_Filename,name);
        cv.put(COL_3_CallTime, time);


        long ins = sqLiteDatabase.insert(TABLE_CALLRECORDING, null, cv);
        if (ins == -1) {
            return false;
        } else
            return true;

    }

    public ArrayList<SugarModel> FetchAllStatus() {
        sqLiteDatabase = this.getReadableDatabase();
        ArrayList <SugarModel> arrayList=new ArrayList<SugarModel>();
        Cursor cursor = sqLiteDatabase.rawQuery(" SELECT * FROM " + TABLE_STATUS,null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                SugarModel model = new SugarModel();
                model.setDate(String.valueOf(cursor.getString(cursor.getColumnIndex(COL_2_STATUS_DATE))));
                model.setNote(String.valueOf(cursor.getString(cursor.getColumnIndex(COL_3_STATUS_VALUE))));
                arrayList.add(model);
                cursor.moveToNext();
            }
        }
        return arrayList;
    }


}
