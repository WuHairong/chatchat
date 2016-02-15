package com.example.hairongwu.chatchat;

import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * Created by hairongwu on 2/14/16.
 */
public class MyAdapter extends ArrayAdapter<ListElement> {
    int resource;
    Context context;

    public MyAdapter(Context _context, int _resource, List<ListElement> items) {
        super(_context, _resource, items);
        resource = _resource;
        context = _context;
        this.context= _context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout newView;

        ListElement w = getItem(position);

        // Inflate a new view if necessary.
        if (convertView == null) {
            newView = new LinearLayout(getContext());
            String inflater = Context.LAYOUT_INFLATER_SERVICE;
            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(inflater);
            vi.inflate(resource, newView, true);
        } else {
            newView = (LinearLayout) convertView;
        }

        // Fills in the view.
        TextView tv = (TextView) newView.findViewById(R.id.itemText);
        Button b = (Button) newView.findViewById(R.id.itemButton);
        tv.setText(w.message);
        b.setText(w.nickname);

        return newView;
    }
}
