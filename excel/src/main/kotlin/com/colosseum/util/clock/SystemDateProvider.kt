package com.colosseum.util.clock

import java.time.LocalDate

class SystemDateProvider : DateProvider {
	override fun nowDate(): LocalDate = LocalDate.now()
}
