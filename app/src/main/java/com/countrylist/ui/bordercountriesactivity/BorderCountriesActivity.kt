package com.countrylist.ui.bordercountriesactivity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.countrylist.ui.adapters.CountryAdapter
import com.countrylist.R
import com.countrylist.ui.mainactivity.BORDER_COUNTRIES
import com.countrylist.model.Country
import kotlinx.android.synthetic.main.activity_border_counries.*
import kotlinx.android.synthetic.main.activity_main.progressBar

class BorderCountriesActivity : AppCompatActivity(), CountryAdapter.OnClickListener {
    private lateinit var viewModel: BorderCountriesViewModel
    private lateinit var adapter: CountryAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_border_counries)
        //   setSupportActionBar(findViewById(R.id.toolbar))
        val borderCountries = intent.getStringExtra(BORDER_COUNTRIES)
        viewModel = ViewModelProviders.of(this).get(BorderCountriesViewModel::class.java)
        setupUI()
        setupObservers()
        viewModel.getBorderCountries(borderCountries)
    }

    private fun setupObservers() {
        viewModel.isLoading.observe(this, Observer {
            progressBar.visibility = it
        })

        viewModel.countries.observe(this, Observer {
            changeLisViewData(it)
        })
    }

    private fun setupUI() {
        adapter = CountryAdapter(arrayListOf(), this);
        border_countries_recyclerView.layoutManager = LinearLayoutManager(this)
        border_countries_recyclerView.addItemDecoration(
            DividerItemDecoration(
                border_countries_recyclerView.context,
                (border_countries_recyclerView.layoutManager as LinearLayoutManager).orientation
            )
        )
        border_countries_recyclerView.adapter = adapter
    }

    private fun changeLisViewData(countries: List<Country>) {
        adapter.changeData(countries)
    }

    override fun onClickItem(country: Country) {
        val borderCountries = country.borders.reduce { acc, s -> "$acc;$s" }
        viewModel.getBorderCountries(borderCountries)
    }
}