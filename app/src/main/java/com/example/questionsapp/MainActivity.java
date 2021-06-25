package com.example.questionsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference reference;
    int questionCount = 0;
    int currQuestion = 0;

    EditText tv_Question;
    TextView tv_question;

    ArrayList<Question> questions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        reference = FirebaseDatabase.getInstance().getReference("Questions");
        database = FirebaseDatabase.getInstance();
        tv_question = findViewById(R.id.tv_QuestionBox);
        questions = new ArrayList<>();

        Intent i =  getIntent();
        String msg = i.getStringExtra("exitcode");
        if (msg != null && msg.equals("clear"))
        {
            emptyQuestions();
        }

        getData();
    }
    public void switchToAddActivity(View view)
    {
        Intent switchToAdd = new Intent(this, AddQuestion.class);
        switchToAdd.putExtra("questions", (Serializable) questions);
        startActivity(switchToAdd);
    }



    public void getData() {

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren())
                {
                    Question q;
                    String tQuestion = ds.child("question").getValue(String.class);
                    String type = ds.child("typeOfQuestion").getValue(String.class);
                    q = new Question(tQuestion, type);
                    questions.add(q);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, "Fail to get data.", Toast.LENGTH_SHORT).show();
            }
        };
        reference.addValueEventListener(postListener);
    }

    public void test_add_Questions(View view) {


        Question question;
        if (questions.size() == 0) {
            question = new Question("How many fingers do you have?", "Anatomy");
            reference.child(UUID.randomUUID().toString()).setValue(question);
        }
        else if (questions.size() == 1)
        {
            question = new Question("Are you gay?", "Homosexuality");
            reference.child(UUID.randomUUID().toString()).setValue(question);
        }
        else
        {
            question =  new Question("Are you going to bed?", "Tiredness");
            reference.child(UUID.randomUUID().toString()).setValue(question);
        }


    }
    public void generateQuestion(View view) {

        //Toast.makeText(this, "Questions available: " + questions.size(), Toast.LENGTH_SHORT).show();

        if (questions.size() == 0)
        {
            Toast.makeText(this, "No questions in database.", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Random rand = new Random();
            int randomQuestion = rand.nextInt(questions.size());
            tv_question.setText(questions.get(randomQuestion).getQuestion());

            // moving question to back of array once asked
        }
    }

    public void clearQuestion(View view)
    {
        tv_question.setText("");
    }
    public void emptyQuestions() {
        questions.clear();
        Toast.makeText(this, "Database has been cleared", Toast.LENGTH_SHORT).show();
        tv_question.setText("Questions will appear here.");

    }

}