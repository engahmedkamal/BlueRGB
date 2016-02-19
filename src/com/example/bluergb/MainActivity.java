package com.example.bluergb;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.UUID; 
import com.example.bluergb.DeviceListActivity;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Toast;
import android.widget.ToggleButton;
  
public class MainActivity extends Activity  {

	  private static final String TAG = "main_colors";
	  private static final boolean D = true;
	  // Message types sent from the BluetoothChatService Handler
	    public static final int MESSAGE_STATE_CHANGE = 1;
	    public static final int MESSAGE_READ = 2;
	    public static final int MESSAGE_WRITE = 3;
	    public static final int MESSAGE_DEVICE_NAME = 4;
	    public static final int MESSAGE_TOAST = 5;

	    // Key names received from the BluetoothChatService Handler
	    public static final String DEVICE_NAME = "device_name";
	    public static final String TOAST = "toast";

	    // Intent request codes
	    private static final int REQUEST_CONNECT_DEVICE_SECURE = 1;
	    private static final int REQUEST_CONNECT_DEVICE_INSECURE = 2;
	    private static final int REQUEST_ENABLE_BT = 3;
	  int prog=0;
	  Button btn_red, btn_blue,btn_green,btn_color,btn_effect;
	 // Handler h;
	  ToggleButton  on_btn;
	 // final int RECIEVE_MESSAGE = 1;        // Status  for Handler
	  private BluetoothAdapter btAdapter = null;
	  private BluetoothSocket btSocket = null;
	  private ConnectedThread mConnectedThread;
	  private BluetoothChatService mChatService = null;
	  // SPP UUID service
	  private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
	  
	  // MAC-address of Bluetooth module (you must edit this line)
	//  private static String address = "98:D3:31:B0:3F:FA";
	  private static String address = "00:13:12:06:62:28";
	  @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		on_btn=(ToggleButton)findViewById(R.id.power);
		btn_color = (Button) findViewById(R.id.bt_color);					
	    btn_effect = (Button) findViewById(R.id.bt_effect);
	    btn_red = (Button) findViewById(R.id.btn_red);					
	    btn_blue = (Button) findViewById(R.id.btn_blue);
	    btn_green = (Button) findViewById(R.id.btn_green);
	    btAdapter = BluetoothAdapter.getDefaultAdapter();		// get Bluetooth adapter
	    checkBTState();
	    btn_red.setOnClickListener(new OnClickListener() {
		      public void onClick(View v) {
		    	mConnectedThread.write("r");	// Send "1" via Bluetooth
		        //Toast.makeText(getBaseContext(), "Turn on LED", Toast.LENGTH_SHORT).show();
		      }
		    });
	    on_btn.setOnClickListener(new OnClickListener()
	    {
	        @Override
	        public void onClick(View v)
	        {
	            if(on_btn.isChecked()){
	        	mConnectedThread.write("o");
	            }
	            else{
	            mConnectedThread.write("f");
	            }
	        }
	    });
	    btn_color.setOnClickListener(new OnClickListener()
	    {
	        @Override
	        public void onClick(View v)
	        {
	        	Intent i=new Intent(MainActivity.this,Color.class);	
	    	    startActivity(i); 
	        }
	    });
	    btn_effect.setOnClickListener(new OnClickListener()
	    {
	        @Override
	        public void onClick(View v)
	        {
	        	Intent i=new Intent(MainActivity.this,Effects.class);	
	    	    startActivity(i); 
	        }
	    });
	    
	    btn_blue.setOnClickListener(new OnClickListener() {
	      public void onClick(View v) {  
	    	mConnectedThread.write("b");	// Send "0" via Bluetooth
	        //Toast.makeText(getBaseContext(), "Turn off LED", Toast.LENGTH_SHORT).show();
	      }
	    });
	    btn_green.setOnClickListener(new OnClickListener() {
		      public void onClick(View v) {  
		    	mConnectedThread.write("g");	// Send "0" via Bluetooth
		        //Toast.makeText(getBaseContext(), "Turn off LED", Toast.LENGTH_SHORT).show();
		      }
		    });
	   SeekBar brig = (SeekBar)findViewById(R.id.seekBar1); // make seekbar object
	   brig.setOnSeekBarChangeListener( new OnSeekBarChangeListener()
	   {
	   public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
	   {
		   if(progress>prog)
		   {
			   mConnectedThread.write("+");
		   }
		   else
		   {
			   mConnectedThread.write("-");   
		   }
		   progress=prog;
	   }

       public void onStartTrackingTouch(SeekBar seekBar)
	   {
      // TODO Auto-generated method stub
       }

public void onStopTrackingTouch(SeekBar seekBar)
	   {
       }
	   });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		 MenuInflater inflater = getMenuInflater();
		    inflater.inflate(R.menu.main, menu);
		    return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
	    switch (item.getItemId()) {
	        case R.id.con:
	        	///////////////////////////////////////////////////
	        	Intent serverIntent = new Intent(this, DeviceListActivity.class);
	            startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE_SECURE);
	            return true;	
	        default:
	            return super.onOptionsItemSelected(item);
	    }}

private BluetoothSocket createBluetoothSocket(BluetoothDevice device) throws IOException {
    if(Build.VERSION.SDK_INT >= 10){
        try {
            final Method  m = device.getClass().getMethod("createInsecureRfcommSocketToServiceRecord", new Class[] { UUID.class });
            return (BluetoothSocket) m.invoke(device, MY_UUID);
        } catch (Exception e) {
            Log.e(TAG, "Could not create Insecure RFComm Connection",e);
        }
    }
    return  device.createRfcommSocketToServiceRecord(MY_UUID);
}
 
@Override
public void onResume() {
  super.onResume();

  Log.d(TAG, "...onResume - try connect...");
 
  // Set up a pointer to the remote node using it's address.
  BluetoothDevice device = btAdapter.getRemoteDevice(address);
 
  // Two things are needed to make a connection:
  //   A MAC address, which we got above.
  //   A Service ID or UUID.  In this case we are using the
  //     UUID for SPP.
  
	try {
		btSocket = createBluetoothSocket(device);
	} catch (IOException e) {
		errorExit("Fatal Error", "In onResume() and socket create failed: " + e.getMessage() + ".");
	}
  
  /*try {
    btSocket = device.createRfcommSocketToServiceRecord(MY_UUID);
  } catch (IOException e) {
    errorExit("Fatal Error", "In onResume() and socket create failed: " + e.getMessage() + ".");
  }*/
 
  // Discovery is resource intensive.  Make sure it isn't going on
  // when you attempt to connect and pass your message.
  btAdapter.cancelDiscovery();
 
  // Establish the connection.  This will block until it connects.
  Log.d(TAG, "...Connecting...");
  try {
    btSocket.connect();
    Log.d(TAG, "....Connection ok...");
  } catch (IOException e) {
    try {
      btSocket.close();
    } catch (IOException e2) {
      errorExit("Fatal Error", "In onResume() and unable to close socket during connection failure" + e2.getMessage() + ".");
    }
  }
   
  // Create a data stream so we can talk to server.
  Log.d(TAG, "...Create Socket...");
 
  mConnectedThread = new ConnectedThread(btSocket);
  mConnectedThread.start();
}
@Override
public void onPause() {
  super.onPause();

  Log.d(TAG, "...In onPause()...");

  try     {
    btSocket.close();
  } catch (IOException e2) {
    errorExit("Fatal Error", "In onPause() and failed to close socket." + e2.getMessage() + ".");
  }
}
 
private void checkBTState() {
  // Check for Bluetooth support and then check to make sure it is turned on
  // Emulator doesn't support Bluetooth and will return null
  if(btAdapter==null) { 
    errorExit("Fatal Error", "Bluetooth not support");
  } else {
    if (btAdapter.isEnabled()) {
      Log.d(TAG, "...Bluetooth ON...");
    } else {
      //Prompt user to turn on Bluetooth
      Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
      startActivityForResult(enableBtIntent, 1);
    }
  }
}

private void errorExit(String title, String message){
  Toast.makeText(getBaseContext(), title + " - " + message, Toast.LENGTH_LONG).show();
  finish();
}
/*---------------------------------------------------------------------------------------*/
private void connectDevice(Intent data, boolean secure) {
    // Get the device MAC address
    String address = data.getExtras()
        .getString(DeviceListActivity.EXTRA_DEVICE_ADDRESS);
    // Get the BluetoothDevice object
    BluetoothDevice device = btAdapter.getRemoteDevice(address);
    // Attempt to connect to the device
    mChatService.connect(device, secure);
}
public void onActivityResult(int requestCode, int resultCode, Intent data) {
    if(D) Log.d(TAG, "onActivityResult " + resultCode);
    switch (requestCode) {
    case REQUEST_CONNECT_DEVICE_SECURE:
        // When DeviceListActivity returns with a device to connect
        if (resultCode == Activity.RESULT_OK) {
            connectDevice(data, true);
        }
        break;
    case REQUEST_ENABLE_BT:
        // When the request to enable Bluetooth returns
        if (resultCode == Activity.RESULT_OK) {
            // Bluetooth is now enabled, so set up a chat session
            setupChat();
        } else {
            // User did not enable Bluetooth or an error occurred
            Log.d(TAG, "BT not enabled");
            Toast.makeText(this, R.string.bt_not_enabled_leaving, Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}
private final Handler mHandler = new Handler() {
    @Override
    public void handleMessage(Message msg) {
        switch (msg.what) {
        case MESSAGE_STATE_CHANGE:
            if(D) Log.i(TAG, "MESSAGE_STATE_CHANGE: " + msg.arg1);
            switch (msg.arg1) {
            case BluetoothChatService.STATE_CONNECTED:
                //setStatus(getString(R.string., mConnectedDeviceName));
                break;
            case BluetoothChatService.STATE_CONNECTING:
                setStatus(R.string.title_connecting);
                break;
            case BluetoothChatService.STATE_LISTEN:
            case BluetoothChatService.STATE_NONE:
                setStatus(R.string.title_not_connected);
                break;
            }
            break;
        }
    }
};
}