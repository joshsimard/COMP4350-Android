package comp4350.doctor_clientportal.presentation;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import biz.kasual.materialnumberpicker.MaterialNumberPicker;
import comp4350.doctor_clientportal.R;

public class OrderMedsActivity extends AppCompatActivity {

    MaterialNumberPicker numberPicker;
    View dialogView;
    TextView name_text_view;
    TextView quantity_text_view;
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_meds);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initt();
        addActionListener();
    }

    private void initt()
    {
        dialogView = getLayoutInflater().inflate(R.layout.dialog_view, null);
        numberPicker = (MaterialNumberPicker)dialogView.findViewById(R.id.number_picker);
        name_text_view = (TextView)findViewById(R.id.med_name_edit);
        quantity_text_view = (TextView)findViewById(R.id.quantity_edit);
        submit = (Button)findViewById(R.id.med_submit);
    }

    private void addActionListener()
    {
        quantity_text_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int[] value = {0};
//                new AlertDialog.Builder(OrderMedsActivity.this)
//                        .setTitle("Hello")
//                        .removeView(dialogView)
//                        .setView(dialogView)
//                        .setPositiveButton(getString(android.R.string.ok), new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                value[0] = numberPicker.getValue();
//                                //Toast.makeText(OrderMedsActivity.this, "You Picked: " + numberPicker.getValue(), Toast.LENGTH_LONG).show();
//                            }
//                        })
//                                .show();
//
//                if(value[0] != 0)
//                    quantity_text_view.setText(value[0]);
//                Toast.makeText(OrderMedsActivity.this, "You Picked: " + numberPicker.getValue(), Toast.LENGTH_LONG).show();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(attemptSave())
                {
                    //do stuff here
                    String name = name_text_view.getText().toString();
                    String quantity = quantity_text_view.getText().toString();
                    Toast.makeText(OrderMedsActivity.this, name+" "+quantity, Toast.LENGTH_LONG).show();

                    //finish() ->when  saved properly
                }
            }
        });


    }

    private boolean attemptSave()
    {
        boolean attempt = true;
        if (name_text_view.getText().toString().isEmpty())
        {
            name_text_view.setError(getString(R.string.error_field_required));
            attempt = false;
        }
        if (quantity_text_view.getText().toString().isEmpty())
        {
            quantity_text_view.setError(getString(R.string.error_field_required));
            attempt = false;
        }

        return attempt;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
