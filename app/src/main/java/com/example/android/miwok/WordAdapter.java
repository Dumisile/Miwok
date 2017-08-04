package com.example.android.miwok;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by DUMISILE on 2017/07/11.
 */

public class WordAdapter extends ArrayAdapter<Word> {
    //resources ID for the background colors for the list of words
    private int mColorResourcesId;

    public WordAdapter(Activity context, ArrayList<Word> words,int colorResourcesId){
        super(context,0, words);
        mColorResourcesId=colorResourcesId;
    }
    @Override
    public View getView(int position, View convertView,ViewGroup parent) {
      View listItemView =convertView;
        if (listItemView==null) {
            listItemView= LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item,parent,false);
        }
        //get the object located at this position in the list
        Word currentWord=getItem(position);
        //find the TextView in the list_item.xml layout with the ID version_name
        TextView miwokTextView=(TextView) listItemView.findViewById(R.id.miwok_text_view);
        miwokTextView.setText(currentWord.getMiwokTranslation());
        //find the TextView in the list_item.xml layout with the ID version_name
        TextView defaultTextView=(TextView) listItemView.findViewById(R.id.default_text_view);
        defaultTextView.setText(currentWord.getDefaultTranslation());


        ImageView image = (ImageView)listItemView.findViewById(R.id.image);
        if (currentWord.hasImage()){
            image.setImageResource(currentWord.getmImageResourcesId());
            image.setVisibility(View.VISIBLE);
        }
        else{
            image.setVisibility(View.GONE);
        }
        //set the theme color for the list item
        View textContainer=listItemView.findViewById(R.id.text_container);
        //find the color that the resource ID maps to
        int color= ContextCompat.getColor(getContext(),mColorResourcesId);
        textContainer.setBackgroundColor(color);
        //return the whole list item layout
        return listItemView;
    }
}
