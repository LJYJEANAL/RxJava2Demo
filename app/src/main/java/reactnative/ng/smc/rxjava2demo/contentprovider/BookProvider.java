package reactnative.ng.smc.rxjava2demo.contentprovider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * 注意  ContentProvider 的QUID 四大方法中 存在多线程的并发访问  因此方法内部要做好线程同步 因为在本例中使用的是SQLite 并且只有一个
 * SQLiteDatabase 连接
 *
 *
 authorities  是contentprovider的唯一标志 通过这个属性 外部应用可以访问我们的contentprovider
 在要访问的 B 应用中需要申请读权限  <uses-permission android:name="smc.contentprovider.BookProvider.read" />

 <provider
 android:name=".contentprovider.BookProvider"
 android:authorities="smc.contentprovider.BookProvider"//唯一标志
 android:exported="true"
 android:process=":provider"
 android:readPermission="smc.contentprovider.BookProvider.read" />


提供给外部的权限
 <permission
 android:name="smc.contentprovider.BookProvider.read"
 android:label="provider pomission"
 android:protectionLevel="normal" />
 */

public class BookProvider extends ContentProvider {
    public static final String AUTHORITY = "smc.contentprovider.BookProvider";
    /**
     * 为了知道外界访问的是那个表需要为他们订阅单独的uri和uricode 并且关联
     */
    public static final Uri BOOK_CONTENT_URL = Uri.parse("content://" + AUTHORITY + "/book");
    public static final Uri USER_CONTENT_URL = Uri.parse("content://" + AUTHORITY + "/user");
    public static final int BOOK_URI_CODE = 0;
    public static final int USER_URI_CODE = 1;
    private static final UriMatcher URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        URI_MATCHER.addURI(AUTHORITY, "book", BOOK_URI_CODE);
        URI_MATCHER.addURI(AUTHORITY, "user", USER_URI_CODE);
    }

    private Context mContext;
    private SQLiteDatabase mDb;


    @Override
    public boolean onCreate() {
        Log.i("信息", "onCreate current thread：" + Thread.currentThread().getName());
        mContext = getContext();
        initProviderData();
        return true;
    }

    /**
     * 初始化数据库存入假数据
     */
    private void initProviderData() {
        mDb = new DbOpenHelper(mContext).getWritableDatabase();
        mDb.execSQL("delete from " + DbOpenHelper.BOOK_TABLE_NAME);
        mDb.execSQL("delete from " + DbOpenHelper.USER_TABLE_NAME);
        mDb.execSQL("insert into book values (3,'android');");
        mDb.execSQL("insert into book values (4,'ios');");
        mDb.execSQL("insert into book values (5,'Html5');");
        mDb.execSQL("insert into user values (1,'jack',1);");
        mDb.execSQL("insert into user values (2,'jeanal',2);");
    }

    private String getTableName(Uri uri) {
        String tableName = null;
        switch (URI_MATCHER.match(uri)) {
            case BOOK_URI_CODE:
                tableName = DbOpenHelper.BOOK_TABLE_NAME;
                break;
            case USER_URI_CODE:
                tableName = DbOpenHelper.USER_TABLE_NAME;
                break;
        }
        return tableName;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection,
                        @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Cursor cursor = null;
        try {

            String tableName = getTableName(uri);
            Log.i("信息", tableName + "query current thread：" + Thread.currentThread().getName());
            if (tableName != null) {
                cursor = mDb.query(tableName, projection, selection, selectionArgs, null, null, sortOrder, null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        Log.i("信息", "getType current thread：" + Thread.currentThread().getName());
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        Log.i("信息", "insert current thread：" + Thread.currentThread().getName());
        try {
            String tableName = getTableName(uri);
            if (tableName != null) {
                mDb.insert(tableName, null, values);
                mContext.getContentResolver().notifyChange(uri, null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return uri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        Log.i("信息", "delete current thread：" + Thread.currentThread().getName());

        int count = 0;
        try {
            String tableName = getTableName(uri);
            if (tableName != null) {
                count = mDb.delete(tableName, selection, selectionArgs);
                if (count > 0) {
                    mContext.getContentResolver().notifyChange(uri, null);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        return count;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        Log.i("信息", "update current thread：" + Thread.currentThread().getName());
        int row = 0;
        try {
            String tableName = getTableName(uri);

            if (tableName != null) {
                row = mDb.update(tableName, values, selection, selectionArgs);
                if (row > 0) {
                    mContext.getContentResolver().notifyChange(uri, null);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return row;

    }
}
