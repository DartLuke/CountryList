package com.countrylist.Adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.countrylist.R
import com.countrylist.model.Country
import kotlinx.android.synthetic.main.item_view.view.*

class CountryAdapter(
    private val countryList: ArrayList<Country>,
    private var onClickListener: OnClickListener
) :
    RecyclerView.Adapter<CountryAdapter.MyViewHolder>() {

    private var sortByAreaDescent: Boolean=false

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(country: Country, onClickListener: OnClickListener) {
            itemView.apply {
                country_name.text = country.name
                country_native_name.text = country.nativeName
                country_region.text= country.area.toString()
                itemView.setOnClickListener { onClickListener.onClickItem(country) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder =
        MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_view, parent, false))


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(countryList[position], onClickListener)
    }

    override fun getItemCount(): Int = countryList.size

    fun changeData(countryList: List<Country>) {
        this.countryList.apply {
            clear()
            addAll(countryList)
        }

        notifyDataSetChanged()
    }

    fun sortByCounries()
    {
        countryList.sortByDescending { country -> country.name  }
    }

    fun sortByArea()
    {
Log.v("Test",sortByAreaDescent.toString())
        sortByAreaDescent = if (sortByAreaDescent) {
            countryList.sortByDescending { country -> country.area }
            false
        } else {
            countryList.sortBy { country -> country.area }
            true
        }
        Log.v("test",countryList.toString())
        notifyDataSetChanged()

    }

    interface OnClickListener {
        fun onClickItem(country: Country)
    }
}