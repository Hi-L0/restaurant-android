package com.example.myapplication;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SystemCapabilitiesActivity extends AppCompatActivity {

    private static final int REQUEST_CALL_PERMISSION = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_system_capabilities);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
        Intent intent = getIntent();
        String popup = intent.getStringExtra("message_popup");

        Toast.makeText(this,popup,Toast.LENGTH_SHORT).show();

        Button make_call = findViewById(R.id.call_button);
        EditText phone_number_input = findViewById(R.id.phone_number_input);

        //Call
        make_call.setOnClickListener(v -> {
            String phone_number=phone_number_input.getText().toString();
//            Intent call_intent = !phone_number.isEmpty() ? new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+ phone_number)):new Intent(Intent.ACTION_DIAL);
//            startActivity(call_intent);
            makePhoneCall(phone_number);
        });

        Button send_sms_btn = findViewById(R.id.send_sms);
        EditText sms_message_input= findViewById(R.id.sms_message_input);
        EditText sms_number_input = findViewById(R.id.sms_number_input);

        //SMS
        send_sms_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone_number=sms_number_input.getText().toString();
                String sms_message = sms_message_input.getText().toString();
                sendSMSIntent(sms_message,phone_number);
            }
        });

        Button send_email_btn= findViewById(R.id.send_email);
        //send Email
        send_email_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Define the recipient, subject, and body
                String recipient = "example@example.com";
                String subject = "Your Subject Here";
                String body = "This is the body of the email.";

                // Create the Intent for email
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                emailIntent.setData(Uri.parse("mailto:")); // Only email apps should handle this
                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{recipient});
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
                emailIntent.putExtra(Intent.EXTRA_TEXT, body);
//                emailIntent.setType("text/plain");
//                startActivity(Intent.createChooser(emailIntent,"Send mail ..."));
                // Verify if there’s an email app available to handle the intent
//                if (emailIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(emailIntent);
//                } else {
//                    Toast.makeText(SystemCapabilitiesActivity.this, "No email app found", Toast.LENGTH_SHORT).show();
//                }

            }
        });

        // Share Content
        Button shareButton = findViewById(R.id.share_button);
        shareButton.setOnClickListener(v -> {
            String textToShare = "This is the text I want to share!";
            // Optional: To share an image, you would include a URI to the image file
            // Uri imageUri = Uri.parse("file://path_to_image");

            // Create the intent and set the action to ACTION_SEND
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");  // For text only
            shareIntent.putExtra(Intent.EXTRA_TEXT, textToShare);

            // For sharing an image, set the type to "image/*" and include the URI
            // shareIntent.setType("image/*");
            // shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);

            // Start the chooser to allow the user to select an app to share with
            startActivity(Intent.createChooser(shareIntent, "Share content via"));
        });

        //location
        Button location_btn= findViewById(R.id.location_button);
        EditText location_input= findViewById(R.id.location_input);
        location_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mapIntent = new Intent(SystemCapabilitiesActivity.this, MapsActivity.class);
                if(!location_input.getText().toString().isEmpty()){
                    mapIntent.putExtra("location",location_input.getText().toString().trim());
                    startActivity(mapIntent);
                }else{
                    Toast.makeText(SystemCapabilitiesActivity.this,"fill the location field",Toast.LENGTH_SHORT).show();
                }
            }
        });


        //open in browser
        Button browser_btn = findViewById(R.id.browser_btn);
        EditText browser_search_input = findViewById(R.id.browser_input);

        browser_btn.setOnClickListener(v->{
            String search_query = browser_search_input.getText().toString();
            if(!search_query.isEmpty()){
                if (!search_query.startsWith("http://") && !search_query.startsWith("https://")) {
                    search_query = "https://google.com/search?q=" + search_query;
                }
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(search_query));
                startActivity(browserIntent);
            }else{
                Toast.makeText(SystemCapabilitiesActivity.this,"fill the browser field",Toast.LENGTH_SHORT).show();
            }
        });

    }


    //check permission for calls
    private void makePhoneCall(String phoneNumber) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
                == PackageManager.PERMISSION_GRANTED) {
            // Permission is already granted, make the call
            startCallIntent(phoneNumber);
        } else {
            // Request permission
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL_PERMISSION);

        }
    }

    private void startCallIntent(String phoneNumber) {
        Intent callIntent;
        if(!phoneNumber.isEmpty()){
            callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:" + phoneNumber));
        }else{
            callIntent = new Intent(Intent.ACTION_DIAL);
        }
        startActivity(callIntent);
    }

    //check permission before sending an SMS
    private void sendSMSIntent(String sms_message,String phoneNumber){
        Intent smsIntent;
        if(!sms_message.isEmpty() && !phoneNumber.isEmpty()){
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
                    == PackageManager.PERMISSION_GRANTED) {
                // Permission is already granted, make the call
//                sendSMSIntent(sms_message,phoneNumber);
                smsIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + phoneNumber));
                smsIntent.putExtra("sms_body",sms_message );
                startActivity(smsIntent);
            } else {
                // Request permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.SEND_SMS}, REQUEST_CALL_PERMISSION);
            }

        }else{
            Toast.makeText(this,"sms message or sms phone number must be filled",Toast.LENGTH_SHORT).show();
        }

    }
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
//                                           @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode == REQUEST_CALL_PERMISSION) {
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                // Permission granted, make the call
//                makePhoneCall("+1234567890");
//            } else {
//                // Permission denied; handle accordingly (e.g., show a message)
//            }
//        }
//    }
}