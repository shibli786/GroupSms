package com.example.shibli.databse;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.Toast;

import com.example.shibli.toolbartoolbar.ContactDetail;
import com.example.shibli.toolbartoolbar.Group;
import com.example.shibli.toolbartoolbar.MessageDataProvider;
import com.example.shibli.toolbartoolbar.MessageListDataProvider;

import java.util.ArrayList;
import java.util.TreeSet;

/**
 * Created by shibli on 4/25/2016.
 */
public class DatabaseScema {
    private static final Uri URI = ContactsContract.Contacts.CONTENT_URI;
    private static final Uri PURI = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
    private static final Uri EURI = ContactsContract.CommonDataKinds.Email.CONTENT_URI;
    private static final Uri AURI = ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_URI;
    private static final String ID = ContactsContract.Contacts._ID;
    private static final String DNAME = ContactsContract.Contacts.DISPLAY_NAME;
    private static final String HPN = ContactsContract.Contacts.HAS_PHONE_NUMBER;
    private static final String LOOKY = ContactsContract.Contacts.LOOKUP_KEY;
    private static final String CID = ContactsContract.CommonDataKinds.Phone.CONTACT_ID;
    private static final String EID = ContactsContract.CommonDataKinds.Email.CONTACT_ID;
    private static final String PNUM = ContactsContract.CommonDataKinds.Phone.NUMBER;
    static Shiblihelper shiblihelper;
    private static boolean isPopulate;
    Context context;
    SQLiteDatabase db;

    public DatabaseScema(Context context) {
        shiblihelper = new Shiblihelper(context);

        this.context = context;


    }

    public void insertData(String name, String number1, String id, String number2) {
        String sub = number1.substring(0, 3);
        if (!sub.equals("+91")) number1 = "+91" + number1;
        ContentValues cv = new ContentValues();
        cv.put(Shiblihelper.CONTACT_NAME, name);
        cv.put(Shiblihelper.CONTACT_NUMBER1, number1);
        cv.put(Shiblihelper.CONTACT_NUMBER2, number2);
        cv.put(Shiblihelper.CONTACT_ID, id);
        //insert more data

        db = shiblihelper.getWritableDatabase();

        db.insert(Shiblihelper.CONTACT_TABLE_NAME, Shiblihelper.CONTACT_NUMBER2, cv);


    }


    public void insertGroupTable(String tableName) {
        ContentValues cv = new ContentValues();
        cv.put(Shiblihelper.GROUP_NAME, tableName);
        db = shiblihelper.getWritableDatabase();

        long in = db.insert(Shiblihelper.GROUP_TABLE_NAME, null, cv);
        if (in < 0) {
            Toast.makeText(context, "unSuccessfull", Toast.LENGTH_LONG).show();
        } else {

            Toast.makeText(context, "Successfull", Toast.LENGTH_LONG).show();


        }


    }


    //get All groupName from Grouptable
    public TreeSet<Group> getAllGroup() {
        TreeSet<Group> ts = new TreeSet<Group>();
        SQLiteDatabase db = shiblihelper.getReadableDatabase();
        Cursor cursor = db.query(Shiblihelper.GROUP_TABLE_NAME, new String[]{Shiblihelper.GROUP_NAME, Shiblihelper.GROUP_ID}, null, null, null, null, null);
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex(Shiblihelper.GROUP_NAME));
            String id = cursor.getString(cursor.getColumnIndex(Shiblihelper.GROUP_ID));


            ts.add(new Group(name, Integer.parseInt(id)));

        }
        cursor.close();
        return ts;


    }


    public Group getGroup(String gname) {
        TreeSet<Group> ts = new TreeSet<Group>();
        SQLiteDatabase db = shiblihelper.getReadableDatabase();
        Cursor cursor = db.query(Shiblihelper.GROUP_TABLE_NAME, null, Shiblihelper.GROUP_NAME + "=?", new String[]{gname

        }, null, null, null);
        String name = "", id = "";
       if(cursor.getCount()>0){
           while (cursor.moveToNext()) {

               name = cursor.getString(cursor.getColumnIndex(Shiblihelper.GROUP_NAME));
               id = cursor.getString(cursor.getColumnIndex(Shiblihelper.GROUP_ID));
               return new Group(name, Integer.parseInt(id));

           }

       }

           return  null;





    }



    public void insertMessage(MessageDataProvider messageDataProvider, String recipient) {
        ContentValues cv = new ContentValues();
        cv.put(Shiblihelper.RECIPIENT, recipient);
        cv.put(Shiblihelper.MESSAGES, messageDataProvider.getMessage());
        cv.put(Shiblihelper.POSITION, messageDataProvider.getPosition() ? 1 : 0);
        cv.put(Shiblihelper.TIMESTAMP, System.currentTimeMillis());
        db = shiblihelper.getWritableDatabase();
        long l = db.insert(Shiblihelper.MESSAGE_TABLE_NAME, null, cv);
        if (l < 0) {
            Toast.makeText(context, "Unsuccessfull", Toast.LENGTH_LONG).show();

        } else {
            Toast.makeText(context, "successfull", Toast.LENGTH_LONG).show();

        }
    }


    public ArrayList<MessageDataProvider> getAdapter(String name) {
        shiblihelper = new Shiblihelper(context);
        SQLiteDatabase db = shiblihelper.getWritableDatabase();
        Cursor cursor = db.query(Shiblihelper.MESSAGE_TABLE_NAME, new String[]{Shiblihelper.MESSAGES, Shiblihelper.POSITION, Shiblihelper.TIMESTAMP}, Shiblihelper.RECIPIENT + "=?",
                new String[]{name}, null, null, null);
        if (cursor.getCount() == 0) {
            return null;
        }
        ArrayList<MessageDataProvider> al = new ArrayList<MessageDataProvider>();
        while (cursor.moveToNext()) {
            String messages = cursor.getString(cursor.getColumnIndex(Shiblihelper.MESSAGES));
            String position = cursor.getString(cursor.getColumnIndex(Shiblihelper.POSITION));
            String time = cursor.getString(cursor.getColumnIndex(Shiblihelper.TIMESTAMP));
            al.add(new MessageDataProvider(messages, position.equals("1") , Long.parseLong(time)));

        }
        cursor.close();
        db.close();
        return al;


    }


    public void updateData(String name, String id, String number1, String number2) {
        SQLiteDatabase db = shiblihelper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(Shiblihelper.CONTACT_NAME, name);
        cv.put(Shiblihelper.CONTACT_NUMBER1, number1);
        cv.put(Shiblihelper.CONTACT_NUMBER2, number2);

        db.update(Shiblihelper.CONTACT_TABLE_NAME, cv, Shiblihelper.CONTACT_ID + "= ?", new String[]{id});

    }

    public boolean searchGroup(String recipient) {
        SQLiteDatabase db = shiblihelper.getWritableDatabase();
        return db.query(Shiblihelper.GROUP_TABLE_NAME, new String[]{Shiblihelper.GROUP_NAME},
                Shiblihelper.GROUP_NAME + "=?", new String[]{recipient}, null, null, null).getCount()>0;


    }


    boolean isTableExists(SQLiteDatabase db, String tableName) {
        if (tableName == null || db == null || !db.isOpen()) {
            return false;
        }
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM sqlite_master WHERE type = ? AND name = ?", new String[]{"table", tableName});
        if (!cursor.moveToFirst()) {
            return false;
        }
        int count = cursor.getInt(0);
        cursor.close();
        return count > 0;
    }


    //get AllContact detail in an arraayList
    public ArrayList<ContactDetail> getAllContacts() {
        ArrayList<ContactDetail> al = new ArrayList<ContactDetail>();
        TreeSet<ContactDetail> ts = new TreeSet<ContactDetail>();
        if (!isPopulate) {
            isPopulate = true;
            readContactData();
        }


        TreeSet<ContactDetail> treeset = new TreeSet<ContactDetail>();
        SQLiteDatabase db = shiblihelper.getWritableDatabase();
        String[] col = {


                Shiblihelper.CONTACT_NAME, Shiblihelper.CONTACT_NUMBER1};


        Cursor cursor = db.query(Shiblihelper.CONTACT_TABLE_NAME, col, null, null, null, null, null);
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex(Shiblihelper.CONTACT_NAME));
            String number1 = cursor.getString(cursor.getColumnIndex(Shiblihelper.CONTACT_NUMBER1));
//            String number2 = cursor.getString(cursor.getColumnIndex(Shiblihelper.CONTACT_NUMBER2));
            String number2 = "";
            ContactDetail contacts = new ContactDetail(name, number2, number1);
            ts.add(contacts);
            // al.add(contacts);

        }
        Toast.makeText(context, treeset.size() + " ", Toast.LENGTH_LONG);
        al = new ArrayList<ContactDetail>(ts);

        return al;

    }


    public ArrayList<ContactDetail> readContactData() {
        ArrayList<ContactDetail> al1 = new ArrayList<ContactDetail>();


        /*********** Reading Contacts Name And Number **********/

        String phoneNumber = "";
        ContentResolver cr = context
                .getContentResolver();

        //Query to get contact name

        Cursor cur = cr
                .query(ContactsContract.Contacts.CONTENT_URI,
                        null,
                        null,
                        null,
                        null);

        // If data data found in contacts
        Log.i("AutocompleteContacts", "Reading   contacts........" + cur.getCount());

        while (cur.moveToNext()) {
            String name = "No Name";
            String no = "";
            name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
            // String val=cur.getString(cur.getColumnIndex());
//if(Integer.parseInt(val)>0){
//     no=cur.getString(cur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
//}

            if (Integer.parseInt(cur.getString(cur.getColumnIndex(
                    ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                Cursor pCur = cr.query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                        new String[]{id}, null);
                while (pCur.moveToNext()) {
                    String phoneNo = pCur.getString(pCur.getColumnIndex(
                            ContactsContract.CommonDataKinds.Phone.NUMBER));

                    Log.i("AutocompleteContacts", name + "  " + id + " " + phoneNo);
                    al1.add(new ContactDetail(name, " v", phoneNo));
                    insertData(name, phoneNo, id, "");


                }

                pCur.close();

            }
        }


        return al1;
    }


    public void insertMessageList(MessageListDataProvider messageListDataProvider) {
        ContentValues cv = new ContentValues();
        cv.put(Shiblihelper.RECIPIENT, messageListDataProvider.getRecipient());
        cv.put(Shiblihelper.TIMESTAMP, messageListDataProvider.getTimeStamp());
        cv.put(Shiblihelper.LAST_MESSAGE, messageListDataProvider.getLastMessage());


        db = shiblihelper.getWritableDatabase();

        long in = db.insert(Shiblihelper.MESSAGE_LIST_TABLE, null, cv);
        if (in < 0) {
            Toast.makeText(context, "unSuccessfull inserted", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, "Successfull inserted", Toast.LENGTH_LONG).show();


        }


    }

    public void updateMessageList(MessageListDataProvider messageListDataProvider) {
        ContentValues cv = new ContentValues();
        cv.put(Shiblihelper.TIMESTAMP, messageListDataProvider.getTimeStamp());
        cv.put(Shiblihelper.LAST_MESSAGE, messageListDataProvider.getLastMessage());


        db = shiblihelper.getWritableDatabase();

        long in = db.update(Shiblihelper.MESSAGE_LIST_TABLE, cv, Shiblihelper.RECIPIENT + "= ?", new String[]{messageListDataProvider.getRecipient()});

        if (in < 0) {
            Toast.makeText(context, "unSuccessfull update", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, "Successfull update", Toast.LENGTH_LONG).show();


        }
    }

    public ArrayList<MessageListDataProvider> getListMessages() {
        ArrayList<MessageListDataProvider> al = new ArrayList<MessageListDataProvider>();
        db = shiblihelper.getWritableDatabase();
        Cursor cursor = db.query(Shiblihelper.MESSAGE_LIST_TABLE, new String[]{Shiblihelper.RECIPIENT, Shiblihelper.TIMESTAMP, Shiblihelper.LAST_MESSAGE},
                null, null, null, null, Shiblihelper.TIMESTAMP + " DESC");

        while (cursor.moveToNext()) {
            String recipient = cursor.getString(cursor.getColumnIndex(Shiblihelper.RECIPIENT));
            String time = cursor.getString(cursor.getColumnIndex(Shiblihelper.TIMESTAMP));
            String lastMessage = cursor.getString(cursor.getColumnIndex(Shiblihelper.LAST_MESSAGE));

            al.add(new MessageListDataProvider(recipient, Long.parseLong(time), lastMessage));
        }
        return al;
    }

    public boolean searchRecipient(MessageListDataProvider messageListDataProvider) {
        db = shiblihelper.getReadableDatabase();
        Cursor cursor = db.query(Shiblihelper.MESSAGE_LIST_TABLE, new String[]{Shiblihelper.RECIPIENT}, Shiblihelper.RECIPIENT + "=?", new String[]{messageListDataProvider.getRecipient()}, null, null, null);
        if (cursor.getCount() > 0) {
            Toast.makeText(context, "found", Toast.LENGTH_LONG).show();
            return true;
        }
        return false;


    }

    public String getContactNameByNumber(String number) {
        db = shiblihelper.getReadableDatabase();
        Cursor cursor = db.query(Shiblihelper.CONTACT_TABLE_NAME, new String[]{Shiblihelper.CONTACT_NAME, Shiblihelper.CONTACT_NUMBER1}, Shiblihelper.CONTACT_NUMBER1 + "=?", new String[]{number}, null, null, null);

        while (cursor.moveToNext())
            if (cursor.getCount() > 0) {
                String name = cursor.getString(cursor.getColumnIndex(Shiblihelper.CONTACT_NAME));
                return name;
            }
        return number;
    }
    public void insertIntoGroupMemberTable(ContactDetail contactDetail, String group) {
        db  =shiblihelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(Shiblihelper.CONTACT_NUMBER1, contactDetail.getNumber1());

        cv.put(Shiblihelper.CONTACT_NAME, contactDetail.getName());
        cv.put(Shiblihelper.GROUP_NAME,group);
       long val= db.insert(Shiblihelper.GROUP_MEMBERS_TABLE,null,cv);
        if(val<0){
            Toast.makeText(context,"failed to add",Toast.LENGTH_LONG).show();

        }
        else{
            Toast.makeText(context,"Member  added",Toast.LENGTH_LONG).show();


        }


    }

    public ArrayList<ContactDetail> getGroupMembers(String groupname) {
        db=shiblihelper.getWritableDatabase();
        ArrayList<ContactDetail> al= new ArrayList<ContactDetail>();
       Cursor cursor= db.query(Shiblihelper.GROUP_MEMBERS_TABLE, new String[]{Shiblihelper.CONTACT_NAME, Shiblihelper.CONTACT_NUMBER1}, Shiblihelper.GROUP_NAME + "=?", new String[]{groupname}, null, null, null);
        if(cursor.getCount()<1){
            return null;


        }
        else{
            while(cursor.moveToNext()){
                String name = cursor.getString(cursor.getColumnIndex(Shiblihelper.CONTACT_NAME));
                String number1 = cursor.getString(cursor.getColumnIndex(Shiblihelper.CONTACT_NUMBER1));
//            String number2 = cursor.getString(cursor.getColumnIndex(Shiblihelper.CONTACT_NUMBER2));
                String number2 = "";
                ContactDetail contacts = new ContactDetail(name, number2, number1);
                al.add(contacts);

            }
            return al;
        }

    }

    public String getNumber(String recipient) {

        db=shiblihelper.getWritableDatabase();
        Cursor cursor=db.query(Shiblihelper.CONTACT_TABLE_NAME, new String[]{Shiblihelper.CONTACT_NUMBER1}, Shiblihelper.CONTACT_NAME + "=?", new String[]{recipient}, null, null, null);
      String number=recipient;
        while(cursor.moveToNext()){
            number=cursor.getString(cursor.getColumnIndex(Shiblihelper.CONTACT_NUMBER1));

        }
        return number;
    }

    public ArrayList<String> getAllGroupNumbers(String recipient) {
        ArrayList<String> al= new ArrayList<String>();
        db=shiblihelper.getWritableDatabase();
        Cursor cursor=db.query(Shiblihelper.GROUP_MEMBERS_TABLE, new String[]{Shiblihelper.CONTACT_NUMBER1}, Shiblihelper.CONTACT_NAME + "=?", new String[]{recipient}, null, null, null);
        String number=recipient;
        while(cursor.moveToNext()){
            number=cursor.getString(cursor.getColumnIndex(Shiblihelper.CONTACT_NUMBER1));
            al.add(number);

        }
        return al;

    }
    public String getMessageListID( String name){
        db=shiblihelper.getWritableDatabase();
        Cursor cursor=db.query(Shiblihelper.MESSAGE_LIST_TABLE,new String[]{Shiblihelper.MESSAGE_LIST_ID},Shiblihelper.RECIPIENT+"=?",new String[]{name},null,null,null);

       String id = null;
        while (cursor.moveToNext()){
             id=cursor.getString(cursor.getColumnIndex(Shiblihelper.MESSAGE_LIST_ID));
        }
cursor.close();
        return id;
    }

    public boolean deleteListMessage(String recipient) {
      db=  shiblihelper.getWritableDatabase();
     String id= getMessageListID(recipient);
     return db.delete(Shiblihelper.MESSAGE_LIST_TABLE,Shiblihelper.MESSAGE_LIST_ID+"=?",new String[]{id})>0;

    }

    public boolean deleteContact(String name) {
        return db.delete(Shiblihelper.CONTACT_TABLE_NAME,Shiblihelper.CONTACT_NAME+"=?",new String[]{name})>0;

    }

    public boolean deleteGroup(String groupName) {
        db=shiblihelper.getWritableDatabase();
        return db.delete(Shiblihelper.GROUP_TABLE_NAME,Shiblihelper.GROUP_NAME+"=?",new String[]{groupName})>0;

    }

    // inner database helper class

    static class Shiblihelper extends SQLiteOpenHelper {

        //database information
        private static final String CONTACT_DATABSE_NAME = "my_database";
        private static final String CONTACT_TABLE_NAME = "mycontacts_table";
        private static final int CONTACT_DATABASE_VERSION = 40;

        //ContactFragment table columns
        private static final String CONTACT_NAME = "Name";
        private static final String CONTACT_ID = "ID";
        private static final String CONTACT_NUMBER1 = "Number1";
        private static final String CONTACT_NUMBER2 = "Number2";

        //GroupFragment table column
        private static final String GROUP_TABLE_NAME = "group_table";
        private static final String GROUP_ID = "group_id";
        private static final String GROUP_NAME = "group_name";

        //adapter table
        // messages id ,message ,position, timestamp,recipient
        private static final String MESSAGE_TABLE_NAME = "message_table";
        private static final String MESSAGE_ID = "message_id";
        private static final String MESSAGES = "messages";
        private static final String POSITION = "position";
        private static final String TIMESTAMP = "time_stamp";
        private static final String RECIPIENT = "user";

        // 1:n table
        private static final String GROUP_MEMBERS_TABLE = "group_member";
        private static final String MEMBER_ID = "member_id";
        private static final String CREATE_GROUP_MEMBERS_TABLE = "CREATE TABLE " +
                GROUP_MEMBERS_TABLE + " (" + CONTACT_NAME + " VARCHAR(255)," + CONTACT_NUMBER1 +
                " VARCHAR(255)," + CONTACT_NUMBER2 + " VARCHAR(255)," + GROUP_NAME + " VARCHAR(255), "
                + MEMBER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT);";


        //messageList tabel


        private static final String MESSAGE_LIST_ID = "message_list_id";
        private static final String LAST_MESSAGE = "last_message";
        private static final String MESSAGE_LIST_TABLE = "message_list_table";




        private static final String CREATE_MESSAGE_LIST_TABLE = "CREATE TABLE " + MESSAGE_LIST_TABLE + "(" + MESSAGE_LIST_ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT ," + RECIPIENT + " VARCHAR(40) ," + TIMESTAMP + " INTEGER ," + LAST_MESSAGE + " TEXt);";




        private static final String CREATE_MESSAGE_TABLE = "CREATE TABLE " + MESSAGE_TABLE_NAME + "(" + MESSAGE_ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT, " + MESSAGES + " TEXT, " + POSITION +
                " INTEGER ," + TIMESTAMP + " INTEGER ," + RECIPIENT + " VARCHAR(40));";




        private static final String CREATE_CONTACT_TABLE = "CREATE TABLE " +
                CONTACT_TABLE_NAME + " (" + CONTACT_NAME + " VARCHAR(255), " +
                CONTACT_ID + " VARCHAR(255), " + CONTACT_NUMBER1 + " VARCHAR(255), " +
                CONTACT_NUMBER2 + " VARCHAR(255));";

//private static final String ALTER_MESSAGE_LIST_TABLE="ALTER TABLE "+MESSAGE_LIST_TABLE+" ADD "+LAST_MESSAGE+" TEXT;";


        private static final String CREATE_GROUP_TABLE = "CREATE TABLE " +
                GROUP_TABLE_NAME + "(" + GROUP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + GROUP_NAME + " VARCHAR(25));";
        Context con;









        public Shiblihelper(Context context) {
            super(context, CONTACT_DATABSE_NAME, null, CONTACT_DATABASE_VERSION);
            con = context;
        }



        @Override
        public void onCreate(SQLiteDatabase db) {
            try {
                db.execSQL(CREATE_GROUP_MEMBERS_TABLE);
                db.execSQL(CREATE_CONTACT_TABLE);
                db.execSQL(CREATE_GROUP_TABLE);
                db.execSQL(CREATE_MESSAGE_TABLE);
                db.execSQL(CREATE_MESSAGE_LIST_TABLE);
            } catch (Exception ex) {
                Log.e("Sam", "table created ex " + ex);
                Toast.makeText(con, ex + "", Toast.LENGTH_LONG).show();

            }

        }


        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            //db.execSQL(CREATE_CONTACT_TABLE);
            //
            // Toast.makeText(con,  "table droped", Toast.LENGTH_SHORT);
            try {
                db.execSQL("DROP TABLE if exists " + GROUP_MEMBERS_TABLE);

                db.execSQL("DROP TABLE if exists " + CONTACT_TABLE_NAME);
                db.execSQL("DROP TABLE if exists " + GROUP_TABLE_NAME);
                db.execSQL("DROP TABLE if exists " + MESSAGE_TABLE_NAME);
                db.execSQL("DROP TABLE if exists " + MESSAGE_LIST_TABLE);

                onCreate(db);
                Log.e("Sam", "table created in onupgrade");
            } catch (Exception e) {
                Toast.makeText(con, e + "", Toast.LENGTH_SHORT).show();


            }

        }
    }
}
