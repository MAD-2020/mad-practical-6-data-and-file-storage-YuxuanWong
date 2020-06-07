package sg.edu.np.week_6_whackamole_3_0;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    /*
        1. This is the main page for user to log in
        2. The user can enter - Username and Password
        3. The user login is checked against the database for existence of the user and prompts
           accordingly via Toastbox if user does not exist. This loads the level selection page.
        4. There is an option to create a new user account. This loads the create user page.
     */

    private static final String FILENAME = "MainActivity.java";
    private static final String TAG = "Whack-A-Mole3.0!";
    private Button login;
    private TextView create;

    MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* Hint:
            This method creates the necessary login inputs and the new user creation ontouch.
            It also does the checks on button selected.
            Log.v(TAG, FILENAME + ": Create new user!");
            Log.v(TAG, FILENAME + ": Logging in with: " + etUsername.getText().toString() + ": " + etPassword.getText().toString());
            Log.v(TAG, FILENAME + ": Valid User! Logging in");
            Log.v(TAG, FILENAME + ": Invalid user!");

        */

        login = findViewById(R.id.login);
        create = findViewById(R.id.textView4);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText username = findViewById(R.id.username);
                EditText password = findViewById(R.id.password);

                if (isValidUser(username.getText().toString(), password.getText().toString())){
                    Intent goLevels = new Intent(MainActivity.this, Main3Activity.class);
                    goLevels.putExtra("username", username.getText().toString());
                    startActivity(goLevels);
                    Log.d(TAG, FILENAME + ": Valid User! Logging in");
                    Log.d(TAG, FILENAME + ": Logging in with: " + username.getText().toString() + ": " + password.getText().toString());
                }
                else{
                    Toast.makeText(getApplicationContext(),"Invalid username or password", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, FILENAME + ": Invalid user!");
                }
            }
        });

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent createAccount = new Intent(MainActivity.this, Main2Activity.class);
                startActivity(createAccount);
                Log.d(TAG, FILENAME + ": Create new user!");
            }
        });

    }

    protected void onStop(){
        super.onStop();
        finish();
    }

    public boolean isValidUser(String userName, String password){

        /* HINT:
            This method is called to access the database and return a true if user is valid and false if not.
            Log.v(TAG, FILENAME + ": Running Checks..." + dbData.getMyUserName() + ": " + dbData.getMyPassword() +" <--> "+ userName + " " + password);
            You may choose to use this or modify to suit your design.
         */
        boolean result;
        UserData dbData = dbHandler.findUser(userName);

        if (dbData.getMyUserName().equals(userName) && dbData.getMyPassword().equals(password)){
            Log.d(TAG, FILENAME + ": Running Checks..." + dbData.getMyUserName() + ": " + dbData.getMyPassword() +" <--> "+ userName + " " + password);
            result = true;
        }

        else
            result = false;
            Log.d(TAG, FILENAME + ": User does not exist");

        return result;
    }

}
