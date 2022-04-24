package com.example.harjoitustyo;
//This is the backend for the activity_main, which is the login page

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    //Import views
    Button sign_in_button;
    Button register_button;
    EditText username;
    EditText password;
    //Create object in order to be able to send data from one view to another
    private DataTransverClass transfer = new DataTransverClass();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Spinner spinner1 = findViewById(R.id.spinnerListMoviesByRating);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.filters, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter1);
        spinner1.setOnItemSelectedListener(this);

        Spinner spinner2 = findViewById(R.id.spinnerShowCurrentMovies);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.movies, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);
        spinner2.setOnItemSelectedListener(this);

        //Initialize all buttons and edit text
        sign_in_button = findViewById(R.id.signIn_Button);
        register_button = findViewById(R.id.register_Button);
        username = findViewById(R.id.usernameID);
        password = findViewById(R.id.PasswordID);
        //Set listener, so that when button is pressed it goes to Main Menu
        //TODO: Needs separate method to check password and username, before sending to main menu
        sign_in_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                loadMainMenu();
            }
        });
        //Set listener, so that when button pressed goes to Register Page
        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadRegister();
            }
        });
    }

    public void loadMainMenu(){
        //Send object to second activity and wait for result from activity
        //TODO: Should be probably edited so that it doesn't wait for response from activity, as this is only the log in page
        //Set intent, which is current activity and to which activity we switch
        Intent intent = new Intent(MainActivity.this, MainMenu.class);
        //Checking functionality of data transfer object
        transfer.setText("Sending some random text from Main Activity!");
        //Put object in Extra
        intent.putExtra("object", transfer);
        //Start Main menu
        startActivityForResult(intent, 1);
    }
    //Same as above but for the Register Page
    public void loadRegister(){
        Intent intent = new Intent(MainActivity.this, RegisterPage.class);
        transfer.setText("Sending some random text from Main Activity!");
        intent.putExtra("object", transfer);
        startActivityForResult(intent, 4);
    }
    @Override
    //On results from Main Menu or Register collect the data that was sent
    //TODO: Maybe no need for the result from Main menu
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //From Main menu as request code is 1 for Main menu
        if (requestCode == 1) {
            //Check whether the returned code is what was expected
            if (resultCode == RESULT_OK) {
                //Collect the data transfer object
                transfer = (DataTransverClass) data.getSerializableExtra("object");
                System.out.println(transfer.getText());
            }
            //For register page, as request code was 4 for register page
        }else if(requestCode == 4){
            //Check result code
            if (resultCode == RESULT_OK) {
                //Collect data from object
                transfer = (DataTransverClass) data.getSerializableExtra("object");
                System.out.println(transfer.getText());
            }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
        String text = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}