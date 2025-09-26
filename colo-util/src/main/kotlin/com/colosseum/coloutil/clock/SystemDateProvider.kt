package com.colosseum.coloutil.clock

import java.time.LocalDate

class SystemDateProvider : DateProvider {
    override fun nowDate(): LocalDate = LocalDate.now()
}
