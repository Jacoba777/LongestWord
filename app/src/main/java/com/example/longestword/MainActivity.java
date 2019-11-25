package com.example.longestword;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    EditText etxt_chars;
    TextView txt_result;
    Button btn_search;

    ArrayList<String> words;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        words = new ArrayList<>();

        etxt_chars = findViewById(R.id.edit_txt_chars);
        txt_result = findViewById(R.id.txt_result);
        btn_search = findViewById(R.id.btn_search);

        getWords();
    }

    private boolean canSpellWord(ArrayList<Character> chars, String word)
    {
        ArrayList<Character> charCopy = (ArrayList<Character>) chars.clone();

        for(char ch : word.toCharArray())
        {
            if(!charCopy.contains(ch))
                return false;
            else
                charCopy.remove(charCopy.indexOf(ch));
        }

        return true;
    }

    private void getWords()
    {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(
                    new InputStreamReader(getAssets().open("words_alpha.txt")));

            // do reading, usually loop until end of file reading
            String mLine;
            while ((mLine = reader.readLine()) != null) {
                words.addAll(Arrays.asList(mLine.split("\n")));
            }
        } catch (IOException e) {
            //log the exception
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    //log the exception
                }
            }
        }
    }

    public void search(View view)
    {
        String raw_chars = etxt_chars.getText().toString().toLowerCase();
        ArrayList<Character> chars = new ArrayList<>();

        for(char ch : raw_chars.toCharArray())
        {
            chars.add(ch);
        }

        ArrayList<String> best_words = new ArrayList<>();
        best_words.add("");

        for(String word : words)
        {
            if(word.length() >= best_words.get(0).length() && canSpellWord(chars, word))
            {
                if(word.length() > best_words.get(0).length())
                    best_words = new ArrayList<>();

                best_words.add(word);
            }
        }

        String res;
        if(best_words.size() == 0)
            res = "You cannot spell any English word with these letters.";
        else if(best_words.size() == 1)
            res = "The longest word you can create with these letters is \"" + best_words.get(0) + "\"";
        else
        {
            res = "The longest words you can create with these letters are: ";

            for(int i = 0; i < best_words.size(); i++)
            {
                res += "\"" + best_words.get(i) + "\"";

                if(i < best_words.size() - 1)
                {
                    res += ", ";
                }
            }
        }
        txt_result.setText(res);
    }
}
