package com.example.calcuadora

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import java.lang.IllegalArgumentException

enum class Operador {Suma, Resta, Division, Multiplicacion, Ninguno}


private var operador: Operador = Operador.Ninguno

class MainActivity : AppCompatActivity() {

    private var NumString = StringBuilder()
    private var OutputString = StringBuilder()
    private lateinit var display: TextView
    private lateinit var displayOperacion: TextView
    private lateinit var buttonAC: Button
    private lateinit var NumerosBTN: Array<Button>
    private lateinit var operadoresBTN: Array<Button>
    private var operadorAnterior: Boolean = false
    private var existeOperador: Boolean = false
    private var resultadoAnterior: Boolean = false
    private var Igual: Boolean = false
    private var hayPunto: Boolean = false
    private var operando1: Double = 0.0
//    private var operando2: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Declaración de elementos


        display = findViewById<EditText>(R.id.TextView)
        displayOperacion = findViewById<EditText>(R.id.textView2)

        val button9: Button = findViewById<Button>(R.id.Button9)
        val button8: Button = findViewById<Button>(R.id.Button8)
        val button7: Button = findViewById<Button>(R.id.Button7)
        val button6: Button = findViewById<Button>(R.id.Button6)
        val button5: Button = findViewById<Button>(R.id.Button5)
        val button4: Button = findViewById<Button>(R.id.Button4)
        val button3: Button = findViewById<Button>(R.id.Button3)
        val button2: Button = findViewById<Button>(R.id.Button2)
        val button1: Button = findViewById<Button>(R.id.Button1)
        val button0: Button = findViewById<Button>(R.id.Button0)

        buttonAC = findViewById<Button>(R.id.ButtonAC)

        val buttonDivision: Button = findViewById<Button>(R.id.ButtonDivision)
        val buttonMultiplicacion: Button = findViewById<Button>(R.id.ButtonMultiplicacion)
        val buttonMenos: Button = findViewById<Button>(R.id.ButtonMenos)
        val buttonMas: Button = findViewById<Button>(R.id.ButtonMas)
        val buttonIgual: Button = findViewById<Button>(R.id.ButtonIgual)
        val buttonPunto: Button = findViewById<Button>(R.id.ButtonPunto)
        val buttonMasMenos: Button = findViewById<Button>(R.id.ButtonMasMenos)

        buttonPunto.setOnClickListener{buttonPuntoClicked()}

        buttonAC.setOnClickListener{buttonACClicked()}

        NumerosBTN = arrayOf(button0,button1,button2,button3,button4,button5,button6,button7,button8,button9)
        for (i in NumerosBTN){
            i.setOnClickListener{numberButtonClicked(i)}
        }
        operadoresBTN = arrayOf(buttonDivision,buttonMas,buttonMenos,buttonMultiplicacion)
        for (i in operadoresBTN){
            i.setOnClickListener{operatorButtonClicked(i)}
        }

        buttonIgual.setOnClickListener{buttonIgualClicked()}


        buttonMasMenos.setOnClickListener{buttonMasMenosClicked()}

    }

    private fun buttonPuntoClicked() {

        if(Igual)buttonACClicked()
        if(!hayPunto){
//            if(operadorAnterior){
//                if(NumString.toString() == "." || NumString.toString() == "0." || NumString.toString() == ".0"){
//                    operando1 = 0.0
//                    NumString.clear()
//                    operadorAnterior = false
//                    hayPunto = true
//                }
//                else{
//                    operando1 = NumString.toString().toDouble()
//                    NumString.clear()
//                    operadorAnterior = false
//                    hayPunto = true
//                }
//            }
            NumString.append(".")
            hayPunto = true
            updateDisplay()
        }
    }

    private fun buttonACClicked() {
        Igual = false
        NumString.clear()
        OutputString.clear()
        operadorAnterior = false
        existeOperador = false
        resultadoAnterior = false
        hayPunto = false
        display.text = "0"
        displayOperacion.text = "0"

    }

    private fun buttonMasMenosClicked() {
        if(Igual)buttonACClicked()
        var text: String
        if(NumString.toString() != ""){
            text = (NumString.toString().toDouble()*(-1)).toString()
            NumString.clear()
            NumString.append(text)
            //OutputString.clear()
            //OutputString.append(text)
            updateDisplay()
            hayPunto = true
        }
    }

    private fun numberButtonClicked(button: Button) {
        if(Igual)buttonACClicked()
        NumString.append(button.text)
        //OutputString.append(button.text)
        updateDisplay()
    }

    private fun operatorButtonClicked(button: Button) {

        if(Igual)buttonACClicked()
        if(NumString.toString() != ""){
            if(!operadorAnterior && !existeOperador){
                if(button.text == "+") operador = Operador.Suma
                else if(button.text == "-") operador = Operador.Resta
                else if(button.text == "x") operador = Operador.Multiplicacion
                else if(button.text == "÷") operador = Operador.Division
                else operador = Operador.Ninguno

                if(NumString.toString() == "." || NumString.toString() == "0." || NumString.toString() == ".0"){
                    operando1 = 0.0
                    OutputString.append(operando1.toString())
                    OutputString.append(button.text)
                    updateDisplayOperacion()
                    NumString.clear()
                    operadorAnterior = false
                    hayPunto = false
                }
                else{
                    operando1 = NumString.toString().toDouble()
                    OutputString.append(operando1.toString())
                    OutputString.append(button.text)
                    updateDisplayOperacion()
                    NumString.clear()
                    operadorAnterior = false
                    hayPunto = false
                }

                operadorAnterior = true
                existeOperador = true
                displayOperacion.text = OutputString
            }
        }

    }

    private fun buttonIgualClicked() {

        if(Igual)buttonACClicked()
        if(existeOperador && NumString.toString() != ""){
            var operando2: Double
            if(NumString.toString() == "." || NumString.toString() == "0." || NumString.toString() == ".0"){
                operando2 = 0.0
            }
            else{
                operando2 = NumString.toString().toDouble()
            }
            var resultado: Double
            when(operador){
                Operador.Suma -> resultado = operando1 + operando2
                Operador.Division -> resultado = operando1 / operando2
                Operador.Resta -> resultado = operando1 - operando2
                Operador.Multiplicacion -> resultado = operando1 * operando2
                else -> resultado = 0.0
            }
            OutputString.append(operando2.toString())
            updateDisplayOperacion()
            NumString.clear()
            NumString.append(resultado.toString())
            updateDisplay()
            operadorAnterior = true
            resultadoAnterior = true
            operadorAnterior = true
            existeOperador = false
            Igual = true
        }

    }

    private fun updateDisplay() {
        try {
            display.text = NumString
        }
        catch (e:IllegalArgumentException){
            NumString.clear()
            display.text = "ERROR"
        }
    }

    private fun updateDisplayOperacion() {
        try {
            displayOperacion.text = OutputString
        }
        catch (e:IllegalArgumentException){
            OutputString.clear()
            displayOperacion.text = "ERROR"
        }
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        display?.text = savedInstanceState.getString("Display".toString())
        displayOperacion?.text = savedInstanceState.getString("DisplayOperacion")
        operadorAnterior = savedInstanceState.getBoolean("operadorAnterior")
        Igual = savedInstanceState.getBoolean("Igual")
        existeOperador = savedInstanceState.getBoolean("existeOperador")
        resultadoAnterior = savedInstanceState.getBoolean("resultadoAnterior")
        hayPunto = savedInstanceState.getBoolean("hayPunto")
        NumString.append(savedInstanceState.getString("NumString"))
        OutputString.append(savedInstanceState.getString("OutputString"))
        operando1 = savedInstanceState.getDouble("operando1Save")
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("DisplayOperacion", displayOperacion?.text.toString())
        outState.putString("Display", display?.text.toString())
        outState.putBoolean("operadorAnterior", operadorAnterior)
        outState.putBoolean("Igual", Igual)
        outState.putBoolean("existeOperador", existeOperador)
        outState.putBoolean("resultadoAnterior", resultadoAnterior)
        outState.putBoolean("hayPunto", hayPunto)
        outState.putString("NumString", NumString.toString())
        outState.putString("OutputString", OutputString.toString())
        outState.putDouble("operando1Save", operando1)

    }

    override fun onStop() {
        super.onStop()
    }

    override fun onRestart() {
        super.onRestart()
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
