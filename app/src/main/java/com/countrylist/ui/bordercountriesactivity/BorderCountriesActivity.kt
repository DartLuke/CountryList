package com.countrylist.ui.bordercountriesactivity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.countrylist.ui.adapters.CountryAdapter
import com.countrylist.R
import com.countrylist.ui.mainactivity.BORDER_COUNTRIES
import com.countrylist.model.Country
import com.countrylist.ui.mainactivity.COUNTRY_NAME
import kotlinx.android.synthetic.main.activity_border_countries.*
import kotlinx.android.synthetic.main.activity_main.progressBar
import kotlinx.android.synthetic.main.toolbar.*

class BorderCountriesActivity : AppCompatActivity(), CountryAdapter.OnClickListener {
    private lateinit var viewModel: BorderCountriesViewModel
    private lateinit var adapter: CountryAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_border_countries)
        setSupportActionBar(findViewById(R.id.toolbar))
        val borderCountries = intent.getStringExtra(BORDER_COUNTRIES)
        val countryName = intent.getStringExtra(COUNTRY_NAME)
        viewModel = ViewModelProviders.of(this).get(BorderCountriesViewModel::class.java)
        setupUI()
        setupObservers()
        setupButtons()
        viewModel.getBorderCountries(borderCountries, countryName)

    }


    private fun setupObservers() {
        viewModel.isLoading.observe(this, Observer {
            progressBar.visibility = it
        })

        viewModel.countries.observe(this, Observer {
            changeLisViewData(it)
        })
        viewModel.errorMessage.observe(
            this,
            Observer { Toast.makeText(this, it, Toast.LENGTH_LONG).show() })

        viewModel.isDescentbyCountry.observe(this, Observer {
            if (adapter != null) adapter.sortByCounries(it)
            if (!it) sortByNameButton.setCompoundDrawablesWithIntrinsicBounds(
                0, 0, R.drawable.ic_down, 0
            )
            else sortByNameButton.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_up, 0)
        })

        viewModel.isDescentbyArea.observe(this, Observer {
            if (adapter != null) adapter.sortByArea(it)
            if (!it) sortByAreaButton.setCompoundDrawablesWithIntrinsicBounds(
                0, 0, R.drawable.ic_down, 0
            )
            else sortByAreaButton.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_up, 0)
        })
        viewModel.countryName.observe(this, Observer { toolbar_country_name.text = it })
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
        toolbar_country_name.visibility=View.VISIBLE
    }

    private fun changeLisViewData(countries: List<Country>) {
        if (adapter != null) adapter.changeData(countries)
    }

    private fun setupButtons() {
        sortByNameButton.setOnClickListener { viewModel.sortByCountry() }
        sortByAreaButton.setOnClickListener { viewModel.sortByArea() }
    }

    override fun onClickItem(country: Country) {
        val borderCountries = country.borders.reduce { acc, s -> "$acc;$s" }
        viewModel.getBorderCountries(borderCountries,country.name)
    }
}