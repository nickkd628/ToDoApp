package com.example.todo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

     public static final String key_Item_text = "item_text";
     public static final String key_Item_position = "item_position";
     public static final int Edit_text_code = 20;
    List<String> items;

    Button btnadd;
    EditText etItem;
    RecyclerView rvitem;
    ItemsAdapter itemsAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnadd = findViewById(R.id.btnAdd);
        etItem = findViewById(R.id.etItem);
        rvitem = findViewById(R.id.rvitems);

        loadItems();


        ItemsAdapter.OnLongClickListener onLongClickListener = new ItemsAdapter.OnLongClickListener(){
            @Override
            public void onItemLongClicked(int position) {
                //delete the item from the model
                items.remove(position);
                //notify the adapter
                itemsAdapter.notifyItemRemoved(position);
                Toast.makeText(getApplicationContext(),"item was removed", Toast.LENGTH_LONG).show();
                saveItems();
            }
        };
        ItemsAdapter.OnClickListener onClickListener = new ItemsAdapter.OnClickListener() {
            @Override
            public void onItemClicked(int position) {
                Log.d("MainActivity", "Single click at "+position);
                //create the new activity
                Intent i = new Intent(MainActivity.this, EditActivity.class);
                //pass the data being edited
                i.putExtra(key_Item_text,items.get(position));
                i.putExtra(key_Item_position,position);
                //display the activity
                startActivityForResult(i,Edit_text_code);
            }
        };

         itemsAdapter = new ItemsAdapter(items, onLongClickListener,onClickListener);
        rvitem.setAdapter(itemsAdapter);
        rvitem.setLayoutManager(new LinearLayoutManager(this));

        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              String todoItem =  etItem.getText().toString();
              items.add(todoItem);
              itemsAdapter.notifyItemInserted(items.size()-1);
              etItem.setText("");
                Toast.makeText(getApplicationContext(),"item was added", Toast.LENGTH_LONG).show();
                saveItems();
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //need this superclass or else code does Not run like in video
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_OK && requestCode == Edit_text_code) {
            // get the updated text
            String itemText = data.getStringExtra(key_Item_text);
            //get the original position of the edited item
            int position = data.getExtras().getInt(key_Item_position);
            // update the model at the right position with the new text
            items.set(position, itemText);

            itemsAdapter.notifyItemChanged(position);
            //persist the changes
            saveItems();
            Toast.makeText(getApplicationContext(), "item updated successfully", Toast.LENGTH_SHORT).show();
        } else {
            Log.w("MainActivity", "unknown call to onActivityResult");
        }
    }

    private File getDataFile(){
        return  new File(getFilesDir(), "data.txt");
    }
    //this function will load items by reading every line of the data file
    private void loadItems() {
        try {
            items = new ArrayList<>(FileUtils.readLines(getDataFile(), Charset.defaultCharset()));
        } catch (IOException e) {
            Log.e("MainActivity", "Error reading items",e);
                items = new ArrayList<>();
        }
    }
    // this function saves items by writing them into the data file
    private  void saveItems(){
        try {
            FileUtils.writeLines(getDataFile(),items);
        } catch (IOException e) {
            Log.e("MainActivity", "Error writing items",e);

        }
    }
}