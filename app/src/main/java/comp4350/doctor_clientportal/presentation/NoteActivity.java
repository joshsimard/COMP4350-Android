package comp4350.doctor_clientportal.presentation;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import comp4350.doctor_clientportal.R;
import comp4350.doctor_clientportal.objects.Note;

public class NoteActivity extends AppCompatActivity {

    final Context CONTEXT = this;

    Note note;
    private Button button;
    private String search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        button = (Button)findViewById(R.id.search_button);
        //Todo: Get list of all games

        addListenerOnButton();

        //createGUI();
    }

    private void addListenerOnButton(){
        button = (Button)findViewById(R.id.search_button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //getselectedItem
                EditText searchField = (EditText) findViewById(R.id.search_field);
                search = searchField.getText().toString();
                displayNotePage(search);
            }
        });
    }

    private void displayNotePage(String name){
        final Context context = this;

        //Todo: use api to search a name with a given string
        note = new Note(0, "This is the title.", "This is the description.");
        //note = null;

        if(note != null)
        {
            Intent intent = new Intent(context, NoteDisplayActivity.class);
            Bundle b = new Bundle();
            b.putString("name", name);
            intent.putExtras(b);
            startActivity(intent);
        } else {
            Toast toast = Toast.makeText(this, "Not found.", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 800);
            toast.show();
        }
    }



//        <TextView
//        android:id="@+id/secondLine"
//        android:layout_width="fill_parent"
//        android:layout_height="26dip"
//        android:layout_alignParentBottom="true"
//        android:layout_alignParentRight="true"
//        android:layout_toRightOf="@id/icon"
//        android:ellipsize="marquee"
//        android:singleLine="true"
//        android:text="Description"
//        android:textSize="12sp" />
//
//        <TextView
//        android:id="@+id/firstLine"
//        android:layout_width="fill_parent"
//        android:layout_height="wrap_content"
//        android:layout_above="@id/secondLine"
//        android:layout_alignParentRight="true"
//        android:layout_alignParentTop="true"
//        android:layout_alignWithParentIfMissing="true"
//        android:layout_toRightOf="@id/icon"
//        android:gravity="center_vertical"
//        android:text="Example application"
//        android:textSize="16sp" />
//
//        </RelativeLayout>
}
