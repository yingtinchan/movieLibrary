    package com.example.mymovieappcs414;


    import android.content.ContentValues;
    import android.content.Context;
    import android.database.Cursor;
    import android.database.sqlite.SQLiteDatabase;
    import android.database.sqlite.SQLiteOpenHelper;
    import android.widget.Toast;


    import androidx.annotation.Nullable;

    class MyDatabaseHelper extends SQLiteOpenHelper {

        private final Context context;
        private static final String DATABASE_NAME = "MovieLibrary.db";
        private static final int DATABASE_VERSION = 2;
        private static final String TABLE_NAME = "my_movie";
        private static final String COLUMN_ID = "movie_id";
        private static final String COLUMN_TITLE = "movie_title";
        private static final String COLUMN_AUTHOR = "movie_author";
        private static final String COLUMN_CONTENT = "movie_content";
        private static final String COLUMN_RATING = "movie_rating";

        public MyDatabaseHelper(@Nullable Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            this.context = context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String query =
                    "CREATE TABLE " + TABLE_NAME +
                            " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                            COLUMN_TITLE + " TEXT, " +
                            COLUMN_AUTHOR + " TEXT, " +
                            COLUMN_CONTENT + " TEXT, " +
                            COLUMN_RATING + " INTEGER);";
            db.execSQL(query);

            db.execSQL("CREATE TABLE users(username TEXT primary key, password TEXT)");

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            db.execSQL("drop Table if exists users");
            onCreate(db);
        }


        boolean addMovie(String title, String author, String content, int rating) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cv = new ContentValues();

            cv.put(COLUMN_TITLE, title);
            cv.put(COLUMN_AUTHOR, author);
            cv.put(COLUMN_CONTENT, content);
            cv.put(COLUMN_RATING, rating);

            long result = db.insert(TABLE_NAME, null, cv);
            db.close();
            return result != -1; // Return true if insert is successful
        }




        Cursor readAllData(){
            String query = "SELECT * FROM " + TABLE_NAME;
            SQLiteDatabase db = this.getReadableDatabase();

            Cursor cursor = null;
            if(db != null){
                cursor = db.rawQuery(query, null);
            }
            return cursor;
        }

        //Set the method according to the row_id to determine the row and do the update action from database
        void updateData(String row_id, String title, String author, String content, String rating){
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(COLUMN_TITLE, title);
            cv.put(COLUMN_AUTHOR, author);
            cv.put(COLUMN_CONTENT, content);
            cv.put(COLUMN_RATING, rating);

            long result = db.update(TABLE_NAME, cv, "movie_id=?", new String[]{row_id});
            //set the variable to return the message whether update action is success or not
            if(result == -1){
                Toast.makeText(context, "Failed to update!", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(context, "Successfully Updated!", Toast.LENGTH_SHORT).show();
            }

        }


        //Set the method according to the row_id to determine the row and do the delete action from database
        void deleteOneRow(String row_id){
            SQLiteDatabase db = this.getWritableDatabase();
            long result = db.delete(TABLE_NAME, "movie_id=?", new String[]{row_id});
            //set the variable to return the message whether delete action is success or not
            if(result == -1){
                Toast.makeText(context, "Failed to delete!", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(context, "Successfully delete!", Toast.LENGTH_SHORT).show();
            }
        }




        public Boolean insertData(String username, String password){
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues= new ContentValues();
            contentValues.put("username", username);
            contentValues.put("password", password);
            long result = db.insert("users", null, contentValues);
            if(result==-1) return false;
            else
                return true;
        }

        public Boolean checkusername(String username) {
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery("Select * from users where username = ?", new String[]{username});
            if (cursor.getCount() > 0)
                return true;
            else
                return false;
        }

        public Boolean checkusernamepassword(String username, String password){
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery("Select * from users where username = ? and password = ?", new String[] {username,password});
            if(cursor.getCount()>0)
                return true;
            else
                return false;
        }
    }



