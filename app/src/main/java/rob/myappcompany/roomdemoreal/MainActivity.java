package rob.myappcompany.roomdemoreal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }
    public void insertSingelUser(View view){
        User user = new User("Matheo", "Dinarvand");
        InsertAsyncTask insertAsyncTask = new InsertAsyncTask();
        insertAsyncTask.execute(user);
    }

    public void getAllUser(View view){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                List<User> userList = MyRoomDatabase.getInstance(getApplicationContext())
                        .userDao()
                        .getAllUsers();
                for (int i = 0; i < userList.size(); i++){
                    Log.i("Main", userList.get(i).firstName);
                }

            }
        });
        thread.start();
    }
    public void deleteUser(View view){
        new Thread(new Runnable() {
            @Override
            public void run() {
                User user = MyRoomDatabase.getInstance(getApplicationContext())
                        .userDao()
                        .findById(1);

                if (user != null){
                    Log.d("Main", user.firstName);

                    MyRoomDatabase.getInstance(getApplicationContext())
                            .userDao()
                            .delete(user);

                    Log.d("Main", "Has been deleted");
                }

            }
        }).start();

    }

    public void updateUser(View view){
        new Thread(new Runnable() {
            @Override
            public void run() {
                User user = MyRoomDatabase.getInstance(getApplicationContext())
                        .userDao()
                        .findById(6);

                if (user != null){
                    Log.d("Main", user.firstName);
                    user.firstName = "Mattt";

                    MyRoomDatabase.getInstance(getApplicationContext())
                            .userDao()
                            .updateUser(user);

                    Log.d("Main", "Has been deleted");
                }

            }
        }).start();

    }

    public void insertMultipleUser(View view){
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<User> userList = new ArrayList<>();
                userList.add(new User("Shahram", "Khodadadi"));
                userList.add(new User("Amir", "Saberi"));
                userList.add(new User("Ali", "Dinarvand"));

                MyRoomDatabase.getInstance(getApplicationContext())
                        .userDao()
                        .insertMultipleUsers(userList);
                Log.d("Main", "User multiple Inserted");
            }
        }).start();

    }
    public void findComplatedUser(View view){
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<User> userList = MyRoomDatabase.getInstance(getApplicationContext())
                        .userDao()
                        .getAllFirstNames();

                for (int i = 0; i < userList.size(); i++){
                    Log.d("Main", userList.get(i).firstName.toString());
                }

            }
        }).start();

    }

    class InsertAsyncTask extends AsyncTask<User, Void, Void>{

        @Override
        protected Void doInBackground(User... users) {
            MyRoomDatabase.getInstance(getApplicationContext())
                    .userDao()
                    .insert(users[0]);
            Log.i("Main", "Inserted");

            return null;
        }
    }
}