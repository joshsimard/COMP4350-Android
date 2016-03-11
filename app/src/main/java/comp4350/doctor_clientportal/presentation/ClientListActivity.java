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

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_client_list);

        scrollView = (ScrollView)findViewById(R.id.scroll);

        createGUI();
    }

    private void createGUI()
    {
        LinearLayout listLayout = new LinearLayout(CONTEXT);

        listLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        listLayout.setOrientation(LinearLayout.VERTICAL);
        listLayout.setGravity(Gravity.CENTER_HORIZONTAL);
        listLayout.setPadding(0, 50, 0, 0);

        listLayout = createCart(listLayout);

        //listLayout.setId(listLayoutID);

        scrollView.addView(listLayout);
        //setContentView(table);
    }

    public LinearLayout createCart(LinearLayout listLayout)
    {
        listLayout.removeAllViews();
        listLayout.addView(createTitleView());
        listLayout.addView(createHeaderLayout());
        if(true /*replace with checking if there are clients to show*/)
        {
            listLayout=createClientLayout(listLayout);
        }
        else
        {
            listLayout.addView(createEmptyLayout());
        }
        //listLayout.addView(createSubtotalLayout());
        //listLayout.addView(createCheckoutLayout());
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

        TableLayout table = new TableLayout(this);

        table.setStretchAllColumns(true);
        table.setShrinkAllColumns(true);

        TableRow rowTitle = new TableRow(this);
        rowTitle.setGravity(Gravity.CENTER_HORIZONTAL);

        TableRow rowDayLabels = new TableRow(this);
        TableRow rowHighs = new TableRow(this);
        TableRow rowLows = new TableRow(this);
        TableRow rowConditions = new TableRow(this);
        rowConditions.setGravity(Gravity.CENTER);

        TextView empty = new TextView(this);

        // title column/row
        TextView title = new TextView(this);
        title.setText("Java Weather Table");

        title.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);
        title.setGravity(Gravity.CENTER);
        title.setTypeface(Typeface.SERIF, Typeface.BOLD);

        TableRow.LayoutParams params = new TableRow.LayoutParams();
        params.span = 6;

        rowTitle.addView(title, params);

        // labels column
        TextView highsLabel = new TextView(this);
        highsLabel.setText("Day High");
        highsLabel.setTypeface(Typeface.DEFAULT_BOLD);

        TextView lowsLabel = new TextView(this);
        lowsLabel.setText("Day Low");
        lowsLabel.setTypeface(Typeface.DEFAULT_BOLD);

        TextView conditionsLabel = new TextView(this);
        conditionsLabel.setText("Conditions");
        conditionsLabel.setTypeface(Typeface.DEFAULT_BOLD);

        rowDayLabels.addView(empty);
        rowHighs.addView(highsLabel);
        rowLows.addView(lowsLabel);
        rowConditions.addView(conditionsLabel);

        // day 1 column
        TextView day1Label = new TextView(this);
        day1Label.setText("Feb 7");
        day1Label.setTypeface(Typeface.SERIF, Typeface.BOLD);

        TextView day1High = new TextView(this);
        day1High.setText("28°F");
        day1High.setGravity(Gravity.CENTER_HORIZONTAL);

        TextView day1Low = new TextView(this);
        day1Low.setText("15°F");
        day1Low.setGravity(Gravity.CENTER_HORIZONTAL);

        rowDayLabels.addView(day1Label);
        rowHighs.addView(day1High);
        rowLows.addView(day1Low);

        // day2 column
        TextView day2Label = new TextView(this);
        day2Label.setText("Feb 8");
        day2Label.setTypeface(Typeface.SERIF, Typeface.BOLD);

        TextView day2High = new TextView(this);
        day2High.setText("26°F");
        day2High.setGravity(Gravity.CENTER_HORIZONTAL);

        TextView day2Low = new TextView(this);
        day2Low.setText("14°F");
        day2Low.setGravity(Gravity.CENTER_HORIZONTAL);

        rowDayLabels.addView(day2Label);
        rowHighs.addView(day2High);
        rowLows.addView(day2Low);

        // day3 column
        TextView day3Label = new TextView(this);
        day3Label.setText("Feb 9");
        day3Label.setTypeface(Typeface.SERIF, Typeface.BOLD);

        TextView day3High = new TextView(this);
        day3High.setText("23°F");
        day3High.setGravity(Gravity.CENTER_HORIZONTAL);

        TextView day3Low = new TextView(this);
        day3Low.setText("3°F");
        day3Low.setGravity(Gravity.CENTER_HORIZONTAL);

        rowDayLabels.addView(day3Label);
        rowHighs.addView(day3High);
        rowLows.addView(day3Low);

        // day4 column
        TextView day4Label = new TextView(this);
        day4Label.setText("Feb 10");
        day4Label.setTypeface(Typeface.SERIF, Typeface.BOLD);

        TextView day4High = new TextView(this);
        day4High.setText("17°F");
        day4High.setGravity(Gravity.CENTER_HORIZONTAL);

        TextView day4Low = new TextView(this);
        day4Low.setText("5°F");
        day4Low.setGravity(Gravity.CENTER_HORIZONTAL);

        rowDayLabels.addView(day4Label);
        rowHighs.addView(day4High);
        rowLows.addView(day4Low);

        // day5 column
        TextView day5Label = new TextView(this);
        day5Label.setText("Feb 11");
        day5Label.setTypeface(Typeface.SERIF, Typeface.BOLD);

        TextView day5High = new TextView(this);
        day5High.setText("19°F");
        day5High.setGravity(Gravity.CENTER_HORIZONTAL);

        TextView day5Low = new TextView(this);
        day5Low.setText("6°F");
        day5Low.setGravity(Gravity.CENTER_HORIZONTAL);

        rowDayLabels.addView(day5Label);
        rowHighs.addView(day5High);
        rowLows.addView(day5Low);

        table.addView(rowTitle);
        table.addView(rowDayLabels);
        table.addView(rowHighs);
        table.addView(rowLows);
        table.addView(rowConditions);

        listLayout.addView(table);

        return listLayout;
    }

    public LinearLayout createPaddingLayout()
    {
        LinearLayout newPaddingLayout = new LinearLayout(CONTEXT);
        newPaddingLayout.setPadding(0, 0, 0, 100);

        return newPaddingLayout;
    }

}
