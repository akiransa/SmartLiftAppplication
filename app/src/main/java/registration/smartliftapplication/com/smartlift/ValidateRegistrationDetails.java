package registration.smartliftapplication.com.smartlift;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

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

    /**
     * 1.Have to get details from the previous form
     * 2.Validate the contents - basic for now. SMS Verification needs to be added
     * 3.If contents are incorrect, prompt a dialog saying the same and the necessary correction required.
     * 4.Transfer Control to another class upon sucessful validation, so they can be entered into database.
     */

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);

    }
    protected  static final String TAG = "Registration Page : ";
    private static String mypassword="Cla551c1";
    private static String myusername="sadeanandkiran@gmail.com";
    private EditText emailId;
    SQLiteDatabase db;

    private String mailhost = "smtp.gmail.com";
    private String user;
    private String password;
    private Session session;

    private String subject = "Email from Android";
    private String body     = "Hi ! This is test mail from Smart Lift Application.";
    private String senderAddress ="sadeanandkiran@gmail.com";
    private String receiverAddress= "anandkiransade@gmail.com";

    public void register(View button ) throws UnirestException {
        EditText userName = (EditText)findViewById(R.id.userNameValue);
        final EditText password = (EditText)findViewById(R.id.passwordValue);
        EditText mobileNo = (EditText)findViewById(R.id.mobileNoValue);
        EditText org = (EditText)findViewById(R.id.orgValue);
        EditText timePicker = (EditText)findViewById(R.id.timePicker);
        EditText startLocation = (EditText)findViewById(R.id.proposedStartLocationVal);
        EditText endLocation = (EditText)findViewById(R.id.proposedEndLocVal);
        EditText vehicleNo  = (EditText)findViewById(R.id.vehicleNoValue);
        emailId = (EditText)findViewById(R.id.emailIdValue);

        RadioGroup gender = (RadioGroup) findViewById(R.id.gender);
        //returns an integer as to which radio button was clicked
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
        String uid="8861208777";
        try{

            ClassLoader classLoader = ValidateRegistrationDetails.class.getClassLoader();
            URL resource = classLoader.getResource("org.apache.http.message.BasicLineFormatter.class");
            System.out.println(resource);
 /*

            HttpResponse<String> request1= Unirest.get("http://alerts.sinfini.com/api/v3/index.php?method=sms&api_key=A5e9975b46xxxxxxxxxxxxxxxxxxxxxxxxx&to=8861208777&sender=8861208777&message=testing&format=json&custom=1,2&flash=0")
                    .asString();

            String stattxt  = request1.getStatusText();
            HttpResponse<JsonNode> request2= Unirest.get("http://alerts.sinfini.com/api/v3/index.php?method=sms&api_key=A5e9975b46xxxxxxxxxxxxxxxxxxxxxxxxx&to=8861208777&sender=8861208777&message=testing&format=json&custom=1,2&flash=0")
                    .asJson();
            String statusTxt=request1.getStatusText();*/

            HttpResponse<JsonNode> request = Unirest.get("https://site2sms.p.mashape.com/index.php?msg=hi+how+r+u-+from+site&phone=8861208777&pwd=grace2583&uid=8861208777")
                    .header("X-Mashape-Key", "p3XaAQinTDmshBPYVDHF2EJgrZj1p1RcrrLjsnDrVCGxXDdQYd")
                    .header("Accept", "application/json")
                    .asJson();
            String statusTxt1=request.getStatusText();
        }
        catch(Exception e)
        {
            String msg=e.getMessage();
            e.printStackTrace();
        }
        //new SendSMS().execute();






        Log.i(TAG, genderwhat);


        String mobNo = mobileNo.getText().toString();
        final String emailIdUser = emailId.getText().toString();
        String orga = org.getText().toString();
        String timePickerVal = timePicker.getText().toString();
        String startLocVal = startLocation.getText().toString();
        String endLocalVal = endLocation.getText().toString();
        String vehicleNoVal = vehicleNo.getText().toString();



        findViewById(R.id.submitRegistration).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isValidEmail(emailIdUser)){
                    emailId.setError("Please enter valid email");
                }
                if(!isValidPassword(passwordValue)){
                    password.setError("Password should contain at-least 1 capital,1 small , 1 number and 1 special character");
                }

                try {

                    // Sending Email after registration - need to ensure prior to this that Validation is successful.
                    new SendEmailAsyncTask().execute();
                }
                catch (Exception e)
                {
                    String msg = e.getMessage().toString();
                }


            }

        });
    }

       private class SendSMS extends AsyncTask<Void, Void, HttpResponse<JsonNode>> {

        protected HttpResponse<JsonNode> doInBackground(Void... params) {

            HttpResponse<JsonNode> request = null;
            try {
                // These code snippets use an open-source library.

                request = Unirest.get("https://site2sms.p.mashape.com/index.php?msg=hi+how+r+u-+from+site&phone=8861208777&pwd=grace2583&uid=8861208777")
                        .header("X-Mashape-Key", "p3XaAQinTDmshBPYVDHF2EJgrZj1p1RcrrLjsnDrVCGxXDdQYd")
                        .header("Accept", "application/json")
                        .asJson();
            } catch (UnirestException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();

            }

            return request;
        }
    }




    class SendEmailAsyncTask extends AsyncTask <Void,Void,Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try {
                GmailSender m;
                m = new GmailSender("sadeanandkiran@gmail.com", "Cla551c1");
                m.sendMail(subject,body,senderAddress,receiverAddress);

            } catch (AuthenticationFailedException e) {
                Log.e(SendEmailAsyncTask.class.getName(), "Bad account details");
                e.printStackTrace();

            } catch (MessagingException e) {
                Log.e(SendEmailAsyncTask.class.getName(),  "failed");
                e.printStackTrace();

            } catch (Exception e) {
                e.printStackTrace();

            }
            return null;
        }

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
    private boolean isValidPassword(String password){
        boolean isValid = true;
        Matcher hasUpperCase = hasUppercase.matcher(password);
        Matcher hasLowerCase = hasLowercase.matcher(password);
        Matcher hasNumber1 =    hasNumber.matcher(password);
        Matcher hasSpecialChar1 = hasSpecialChar.matcher(password);

        return isValid&&hasUpperCase.matches()&& hasLowerCase.matches() && hasNumber1.matches() && hasSpecialChar1.matches();
    }

    private final Pattern hasUppercase = Pattern.compile("[A-Z]");
    private final Pattern hasLowercase = Pattern.compile("[a-z]");
    private final Pattern hasNumber = Pattern.compile("\\d");
    private final Pattern hasSpecialChar = Pattern.compile("[^a-zA-Z0-9 ]");


    }


