package com.countrylist.ui.adapters

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

    private var sortByAreaDescent: Boolean = false

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(country: Country, onClickListener: OnClickListener) {
            itemView.apply {
                country_name.text = country.name
                country_native_name.text = country.nativeName
                country_area.text = country.area.toString()
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

    fun sortByCounries(sortByDescent: Boolean) {
        // countryList.sortByDescending { country -> country.name  }

        if (sortByDescent)
            countryList.sortByDescending { country -> country.name }
        else
            countryList.sortBy { country -> country.name }
        notifyDataSetChanged()
    }

    fun sortByArea(sortByDescent: Boolean) {
        if (sortByDescent)
            countryList.sortByDescending { country -> country.area }
        else
            countryList.sortBy { country -> country.area }
        notifyDataSetChanged()
    }

    interface OnClickListener {
        fun onClickItem(country: Country)
    }
}