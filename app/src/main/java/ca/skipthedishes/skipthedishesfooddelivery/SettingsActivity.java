package ca.skipthedishes.skipthedishesfooddelivery;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import bean.OrderItem;


public class SettingsActivity extends Activity {

    private TextView textView;
    private EditText editTextUrl;

    private EditText editTextName;
    private EditText editTextAddress;
    private EditText editTextEmail;
    private EditText editTextLogin;
    private EditText editTextPassword;

    private EditText editTextCountry;
    private EditText editTextProvince;
    private EditText editTextCity;
    private Button buttonBack;
    private Button buttonAdvanced;
    private Button buttonSave;
    private Intent intent;
    private static final String SETTINGS_FILE =  "SettingsFile";
    public static String restaurantId = "";
    public static String url = "";
    public static String storeUrl = "";
    public static String storeName = "";

    private AlertDialog.Builder alertDialogSaveSetting;

    public ArrayList<OrderItem> orderItemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        init();
        textView.setEnabled(false);
        editTextUrl.setEnabled(false);

        buttonAdvanced.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.setEnabled(true);
                editTextUrl.setEnabled(true);
            }
        });

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.setEnabled(false);
                editTextUrl.setEnabled(false);

                alertDialogSaveSetting = new AlertDialog.Builder(SettingsActivity.this);
                alertDialogSaveSetting.setTitle("Save Setting");
                alertDialogSaveSetting.setMessage("Do want to save the current setting?");
                alertDialogSaveSetting.setCancelable(false);

                alertDialogSaveSetting.setNegativeButton("No",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });

                alertDialogSaveSetting.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                saveSettings();
                            }
                        });
                alertDialogSaveSetting.create();
                alertDialogSaveSetting.show();

            }
        });

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(SettingsActivity.this,MainActivity.class);
                intent.putExtra("orderItemList", orderItemList);
                intent.putExtra("restaurantId", restaurantId);
                intent.putExtra("url", url);
                intent.putExtra("storeUrl", storeUrl);
                intent.putExtra("storeName", storeName);
                startActivity(intent);
            }
        });



    }

    public void init(){
        textView        = findViewById(R.id.labelUrlId);

        editTextName     = findViewById(R.id.editTextNameId);;
        editTextAddress  = findViewById(R.id.editTextAddressId);;
        editTextEmail    = findViewById(R.id.editTextEmailId);;
        editTextLogin    = findViewById(R.id.editTextLoginId);;
        editTextPassword = findViewById(R.id.editTextPasswordId);;
        editTextCountry  = findViewById(R.id.editTextCountryId);
        editTextProvince = findViewById(R.id.editTextProvinceId);
        editTextCity     = findViewById(R.id.editTextCityId);
        editTextUrl      = findViewById(R.id.imputTextUrlId);

        buttonBack      = findViewById(R.id.buttonBackId);
        buttonAdvanced  = findViewById(R.id.buttonAdvancedId);
        buttonSave      = findViewById(R.id.buttonSaveId);

        loadSettings();

        Bundle extra = getIntent().getExtras();
        String str =  extra.getString(restaurantId);

        if (str != null){
            if ( ! extra.getString(restaurantId).equals(null) ){
                restaurantId  = extra.getString("restaurantId").toString();
                orderItemList = (ArrayList<OrderItem>) getIntent().getSerializableExtra("orderItemList");
                url             = extra.getString("url").toString();
                storeUrl             = extra.getString("storeUrl").toString();
                storeName             = extra.getString("storeName").toString();
            }
        }
    }

    public void saveSettings(){

        SharedPreferences sharedPreferences = getSharedPreferences(SETTINGS_FILE, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        if (editTextName.getText().equals("") ||
            editTextAddress.getText().equals("") ||
            editTextEmail.getText().equals("") ||
            editTextLogin.getText().equals("") ||
            editTextPassword.getText().equals("") ||
            editTextCountry.getText().equals("") ||
            editTextProvince.getText().equals("") ||
            editTextCity.getText().equals("") ||
            editTextUrl.getText().equals("")){
                Toast.makeText(SettingsActivity.this,"Please, Fill ou all fields",Toast.LENGTH_SHORT).show();
        }else{
            editor.putString("userName", editTextName.getText().toString() );
            editor.putString("userAddress", editTextAddress.getText().toString() );
            editor.putString("userEmail", editTextEmail.getText().toString() );
            editor.putString("userLogin", editTextLogin.getText().toString() );
            editor.putString("userPassword", editTextPassword.getText().toString() );
            editor.putString("userCountry", editTextCountry.getText().toString() );
            editor.putString("userProvince", editTextProvince.getText().toString() );
            editor.putString("userCity", editTextCity.getText().toString() );
            editor.putString("applicationUrl", editTextUrl.getText().toString() );
            //url = editTextUrl.getText().toString();
            editor.commit();
            Toast.makeText(getApplicationContext(),"Saving Settings", Toast.LENGTH_SHORT).show();
        }
    }

    public void loadSettings(){

        SharedPreferences sharedPreferences = getSharedPreferences(SETTINGS_FILE, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        if (sharedPreferences.contains("userName") &&
            sharedPreferences.contains("userAddress")  &&
            sharedPreferences.contains("userEmail")  &&
            sharedPreferences.contains("userLogin")  &&
            sharedPreferences.contains("userPassword")  &&
            sharedPreferences.contains("userCountry")  &&
            sharedPreferences.contains("userProvince")  &&
            sharedPreferences.contains("userCity")  &&
            sharedPreferences.contains("applicationUrl") ){
                editTextName.setText(sharedPreferences.getString("userName","Name not found"));
                editTextAddress.setText(sharedPreferences.getString("userAddress","Address not found"));
                editTextEmail.setText(sharedPreferences.getString("userEmail","Email not found"));
                editTextLogin.setText(sharedPreferences.getString("userLogin","Login not found"));
                editTextPassword.setText(sharedPreferences.getString("userPassword","Password not found"));
                editTextCountry.setText(sharedPreferences.getString("userCountry","Country not found"));
                editTextProvince.setText(sharedPreferences.getString("userProvince","Province not found"));
                editTextCity.setText(sharedPreferences.getString("userCity","City not found"));
                editTextUrl.setText(sharedPreferences.getString("applicationUrl","Url not found"));
        }else{
            Toast.makeText(getApplicationContext(),"Please, fill out all fields", Toast.LENGTH_SHORT).show();
        }
    }

}
