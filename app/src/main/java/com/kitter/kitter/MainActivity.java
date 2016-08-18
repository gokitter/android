package com.kitter.kitter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import go.client.Client;

import static go.client.Client.NewKitterClient;


public class MainActivity extends AppCompatActivity implements Client.KitterCallback {

    ArrayList<String> listItems=new ArrayList<String>();
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);

        listView.post(new Runnable() {
              @Override
              public void run() {
                  listView.smoothScrollToPosition(0);
              }
          });

        Client.KitterClient client = NewKitterClient(getString(R.string.kitterurl));
        client.ReadStream(this);

    }

    @Override
    public void NewMessage(String s) {
        Log.d("Kitter", s);
        listItems.add(0, s);
        adapter.notifyDataSetChanged();
    }
}
