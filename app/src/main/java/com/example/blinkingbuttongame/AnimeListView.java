package com.example.blinkingbuttongame;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class AnimeListView extends AppCompatActivity {

    private ListView listView;
    private List<String> urlList;
    private List<String> nameList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anime_list_view);

        Intent listIntent = getIntent();
        urlList = listIntent.getStringArrayListExtra("first_url_list");
        nameList = listIntent.getStringArrayListExtra("first_name_list");

        wireWidgets();

        AnimeAdapter adapter = new AnimeAdapter(this, 0, nameList ,urlList  );

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                Intent targetIntent = new Intent(AnimeListView.this, WaifuSearcherActivty.class);
                targetIntent.putStringArrayListExtra("url_list", (ArrayList<String>) urlList);
                targetIntent.putStringArrayListExtra("name_List", (ArrayList<String>) nameList);
                startActivity(targetIntent);
                finish();

            }
        });
    }

    public void wireWidgets()
    {
        listView = findViewById(R.id.listview_animelistview_listview);
    }

    private class AnimeAdapter extends ArrayAdapter<String>
    {
        Context context;
        List<String> name;
        List<String> url;

        public AnimeAdapter(@NonNull Context context, int resource, List<String> name, List<String> url) {
            super(context, resource);
            this.name = name;
            this.url = url;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            LayoutInflater layoutInflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = getLayoutInflater().inflate(R.layout.listview_row,parent,false);
            TextView textView = row.findViewById(R.id.textView_row_rowThing);

            textView.setText(name.get(position));

            return row;
        }
    }
}
