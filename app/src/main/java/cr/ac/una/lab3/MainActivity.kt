package cr.ac.una.lab3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.content.Intent
import android.app.Activity
import android.widget.ListView

class MainActivity : AppCompatActivity() {
    private val transactions = mutableListOf<Transaction>()
    private lateinit var adapter: ArrayAdapter<Transaction>
    private lateinit var listView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listView = findViewById(R.id.listaMovimientos)
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, transactions)
        listView.adapter = adapter

        val botonNuevo = findViewById<Button>(R.id.botonNuevo)
        botonNuevo.setOnClickListener {
            val intent = Intent(this, SecondActivity::class.java)
            startActivityForResult(intent, 1)
        }
        listView.setOnItemClickListener { parent, view, position, id ->
            val selectedTransaction = transactions[position]
            val intent = Intent(this, SecondActivity::class.java).apply {
                putExtra("transaction", selectedTransaction)
            }
            startActivity(intent)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            val transaction = data?.getSerializableExtra("transaction") as Transaction
            transactions.add(transaction)
            adapter.notifyDataSetChanged() // Notifica al adaptador que los datos han cambiado
        }
    }

}