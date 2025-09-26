package com.colosseum.coloutil.clock

import java.time.LocalDate

interface DateProvider {
    fun nowDate(): LocalDate
}
