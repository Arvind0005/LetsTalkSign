package com.example.myapplication

import android.os.Build
import android.telecom.Connection
import android.telecom.ConnectionRequest
import android.telecom.ConnectionService
import android.telecom.PhoneAccountHandle
import androidx.annotation.RequiresApi

class PhoneCall_Service : ConnectionService() {

    @RequiresApi(Build.VERSION_CODES.N_MR1)
    override fun onCreateOutgoingConnection(
        connectionManagerPhoneAccount: PhoneAccountHandle?,
        request: ConnectionRequest?
    ): Connection {
        println("succccccccccceeeeeeeeeeeesssssssssssssssss");
        val connection =MyConnection();

        // Customize the connection properties, capabilities, etc.
        connection.setConnectionProperties(Connection.PROPERTY_SELF_MANAGED)
        connection.onShowIncomingCallUi()
      //  connection.setVideoState(ConnectionRequest.getVideoState())

        // ... Other customizations

        return connection
    }
    override fun onCreateOutgoingConnectionFailed(
        phoneAccountHandle: PhoneAccountHandle,
        connectionRequest: ConnectionRequest
    ) {
        println("failed");
        // Inform the user that the outgoing call failed
    }

    @RequiresApi(Build.VERSION_CODES.N_MR1)
    override fun onCreateIncomingConnection(
        phoneAccountHandle: PhoneAccountHandle,
        connectionRequest: ConnectionRequest
    ): Connection {
        println("iiiiiiiiiinnnnnnnnnnnnnnn");
        // Create a new instance of your Connection class
        val connection = MyConnection()

        // Customize the connection properties, capabilities, etc.
        connection.setConnectionProperties(Connection.PROPERTY_SELF_MANAGED)
        connection.setVideoState(connectionRequest.getVideoState())

        // ... Other customizations

        return connection
    }
    override fun onCreateIncomingConnectionFailed(
        phoneAccountHandle: PhoneAccountHandle,
        connectionRequest: ConnectionRequest
    ) {
        println("infaillllllllllllllllleddddddd");
        // Silently reject the incoming call or notify the user
    }
}