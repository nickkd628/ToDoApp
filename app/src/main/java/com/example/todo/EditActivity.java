package com.example.todo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import javax.xml.transform.Result;

public class EditActivity extends AppCompatActivity {
    EditText etText; //etitem
    Button btnsave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        etText = findViewById(R.id.etText);
        btnsave = findViewById(R.id.btnsave);

        getSupportActionBar().setTitle("edit item");
        etText.setText(getIntent().getStringExtra(MainActivity.key_Item_text));

       btnsave.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               // create an intent
               Intent intent = new Intent();
               //pass the data
                intent.putExtra(MainActivity.key_Item_text, etText.getText().toString());
                intent.putExtra(MainActivity.key_Item_position, getIntent().getExtras().getInt(MainActivity.key_Item_position));
               //set the results
               setResult(RESULT_OK,intent);
               finish();
           }
       });
    }
}