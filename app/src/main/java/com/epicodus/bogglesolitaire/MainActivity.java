package com.epicodus.bogglesolitaire;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    @Bind(R.id.submitWordButton) Button mSubmitWordButton;
    @Bind(R.id.inputWordText) EditText mInputWordText;
    @Bind(R.id.boggleLettersView) TextView mBoggleLettersView;
    @Bind(R.id.wordsView) ListView mWordsView;


    ArrayList<String> boggleWords = new ArrayList<String>();
    private String randomLetterString= "";
    private char[] boggleLetters = new char[8];
    private char[] alphabet = new char[] {
          'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'
    };
    private char[] vowel = new char[] {
            'A', 'E', 'I', 'O', 'U'
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mSubmitWordButton.setOnClickListener(this);
        this.getLetters();
        mBoggleLettersView.setText(randomLetterString);
    }
    @Override
    public void onClick(View v){
        String inputWord = mInputWordText.getText().toString();
        if ( wordChecker(inputWord)){
            boggleWords.add(inputWord);
            ArrayAdapter adapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, boggleWords);
            mWordsView.setAdapter(adapter);
        } else {
            Toast.makeText(MainActivity.this, "Not Allowed", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean wordChecker(String inputWord){
        boolean validWord = true;
        char[] inputLetters = inputWord.toCharArray();
        char[] removeLetters = boggleLetters.clone();

        for (int j=0; j<inputLetters.length; j++){
            if(validWord) {
                int matchedLetter = 0;
                    for (int i=0; i < removeLetters.length; i++){
                        if (removeLetters[i] == inputLetters[j]) {
                            matchedLetter++;
                            removeLetters[i] = 0;
                            break;
                        }
                    }
                if(matchedLetter > 0) {
                    validWord = true;
                } else {
                    validWord = false;
                }
            }
        }
        return validWord;
    }

    public int randNumber(){
        Random rand = new Random();
        int n = rand.nextInt(25);
        return n;
    }

    public int randVowel() {
        Random rand = new Random();
        return rand.nextInt(4);
    }

    public boolean checkVowels() {
        int vowelCounter = 0;
        for (int i=0; i < boggleLetters.length; i++) {
            for (int j = 0; j <= 4; j++) {
                if (boggleLetters[i] == vowel[j]) {
                    vowelCounter++;
                }
            }
        }
        if (vowelCounter >=2) {
            return true;
        } else {
            return false;
        }
    }

    public void getLetters(){
        for(int i=0; i<=7; i++){
            if(i <=5){
                char letter1 = alphabet[randNumber()];
                boggleLetters[i] = letter1;
                Log.v("GetaLetter", boggleLetters.toString());
            }else {
                if(checkVowels()) {
                    char letter2 = alphabet[randNumber()];
                    boggleLetters[i] = letter2;
                } else {
                    char letter2 = vowel[randVowel()];
                    boggleLetters[i] = letter2;
                }
            }
        }
        randomLetterString = String.valueOf(boggleLetters);
    }
}