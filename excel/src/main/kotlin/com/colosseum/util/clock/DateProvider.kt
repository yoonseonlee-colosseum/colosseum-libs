package com.colosseum.util.clock

import java.time.LocalDate

interface DateProvider {
	fun nowDate(): LocalDate
}
