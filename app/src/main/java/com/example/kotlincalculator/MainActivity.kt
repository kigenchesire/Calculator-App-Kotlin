package com.example.kotlincalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.example.kotlincalculator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var canAddOperation = false
    private var canAddDecimal = true
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
    fun numberAction(view:View){
        if (view is Button){
            if (view.text == "."){
                if(canAddDecimal)
                    binding.workingTv.append(view.text)
                canAddDecimal = false

            }
            else
                binding.workingTv.append(view.text)
        }

    }
    fun operationAction(view: View){
        if (view is Button && canAddOperation){
            binding.workingTv.append(view.text)
            canAddOperation = false
            canAddDecimal = true
        }
    }

    fun backSpaceAction(view: View) {
        var length = binding.workingTv.length()
        if (length>0){
            binding.workingTv.text = binding.workingTv.text.subSequence(0, length-1)
        }
    }
    fun AllClearAction(view: View) {
        binding.workingTv.text = ""
        binding.resultsTv.text = ""


    }
    fun equalsAction(view: View) {}
}