package com.marmatsan.core_domain.use_case

class FilterOutWeight {
    // TODO: Send error message if the input is being invalid
    operator fun invoke(text: String): String {
        val weight = text.filter { it.isDigit() || it == '.' }
        return if (isValidWeight(weight)) {
            weight
        } else {
            ""
        }
    }

    private fun isValidWeight(weight: String): Boolean {
        // Check if the whole part of the number has at least 2 digits
        val wholePartDigits = weight.indexOf('.')
        if (wholePartDigits in 0..1) {
            return false
        }

        // Check if the decimal part has at most 1 digit
        val decimalPart = weight.substringAfter('.', "")
        return decimalPart.length <= 1
    }
}