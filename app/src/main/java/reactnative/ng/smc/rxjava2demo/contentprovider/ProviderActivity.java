package reactnative.ng.smc.rxjava2demo.contentprovider;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import reactnative.ng.smc.rxjava2demo.R;
import reactnative.ng.smc.rxjava2demo.aidl.Book;
import reactnative.ng.smc.rxjava2demo.ipc.User;

public class ProviderActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView msg_tv;
    private StringBuilder strBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider);
        findViewById(R.id.book_btn).setOnClickListener(this);
        findViewById(R.id.user_btn).setOnClickListener(this);
        msg_tv = (TextView) findViewById(R.id.msg_tv);
        strBuilder = new StringBuilder();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.book_btn:
                getBookData();
                break;
            case R.id.user_btn:
                getUserData();
                break;
        }

    }

    /**
     * B应用 调用 查询之类
     */
    private void getBookData() {
        try {
            Uri bookUri = Uri.parse("content://" + BookProvider.AUTHORITY + "/book");
            ContentValues values = new ContentValues();
            values.put("_id", 6);
            values.put("name", "anroid 开发艺术探索");
            //插入数据
            getContentResolver().insert(bookUri, values);
            //查询数据
            Cursor bookCusor = getContentResolver().query(bookUri, new String[]{"_id", "name"}, null, null, null);
            while (bookCusor.moveToNext()) {
                Book book = new Book(bookCusor.getInt(0), bookCusor.getString(1));
                strBuilder.append("Bookid:" + book.getBookId() + "-->BookName:" + book.getBookName() + "\n");
                Log.e("信息", "查询Book:" + book.toString());
            }
            bookCusor.close();
            msg_tv.setText(strBuilder.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void getUserData() {
        try {
            Uri userUri = Uri.parse("content://" + BookProvider.AUTHORITY + "/user");
            ContentValues values = new ContentValues();
            values.put("_id", 7);
            values.put("name", "ljeajn");
            //插入数据
            getContentResolver().insert(userUri, values);
            //查询数据
            Cursor userCusor = getContentResolver().query(userUri, new String[]{"_id", "name"}, null, null, null);
            while (userCusor.moveToNext()) {
                User user = new User(userCusor.getInt(0), userCusor.getString(1), false);
                Log.e("信息", "查询Book:" + user.toString());
                strBuilder.append("UserId:" + user.getUserId() + "-->UserName:" + user.getUserName() + "\n");
            }
            userCusor.close();
            msg_tv.setText(strBuilder.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
