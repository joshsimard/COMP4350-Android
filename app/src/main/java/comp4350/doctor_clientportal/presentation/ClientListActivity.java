package comp4350.doctor_clientportal.presentation;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import comp4350.doctor_clientportal.R;

public class ClientListActivity extends AppCompatActivity {

    final Context CONTEXT = this;
    final ViewGroup.LayoutParams LAYOUT_PARAMETERS = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);

    private ScrollView scrollView;
    private LinearLayout content;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_client_list);

        content = (LinearLayout)findViewById(R.id.content);

        //scrollView = (ScrollView)findViewById(R.id.scroll);

        //createGUI();
        content = createLayout(content);
    }

    private void createGUI()
    {
        LinearLayout listLayout = new LinearLayout(CONTEXT);

        listLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        listLayout.setOrientation(LinearLayout.VERTICAL);
        listLayout.setGravity(Gravity.CENTER_HORIZONTAL);
        listLayout.setPadding(0, 50, 0, 0);

        listLayout = createLayout(listLayout);

        //listLayout.setId(listLayoutID);

        scrollView.addView(listLayout);
        //setContentView(table);
    }

    public LinearLayout createLayout(LinearLayout listLayout)
    {
        ScrollView content;
        listLayout.removeAllViews();
        listLayout.addView(createTitleView());
        listLayout.addView(createHeaderLayout());
        if(true /*Todo: replace with checking if there are clients to show*/)
        {
            listLayout = createClientLayout(listLayout);
        }
        else
        {
            listLayout.addView(createEmptyLayout());
        }
        listLayout.addView(createPaddingLayout());

        return listLayout;
    }

    public TextView createTitleView()
    {
        TextView titleText = new TextView(CONTEXT);

        titleText.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        titleText.setPadding(0, 0, 0, 50);
        titleText.setGravity(Gravity.CENTER);
        titleText.setTextSize(24);
        titleText.setText("My Client List:");

        return titleText;
    }

    public LinearLayout createHeaderLayout()
    {
        //header if needed
        LinearLayout headerLayout = new LinearLayout(CONTEXT);
        TextView productHeaderText = new TextView(CONTEXT);
        TextView quantityHeaderText = new TextView(CONTEXT);
        TextView priceHeaderText = new TextView(CONTEXT);

        headerLayout.setLayoutParams(new ViewGroup.LayoutParams(1000, ViewGroup.LayoutParams.WRAP_CONTENT));
        headerLayout.setOrientation(LinearLayout.HORIZONTAL);
        headerLayout.setBackgroundColor(0xFF848484);

        productHeaderText.setLayoutParams(new ViewGroup.LayoutParams(480, ViewGroup.LayoutParams.WRAP_CONTENT));
        productHeaderText.setPadding(20, 0, 0, 0);
        productHeaderText.setTextColor(0xFF000000);
        productHeaderText.setTextSize(18);
        productHeaderText.setText("Name");
        headerLayout.addView(productHeaderText);

        quantityHeaderText.setLayoutParams(new ViewGroup.LayoutParams(260, ViewGroup.LayoutParams.WRAP_CONTENT));
        quantityHeaderText.setTextColor(0xFF000000);
        quantityHeaderText.setTextSize(18);
        quantityHeaderText.setText("Email");
        headerLayout.addView(quantityHeaderText);

        priceHeaderText.setLayoutParams(new ViewGroup.LayoutParams(260, ViewGroup.LayoutParams.WRAP_CONTENT));
        priceHeaderText.setTextColor(0xFF000000);
        priceHeaderText.setTextSize(18);
        priceHeaderText.setText("ID");
        headerLayout.addView(priceHeaderText);

        return headerLayout;
    }

    public LinearLayout createEmptyLayout()
    {
        LinearLayout emptyLayout = new LinearLayout(CONTEXT);
        emptyLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        emptyLayout.setOrientation(LinearLayout.HORIZONTAL);
        emptyLayout.setGravity(Gravity.CENTER);
        emptyLayout.setPadding(0, 30, 0, 30);
        emptyLayout.setBackgroundColor(0x00343434);

        TextView emptyText = new TextView(CONTEXT);
        emptyText.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        emptyText.setGravity(Gravity.CENTER);
        emptyText.setTextSize(18);
        emptyText.setText("No Clients were found!");
        emptyLayout.addView(emptyText);

        return emptyLayout;
    }

    public LinearLayout createClientLayout(LinearLayout listLayout)
    {
        /* this will be changed when api is figured out
            Will be a loop that makes each linearlayout
            horizontal with a listener set up for clicks
            (to go to the client info page)
         */

        final ViewGroup.LayoutParams LAYOUT_PARAMETERS = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        //TODO: Access all clients through API

        ScrollView scrollView = new ScrollView(CONTEXT);
        scrollView.setLayoutParams(LAYOUT_PARAMETERS);
        scrollView.setBackgroundColor(getResources().getColor(R.color.black));

        LinearLayout linear = new LinearLayout(CONTEXT);
        linear.setLayoutParams(LAYOUT_PARAMETERS);
        linear.setOrientation(LinearLayout.VERTICAL);

        //for all notes, do this
        for(int i=0; i<30; i++) {
            LinearLayout listItem = new LinearLayout(CONTEXT);
            listItem.setLayoutParams(LAYOUT_PARAMETERS);
            listItem.setOrientation(LinearLayout.HORIZONTAL);
            listItem.setPadding(6, 6, 6, 6);
            listItem.setClickable(true);//click to link to the note content

            TextView name = new TextView(CONTEXT);
            name.setTypeface(null, Typeface.BOLD);
            name.setTextSize(18);
            name.setBackgroundColor(getResources().getColor(R.color.white));
            name.setLayoutParams(new ViewGroup.LayoutParams(400, 60));
            name.setText("Patient " + (int) (i + Math.floor(Math.random() * 100)) + "");
            listItem.addView(name);

            TextView email = new TextView(CONTEXT);
            email.setTypeface(null, Typeface.ITALIC);
            email.setBackgroundColor(getResources().getColor(R.color.white));
            email.setLayoutParams(new ViewGroup.LayoutParams(350, 60));
            email.setTextSize(18);
            email.setText("email@email.com");
            listItem.addView(email);

            TextView id = new TextView(CONTEXT);
            id.setTypeface(null, Typeface.ITALIC);
            id.setBackgroundColor(getResources().getColor(R.color.white));
            id.setLayoutParams(new ViewGroup.LayoutParams(350, 60));
            id.setTextSize(18);
            id.setText(i+"");
            listItem.addView(id);

            //add the view to the scroll view
            linear.addView(listItem);
        }
        //end

        scrollView.addView(linear);
        listLayout.addView(scrollView);

        return listLayout;
    }

    public LinearLayout createPaddingLayout()
    {
        LinearLayout newPaddingLayout = new LinearLayout(CONTEXT);
        newPaddingLayout.setPadding(0, 0, 0, 100);

        return newPaddingLayout;
    }

}
