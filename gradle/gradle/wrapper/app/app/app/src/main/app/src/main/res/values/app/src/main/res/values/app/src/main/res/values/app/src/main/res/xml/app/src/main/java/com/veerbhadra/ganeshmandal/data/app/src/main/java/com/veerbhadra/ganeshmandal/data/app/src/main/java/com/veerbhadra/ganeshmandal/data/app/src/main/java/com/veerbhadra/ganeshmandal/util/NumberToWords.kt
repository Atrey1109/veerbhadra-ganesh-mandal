package com.veerbhadra.ganeshmandal.util

object NumberToWords {
    private val units = arrayOf(
        "", "एक", "दोन", "तीन", "चार", "पाच", "सहा", "सात", "आठ", "नऊ", "दहा",
        "अकरा", "बारा", "तेरा", "चौदा", "पंधरा", "सोळा", "सतरा", "अठरा", "एकोणीस"
    )
    private val tens = arrayOf(
        "", "", "वीस", "तीस", "चाळीस", "पन्नास", "साठ", "सत्तर", "ऐंशी", "नव्वद"
    )

    fun convertToMarathi(amount: Long): String {
        if (amount == 0L) return "शून्य रुपये फक्त"
        return convert(amount) + " रुपये फक्त"
    }

    private fun convert(n: Long): String {
        return when {
            n < 20 -> units[n.toInt()]
            n < 100 -> tens[(n / 10).toInt()] +
                    if (n % 10 != 0L) " " + units[(n % 10).toInt()] else ""
            n < 1000 -> units[(n / 100).toInt()] + " शे " + convert(n % 100)
            n < 100000 -> convert(n / 1000) + " हजार " + convert(n % 1000)
            n < 10000000 -> convert(n / 100000) + " लाख " + convert(n % 100000)
            else -> convert(n / 10000000) + " कोटी " + convert(n % 10000000)
        }.trim()
    }
}
