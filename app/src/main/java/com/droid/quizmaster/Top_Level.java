package com.droid.quizmaster;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import android.view.Menu;
import android.widget.Toast;

public class Top_Level extends AppCompatActivity {

    private ProgressDialog progress;

    private QuizFile[] quizFiles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top__level);

        Field[] rawFiles  = R.raw.class.getFields();
        quizFiles = new QuizFile[rawFiles.length];
        for(int iFileIndex = 0; iFileIndex < rawFiles.length;iFileIndex++) {
            Field field = rawFiles[iFileIndex];
            try {
                quizFiles[iFileIndex] = new QuizFile(field.getName(),
                        field.getInt(field));
            } catch (IllegalAccessException ex) {
                Toast toast = Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT);
                toast.show();
            }
        }

        ArrayAdapter<QuizFile> listAdapter = new ArrayAdapter<QuizFile>(this,
                android.R.layout.simple_list_item_1,
                quizFiles);

        ListView listView = (ListView) findViewById(R.id.list_subject_options);
        listView.setAdapter(listAdapter);

        AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> listView, View itemView, int Position,
                                    long id) {
                //new GetQuestions(Top_Level.this).execute();

                Resources resources = getResources();
                String quizFromJson;
                QuizFile objQuiz = (QuizFile) listView.getItemAtPosition(Position);

                InputStream is = null;
                try{
                    is = resources.openRawResource(objQuiz.fileId);
                    BufferedReader reader = new BufferedReader((new InputStreamReader(is)));
                    StringBuilder builder = new StringBuilder();
                    for (String line = null; (line = reader.readLine()) != null;)
                    {
                        builder.append(line).append("\n");
                    }
                    quizFromJson = builder.toString();
                    callQuestionTemplate(quizFromJson);
                }
                catch(IOException ex) {
                    ex.printStackTrace();
                    Log.e("ReadRawResourceFile", ex.getMessage(), ex);
                }
                finally {
                    if(is != null){
                        try {
                            is.close();
                        }
                        catch (IOException ex) {}
                    }
                }
            }
        };

        listView.setOnItemClickListener(itemClickListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        //Inflate the menu; this adds items to the action bar if it is present...
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_download:
                Toast toast = Toast.makeText(this, "Download in progress", Toast.LENGTH_SHORT);
                toast.setMargin(50, 50);
                toast.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void callQuestionTemplate(String paramQuestion){
        Intent intent = new Intent(Top_Level.this, QuizTemplate.class);

        SharedPreferences shardPref = getApplicationContext().getSharedPreferences("Quiz", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = shardPref.edit();
        editor.putString( getString(R.string.QuizQuestions) , paramQuestion);
        editor.apply();
        startActivity(intent);
    }

    private class QuizFile
    {
        private String quizfileName;
        private int fileId;

        public  QuizFile(String paramFileName, int paramFileId){
            this.quizfileName = paramFileName;
            this.fileId = paramFileId;
        }

        public String toString()
        {
            return this.quizfileName;
        }
    }

    private class GetQuestions extends AsyncTask<String, Void, String> {

        private final Context context;

        private GetQuestions(Context paramContext){
            context = paramContext;
        }

        @Override
        protected void onPreExecute() {
            progress = new ProgressDialog(this.context);
            progress.setMessage("Fetching questions...");
            progress.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            try {

                Log.d("URL_INITIALIZE", "Initializing URL");
                URL url = new URL("http://192.168.1.5:4000/listQuestions?fileName=English_ Grammar_General");

                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("USER-AGENT", "Mozilla/5.0");
                connection.setRequestProperty("ACCEPT-LANGUAGE", "en-US,en;0.5");

                Log.d("CONN_BEFORE_OPEN", "Before opening connection");
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                Log.d("CONN_OPEN", "after opening connection");
                String line;
                final StringBuilder responseOutput = new StringBuilder();

                while ((line = br.readLine()) != null) {
                    responseOutput.append(line);
                }
                br.close();

                return responseOutput.toString();

            }catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            finally{
                progress.dismiss();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            callQuestionTemplate(s);
        }
    }

    private class DownloadQuestion extends AsyncTask<String, Void, Boolean>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(String... strings) {
            return null;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
        }
    }
}
