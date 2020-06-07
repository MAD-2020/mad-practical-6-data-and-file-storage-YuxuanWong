package sg.edu.np.week_6_whackamole_3_0;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class CustomScoreAdaptor extends RecyclerView.Adapter<CustomScoreViewHolder> {
    /* Hint:
        1. This is the custom adaptor for the recyclerView list @ levels selection page

     */
    private static final String FILENAME = "CustomScoreAdaptor.java";
    private static final String TAG = "Whack-A-Mole3.0!";
    private UserData userData;
    private ArrayList<Integer> level_list;
    private ArrayList<Integer> score_list;

    public CustomScoreAdaptor(UserData userdata){
        /* Hint:
        This method takes in the data and readies it for processing.
         */
        userData = userdata;
        level_list = userdata.getLevels();
        score_list = userdata.getScores();
    }

    public CustomScoreViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        /* Hint:
        This method dictates how the viewholder layuout is to be once the viewholder is created.
         */
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.level_select, parent, false);
        return new CustomScoreViewHolder(item);
    }

    public void onBindViewHolder(final CustomScoreViewHolder holder, final int position){

        /* Hint:
        This method passes the data to the viewholder upon bounded to the viewholder.
        It may also be used to do an onclick listener here to activate upon user level selections.

        Log.v(TAG, FILENAME + " Showing level " + level_list.get(position) + " with highest score: " + score_list.get(position));
        Log.v(TAG, FILENAME+ ": Load level " + position +" for: " + list_members.getMyUserName());
         */
        final Integer level = level_list.get(position);
        Integer score = score_list.get(position);
        holder.levelName.setText("Level " + level);
        holder.highScore.setText("Highest Score: " + score);
        Log.d(TAG, FILENAME + " Showing level " + level_list.get(position) + " with highest score: " + score_list.get(position));

        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Log.d(TAG, "level selected = " + level_list.get(position));
                Intent startGame = new Intent (v.getContext(), Main4Activity.class);
                startGame.putExtra("level", level_list.get(position));
                startGame.putExtra("name", userData.getMyUserName());
                v.getContext().startActivity(startGame);
                Log.d(TAG, FILENAME+ ": Load level " + level_list.get(position) +" for: " + userData.getMyUserName());
            }
        });
    }

    public int getItemCount(){
        /* Hint:
        This method returns the the size of the overall data.
         */
        return level_list.size();
    }
}