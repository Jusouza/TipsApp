package com.example.tipsapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.tipsapp.databinding.ActivitySummaryBinding

class SummaryActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySummaryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySummaryBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val totalTable = intent.getFloatExtra("totalTable", 0.0f)
        val numPeople = intent.getIntExtra("numPeople", 0)
        val percentage = intent.getIntExtra("percentage", 0)
        val totalAmount = intent.getFloatExtra("totalAmount", 0.0f)

        binding.tvTotalPercentage.text = percentage.toString()
        binding.tvTotalTableAmount.text = totalTable.toString()
        binding.tvTotalNumOfPeople.text = numPeople.toString()
        binding.tvTotal.text = totalAmount.toString()

        binding.btnNewCalculation.setOnClickListener {
            finish()
        }

    }

}