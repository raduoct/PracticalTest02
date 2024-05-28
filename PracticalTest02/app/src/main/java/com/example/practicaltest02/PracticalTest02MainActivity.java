package com.example.practicaltest02;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class PracticalTest02MainActivity extends AppCompatActivity {

    private EditText serverPortEditText = null;
    private EditText clientPortEditText = null;
    private EditText wordEditText = null;
    private TextView infoTextView = null;

    private ServerThread serverThread = null;

    private final ConnectButtonClickListener connectButtonClickListener = new ConnectButtonClickListener();
    private class ConnectButtonClickListener implements Button.OnClickListener {

        @Override
        public void onClick(View view) {
            String serverPort = serverPortEditText.getText().toString();
            if (serverPort.isEmpty()) {
                Toast.makeText(getApplicationContext(), "[MAIN ACTIVITY] Server port should be filled!", Toast.LENGTH_SHORT).show();
                return;
            }
            serverThread = new ServerThread(Integer.parseInt(serverPort));
            if (serverThread.getServerSocket() == null) {
                Log.e(Constants.TAG, "[MAIN ACTIVITY] Could not create server thread!");
                return;
            }
            serverThread.start();
        }

    }

    private final GetInfoButtonClickListener getInfoButtonClickListener = new GetInfoButtonClickListener();
    private class GetInfoButtonClickListener implements Button.OnClickListener {

        @Override
        public void onClick(View view) {
            String clientAddress = "127.0.0.1";
            String clientPort = clientPortEditText.getText().toString();
            if (clientPort.isEmpty()) {
                Toast.makeText(getApplicationContext(), "[MAIN ACTIVITY] Client connection parameters should be filled!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (serverThread == null || !serverThread.isAlive()) {
                Toast.makeText(getApplicationContext(), "[MAIN ACTIVITY] There is no server to connect to!", Toast.LENGTH_SHORT).show();
                return;
            }
            String word = wordEditText.getText().toString();
            if (word.isEmpty()) {
                Toast.makeText(getApplicationContext(), "[MAIN ACTIVITY] Parameters from client (word / information type) should be filled", Toast.LENGTH_SHORT).show();
                return;
            }

            infoTextView.setText(Constants.EMPTY_STRING);

            ClientThread clientThread = new ClientThread(
                    clientAddress, Integer.parseInt(clientPort), word, infoTextView
            );
            clientThread.start();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(Constants.TAG, "[MAIN ACTIVITY] onCreate() callback method has been invoked");
        setContentView(R.layout.activity_practical_test02_main);

        serverPortEditText = (EditText)findViewById(R.id.portServerEditText);
        Button connectButton = (Button) findViewById(R.id.startButton);
        connectButton.setOnClickListener(connectButtonClickListener);

        clientPortEditText = (EditText)findViewById(R.id.clientPortEditText);
        wordEditText = (EditText)findViewById(R.id.wordEditText);
        Button getInfobutton = (Button) findViewById(R.id.getInfoButton);
        getInfobutton.setOnClickListener(getInfoButtonClickListener);
        infoTextView = (TextView)findViewById(R.id.infoTextView);
    }

    @Override
    protected void onDestroy() {
        Log.i(Constants.TAG, "[MAIN ACTIVITY] onDestroy() callback method has been invoked");
        if (serverThread != null) {
            serverThread.stopThread();
        }
        super.onDestroy();
    }

}