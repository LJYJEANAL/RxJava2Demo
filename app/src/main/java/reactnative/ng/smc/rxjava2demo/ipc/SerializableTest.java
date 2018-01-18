package reactnative.ng.smc.rxjava2demo.ipc;

import android.util.Log;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by Administrator on 2017/10/24.
 */

public class SerializableTest {

    //序列话过程
    public  void setSerialzable(){
        User user=new User(0,"jake",true);
        try {
            ObjectOutputStream out=new ObjectOutputStream(new FileOutputStream("user.txt"));
            out.writeObject(user);
            out.close();
            Log.e("信息",out.toString());
        } catch (IOException e) {
            e.printStackTrace();

        }
    }
    //序列话过程
    public  void _setSerialzable(){
        try {
            ObjectInputStream in=new ObjectInputStream(new FileInputStream("user.txt"));
            User user= (User) in.readObject();
            in.close();
            Log.e("信息",user.getUserName());
        } catch (IOException e) {
            e.printStackTrace();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
