package com.jun.jsouptutorial;

import android.content.Context;
import android.os.Bundle;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText respText;
    private ListView mListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText edtUrl = (EditText) findViewById(R.id.edtURL);
        Button btnGo = (Button) findViewById(R.id.btnGo);
//        respText = (EditText) findViewById(R.id.edtResp);
        mListView = (ListView) findViewById(R.id.recipe_list_view);
        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
                String siteUrl = edtUrl.getText().toString();
                ( new ParseURL() ).execute(new String[]{siteUrl});
            }
        });    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class ParseURL extends AsyncTask<String, Void, String[]> {
        private Elements mangaList;

        @Override
        protected String[] doInBackground(String... strings) {
            //StringBuffer buffer = new StringBuffer();
            String[] mangaItems = {};
            try {
                Log.d("JSwa", "Connecting to ["+strings[0]+"]");
                Document doc  = Jsoup.connect(strings[0]).get();
                Log.d("JSwa", "Connected to ["+strings[0]+"]");
                // Get document (HTML page) title
                String title = doc.title();
                Log.d("JSwA", "Title ["+title+"]");
//                buffer.append("Title: " + title + "\r\n");
//
//                // Get meta info
//                Elements metaElems = doc.select("meta");
//                buffer.append("META DATA\r\n");
//                for (Element metaElem : metaElems) {
//                    String name = metaElem.attr("name");
//                    String content = metaElem.attr("content");
//                    buffer.append("name ["+name+"] - content ["+content+"] \r\n");
//                }
//
//                Elements topicList = doc.select("h2.topic");
//                buffer.append("Topic list\r\n");
//                for (Element topic : topicList) {
//                    String data = topic.text();
//
//                    buffer.append("Data ["+data+"] \r\n");
//                }

                // Get popular manga
//                mangaList = doc.select("a.popularitemcaption");
//                mangaItems = new String[mangaList.size()];
//                for (int i = 0; i < mangaItems.length; i++) {
//                    mangaItems[i] = mangaList.get(i).text();
//                }

                mangaList = doc.select("a.chapters");
                mangaItems = new String[mangaList.size()];
                for (int i = 0; i < mangaItems.length; i++) {
                    mangaItems[i] = mangaList.get(i).text();
                }
//                int count = 1;
//
//                for (Element manga : mangaList) {
//                    String data = manga.text();
//                    if (count - 1 == 0) {
//                        System.out.println(data);
//                        count--;
//                    }
//                }

            }
            catch(Throwable t) {
                t.printStackTrace();
            }

            return mangaItems;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

//        @Override
//        protected void onPostExecute(String s) {
//            super.onPostExecute(s);
//            respText.setText(s);
//            String[] mangaItems = new String[mangaList.size()];
//            for (int i = 0; i < mangaItems.length; i++) {
//                mangaItems[i] = mangaList.get(i).text();
//            }
//            ArrayAdapter adapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, mangaItems);
//            mListView.setAdapter(adapter);
//        }


        protected void onPostExecute(String[] res) {
            //super.onPostExecute(res);
            //respText.setText(s);
//            String[] mangaItems = new String[mangaList.size()];
//            for (int i = 0; i < mangaItems.length; i++) {
//                mangaItems[i] = mangaList.get(i).text();
//            }
            ArrayAdapter adapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, res);
            mListView.setAdapter(adapter);
        }
    }

}
