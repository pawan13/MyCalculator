package com.pk.android.mycalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.ArithmeticException

class MainActivity : AppCompatActivity() {

    var lastNumeric = false
    var lastDot = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onDigit(view: View) {
        txtInput.append((view as Button).text)
        lastNumeric = true
    }

    fun onClear(view: View) {
        txtInput.text = ""
        lastNumeric = false
        lastDot = false
    }

    fun onDecimalPoint(view: View) {
        if (lastNumeric && !lastDot) {
            txtInput.append(".")
            lastNumeric = false
            lastDot = true
        }
    }
    private fun isOperatorAdded(value: String) : Boolean{
        return if (value.startsWith("-")){
            false
        }else{
            value.contains("/")|| value.contains("*")
                    || value.contains("+") || value.contains("-")
        }
    }
    private  fun removeZeroAfterDot(result: String): String{
        var value = result
        if (result.contains(".0"))
            value = result.substring(0, result.length-2)
        return value
    }

    fun onOperator(view: View) {
        if (lastNumeric && !isOperatorAdded(txtInput.text.toString())){
            txtInput.append((view as Button).text)
            lastNumeric = false
            lastDot = false
        }
    }

    fun onEqual(view: View) {
        if (lastNumeric){
            var txtValue = txtInput.text.toString()
            var prefix = ""

            try {

                if (txtValue.startsWith("-")){
                    prefix = "-"
                    txtValue = txtValue.substring(1)
                }

                if (txtValue.contains("-")){
                    val splitValue = txtValue.split("-")
                    var one = splitValue[0]
                    var two = splitValue[1]

                    if(!prefix.isEmpty()){
                        one = prefix + one
                    }

                    txtInput.text = removeZeroAfterDot((one.toDouble() - two.toDouble()).toString())
                }else  if (txtValue.contains("+")){
                    val splitValue = txtValue.split("+")
                    var one = splitValue[0]
                    var two = splitValue[1]

                    if(!prefix.isEmpty()){
                        one = prefix + one
                    }

                    txtInput.text = removeZeroAfterDot((one.toDouble() + two.toDouble()).toString())
                }else  if (txtValue.contains("*")){
                    val splitValue = txtValue.split("*")
                    var one = splitValue[0]
                    var two = splitValue[1]

                    if(!prefix.isEmpty()){
                        one = prefix + one
                    }

                    txtInput.text = removeZeroAfterDot((one.toDouble() * two.toDouble()).toString())
                }else if(txtValue.contains("/")){
                    val splitValue = txtValue.split("/")
                    var one = splitValue[0]
                    var two = splitValue[1]

                    if(!prefix.isEmpty()){
                        one = prefix + one
                    }

                    txtInput.text = removeZeroAfterDot((one.toDouble() / two.toDouble()).toString())
                }

            }catch (e: ArithmeticException){
                e.printStackTrace()
            }
        }
    }
}