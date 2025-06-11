package com.example.tipsapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tipsapp.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

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

        binding.btnClean.setOnClickListener{
            clearFields()
        }

        binding.btnCalculate.setOnClickListener {
            calculateTips()
        }
    }


    private fun calculateTips(){
        val totalTableTemp = binding.tieTotal.text
        val totalPeopleTemp = binding.tieNumberPeople.text
        val totalPercentageTemp = binding.tiePercentage.text

        if (totalTableTemp?.isEmpty() == true ||
            totalPeopleTemp?.isEmpty() == true ||
            totalPercentageTemp?.isEmpty() == true){
            Snackbar
                .make(binding.tieTotal, getString(R.string.fill_fields), Snackbar.LENGTH_LONG)
                .show()
        }
        else {
            val totalTable: Float = totalTableTemp.toString().toFloat()
            val nPeople: Int = totalPeopleTemp.toString().toInt()
            val percentageTips: Int = totalPercentageTemp.toString().toInt()

            val totalTemp = totalTable / nPeople
            val tips = totalTemp * percentageTips / 100
            val totalWithTips = totalTemp + tips


            val intent = Intent(this, SummaryActivity::class.java)
            intent.apply {
                putExtra("totalTable", totalTable)
                putExtra("numPeople", nPeople)
                putExtra("percentage", percentageTips)
                putExtra("totalAmount", totalWithTips)
            }
            clearFields()
            startActivity(intent)
        }
    }

    private fun clearFields() = with(binding) {
        tieTotal.setText("")
        tiePercentage.setText("")
        tieNumberPeople.setText("")
    }

}