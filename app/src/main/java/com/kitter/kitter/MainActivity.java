package com.kitter.kitter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import go.client.Client;

import static go.client.Client.NewKitterClient;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ListView listView = (ListView) findViewById(R.id.listView);
        final ArrayList<String> listItems = new ArrayList<String>();
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                listItems);
        listView.setAdapter(adapter);


        listView.post(new Runnable() {
              @Override
              public void run() {
                  listView.smoothScrollToPosition(0);
              }
          });

        Client.KitterClient client = NewKitterClient(getString(R.string.kitterurl));
        Client.KitterCallback callback = new Client.KitterCallback() {
            @Override
            public void NewMessage(final String s) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("Kitter", s);
                        listItems.add(0, s);
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        };

        client.ReadStream(callback);
        Log.d("Kitter", "Started App");
    }


}
