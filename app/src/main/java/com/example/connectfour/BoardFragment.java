//Board java file
package com.example.connectfour;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.Fragment;
import androidx.gridlayout.widget.GridLayout;

import java.util.Objects;


public class BoardFragment extends Fragment {

    private static final String GAME_STATE = "gameState";

    private ConnectFourGame mGame;
    private GridLayout mGrid;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_board, container, false);

        mGrid = view.findViewById(R.id.Grid_layout);

        // Iterate through the collection of button elements to set an on click listener as method onButtonClick
        for (int i = 0; i < mGrid.getChildCount(); i++) {
            View child = mGrid.getChildAt(i);
            if (child instanceof Button) {
                child.setOnClickListener(this::onButtonClick);
            }
        }

        mGame = new ConnectFourGame();
        if(savedInstanceState == null){
            startGame();
        }
        else{
            String gameState = savedInstanceState.getString("gameState");
            assert gameState != null;
            mGame.setState(gameState);
            setDisc();
        }

        return view;
    }

    private void onButtonClick(View view) {

        int buttonIndex = mGrid.indexOfChild(view);
        int row = buttonIndex / ConnectFourGame.COL;
        int col = buttonIndex % ConnectFourGame.COL;

        mGame.selectDisc(row, col);
        setDisc();

        if(mGame.isGameOver()){
            String winMessage = mGame.isWin()
                    ? "Congratulations, we have a winner!"
                    : "It's a draw!";
            Toast.makeText(getActivity(), winMessage, Toast.LENGTH_LONG).show();
            //delaying the board reset to give chance to see toast!
            mGrid.postDelayed(() -> {
                mGame.newGame(); // Reset the game logic
                setDisc();       // Reset the board UI
            }, 2000);
        }

    }

    void startGame(){
        mGame.newGame();
        setDisc();
    }


    void setDisc(){
        for(int buttonIndex = 0; buttonIndex < mGrid.getChildCount(); buttonIndex++){
            Button gridButton = (Button) mGrid.getChildAt(buttonIndex);

            int row = buttonIndex / ConnectFourGame.COL;
            int col = buttonIndex % ConnectFourGame.COL;


            Drawable emptyDisc = DrawableCompat.wrap(Objects.requireNonNull(ContextCompat.getDrawable(requireActivity(), R.drawable.circle_white)));
            Drawable blueDisc = DrawableCompat.wrap(Objects.requireNonNull(ContextCompat.getDrawable(requireActivity(), R.drawable.circle_blue)));
            Drawable redDisc = DrawableCompat.wrap(Objects.requireNonNull(ContextCompat.getDrawable(requireActivity(), R.drawable.circle_red)));

            int discState = mGame.getDisc(row, col);

            if (discState == ConnectFourGame.BLUE) {
                gridButton.setBackground(blueDisc);
            } else if (discState == ConnectFourGame.RED) {
                gridButton.setBackground(redDisc);
            } else {
                gridButton.setBackground(emptyDisc);
            }
        }
    }

    @Override
    //Override method onSaveInstanceState
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        //call method putString on object outState passing arguments: GAME_STATE and mGame.getState()
        outState.putString(GAME_STATE, mGame.getState());
    }


}
