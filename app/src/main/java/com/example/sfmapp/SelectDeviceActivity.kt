package com.example.sfmapp

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import com.google.android.gms.common.util.Strings
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class SelectDeviceActivity : AppCompatActivity() {

    private lateinit var device : Spinner
    lateinit var result : TextView
    private val database = Firebase.database
    var user = FirebaseAuth.getInstance().currentUser
    var uID= user?.uid
    private val myDevicesRef = database.getReference("").child("Users/$uID/Ref")
    val devicesList = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_device)
        devicesList.add("Select a device")
        val myChildEventListener = object : ChildEventListener {
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                TODO("Not yet implemented")
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                TODO("Not yet implemented")
            }

            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                devicesList.add(snapshot.key!!)
                device.isEnabled = true
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                TODO("Not yet implemented")
            }

        }
        myDevicesRef.addChildEventListener(myChildEventListener)

        device = findViewById<Spinner>(R.id.spinnerSelectDevice)
        result = findViewById<TextView>(R.id.textSpinner)
        device.isEnabled = false

        // initialize an array adapter for spinner
        val adapter:ArrayAdapter<String> = object: ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,devicesList){
            override fun isEnabled(position: Int): Boolean {
                // disable the third item of spinner
                return position != 0
            }
            override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view:TextView = super.getDropDownView(position, convertView, parent) as TextView

                // set item text bold and sans serif font
                view.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD)

                if (position == 0){
                    // set the spinner disabled item text color
                    view.setTextColor(Color.LTGRAY)
                }

                // set selected item style
                if (position == device.selectedItemPosition){
                    view.background = ColorDrawable(Color.parseColor("#F5F5F5"))
                }

                return view
            }
        }


        device.adapter = adapter

        device.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {
                result.text = "Please select a device"
            }

            override fun onItemSelected(p0: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (devicesList[position]!="Select a device"){
                    Variables.SelectedReference = devicesList[position]
                    myDevicesRef.removeEventListener(myChildEventListener)
                    val intent = Intent(this@SelectDeviceActivity , UseDeviceActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                    finish()
                }
                else{
                    result.text = "Please select a device"
                }
            }

        }

    }
}