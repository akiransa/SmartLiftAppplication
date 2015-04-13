package registration.smartliftapplication.com.smartlift;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.AuthenticationFailedException;
import javax.mail.MessagingException;
import javax.mail.Session;


/**
 * Created by anand.sade on 26-02-2015.
 */
public class ValidateRegistrationDetails extends Activity {
    //implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    /**
     * 1.Have to get details from the previous form
     * 2.Validate the contents - basic for now. SMS Verification needs to be added
     * 3.If contents are incorrect, prompt a dialog saying the same and the necessary correction required.
     * 4.Transfer Control to another class upon sucessful validation, so they can be entered into database.
     */

    protected static final String TAG = "Registration Page : ";
    private static String mypassword = "Letsdoit";
    private static String myusername = "smartliftapp@gmail.com";
    private final Pattern hasUppercase = Pattern.compile("[A-Z]");
    private final Pattern hasLowercase = Pattern.compile("[a-z]");
    private final Pattern hasNumber = Pattern.compile("\\d");
    private final Pattern hasSpecialChar = Pattern.compile("[^a-zA-Z0-9 ]");
    /**
     * Provides the entry point to Google Play services.
     */
    protected GoogleApiClient mGoogleApiClient;
    /**
     * Represents a geographical location.
     */
    protected Location mLastLocation;
    protected TextView mLatitudeText;
    protected TextView mLongitudeText;
    SQLiteDatabase db;
    private EditText emailId;
    private EditText mobileNo;
    private String mailhost = "smtp.gmail.com";
    private String user;
    private String password;
    private Session session;
    private String subject = "Email from Android";
    private String body = "<i>Greetings!</i><br>Hi ! This is test mail from Smart Lift Application.";
    private String senderAddress = "smartliftapp@gmail.com";
    private String receiverAddress = "sadeanandkiran@gmail.com";

    static String convertStreamToString(java.io.InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);

        // buildGoogleApiClient();


    }

    public void register(View button) {

        // System.err.println(GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext()));
        //buildGoogleApiClient();


//        Location mLastLocation1 = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
//        System.err.println(mLastLocation1.getProvider());
//        if(mLastLocation1 != null)
//        {
//
//            System.err.println(mLastLocation1.toString()+"Hi There got some location");
//        }
//        if (mLastLocation1 != null) {
//
//            System.err.println("Latitude"+String.valueOf(mLastLocation1.getLatitude()));
//
//            System.err.println("Longitude"+String.valueOf(mLastLocation1.getLongitude()));
////            mLatitudeText.setText(String.valueOf(mLastLocation.getLatitude()));
////            mLongitudeText.setText(String.valueOf(mLastLocation.getLongitude()));
//        } else {
//            Toast.makeText(this, R.string.no_location_detected, Toast.LENGTH_LONG).show();
//        }
        EditText userName = (EditText) findViewById(R.id.userNameValue);
        final EditText password = (EditText) findViewById(R.id.passwordValue);
        mobileNo = (EditText) findViewById(R.id.mobileNoValue);
        EditText org = (EditText) findViewById(R.id.orgValue);
        EditText timePicker = (EditText) findViewById(R.id.timePicker);
        EditText startLocation = (EditText) findViewById(R.id.proposedStartLocationVal);
        EditText endLocation = (EditText) findViewById(R.id.proposedEndLocVal);
        EditText vehicleNo = (EditText) findViewById(R.id.vehicleNoValue);
        emailId = (EditText) findViewById(R.id.emailIdValue);

        RadioGroup gender = (RadioGroup) findViewById(R.id.gender);
        //returns an inte
        // ger as to which radio button was clicked
        int selected = gender.getCheckedRadioButtonId();
        // gets a reference to our "Selected " button
        RadioButton genderClicked = (RadioButton) findViewById(selected);
        String genderwhat = genderClicked.getText().toString();

        final String passwordValue = password.getText().toString();
        boolean providerCheck = findViewById(R.id.isProvider).isEnabled();
        boolean consumerCheck = findViewById(R.id.isConsumer).isEnabled();
/*
        Context context;
        DatabaseHelper dbHelper;
        dbHelper = new DatabaseHelper(this,"",null,1);
        SQLiteDatabase db=dbHelper.getWritableDatabase();

        ContentValues contentValues= new ContentValues();
        */
        // These code snippets use an open-source library. http://unirest.io/java
        String message = "Hi - Message from SmartLift Application";
        String toPhoneNo = "8861208777";
        String pwd = "grace2583";
        String uid = "8861208777";

//        new SendSMS1().execute();

        final String mobNo = mobileNo.getText().toString();
        final String emailIdUser = emailId.getText().toString();
        String orga = org.getText().toString();
        String timePickerVal = timePicker.getText().toString();
        String startLocVal = startLocation.getText().toString();
        String endLocalVal = endLocation.getText().toString();
        String vehicleNoVal = vehicleNo.getText().toString();

        mobileNo.addTextChangedListener(new TextValidator(mobileNo) {
            @Override
            public void validate(TextView mobileNo, String mobNo) {
                if (mobNo == null || mobNo.length() == 0 || !(mobNo.length() == 10)) {
                    System.err.print(mobileNo);
                    mobileNo.setError(getString(R.string.MobNoError));
                }
            }
        });

        Button btnSubmit;
        btnSubmit = (Button) findViewById(R.id.submitRegistration);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                Validation class will check the error and display the error on respective fields
                but it won't resist the form submission, so we need to check again before submit
                 */
                if (checkValidation())
                    submitForm();
                else
                    Toast.makeText(ValidateRegistrationDetails.this, getString(R.string.FormErrorBeforeSubmit), Toast.LENGTH_LONG).show();
            }
        });







/*
        findViewById(R.id.submitRegistration).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                List<View> possibleTextViews = v.getFocusables(View.FOCUS_FORWARD);
                //return true;
                for (View possibleTextView : possibleTextViews) {
                    System.err.println("Possible Text View !! - " + possibleTextView.toString());


                    if (possibleTextView instanceof EditText) {
                        String error = ((EditText) possibleTextView).getError() == null ? null :
                                ((EditText) possibleTextView).getError().toString();
                        // boolean validated = StringUtils.isBlank(error);

                        //if (!validated) return false;
                    }
                }
            }
        });*/


//        findViewById(R.id.submitRegistration).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (!isValidEmail(emailIdUser) || (emailIdUser.length()==0)) {
//                    emailId.setError(getString(R.string.EmailIdError));
//                    emailId.requestFocus();
//                }
//                if (!isValidPassword(passwordValue) || (passwordValue.length()==0)) {
//                    password.setError(getString(R.string.PasswordError));
//                    password.requestFocus();
//                }
//                if (mobNo.length()==0 || mobNo.length()<10){
//                    System.err.print(mobileNo);
//                    mobileNo.setError(getString(R.string.MobNoError));
//                    mobileNo.requestFocus();
//                }
//
//
//                try {
//
//                    // Sending Email after registration - need to ensure prior to this that Validation is successful.
//                   new SendEmailAsyncTask().execute();
//                } catch (Exception e) {
//                    String msg = e.getMessage().toString();
//                }
//
//
//            }
//
//        });
    }

    private void submitForm() {
        // Submit your form here. your form is valid
        Toast.makeText(this, "Submitting form...", Toast.LENGTH_LONG).show();
    }

    private boolean checkValidation() {
        boolean ret = true;

        // if (!TextValidator.hasText(etNormalText)) ret = false;
        if (!TextValidator.isEmailAddress(emailId, true)) ret = false;
        if (!TextValidator.isPhoneNumber(mobileNo, false)) ret = false;

        return ret;
    }

    // validating email id
    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    //validating password
    private boolean isValidPassword(String password) {
        boolean isValid = true;
        Matcher hasUpperCase = hasUppercase.matcher(password);
        Matcher hasLowerCase = hasLowercase.matcher(password);
        Matcher hasNumber1 = hasNumber.matcher(password);
        Matcher hasSpecialChar1 = hasSpecialChar.matcher(password);

        return isValid && hasUpperCase.matches() && hasLowerCase.matches() && hasNumber1.matches() && hasSpecialChar1.matches();
    }

    private class SendSMS1 extends AsyncTask<Void, Void, String> {
        String response = null;

        protected String doInBackground(Void... params) {
            URL url = null;
            try {
                url = new URL("https://site2sms.p.mashape.com/index.php?msg=Welcome+to+SmartLift&phone=8861208777&pwd=grace2583&uid=8861208777");


            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            HttpURLConnection urlConnection = null;
            try {
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestProperty("X-Mashape-Key", "p3XaAQinTDmshBPYVDHF2EJgrZj1p1RcrrLjsnDrVCGxXDdQYd");
                urlConnection.setRequestProperty("Accept", "application/json");
                System.err.print("Connection parameters set!");
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                System.err.print("Connection response requested !");
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                System.err.print("Connection response got !");

                response = convertStreamToString(in);
                System.err.print(response);
            } catch (Exception e) {

                System.out.print(e.toString());
            } finally {
                urlConnection.disconnect();
            }

            return response;
        }
    }

    class SendEmailAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try {
                GmailSender m;
                m = new GmailSender("smartliftapp@gmail.com", "Letsdoit");
                m.sendMail(subject, body, senderAddress, receiverAddress);

            } catch (AuthenticationFailedException e) {
                Log.e(SendEmailAsyncTask.class.getName(), "Bad account details");
                e.printStackTrace();

            } catch (MessagingException e) {
                Log.e(SendEmailAsyncTask.class.getName(), "failed");
                e.printStackTrace();

            } catch (Exception e) {
                e.printStackTrace();

            }
            return null;
        }

    }

    /**
     * Builds a GoogleApiClient. Uses the addApi() method to request the LocationServices API.
     */
//
//    protected synchronized void buildGoogleApiClient() {
//        mGoogleApiClient = new GoogleApiClient.Builder(this)
//                .addConnectionCallbacks(this)
//                .addOnConnectionFailedListener(this)
//                .addApi(LocationServices.API)
//                .build();
//    }

//    @Override
//    protected void onStart() {
//        System.err.println("onStartMethod invoked");
//        super.onStart();
//        mGoogleApiClient.connect();
//        System.err.println("onStartMethod after connect");
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        if (mGoogleApiClient.isConnected()) {
//            mGoogleApiClient.disconnect();
//        }
//    }
//
//    /**
//     * Runs when a GoogleApiClient object successfully connects.
//     */
//    @Override
//    public void onConnected(Bundle connectionHint) {
//// Provides a simple way of getting a device's location and is well suited for
//// applications that do not require a fine-grained location and that do not need location
//// updates. Gets the best and most recent location currently available, which may be null
//// in rare cases when a location is not available.
//        System.err.println("OnConnected Method invoked");
//        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
//        if(mLastLocation != null)
//        {
//            System.err.println(mLastLocation.toString()+"Hi There got some location");
//        }
//        if (mLastLocation != null) {
//
//            System.err.println("Latitude"+String.valueOf(mLastLocation.getLatitude()));
//
//            System.err.println("Longitude"+String.valueOf(mLastLocation.getLongitude()));
////            mLatitudeText.setText(String.valueOf(mLastLocation.getLatitude()));
////            mLongitudeText.setText(String.valueOf(mLastLocation.getLongitude()));
//        } else {
//            Toast.makeText(this, R.string.no_location_detected, Toast.LENGTH_LONG).show();
//        }
//    }
//
//    @Override
//    public void onConnectionFailed(ConnectionResult result) {
//// Refer to the javadoc for ConnectionResult to see what error codes might be returned in
//// onConnectionFailed.
//        Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = " + result.getErrorCode());
//    }
//
//    @Override
//    public void onConnectionSuspended(int cause) {
//// The connection to Google Play services was lost for some reason. We call connect() to
//// attempt to re-establish the connection.
//        Log.i(TAG, "Connection suspended");
//        mGoogleApiClient.connect();
//    }

}


