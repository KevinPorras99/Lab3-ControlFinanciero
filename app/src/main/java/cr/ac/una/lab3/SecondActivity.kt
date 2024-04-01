package cr.ac.una.lab3

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_movimiento)

        val datePicker = findViewById<DatePicker>(R.id.selectorFecha)
        val typeSpinner = findViewById<Spinner>(R.id.SelectorTIpoTarjeta)
        val items = resources.getStringArray(R.array.opciones_spinner)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, items)
        typeSpinner.adapter = adapter
        val amountEditText = findViewById<EditText>(R.id.EspacioMonto)

        val transaction = intent.getSerializableExtra("transaction") as? Transaction
        transaction?.let {
            val dateParts = it.Dia.split("/")
            datePicker.updateDate(dateParts[2].toInt(), dateParts[1].toInt() - 1, dateParts[0].toInt())
            val spinnerPosition = (typeSpinner.adapter as ArrayAdapter<String>).getPosition(it.Tipo_Tarjeta)
            typeSpinner.setSelection(spinnerPosition)
            amountEditText.setText(it.Monto.toString())
        }

        val insertButton = findViewById<Button>(R.id.insertar)
        insertButton.setOnClickListener {
            val date = "${datePicker.dayOfMonth}/${datePicker.month + 1}/${datePicker.year}"
            val type = typeSpinner.selectedItem.toString()
            val amount = amountEditText.text.toString().toDoubleOrNull()

            if (amount != null) {
                val newTransaction = Transaction(date, type, amount)
                val intent = Intent().apply {
                    putExtra("transaction", newTransaction)
                }
                setResult(RESULT_OK, intent)
                finish()
            } else {
                // Muestra un mensaje de error
            }
        }

        val exitButton = findViewById<Button>(R.id.Salir)
        exitButton.setOnClickListener {
            finish()
        }
    }
}