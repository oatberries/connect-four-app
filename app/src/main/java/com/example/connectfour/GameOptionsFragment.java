package com.example.connectfour;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

//Modify class GameOptionsFragment to extend class Fragment
public class GameOptionsFragment extends Fragment {

    private SharedPreferences sharedPreferences;

    @Override
    //replace method onCreate with onCreateView
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // View object inflates fragment_game_options layout
        View view = inflater.inflate(R.layout.fragment_game_options, container, false);

        // instantiate an instance of class shared preferences
        sharedPreferences = requireActivity().getSharedPreferences("GamePreferences", Context.MODE_PRIVATE);

        // Get the RadioGroup and set the selected radio button based on saved preferences
        RadioGroup radioGroup = view.findViewById(R.id.Difficulty_option_buttons);
        //select the radio button matching the level selected
        int selectedLevel = sharedPreferences.getInt("selectedLevel", -1);
        if (selectedLevel != -1) {
            RadioButton selectedButton = view.findViewById(selectedLevel);
            if (selectedButton != null) {
                //set the selected radio button so it is checked
                selectedButton.setChecked(true);
            }
        }


        // Add click callback to all radio buttons in the radio group as method onLevelSelected
        for (int i = 0; i < radioGroup.getChildCount(); i++) {
            View child = radioGroup.getChildAt(i);
            if (child instanceof RadioButton) {
                child.setOnClickListener(this::onLevelSelected);
            }
        }

        return view;
    }

    private void onLevelSelected(View view) {
        if (view instanceof RadioButton) {
            int selectedLevelId = view.getId();
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("selectedLevel", selectedLevelId);
            editor.apply();
        }
    }

}
