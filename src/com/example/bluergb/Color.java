package com.example.bluergb;

import java.util.UUID;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import com.example.bluergb.*;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.content.Intent;
public class Color extends Activity {
	private static final String TAG = "BlueRGB";
	Button r3,r4,r5,b3,b4,b5,w3,w4,w5,g3,g4,g5,bck;
	private BluetoothAdapter btAdapter = null;
	private BluetoothSocket btSocket = null;
	private ConnectedThread mConnectedThread;
	private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
	// MAC-address of Bluetooth module (you must edit this line)
	private static String address = "98:D3:31:B0:3F:FA";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_color);
		r3= (Button) findViewById(R.id.r3);					
	    g3 = (Button) findViewById(R.id.g3);
	    b3 = (Button) findViewById(R.id.b3);
	    w3 = (Button) findViewById(R.id.w3);
	    r4= (Button) findViewById(R.id.r4);					
	    g4 = (Button) findViewById(R.id.g4);
	    b4 = (Button) findViewById(R.id.b4);
	    w4 = (Button) findViewById(R.id.w4);
	    r5= (Button) findViewById(R.id.r5);					
	    g5 = (Button) findViewById(R.id.g5);
	    b5 = (Button) findViewById(R.id.b5);
	    w5 = (Button) findViewById(R.id.w5);
	    bck = (Button) findViewById(R.id.bck);
	    bck.setOnClickListener(new OnClickListener()
	    {
	        @Override
	        public void onClick(View v)
	        {
	        	Intent i=new Intent(Color.this,MainActivity.class);	
	    	    startActivity(i);
	        }
	    });
	    r3.setOnClickListener(new OnClickListener()
	    {
	        @Override
	        public void onClick(View v)
	        {
	        	mConnectedThread.write("5");
	        }
	    });
	    g3.setOnClickListener(new OnClickListener()
	    {
	        @Override
	        public void onClick(View v)
	        {
	        	mConnectedThread.write("6");
	        }
	    });
	    b3.setOnClickListener(new OnClickListener()
	    {
	        @Override
	        public void onClick(View v)
	        {
	        	mConnectedThread.write("7");
	        }
	    });
	    w3.setOnClickListener(new OnClickListener()
	    {
	        @Override
	        public void onClick(View v)
	        {
	        	mConnectedThread.write("8"); 
	        }
	    });
	    r4.setOnClickListener(new OnClickListener()
	    {
	        @Override
	        public void onClick(View v)
	        {
	        	mConnectedThread.write("9"); 
	        }
	    });
	    g4.setOnClickListener(new OnClickListener()
	    {
	        @Override
	        public void onClick(View v)
	        {
	        	mConnectedThread.write("0");
	        }
	    });
	    b4.setOnClickListener(new OnClickListener()
	    {
	        @Override
	        public void onClick(View v)
	        {
	        	mConnectedThread.write("a");
	        }
	    });
	    w4.setOnClickListener(new OnClickListener()
	    {
	        @Override
	        public void onClick(View v)
	        {
	        	mConnectedThread.write("b"); 
	        }
	    });
	    r5.setOnClickListener(new OnClickListener()
	    {
	        @Override
	        public void onClick(View v)
	        {
	        	mConnectedThread.write("c"); 
	        }
	    });
	    g5.setOnClickListener(new OnClickListener()
	    {
	        @Override
	        public void onClick(View v)
	        {
	        	mConnectedThread.write("d");
	        }
	    });
	    b5.setOnClickListener(new OnClickListener()
	    {
	        @Override
	        public void onClick(View v)
	        {
	        	mConnectedThread.write("e");
	        }
	    });
	    w5.setOnClickListener(new OnClickListener()
	    {
	        @Override
	        public void onClick(View v)
	        {
	        	mConnectedThread.write("f"); 
	        }
	    });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.color, menu);
		return true;
		
	}

}
