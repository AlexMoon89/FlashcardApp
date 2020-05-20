package com.example.studycards;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddCardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)

    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);


        ((EditText) findViewById(R.id.editQuestion)).setText(getIntent().getStringExtra("question"));
        ((EditText) findViewById(R.id.editCorrect)).setText(getIntent().getStringExtra("answer"));
        ((EditText) findViewById(R.id.editWrong1)).setText(getIntent().getStringExtra("wrong_answer1"));
        ((EditText) findViewById(R.id.editWrong2)).setText(getIntent().getStringExtra("wrong_answer2"));

//ADD SAVE BUTTON
        Button saveButton=
                findViewById(R.id.saveButton);

        saveButton.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View view)

            {

                String question = ((EditText) findViewById(R.id.editQuestion)).getText().toString();
                String correctAnswer = ((EditText) findViewById(R.id.editCorrect)).getText().toString();
                String wrongAnswer1 = ((EditText) findViewById(R.id.editWrong1)).getText().toString();
                String wrongAnswer2 = ((EditText) findViewById(R.id.editWrong2)).getText().toString();

                if(question.equals("") || correctAnswer.equals("")
                        || wrongAnswer1.equals("") || wrongAnswer2.equals(""))
                {
                    Toast toast = Toast.makeText(getApplicationContext(), "Error: Please fill out all missing information.", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                    toast.show();
                }else{

                    Intent data = new Intent();
                    data.putExtra("question", question);
                    data.putExtra("answer", correctAnswer);
                    data.putExtra("wrong_answer1", wrongAnswer1);
                    data.putExtra("wrong_answer2", wrongAnswer2);

                    setResult(RESULT_OK, data);
                    finish();
                }
            }
        });

        findViewById(R.id.cancelButton).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}