package comp4350.doctor_clientportal.presentation;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

import comp4350.doctor_clientportal.R;

public class NoteDisplayActivity extends AppCompatActivity {

    final Context CONTEXT = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_display);

        createGUI();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Add a Note", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                displayAddNotePage();
            }
        });
    }

    private void displayAddNotePage()
    {
        final Context context = this;

        Intent intent = new Intent(context, AddNoteActivity.class);
        Bundle b = new Bundle();
        b.putString("name", "Name");
        intent.putExtras(b);
        startActivity(intent);
    }

    private void createGUI() {
        LinearLayout list;
        ScrollView scrollView;

        final LayoutParams LAYOUT_PARAMETERS = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

        //TODO: Access all clients through API

        //start generating notes from this view
        scrollView = (ScrollView) findViewById(R.id.note_scroll);

        //list where we keep each note
        list = new LinearLayout(CONTEXT);
        list.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        list.setOrientation(LinearLayout.VERTICAL);

        //for all notes, do this
        for(int i=0; i<10; i++) {
            LinearLayout listItem = new LinearLayout(CONTEXT);
            listItem.setLayoutParams(LAYOUT_PARAMETERS);
            listItem.setOrientation(LinearLayout.VERTICAL);
            listItem.setPadding(6, 6, 6, 6);
            listItem.setClickable(true);//click to link to the note content

            TextView title = new TextView(CONTEXT);
            title.setTypeface(null, Typeface.BOLD);
            title.setBackgroundColor(getResources().getColor(R.color.white));
            title.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 50));
            title.setText("Fill with note title");
            listItem.addView(title);

            TextView body = new TextView(CONTEXT);
            body.setTypeface(null, Typeface.ITALIC);
            body.setPadding(0, 3, 0, 0);
            body.setBackgroundColor(getResources().getColor(R.color.white));
            body.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
            body.setText("Fill with note body. Fill with note body. Fill with note body. Fill with note body. Fill with note body. Fill with note body. Fill with note body. Fill with note body. ");
            listItem.addView(body);

            //add the view to the scroll view
            list.addView(listItem);
        }
        //end

        scrollView.addView(list);
    }
}