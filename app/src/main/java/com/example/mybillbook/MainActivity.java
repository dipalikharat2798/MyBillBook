package com.example.mybillbook;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private DBHandler myDb;
    Button addBillBtn;
    Spinner spinner;
    TextView date,time,date1,time1,gallery;
    EditText billnumber,billname,billamount;
    RadioGroup radioGroup;
    String type;
    Uri contentUri;
    String category;
    String imageFileName;
    public static final int GALLERY_REQUEST_CODE = 105;
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        // view elements
        spinner = (Spinner) findViewById(R.id.spinner);
        date=findViewById(R.id.date);
        date1=findViewById(R.id.date1);
        time=findViewById(R.id.time);
        time1=findViewById(R.id.time1);
        gallery=findViewById(R.id.gallery);
        radioGroup= (RadioGroup) findViewById(R.id.radiogroup);
        billamount=findViewById(R.id.amountPaid);
        billname=findViewById(R.id.billName);
        billnumber=findViewById(R.id.billNumber);
        addBillBtn = findViewById(R.id.idBtnAddBill);

        myDb = new DBHandler(this);
        addBillBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // below line is to get data from all edit text fields.
                String billnum=billnumber.getText().toString();
                String billNAMe=billname.getText().toString();
                String billamt=billamount.getText().toString();
                String dateBill=date1.getText().toString();
                String timeBill=time1.getText().toString();
                String status=type;
                // validating if the text fields are empty or not.

                // on below line we are calling a method to add new
                // billto sqlite data and pass all our values to it.
                if (TextUtils.isEmpty(billnum)||TextUtils.isEmpty(billNAMe)||TextUtils.isEmpty(billamt)||TextUtils.isEmpty(dateBill)||
                        TextUtils.isEmpty(timeBill)||TextUtils.isEmpty(status)) {
                    Toast.makeText(MainActivity.this, "Please Enter all details", Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    myDb.addNewBill(category, billnum, billNAMe, dateBill, timeBill, Integer.parseInt(billamt), status, String.valueOf(contentUri));
                }

                // after adding the data we are displaying a toast message.
                Toast.makeText(MainActivity.this, "Bill has been added.", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(MainActivity.this, ViewActivity.class);
                startActivity(i);
                billnumber.setText("");
                billname.setText("");
                billamount.setText("");
                date1.setText("");
                time1.setText("");
            }
        });
        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(gallery, GALLERY_REQUEST_CODE);
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int id = radioGroup.getCheckedRadioButtonId();
                switch (id) {
                    case R.id.paid:
                        type = "Paid";
                        Toast.makeText(MainActivity.this, ""+type, Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.failed:
                        type = "failed";
                        Toast.makeText(MainActivity.this, ""+type, Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.pending:
                        type = "Pending";
                        Toast.makeText(MainActivity.this, ""+type, Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
            }
        });

        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleTimeButton();
            }
        });

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleDateButton();
            }
        });


        // Spinner click listener
        spinner.setOnItemSelectedListener(this);
        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("Business Services");
        categories.add("Computers");
        categories.add("Education");
        categories.add("Shoping");
        categories.add("Health");
        categories.add("Travel");
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                contentUri = data.getData();
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                imageFileName = "JPEG_" + timeStamp + "." + getFileExt(contentUri);
                Log.d("tag", "onActivityResult: Gallery Image Uri:  " + imageFileName);
            }

        }
    }

    private String getFileExt(Uri contentUri) {
        ContentResolver c = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(c.getType(contentUri));
    }


    private void handleDateButton() {
        Calendar calendar = Calendar.getInstance();
        int YEAR = calendar.get(Calendar.YEAR);
        int MONTH = calendar.get(Calendar.MONTH);
        int DATE = calendar.get(Calendar.DATE);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int date) {

                Calendar calendar1 = Calendar.getInstance();
                calendar1.set(Calendar.YEAR, year);
                calendar1.set(Calendar.MONTH, month);
                calendar1.set(Calendar.DATE, date);
                String dateText = DateFormat.format("EEEE, MMM d, yyyy", calendar1).toString();
                date1.setText(dateText);
            }
        }, YEAR, MONTH, DATE);

        datePickerDialog.show();

    }

    private void handleTimeButton() {
        Calendar calendar = Calendar.getInstance();
        int HOUR = calendar.get(Calendar.HOUR);
        int MINUTE = calendar.get(Calendar.MINUTE);
        boolean is24HourFormat = DateFormat.is24HourFormat(this);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                Log.i(TAG, "onTimeSet: " + hour + minute);
                Calendar calendar1 = Calendar.getInstance();
                calendar1.set(Calendar.HOUR, hour);
                calendar1.set(Calendar.MINUTE, minute);
                String dateText = DateFormat.format("h:mm a", calendar1).toString();
                time1.setText(dateText);
            }
        }, HOUR, MINUTE, is24HourFormat);

        timePickerDialog.show();

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();
        category=item;
        // Showing selected spinner item
        //   Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

}