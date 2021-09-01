package com.aleangelozi.mvvm_pattern.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import com.aleangelozi.mvvm_pattern.R
import com.aleangelozi.mvvm_pattern.view_model.CountriesViewModel

class ViewActivity : AppCompatActivity() {

    private val listValues: MutableList<String> = ArrayList()
    lateinit var adapter: ArrayAdapter<String>
    lateinit var list: ListView
    lateinit var viewModel: CountriesViewModel
    lateinit var retryButton: Button
    lateinit var progress: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view)

        viewModel = ViewModelProvider(this).get(CountriesViewModel::class.java)

        list = findViewById(R.id.list)
        retryButton = findViewById(R.id.retryButton)
        progress = findViewById(R.id.progress)
        adapter = ArrayAdapter(this, R.layout.row_layout, R.id.listText, listValues)

        list.adapter = adapter
        list.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                Toast.makeText(
                    this@ViewActivity, "You clicked " +
                            listValues[position], Toast.LENGTH_SHORT
                ).show()
            }

        observeViewModel()

    }

    private fun observeViewModel() {
        viewModel.getCountries().observe(this, { countries ->
            if (countries != null) {
                listValues.clear()
                listValues.addAll(countries)
                list.visibility = View.VISIBLE
                adapter.notifyDataSetChanged()
            } else {
                list.visibility = View.GONE
            }
        })

        viewModel.getCountryError().observe(this, { error ->
            progress.visibility = View.GONE
            if (error) {
                Toast.makeText(this, getString(R.string.error_message), Toast.LENGTH_SHORT).show()
                retryButton.visibility = View.VISIBLE
            } else {
                retryButton.visibility = View.GONE
            }
        })
    }

    fun onRetry(view: View) {
        viewModel.onRefresh()
        list.visibility = View.GONE
        retryButton.visibility = View.GONE
        progress.visibility = View.VISIBLE
    }
}