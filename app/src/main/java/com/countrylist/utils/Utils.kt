package com.countrylist.utils

class Utils {
    fun listToString(list: List<String>): String {
        var output = "";
        for (s in list)
            output = "$output$s;"
        return output
    }
}