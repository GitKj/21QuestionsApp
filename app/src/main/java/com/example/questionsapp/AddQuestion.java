package com.example.questionsapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;

public class AddQuestion extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference reference;
    EditText et_question;

    Button backButton;
    Button clrDB;

    ArrayList<Question> questionArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_question);


        Intent i = getIntent();
        questionArrayList = (ArrayList<Question>) i.getSerializableExtra("questions");
        reference = FirebaseDatabase.getInstance().getReference("Questions");
        database = FirebaseDatabase.getInstance();
        et_question = findViewById(R.id.et_questionInput);

        backButton = findViewById(R.id.btn_Back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



    }

    public void addQuestion(View view)
    {
        Question q = new Question(et_question.getText().toString(), "User added.");
        if (et_question.getText().toString().length() <= 0 || et_question.getText().toString().replace(" ", "").length() > 120)
        {
            Toast.makeText(this, "Questions must be between 1 and 120 characters.", Toast.LENGTH_SHORT).show();
            return;
        }
        for (int i = 0; i < questionArrayList.size(); i++)
        {
            if (et_question.getText().toString().toLowerCase().equals(questionArrayList.get(i).getQuestion().toLowerCase()))
            {
                Toast.makeText(this, "Question already in database!", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        reference.child(UUID.randomUUID().toString()).setValue(q);
        Toast.makeText(this, "Question added to Database.", Toast.LENGTH_SHORT).show();
        finish();

    }

    public void clearDatabase(View view)
    {
        questionArrayList.clear();
        reference = FirebaseDatabase.getInstance().getReference();
        reference.setValue(null);
        Intent switchToMain = new Intent(this, MainActivity.class);
        switchToMain.putExtra("exitcode", "clear");
        startActivity(switchToMain);
        finish();


    }


}