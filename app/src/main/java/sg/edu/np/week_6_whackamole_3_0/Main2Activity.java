package sg.edu.np.week_6_whackamole_3_0;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity {
    /* Hint:
        1. This is the create new user page for user to log in
        2. The user can enter - Username and Password
        3. The user create is checked against the database for existence of the user and prompts
           accordingly via Toastbox if user already exists.
        4. For the purpose the practical, successful creation of new account will send the user
           back to the login page and display the "User account created successfully".
           the page remains if the user already exists and "User already exist" toastbox message will appear.
        5. There is an option to cancel. This loads the login user page.
     */


    private static final String FILENAME = "Main2Activity.java";
    private static final String TAG = "Whack-A-Mole3.0!";
    private Button create;
    private Button cancel;
    private EditText newUsername;
    private EditText newPassword;
    MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        /* Hint:
            This prepares the create and cancel account buttons and interacts with the database to determine
            if the new user created exists already or is new.
            If it exists, information is displayed to notify the user.
            If it does not exist, the user is created in the DB with default data "0" for all levels
            and the login page is loaded.

            Log.v(TAG, FILENAME + ": New user created successfully!");
            Log.v(TAG, FILENAME + ": User already exist during new user creation!");

         */

        create = findViewById(R.id.create);
        cancel = findViewById(R.id.cancel);
        newUsername = findViewById(R.id.new_username);
        newPassword = findViewById(R.id.new_password);

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dbHandler.findUser(newUsername.getText().toString()) == null){
                    UserData ud = new UserData();
                    ArrayList<Integer> level = new ArrayList<>();
                    ArrayList<Integer> score = new ArrayList<>();

                    for (int i = 1; i <= 10; i++){
                        level.add(i);
                        score.add(0);
                    }
                    ud.setMyUserName(newUsername.getText().toString());
                    ud.setMyPassword(newPassword.getText().toString());
                    ud.setLevels(level);
                    ud.setScores(score);
                    dbHandler.addUser(ud);
                    //dbHandler.close();
                    Toast.makeText(getApplicationContext(), "User account created successfully", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, FILENAME + ": New user created successfully!");

                    Intent goLogin = new Intent(Main2Activity.this, MainActivity.class);
                    startActivity(goLogin);
                }
                else{
                    Toast.makeText(getApplicationContext(), "User already exist, please try again", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, FILENAME + ": User already exist during new user creation!");
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cancelCreate = new Intent(Main2Activity.this, MainActivity.class);
                startActivity(cancelCreate);
                Log.d(TAG, FILENAME + ": Cancel creation");
            }
        });

    }

    protected void onStop() {
        super.onStop();
        finish();
    }
}
