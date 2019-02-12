package com.example.teach06;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<Integer> listNum;
    private ProgressBar mProgBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void create(View view) {
        final Context context = view.getContext();

        mProgBar = (ProgressBar) findViewById(R.id.determinateBar);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String filename = "numbers.txt";
                    String fileContents = "";
                    OutputStreamWriter outputStreamWriter;
                    outputStreamWriter = new OutputStreamWriter(context.openFileOutput(filename, Context.MODE_PRIVATE));

                    for (int i = 1; i <= 10; i++) {
                        fileContents = String.valueOf(i) + "\n";
                        outputStreamWriter.write(fileContents);
                        //Toast.makeText(this, fileContents, Toast.LENGTH_SHORT).show();
                        Thread.sleep(250);
                        mProgBar.setProgress(i * 10);

                    }
                    outputStreamWriter.close();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    public void load(View view) {
        final Context context = view.getContext();
        listNum = new ArrayList();
        mProgBar = (ProgressBar) findViewById(R.id.determinateBar);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    InputStream inputStream = context.openFileInput("numbers.txt");


                    if (inputStream != null) {
                        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                        String receiveString = "";

                        //Toast.makeText(this, "made it here", Toast.LENGTH_SHORT).show();

                        int counter = 1;
                        while ((receiveString = bufferedReader.readLine()) != null) {
                            listNum.add(Integer.valueOf(receiveString));
                            //Toast.makeText(context, receiveString, Toast.LENGTH_SHORT).show();
                            Thread.sleep(250);
                            mProgBar.setProgress(counter * 10);
                            counter++;

                        }

                        inputStream.close();
                    }
                }
                catch (FileNotFoundException e) {
                    Log.e("login activity", "File not found: " + e.toString());
                }
                catch (IOException e) {
                    Log.e("login activity", "Can not read file: " + e.toString());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }).start();
        Toast.makeText(this, "hello", Toast.LENGTH_SHORT).show();

        ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(this,
                android.R.layout.simple_list_item_1, listNum);
        ListView listView = (ListView) findViewById(R.id.listview);
        listView.setAdapter(adapter);

    }

       /* FileInputStream inputStream;
        int fContents;

        try {
            inputStream = openFileInput("numbers.txt");
            while (inputStream != null) {
                fContents = inputStream.read();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/
}
