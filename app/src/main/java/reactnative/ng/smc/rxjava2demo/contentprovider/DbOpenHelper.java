package reactnative.ng.smc.rxjava2demo.contentprovider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


/**
 * Created by Administrator on 2017/11/7.
 */

public class DbOpenHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "book_provider.db";
    public static final String BOOK_TABLE_NAME = "book";
    public static final String USER_TABLE_NAME = "user";
    private static final int DB_VERSION = 1;
    private static final String CREATE_BOOK_TABLE = "create table " + BOOK_TABLE_NAME + "( _id integer primary key, " + "name TEXT)";

    private static final String CREATE_USER_TABLE = "create table " + USER_TABLE_NAME + "( _id integer primary key, " + "name TEXT," + "sex INT)";


    public DbOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_BOOK_TABLE);
        db.execSQL(CREATE_USER_TABLE);
        Log.i("信息","onCreate DbOpenHelper：");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + BOOK_TABLE_NAME);
        db.execSQL("drop table if exists " + USER_TABLE_NAME);
        onCreate(db);
    }
}
