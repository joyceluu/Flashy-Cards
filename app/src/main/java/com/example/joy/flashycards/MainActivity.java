package com.example.joy.flashycards;

import android.content.Intent;
import android.media.Image;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    // database variables here
    FlashcardDatabase flashcardDatabase;
    List<Flashcard> allFlashcards;
    Flashcard currentlyEditing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        flashcardDatabase = new FlashcardDatabase(getApplicationContext());
        allFlashcards = flashcardDatabase.getAllCards();

        if (allFlashcards != null && allFlashcards.size() != 0)
        {
            ((TextView) findViewById(R.id.flashcard_question)).setText(allFlashcards.get(0).getQuestion());
            ((TextView) findViewById(R.id.flashcard_answer)).setText(allFlashcards.get(0).getAnswer());
            ((TextView) findViewById(R.id.answer1)).setText(allFlashcards.get(0).getWrongAnswer1());
            ((TextView) findViewById(R.id.answer2)).setText(allFlashcards.get(0).getWrongAnswer2());
        }

        findViewById(R.id.flashcard_question).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.flashcard_question).setVisibility(View.INVISIBLE);
                findViewById(R.id.flashcard_answer).setVisibility(View.VISIBLE);
            }
        });

        findViewById(R.id.flashcard_answer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.flashcard_answer).setVisibility(View.INVISIBLE);
                findViewById(R.id.flashcard_question).setVisibility(View.VISIBLE);
            }
        });

        ImageView ico = (ImageView) findViewById(R.id.toggle_choices_visibility);
        ico.setOnClickListener(new View.OnClickListener() {
            boolean isShowingAnswers = false;
            @Override
            public void onClick(View v) {
                if (isShowingAnswers) {
                    findViewById(R.id.answer1).setVisibility(View.INVISIBLE);
                    findViewById(R.id.answer2).setVisibility(View.INVISIBLE);
                    findViewById(R.id.answer3).setVisibility(View.INVISIBLE);
                    isShowingAnswers = false;
                }
                else {
                    findViewById(R.id.answer1).setVisibility(View.VISIBLE);
                    findViewById(R.id.answer2).setVisibility(View.VISIBLE);
                    findViewById(R.id.answer3).setVisibility(View.VISIBLE);
                    isShowingAnswers = true;
                }

            }
        });

        findViewById(R.id.answer1).setOnClickListener(new View.OnClickListener() {
            boolean clicked1 = false;
            @Override
            public void onClick(View v) {
                if (clicked1)
                {
                    findViewById(R.id.answer1).setBackgroundColor(getColor(R.color.answer));
                    clicked1 = false;
                }
                else {
                    findViewById(R.id.answer1).setBackgroundColor(getColor(R.color.wrong));
                    clicked1 = true;
                }
            }
        });


        findViewById(R.id.answer2).setOnClickListener(new View.OnClickListener() {
            boolean clicked2 = false;
            @Override
            public void onClick(View v) {
                if (clicked2)
                {
                    findViewById(R.id.answer2).setBackgroundColor(getColor(R.color.answer));
                    clicked2 = false;
                }
                else {
                    findViewById(R.id.answer2).setBackgroundColor(getColor(R.color.wrong));
                    clicked2 = true;
                }
            }
        });

        findViewById(R.id.answer3).setOnClickListener(new View.OnClickListener() {
            boolean clicked3 = false;
            @Override
            public void onClick(View v) {
                if (clicked3)
                {
                    findViewById(R.id.answer3).setBackgroundColor(getColor(R.color.answer));
                    clicked3 = false;
                }
                else {
                    findViewById(R.id.answer3).setBackgroundColor(getColor(R.color.wrong));
                    clicked3 = true;
                }
            }
        });

        ImageView addNewCard = (ImageView) findViewById(R.id.addCard);
        addNewCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddCardActivity.class);
                MainActivity.this.startActivityForResult(intent, 100);
            }
        });

        ImageView editCard = (ImageView) findViewById(R.id.edit);
        editCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddCardActivity.class);
                intent.putExtra("question", ((TextView) findViewById(R.id.flashcard_question)).getText().toString());
                intent.putExtra("answer", ((TextView) findViewById(R.id.flashcard_answer)).getText().toString());
                intent.putExtra("wrong1", ((TextView) findViewById(R.id.answer1)).getText().toString());
                intent.putExtra("wrong2", ((TextView) findViewById(R.id.answer3)).getText().toString());
                setResult(RESULT_OK,intent);
                for (int i = 0; i <= allFlashcards.size()-1; i++)
                {
                    if (allFlashcards.get(i).getQuestion() == ((TextView) findViewById(R.id.flashcard_question)).getText().toString())
                    {
                        currentlyEditing = allFlashcards.get(i);
                    }
                }
                MainActivity.this.startActivityForResult(intent, 101);
            }
        });

        ImageView deleteCard = (ImageView) findViewById(R.id.delete) ;
        deleteCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flashcardDatabase.deleteCard(((TextView)findViewById(R.id.flashcard_question)).getText().toString());
                allFlashcards = flashcardDatabase.getAllCards();
                if (allFlashcards.size() == 0)
                {
                    ((TextView) findViewById(R.id.flashcard_question)).setText("Add a card!");
                    ((TextView) findViewById(R.id.flashcard_answer)).setText("What should we study?");
                }
                else
                {
                    Flashcard random = allFlashcards.get(getRandomNumber(0, allFlashcards.size() - 1));
                    ((TextView) findViewById(R.id.flashcard_question)).setText(random.getQuestion());
                    ((TextView) findViewById(R.id.flashcard_answer)).setText(random.getAnswer());
                    ((TextView) findViewById(R.id.answer1)).setText(random.getWrongAnswer1());
                    ((TextView) findViewById(R.id.answer2)).setText(random.getAnswer());
                    ((TextView) findViewById(R.id.answer3)).setText(random.getWrongAnswer2());
                }
            }
        });

        ImageView nextCard = (ImageView) findViewById(R.id.next);
        nextCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.flashcard_answer).setVisibility(View.INVISIBLE);
                findViewById(R.id.flashcard_question).setVisibility(View.VISIBLE);

                if (allFlashcards.size() == 0)
                {
                }
                else
                {
                    Flashcard random = allFlashcards.get(getRandomNumber(0, allFlashcards.size() - 1));
                    ((TextView) findViewById(R.id.flashcard_question)).setText(random.getQuestion());
                    ((TextView) findViewById(R.id.flashcard_answer)).setText(random.getAnswer());
                    ((TextView) findViewById(R.id.answer1)).setText(random.getWrongAnswer1());
                    ((TextView) findViewById(R.id.answer2)).setText(random.getAnswer());
                    ((TextView) findViewById(R.id.answer3)).setText(random.getWrongAnswer2());
                }
            }

        });
    }

    public int getRandomNumber(int minNumber, int maxNumber) {
        Random rand = new Random();
        return rand.nextInt((maxNumber - minNumber) + 1) + minNumber;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        String question = data.getExtras().getString("question");
        String answer = data.getExtras().getString("answer");
        String wrong1 = data.getExtras().getString("wrong1");
        String wrong2 = data.getExtras().getString("wrong2");
        ((TextView) findViewById(R.id.flashcard_question)).setText(question);
        ((TextView) findViewById(R.id.flashcard_answer)).setText(answer);
        ((TextView) findViewById(R.id.answer1)).setText(wrong1);
        ((TextView) findViewById(R.id.answer3)).setText(wrong2);
        ((TextView) findViewById(R.id.answer2)).setText(answer);

        if (requestCode == 100 &&  resultCode == RESULT_OK) {
            flashcardDatabase.insertCard(new Flashcard(question, answer, wrong1, wrong2));
            allFlashcards = flashcardDatabase.getAllCards();

            View.OnClickListener myOnClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            };
            Snackbar.make((RelativeLayout) findViewById(R.id.parentView), R.string.snackbar_text, Snackbar.LENGTH_LONG)
                    .setAction(R.string.snackbar_action, myOnClickListener)
                    .show();
        }

        if (requestCode == 101 && resultCode == RESULT_OK)
        {
            currentlyEditing.setQuestion(question);
            currentlyEditing.setAnswer(answer);
            currentlyEditing.setWrongAnswer1(wrong1);
            currentlyEditing.setWrongAnswer2(wrong2);

            flashcardDatabase.updateCard(currentlyEditing);
            allFlashcards = flashcardDatabase.getAllCards();
        }
    }
}
