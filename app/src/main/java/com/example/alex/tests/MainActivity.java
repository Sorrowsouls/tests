package com.example.alex.tests;

import android.annotation.TargetApi;

import java.lang.annotation.Target;



import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.le.BluetoothLeScanner;
import android.content.Context;
import android.content.Intent;
import android.graphics.Region;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Handler;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.ToggleButton;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.jar.Manifest;


@TargetApi(25)

public class MainActivity extends AppCompatActivity {

    private Region region;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Spinner dropbown = (Spinner)findViewById(R.id.spinner);

        Button closebutton = (Button)findViewById(R.id.close_button);

        closebutton.setVisibility(View.INVISIBLE);

        String[] list = new String[]{"0", "1", "2","3","4","5"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, list);
        dropbown.setAdapter(adapter);
        //dropbown.setVisibility(View.INVISIBLE);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

        //setadresses(); //crashes when ran here
    }

    private BluetoothAdapter mBluetooth;

    private SparseArray<BluetoothDevice> mDevices;


    int beconnum;

    String name;

    String[] adresslist = new String[5];
    String[] weblinks = new String[5];
    UUID[] knownuuids = new UUID[5];

    boolean foundbeacon = false;
    //String[] knownuuids = new String[5];
public void setadresses ()
    {

        String temp;
        temp = "0C:F3:EE:00:4F:6C";
        //knownuuids = UUID.fromString("0C:F3:EE:00:4F:6C");




        knownuuids[0] = UUID.fromString("8a5a8c8f-58ea-4955-bc5e-9cbd4af87286");
       // knownuuids[2] = "E3:C9:42:12:DB:71";

        // Reference Ashley Williamson 27/03/16 - Discussion on the Map DataType.
        HashMap< String, String > someMap = new HashMap< String, String>(5);
        someMap.put( "0C:F3:EE:00:4F:6C", "http://www.lincstothepast.com/Museum-of-Lincolnshire-Life--Burton-Road--Lincoln/245911.record?pt=S" );
        Log.d( "FUCKOFF", someMap.get( "0C:F3:EE:00:4F:6C" ) );

        adresslist[0] = "0C:F3:EE:00:4F:6C  "; //beacon 1
       // adresslist[1] = "E3:C9:42:12:DB:71"; //esitom
        adresslist[1] = "0C:F3:EE:04:31:7B"; //beacon 2
        adresslist[2] = "0C:F3:EE:04:30:3A";//beacon 3
        adresslist[3] = "0C:F3:EE:04:2E:26"; //beacon 4
        adresslist[4] = "0C:F3:EE:04:2E:53";//beacon 5

        weblinks[0] = "http://www.lincstothepast.com/Museum-of-Lincolnshire-Life--Burton-Road--Lincoln/245911.record?pt=S";
        weblinks[1] = "http://www.lincstothepast.com/exhibitions/treasures/lincolnshire-tank/";
        weblinks[2] = "http://www.lincstothepast.com/exhibitions/lincolnshire-at-war/war-memorials/";
        weblinks[3] = "http://www.lincstothepast.com/exhibitions/lincs-through-the-ages/plagues-potions-and-pills/";
        weblinks[4] = "http://www.lincstothepast.com/exhibitions/lincolnshire-at-war/lincolnshire-pioneering-engineering/";

    }




    boolean mybeacon = false;
    boolean isopen = false;
    boolean manualinput = false;
    boolean hasvibrated = false;
    boolean cont = false;

    String currentaddress;

    String tmpadress;


    public void demoscan(View view) {

        ((Button) view).setText("Demo Scan");

        Spinner dropdown = (Spinner) findViewById(R.id.spinner);

        int value = Integer.parseInt(dropdown.getSelectedItem().toString());

        isopen = true;

        openURL(weblinks[value]);

        //openWebApp();
    }


    private BluetoothAdapter.LeScanCallback mLeScanCalllBack = new BluetoothAdapter.LeScanCallback() {
        @Override
        public void onLeScan(final BluetoothDevice device, final int rssi, final byte[] scanRecord) {

            Spinner dropdown = (Spinner)findViewById(R.id.spinner);

            /*for(int i = 0; i < knownuuids.length;i++) {

                if(device.getUuids().toString() == knownuuids[i])
                {
                    mybeacon = true;
                }
                else
                {
                    Log.d("BEACON","New Beacon found: " + device.getUuids().toString());
                }

            }
*/

                if (!device.getAddress().isEmpty() /*&& mybeacon == true*/) {
                ///if  the device has an address continue
                /*if(dropdown.getPopupContext().toString().equals("0")){}
                else {

                    beconnum = Integer.parseInt(dropdown.getPopupContext().toString());
                    foundbeacon = true;
                    manualinput = true;

                    Log.d("LOGGING","beconnum: " + dropdown.getPopupContext().toString());

                    }
                */
                    //foundbeacon = true;
                    int i = 0;
                    name = device.getAddress().toString();
                    mybeacon = false;


                    while (i <= adresslist.length) {
                        ///itterate through the address list to check if value == any off the known beacons
                        cont = true;


                        tmpadress = adresslist[i];


                        if (name.equals(tmpadress)){

                            foundbeacon = true;
                            beconnum = i;
                                ///sets the value of the found ebacon to the beacon number


                            ///no longer required but kept just encase

                            /*if (isopen == false) {
                                 vibrate();

                                    //vibreates if the webpage isn't opened
                                }
*/

                                if (rssi >= -75 && !isopen) {
                                    ///checks the current rssi value is close enough to the beacon

                                        openURL(weblinks[beconnum]);

                                        isopen = true;

                                    if(!hasvibrated) {
                                        vibrate();
                                        hasvibrated = true;
                                    }
                                   // isopen = true;
                                    currentaddress = name;
                                    ///sets the address of beacon to a value to be checked against later

                                }





                               // beconnum = 0;
                                //
                                // isopen = false;
                                manualinput = false;

                        }

                        if (rssi <= -115 && isopen && name.equals(currentaddress)) {
                            ///checks if the beacon is far away enough and is also the correct beacon
                            foundbeacon = false;

                            closeWebApp();
                            isopen = false;
                            hasvibrated = false;
                            i = 0;
                            ///allows for vibrate again if ffound in presence of a new beacon
                        }

                        i++;

                        ///i++ for itteration
                    }
                    if (i == 5){i=0;}
                }

            int startByte = 2;

            while (startByte <= 5) {
                if (

                        ((int) scanRecord[startByte + 2] & 0xff) == 0x02 &&

                                ((int) scanRecord[startByte + 3] & 0xff) == 0x15) {
                    break;
                }
                startByte++;
            }

            ///section was for recording the device uuid but may not be needed anymore


        }


    };

    BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    boolean hasBluetooth = (mBluetoothAdapter == null);
///old code used encasethe phones bluetooth failed, removed has makes the project redundant anyway
    String publicURL;
    UUID id1;
    WebView mWebView;

    public void closeWebApp()///misnomer, acutally hides both not destroys
    {
        MainActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                final WebView mWebView = (WebView) findViewById(R.id.webView);
                final Button button = (Button) findViewById(R.id.close_button);
                mWebView.setVisibility(View.INVISIBLE);
                button.setVisibility(View.INVISIBLE);

            }
        });

    }

    public void openWebApp()
    {
        MainActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                final WebView mWebView = (WebView) findViewById(R.id.webView);
                final Button button = (Button) findViewById(R.id.close_button);
                mWebView.setVisibility(View.VISIBLE);
                button.setVisibility(View.VISIBLE);

            }
        });

    }
    public void openURL(String inURL) {
        //Intent browse = new Intent(Intent.ACTION_VIEW, Uri.parse(inURL));

        publicURL = inURL;
        MainActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {

                final WebView mWebView = (WebView) findViewById(R.id.webView);

                /*
                mWebView.getSettings().setJavaScriptEnabled(true);
                mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
                mWebView.getSettings().setLoadWithOverviewMode(true);
                mWebView.getSettings().setUseWideViewPort(true);
*/
                mWebView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
                mWebView.setScrollbarFadingEnabled(false);


                mWebView.loadUrl(publicURL);

                isopen = true;
                //startActivity(browse);
                mWebView.setWebViewClient(new WebViewClient() {
                    public void onPageFinished(WebView view, String url) {

                        //  mWebView.setVisibility(View.VISIBLE); //old code

                        openWebApp();

                        //   isopen = true;
                    }
                });
            }
        });



    }

    public void closebuttonOnClick(View v){


        Button button = (Button) v;

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                closeWebApp();
            }
        });

    }

    public void buttonOnClick(View v) {

        boolean selectable = false;


        id1.fromString("8a5a8c8f-58ea-4955-bc5e-9cbd4af87286");

        final UUID[] uuids = {UUID.fromString("8a5a8c8f-58ea-4955-bc5e-9cbd4af87286")};

        ToggleButton button = (ToggleButton) v;


        if (button.isChecked()) {
            button.setText("ON");

            mBluetoothAdapter.enable();

            //startScan();

        }

        if (!button.isChecked()) {
            mBluetoothAdapter.disable();
            button.setText("OFF");
        }

    }

    public void vibrate()///simply vibrates the phone briefly
    {
       Vibrator vibe = (Vibrator) this.getSystemService(VIBRATOR_SERVICE);

        if(vibe.hasVibrator()) {
        vibe.vibrate(1000);
        }
        else {
            Log.d("ERROR", "No Vibe functioanily found");
        }


    }



    public void rescanButton(View view) {

        if (mBluetoothAdapter.isEnabled()) {
            ((Button) view).setText("Scan Started");
            startScan();
        } else {

            ((Button) view).setText("Turning on Bluetooth");
            ToggleButton toggle = (ToggleButton) findViewById(R.id.togglebutton);
            toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    if (buttonView.isChecked()) {
                        isChecked = true;
                    } else {
                        isChecked = false;
                    }

                    if (!isChecked) {
                        buttonView.toggle();
                        mBluetoothAdapter.enable();
                    }
                }
            });

        }
    }

    public void startScan() {

        Log.d("Log", "StartScan");

        setadresses(); //crashes here too

        if (!mBluetoothAdapter.isEnabled()) {

        } else {
            Handler handler = new Handler();

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mBluetoothAdapter.stopLeScan(mLeScanCalllBack);

                    final UUID[] uuids = {UUID.fromString("8a5a8c8f-58ea-4955-bc5e-9cbd4af87286")};

                        mBluetoothAdapter.startLeScan(mLeScanCalllBack);

                   /* while (mBluetoothAdapter.startLeScan(uuids, mLeScanCalllBack))


                    {

                        //while (blah.toString() == "0C:F3:EE:00:4F:6C"){
                            mBluetoothAdapter.startLeScan(mLeScanCalllBack);
                            //openURL("http://www.dum12373618.wordpress.com");
                    }*/
                }
            }, 5000);//50 seconds timer
        }
    }


    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.alex.tests/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);

        if (mBluetoothAdapter.isEnabled()
                ) {
            mBluetoothAdapter.disable();
            //can do something here if needed later
        }

    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.alex.tests/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        mBluetoothAdapter.disable();
        client.disconnect();
    }
}
