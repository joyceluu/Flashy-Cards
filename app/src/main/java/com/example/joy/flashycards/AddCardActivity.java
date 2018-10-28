package com.example.joy.flashycards;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class AddCardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);
        ((TextView)findViewById(R.id.question)).setText(getIntent().getStringExtra("question"));
        ((TextView)findViewById(R.id.answer)).setText(getIntent().getStringExtra("answer"));
        ((TextView)findViewById(R.id.answer2)).setText(getIntent().getStringExtra("wrong1"));
        ((TextView)findViewById(R.id.answer3)).setText(getIntent().getStringExtra("wrong2"));


        ImageView CancelView = (ImageView) findViewById(R.id.cancel);
        CancelView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddCardActivity.this, MainActivity.class);
                AddCardActivity.this.startActivity(intent);
            }
        });

        ImageView save = (ImageView) findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data = new Intent();
                String question = ((EditText) findViewById(R.id.question)).getText().toString();
                String answer = ((EditText) findViewById(R.id.answer)).getText().toString();
                String wrongAnswer1 =  ((EditText) findViewById(R.id.answer2)).getText().toString();
                String wronganswer2 = ((EditText) findViewById(R.id.answer3)).getText().toString();

                if (question.equals("") || answer.equals(""))
                {
                    Toast.makeText(getApplicationContext(), "You must enter both a question and answer!", Toast.LENGTH_SHORT).show();
                    return; // stop function here, don't return back to MainActivity
                }

                data.putExtra("question", question);
                data.putExtra("answer", answer);
                data.putExtra("wrong1", wrongAnswer1);
                data.putExtra("wrong2", wronganswer2);
                setResult(RESULT_OK, data);
                finish();
            }
        });

    }
}
