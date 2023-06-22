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
    fun equalsAction(view: View) {
        binding.resultsTv.text = calculateResults()
    }


    private fun calculateResults(): String {
        val digitsOperators = digitsOperators()
        if (digitsOperators.isEmpty()) return ""
        val timesDivision = timesDivisionCalculate(digitsOperators)
        if (timesDivision.isEmpty()) return ""

        val result = addSubstractCalculate(timesDivision)
        return result.toString()


    }

    private fun addSubstractCalculate(passedList: MutableList<Any>): Float {
        var result =  passedList[0] as Float
        for (i in passedList.indices){
            if (passedList[i] is Char && i != passedList.lastIndex){
                val operator = passedList[i]
                val nextDigit = passedList[i+1] as Float
                val prevDigit = passedList[i-1] as Float
                if (operator == '+')
                    result+=nextDigit
                if (operator == '-')
                    result-=nextDigit
            }
        }
        return result

    }

    private fun timesDivisionCalculate(passedList : MutableList<Any>): MutableList<Any> {
        val newList = mutableListOf<Any>()
        var restartIndex = passedList.size
        for(i in passedList.indices){
            if (passedList[i] is Char && i != passedList.lastIndex && i < restartIndex){
                val operator = passedList[i]
                val prevDigit = passedList[i-1] as Float
                val nextDigit = passedList[i+1] as Float

                when(operator){
                    'x' -> {
                        newList.add(prevDigit * nextDigit)
                        restartIndex = i + 1
                    }
                    '/' ->{
                        newList.add(prevDigit / nextDigit)
                        restartIndex = i + 1

                    }
                    else ->{
                        newList.add(prevDigit)
                        newList.add(operator)
                    }

                }
                if (i > restartIndex)
                    newList.add(passedList[i])
            }

        }
        return newList


    }

    private fun digitsOperators(): MutableList<Any>{
        val list = mutableListOf<Any>()
        var currentDigit = ""
        for (character in binding.workingTv.text){
            if(character.isDigit() || character == '.')
                currentDigit+= character
            else
            {
                list.add(currentDigit.toFloat())
                currentDigit = ""
                list.add(character)
            }
        }
        if(currentDigit != ""){
            list.add(currentDigit.toFloat())

        }

        return list
    }
}