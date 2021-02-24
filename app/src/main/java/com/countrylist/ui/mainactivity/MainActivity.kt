package com.countrylist.ui.mainactivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.countrylist.ui.adapters.CountryAdapter
import com.countrylist.R
import com.countrylist.ui.bordercountriesactivity.BorderCountriesActivity
import com.countrylist.model.Country
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*

const val BORDER_COUNTRIES = "BORDER_COUNTRIES"
const val COUNTRY_NAME = "COUNTRY_NAME"

class MainActivity : AppCompatActivity(), CountryAdapter.OnClickListener {
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: CountryAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        setupUI()
        setupButtons()

        setupObservers()
        viewModel.getAllCountries()
    }

    private fun setupButtons() {
        sortByNameButton.setOnClickListener { viewModel.sortByCountry() }
        sortByAreaButton.setOnClickListener { viewModel.sortByArea() }
    }

    private fun setupUI() {
        setSupportActionBar(findViewById(R.id.toolbar))
        adapter = CountryAdapter(arrayListOf(), this);
        countries_recyclerView.layoutManager = LinearLayoutManager(this)
        countries_recyclerView.addItemDecoration(
            DividerItemDecoration(
                countries_recyclerView.context,
                (countries_recyclerView.layoutManager as LinearLayoutManager).orientation
            )
        )
        countries_recyclerView.adapter = adapter
    }

    private fun setupObservers() {

        viewModel.isDescentbyCountry.observe(this, Observer {
            if (adapter != null) adapter.sortByCounries(it)
            if (!it) sortByNameButton.setCompoundDrawablesWithIntrinsicBounds(
                0,
                0,
                R.drawable.ic_down,
                0
            )
            else sortByNameButton.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_up, 0)
        })

        viewModel.isDescentbyArea.observe(this, Observer {
            if (adapter != null) adapter.sortByArea(it)
            if (!it) sortByAreaButton.setCompoundDrawablesWithIntrinsicBounds(
                0,
                0,
                R.drawable.ic_down,
                0
            )
            else sortByAreaButton.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_up, 0)
        })

        viewModel.isLoading.observe(this, Observer {
            progressBar.visibility = it
        })

        viewModel.countries.observe(this, Observer {
            changeListViewData(it)
        })

        viewModel.errorMessage.observe(
            this,
            Observer { Toast.makeText(this, it, Toast.LENGTH_LONG).show() })

    }

    private fun changeListViewData(countries: List<Country>) {
        adapter.changeData(countries)
    }

    override fun onClickItem(country: Country) {
        if (country.borders.isEmpty())
            noBorderCountries(country.name)
        else
            showBorderCountries(country)

    }

    private fun showBorderCountries(country: Country) {
        val intent = Intent(this, BorderCountriesActivity::class.java).apply {
            val borderCountries = country.borders.reduce { acc, s -> "$acc;$s" }
            putExtra(BORDER_COUNTRIES, borderCountries)
            putExtra(COUNTRY_NAME, country.name)
        }
        startActivity(intent)

    }

    private fun noBorderCountries(countryName: String) {
        val text = "$countryName doesn't have neighbors"
        val duration = Toast.LENGTH_SHORT
        val toast = Toast.makeText(applicationContext, text, duration)
        toast.show()
    }

    override fun onBackPressed() {
        //do nothing
        //  super.onBackPressed()
    }
}
