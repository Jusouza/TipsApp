package com.example.tipsapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tipsapp.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private var percentage: Int = 0
    private var numOfPeopleSelected: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        spinnerAdapterConfig()

        binding.rgPercentage.setOnCheckedChangeListener { _, checkedId ->
            percentage = when (checkedId) {
                R.id.rb_option_one -> 10
                R.id.rb_option_two -> 15
                R.id.rb_option_three -> 20
                else -> 0
            }
        }

        binding.btnClean.setOnClickListener{
            clearFields()
        }

        binding.btnCalculate.setOnClickListener {
            calculateTips()
        }
    }

    private fun spinnerAdapterConfig(){
        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.num_people,
            android.R.layout.simple_spinner_item
        )

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerNumberOfPeople.adapter = adapter


        binding.spinnerNumberOfPeople.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                numOfPeopleSelected = position
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

        }
    }

    private fun calculateTips(){
        val totalTableTemp = binding.tieTotal.text

        if (totalTableTemp?.isEmpty() == true ){
            Snackbar
                .make(binding.tieTotal, getString(R.string.fill_fields), Snackbar.LENGTH_LONG)
                .show()
        }
        else {
            val totalTable: Float = totalTableTemp.toString().toFloat()
            val nPeople: Int = numOfPeopleSelected

            val totalTemp = totalTable / nPeople
            val tips = totalTemp * percentage / 100
            val totalWithTips = totalTemp + tips


            val intent = Intent(this, SummaryActivity::class.java)
            intent.apply {
                putExtra("totalTable", totalTable)
                putExtra("numPeople", nPeople)
                putExtra("percentage", percentage)
                putExtra("totalAmount", totalWithTips)
            }
            clearFields()
            startActivity(intent)
        }
    }

    private fun clearFields() = with(binding) {
        tieTotal.setText("")
        rgPercentage.clearCheck()
        spinnerNumberOfPeople.setSelection(0)
    }

}