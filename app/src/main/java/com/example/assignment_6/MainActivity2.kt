package com.example.assignment_6

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity2 : AppCompatActivity() {

    private lateinit var editTextName: EditText
    private lateinit var editTextPhone: EditText
    private lateinit var SaveButton: Button

    private lateinit var ref: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        editTextName = findViewById(R.id.editTextName)
        editTextPhone = findViewById(R.id.editTextPhone)
        SaveButton = findViewById(R.id.SaveButton)

        ref = FirebaseDatabase.getInstance().getReference("contacts")


        SaveButton.setOnClickListener {
            saveContacts()
        }

    }

    private fun saveContacts(){
        val name = editTextName.text.toString()
        val phone = editTextPhone.text.toString()

        if(name.isEmpty()){
            editTextName.error = "please enter name"
        }
        if(phone.isEmpty()){
            editTextPhone.error = "please enter phone number"
        }

        val contactId = ref.push().key!!

        val Contact = contact(contactId, name,phone)

        ref.child(contactId).setValue(Contact)
            .addOnCompleteListener{
                Toast.makeText(this, "data inserted successfully", Toast.LENGTH_LONG).show()
                editTextName.text.clear()
                editTextPhone.text.clear()
            }.addOnFailureListener{ err ->
                Toast.makeText(this, "error ${err.message}", Toast.LENGTH_LONG).show()
            }
    }
}