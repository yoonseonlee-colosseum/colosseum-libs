package com.colosseum.coloutil.clock

import java.time.LocalDate

class FakeDateProvider : DateProvider {
    override fun nowDate(): LocalDate {
        return LocalDate.of(9999, 12, 31);
    }
}
