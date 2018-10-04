package com.mayank.starwars;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import static com.mayank.starwars.MainActivity.characters;
import static com.mayank.starwars.MainActivity.nextUrl;

public class Adapter extends BaseAdapter {
    Context context;
    Adapter(Context context){
        this.context = context;
    }
    @Override
    public int getCount() {
        if(nextUrl == null || nextUrl.equals("null"))
            return characters.size();
        else
            return characters.size() + 1;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        TextView textView;
//            implementing viewholder pattern - in case we need to go for more complicated list item, this would save resources
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.list_item, viewGroup, false);
            textView = view.findViewById(R.id.name);
            view.setTag(textView);
        } else
            textView = (TextView) view.getTag();
        if(i == characters.size()) {
            textView.setText(R.string.load_more);
        } else
            textView.setText(characters.get(i).Name);
        return view;
    }
}
