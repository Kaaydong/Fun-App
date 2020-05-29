package com.example.blinkingbuttongame;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AnimeListView extends AppCompatActivity {

    private ListView listView;
    private List<String> urlList;
    private List<String> nameList;
    private AnimeAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anime_list_view);

        Intent listIntent = getIntent();
        urlList = listIntent.getStringArrayListExtra("first_url_list");
        nameList = listIntent.getStringArrayListExtra("first_name_list");

        Log.e("STUFF HAPPENED", urlList.get(0));
        wireWidgets();

        adapter = new AnimeAdapter( nameList, urlList );
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                Intent targetIntent = new Intent(AnimeListView.this, WaifuSearcherActivty.class);
                targetIntent.putStringArrayListExtra("url_list", (ArrayList<String>)urlList);
                targetIntent.putStringArrayListExtra("name_List", (ArrayList<String>)nameList);
                targetIntent.putExtra("position", pos);
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
        List<String> name;
        List<String> url;

        public AnimeAdapter(List<String> name, List<String> url) {
            super(AnimeListView.this,-1, url);
            this.name = name;
            this.url = url;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            LayoutInflater inflater = getLayoutInflater();

            if (convertView == null) {
                convertView = inflater.inflate(R.layout.listview_row, parent, false);
            }
            Log.e("LOOK HERE", "INFLATER HAPPENED");

            TextView textView = convertView.findViewById(R.id.textView_row_rowThing);
            ImageView image = convertView.findViewById(R.id.imageView_row_picture);

            textView.setText(url.get(position));
            Picasso.get().load(url.get(position)).into(image);

            return convertView;
        }
    }
}
