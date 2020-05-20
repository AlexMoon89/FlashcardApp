package com.example.studycards;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;


import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    FlashcardDatabase flashcardDatabase;
    List<Flashcard> allFlashcards;
    int currentCardDisplayedIndex = 0;

    Flashcard cardToEdit;

    public int getRandomNumber(int minNumber, int maxNumber) {
        Random rand = new Random();
        return rand.nextInt((maxNumber - minNumber) + 1) + minNumber;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    //Flashcard Database
    flashcardDatabase = new FlashcardDatabase(getApplicationContext());
    allFlashcards = flashcardDatabase.getAllCards();

    if (allFlashcards != null && allFlashcards.size() > 0) {
        ((TextView) findViewById(R.id.flashcard_question)).setText(allFlashcards.get(0).getQuestion());
        ((TextView) findViewById(R.id.flashcard_answer)).setText(allFlashcards.get(0).getAnswer());
        ((TextView) findViewById(R.id.answer1)).setText(allFlashcards.get(0).getWrongAnswer1());
        ((TextView) findViewById(R.id.answer2)).setText(allFlashcards.get(0).getWrongAnswer2());
        ((TextView) findViewById(R.id.answer3)).setText(allFlashcards.get(0).getAnswer());
    }

        findViewById(R.id.flashcard_question).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                findViewById(R.id.flashcard_question).setVisibility(View.INVISIBLE);
                findViewById(R.id.flashcard_answer).setVisibility(View.VISIBLE);
                View answerSideView = findViewById(R.id.flashcard_answer);
                final View questionSideView= findViewById(R.id.flashcard_question);

                answerSideView.setVisibility(View.VISIBLE);
                questionSideView.setVisibility(View.INVISIBLE);

                findViewById(R.id.answer1).setBackgroundColor(getResources().getColor(R.color.Yellow));
                findViewById(R.id.answer2).setBackgroundColor(getResources().getColor(R.color.Yellow));
                findViewById(R.id.answer3).setBackgroundColor(getResources().getColor(R.color.Yellow));

                // get the center for the clipping circle
                int cx = answerSideView.getWidth() / 2;
                int cy = answerSideView.getHeight() / 2;

                // get the final radius for the clipping circle
                float finalRadius = (float) Math.hypot(cx, cy);

                // create the animator for this view (the start radius is zero)
                Animator anim = ViewAnimationUtils.createCircularReveal(answerSideView, cx, cy, 0f, finalRadius);

                // hide the question and show the answer to prepare for playing the animation!

                anim.setDuration(3000);
                anim.start();


            }

        });
        findViewById(R.id.flashcard_answer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //    new ParticleSystem(MainActivity.this, 100, R.drawable.confetti, 3000)
                //           .setSpeedRange(0.2f, 0.5f)
                //           .oneShot(findViewById(R.id.answer3), 100);


                findViewById(R.id.flashcard_question).setVisibility(View.VISIBLE);
                findViewById(R.id.flashcard_answer).setVisibility(View.INVISIBLE);

                findViewById(R.id.answer1).setBackgroundColor(getResources().getColor(R.color.colorPurpleBackground));
                findViewById(R.id.answer2).setBackgroundColor(getResources().getColor(R.color.colorPurpleBackground));
                findViewById(R.id.answer3).setBackgroundColor(getResources().getColor(R.color.colorPurpleBackground));

            }

        });
        findViewById(R.id.answer1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.answer1).setBackgroundColor(getResources().getColor(R.color.WrongRed));
                findViewById(R.id.answer3).setBackgroundColor(getResources().getColor(R.color.RightGreen));

            }

        });

        findViewById(R.id.answer2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.answer3).setBackgroundColor(getResources().getColor(R.color.RightGreen));
                findViewById(R.id.answer2).setBackgroundColor(getResources().getColor(R.color.WrongRed));
            }

        });
        findViewById(R.id.answer3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.answer3).setBackgroundColor(getResources().getColor(R.color.RightGreen));

                //new ParticleSystem(MainActivity.this, 100, R.drawable.confetti, 3000)
                   //     .setSpeedRange(0.2f, 0.5f)
                     //   .oneShot(findViewById(R.id.answer3), 100);


            }

        });

        findViewById(R.id.unseeIcon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.unseeIcon).setVisibility(View.INVISIBLE);
                findViewById(R.id.seeIcon).setVisibility(View.VISIBLE);
                findViewById(R.id.answer1).setVisibility(View.INVISIBLE);
                findViewById(R.id.answer2).setVisibility(View.INVISIBLE);
                findViewById(R.id.answer3).setVisibility(View.INVISIBLE);

            }
        });

        findViewById(R.id.seeIcon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.unseeIcon).setVisibility(View.VISIBLE);
                findViewById(R.id.seeIcon).setVisibility(View.INVISIBLE);
                findViewById(R.id.answer1).setVisibility(View.VISIBLE);
                findViewById(R.id.answer2).setVisibility(View.VISIBLE);
                findViewById(R.id.answer3).setVisibility(View.VISIBLE);

            }
        });


        Button addButton =
                findViewById(R.id.addButton);


        addButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, AddCardActivity.class);
                MainActivity.this.startActivityForResult(intent, 100);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
            }
        });

        Button editButton =
                findViewById(R.id.editButton);

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cardToEdit = allFlashcards.get(currentCardDisplayedIndex);
                System.out.println("Hello this: " + cardToEdit.getQuestion());


                String question = ((TextView) findViewById(R.id.flashcard_question)).getText().toString();
                String correctAnswer = ((TextView) findViewById(R.id.flashcard_answer)).getText().toString();
                String wrongAnswer1 = ((TextView) findViewById(R.id.answer1)).getText().toString();
                String wrongAnswer2 = ((TextView) findViewById(R.id.answer2)).getText().toString();

                Intent intent = new Intent(MainActivity.this, AddCardActivity.class);
                intent.putExtra("question", question);
                intent.putExtra("answer", correctAnswer);
                intent.putExtra("wrong_answer1", wrongAnswer1);
                intent.putExtra("wrong_answer2", wrongAnswer2);
                MainActivity.this.startActivityForResult(intent, 100);

            }
        });


        findViewById(R.id.nextButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Animation leftOutAnim = AnimationUtils.loadAnimation(v.getContext(), R.anim.left_out);
                final Animation rightInAnim = AnimationUtils.loadAnimation(v.getContext(), R.anim.right_in);

                leftOutAnim.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        // this method is called when the animation first starts
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        // this method is called when the animation is finished playing

                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                        // we don't need to worry about this method
                    }
                });


                if(allFlashcards.size() == 0)  {
                    return;
                    // add toast message
                    //Toast toast = Toast.makeText(MainActivity.this, "There are no remaining cards.", Toast.LENGTH_SHORT).show();
                }
                // advance our pointer index so we can show the next card
                currentCardDisplayedIndex = getRandomNumber(0, allFlashcards.size() - 1);


                findViewById(R.id.flashcard_answer).setVisibility(View.INVISIBLE);
                findViewById(R.id.flashcard_question).startAnimation(leftOutAnim);

                findViewById(R.id.flashcard_question).setVisibility(View.VISIBLE);
                findViewById(R.id.flashcard_question).startAnimation(rightInAnim);


                // set the question and answer TextViews with data from the database
                ((TextView) findViewById(R.id.flashcard_question)).setText(allFlashcards.get(currentCardDisplayedIndex).getQuestion());
                ((TextView) findViewById(R.id.flashcard_answer)).setText(allFlashcards.get(currentCardDisplayedIndex).getAnswer());
                ((TextView) findViewById(R.id.answer1)).setText(allFlashcards.get(currentCardDisplayedIndex).getWrongAnswer1());
                ((TextView) findViewById(R.id.answer2)).setText(allFlashcards.get(currentCardDisplayedIndex).getWrongAnswer2());
                ((TextView) findViewById(R.id.answer3)).setText(allFlashcards.get(currentCardDisplayedIndex).getAnswer());
            }
        });

        findViewById(R.id.deleteButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                flashcardDatabase.deleteCard(((TextView) findViewById(R.id.flashcard_question)).getText().toString());
                allFlashcards = flashcardDatabase.getAllCards();
                currentCardDisplayedIndex++;

                // make sure we don't get an IndexOutOfBoundsError if we are viewing the last indexed card in our list
                if (currentCardDisplayedIndex > allFlashcards.size() - 1) {
                    currentCardDisplayedIndex = 0;
                }

                ((TextView) findViewById(R.id.flashcard_question)).setText(allFlashcards.get(currentCardDisplayedIndex).getQuestion());
                ((TextView) findViewById(R.id.flashcard_answer)).setText(allFlashcards.get(currentCardDisplayedIndex).getAnswer());
                ((TextView) findViewById(R.id.answer1)).setText(allFlashcards.get(currentCardDisplayedIndex).getWrongAnswer1());
                ((TextView) findViewById(R.id.answer2)).setText(allFlashcards.get(currentCardDisplayedIndex).getWrongAnswer2());
                ((TextView) findViewById(R.id.answer3)).setText(allFlashcards.get(currentCardDisplayedIndex).getAnswer());
            }

        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        allFlashcards = flashcardDatabase.getAllCards();

        if (resultCode == RESULT_OK){

            String question = data.getExtras().getString("question");
            String answer = data.getExtras().getString("answer");
            String wrongAnswer1 = data.getExtras().getString("wrong_answer1");
            String wrongAnswer2 = data.getExtras().getString("wrong_answer2");

            int EDIT_CARD_REQUEST_CODE = 200;
            if (requestCode == 100) {


                ((TextView) findViewById(R.id.flashcard_question)).setText(question);
                ((TextView) findViewById(R.id.flashcard_answer)).setText(answer);

                ((TextView) findViewById(R.id.answer1)).setText(wrongAnswer1);
                ((TextView) findViewById(R.id.answer2)).setText(wrongAnswer2);
                ((TextView) findViewById(R.id.answer3)).setText(answer);

                flashcardDatabase.insertCard(new Flashcard(question, answer, wrongAnswer1, wrongAnswer2));
            }
            else if (requestCode == EDIT_CARD_REQUEST_CODE) {
                cardToEdit.setQuestion(question);
                cardToEdit.setAnswer(answer);
                cardToEdit.setWrongAnswer1(wrongAnswer1);
                cardToEdit.setWrongAnswer2(wrongAnswer2);

                flashcardDatabase.updateCard(cardToEdit);
            }

        }


        allFlashcards = flashcardDatabase.getAllCards();

    }
}


