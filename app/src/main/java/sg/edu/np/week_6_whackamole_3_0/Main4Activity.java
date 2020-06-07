package sg.edu.np.week_6_whackamole_3_0;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class Main4Activity extends AppCompatActivity {

    /* Hint:
        1. This creates the Whack-A-Mole layout and starts a countdown to ready the user
        2. The game difficulty is based on the selected level
        3. The levels are with the following difficulties.
            a. Level 1 will have a new mole at each 10000ms.
                - i.e. level 1 - 10000ms
                       level 2 - 9000ms
                       level 3 - 8000ms
                       ...
                       level 10 - 1000ms
            b. Each level up will shorten the time to next mole by 100ms with level 10 as 1000 second per mole.
            c. For level 1 ~ 5, there is only 1 mole.
            d. For level 6 ~ 10, there are 2 moles.
            e. Each location of the mole is randomised.
        4. There is an option return to the login page.
     */
    private static final String FILENAME = "Main4Activity.java";
    private static final String TAG = "Whack-A-Mole3.0!";
    CountDownTimer readyTimer;
    CountDownTimer newMolePlaceTimer;
    private TextView score;
    private int count = 0;
    private String username;
    private int level;
    private int interval;
    private Button back;
    MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);

    private void readyTimer(){
        /*  HINT:
            The "Get Ready" Timer.
            Log.v(TAG, "Ready CountDown!" + millisUntilFinished/ 1000);
            Toast message -"Get Ready In X seconds"
            Log.v(TAG, "Ready CountDown Complete!");
            Toast message - "GO!"
            belongs here.
            This timer countdown from 10 seconds to 0 seconds and stops after "GO!" is shown.
         */
        readyTimer = new CountDownTimer(10000, 1000){
            public void onTick(long millisUntilFinished){
                Toast.makeText(getApplicationContext(), "Get ready in " + millisUntilFinished/ 1000 + " seconds",Toast.LENGTH_SHORT).show();
                Log.d(TAG, "Ready CountDown!" + millisUntilFinished/ 1000);
            }
            public void onFinish(){
                Toast.makeText(getApplicationContext(), "GO!", Toast.LENGTH_LONG).show();
                Log.d(TAG, "Ready CountDown Complete!");
                placeMoleTimer();
            }
        };
        readyTimer.start();
    }

    private void placeMoleTimer(){
        /* HINT:
           Creates new mole location each second.
           Log.v(TAG, "New Mole Location!");
           setNewMole();
           belongs here.
           This is an infinite countdown timer.
         */
        if (level == 1){
            interval = 10000;
        }
        else if (level == 2){
            interval = 9000;
        }
        else if (level == 3){
            interval = 8000;
        }
        else if (level == 4){
            interval = 7000;
        }
        else if (level == 5){
            interval = 6000;
        }
        else if (level == 6){
            interval = 5000;
        }
        else if (level == 7){
            interval = 4000;
        }
        else if (level == 8){
            interval = 3000;
        }
        else if (level == 9){
            interval = 2000;
        }
        else if (level == 10){
            interval = 1000;
        }

        newMolePlaceTimer = new CountDownTimer(9000000, interval) {
            @Override
            public void onTick(long millisUntilFinished) {
                Log.d(TAG, "New Mole Location!");
                setNewMole();
            }
            @Override
            public void onFinish() {
                cancel();
            }
        };
        newMolePlaceTimer.start();
    }

    private static final int[] BUTTON_IDS = {
            /* HINT:
                Stores the 9 buttons IDs here for those who wishes to use array to create all 9 buttons.
                You may use if you wish to change or remove to suit your codes.*/
            R.id.hole1,R.id.hole2,R.id.hole3,R.id.hole4,R.id.hole5,
            R.id.hole6,R.id.hole7,R.id.hole8,R.id.hole9
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        /*Hint:
            This starts the countdown timers one at a time and prepares the user.
            This also prepares level difficulty.
            It also prepares the button listeners to each button.
            You may wish to use the for loop to populate all 9 buttons with listeners.
            It also prepares the back button and updates the user score to the database
            if the back button is selected.
         */
        score = findViewById(R.id.score);
        back = findViewById(R.id.back);
        Intent getUserDetails = getIntent();
        username = getUserDetails.getStringExtra("name");
        level = getUserDetails.getIntExtra("level", 100);

        for(final int id : BUTTON_IDS){
            /*  HINT:
            This creates a for loop to populate all 9 buttons with listeners.
            You may use if you wish to remove or change to suit your codes.
            */
            findViewById(id).setOnClickListener(clicked);
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserData userdata = dbHandler.findUser(username);
                if (count > userdata.getScores().get(level - 1)){
                    updateUserScore();
                }
                Intent back = new Intent(Main4Activity.this, Main3Activity.class);
                startActivity(back);
            }
        });
    }

    @Override
    protected void onStart(){
        super.onStart();
        readyTimer();
    }
    private void doCheck(Button checkButton)
    {
        /* Hint:
            Checks for hit or miss
            Log.v(TAG, FILENAME + ": Hit, score added!");
            Log.v(TAG, FILENAME + ": Missed, point deducted!");
            belongs here.
        */
        if (checkButton.getText() == "*"){
            count += 1;
            score.setText(String.valueOf(count));
            Log.d(TAG, FILENAME + ": Hit, score added!");
        }
        else {
            count -= 1;
            score.setText(String.valueOf(count));
            Log.d(TAG, FILENAME + ": Missed, score deducted!");
        }
    }

    public void setNewMole()
    {
        /* Hint:
            Clears the previous mole location and gets a new random location of the next mole location.
            Sets the new location of the mole. Adds additional mole if the level difficulty is from 6 to 10.
         */
        Random ran = new Random();

        if (level < 6){
            int randomLocation = ran.nextInt(9);
            for (final int id : BUTTON_IDS){
                Button button = findViewById(id);
                button.setText("O");
            }
            Button b = findViewById(BUTTON_IDS[randomLocation]);
            b.setText("*");
        }

        else {
            int randomLocation = ran.nextInt(9);
            int secondMole = ran.nextInt(9);
            for (final int id : BUTTON_IDS){
                Button b1 = findViewById(id);
                b1.setText("O");
            }
            Button b = findViewById(BUTTON_IDS[randomLocation]);
            b.setText("*");
            Button b2 = findViewById(BUTTON_IDS[secondMole]);
            b2.setText("*");
        }
    }

    private void updateUserScore()
    {
     /* Hint:
        This updates the user score to the database if needed. Also stops the timers.
        Log.v(TAG, FILENAME + ": Update User Score...");
      */
        newMolePlaceTimer.cancel();
        readyTimer.cancel();

        UserData userdata = dbHandler.findUser(username);
        ArrayList<Integer> score_list = userdata.getScores();

        if (count > score_list.get(level - 1)){
            score_list.set(level - 1, count);
            userdata.setScores(score_list);
            dbHandler.deleteAccount(username);
            dbHandler.addUser(userdata);
            Log.d(TAG, FILENAME + ": Update User Score...");
        }
        Log.d(TAG, FILENAME + ": Top score for level " + level + " is " + score_list.get(level - 1));
        Log.d(TAG, FILENAME + ": " + score_list);
        dbHandler.close();
    }

    private View.OnClickListener clicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Button b1 = (Button) v;
            doCheck(b1);
            placeMoleTimer();
        }
    };
}
