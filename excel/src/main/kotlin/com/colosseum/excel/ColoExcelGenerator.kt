package com.colosseum.excel

import com.colosseum.excel.style.ExcelBodyCreator
import com.colosseum.excel.style.ExcelBodyStyleGenerator
import com.colosseum.excel.style.ExcelHeaderCreator
import com.colosseum.excel.style.ExcelHeaderStyleGenerator
import com.colosseum.excel.style.ExcelStylesFactory
import com.colosseum.excel.style.applystrategy.DefaultExcelBodyStyle
import com.colosseum.excel.style.applystrategy.DefaultExcelHeaderStyle
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.xssf.streaming.SXSSFWorkbook
import java.io.ByteArrayOutputStream
import java.io.OutputStream

open class ColoExcelGenerator<T> protected constructor(
	dataList: List<T>,
	type: Class<T>,
	headerStyle: ExcelHeaderStyleGenerator<T>,
	bodyStyle: ExcelBodyStyleGenerator<T>,
) {
	private val workbook: SXSSFWorkbook = SXSSFWorkbook()
	private val sheet: Sheet = workbook.createSheet()
	private val metadata: ExcelMetadata = ExcelMetadataFactory.generateMetadata(type)
	private val dataList: List<T> = dataList
	private val styles = ExcelStylesFactory.create(type, workbook, headerStyle, bodyStyle)

	constructor(
		dataList: List<T>,
		type: Class<T>,
	) : this(
		dataList = dataList,
		type = type,
		headerStyle = DefaultExcelHeaderStyle(),
		bodyStyle = DefaultExcelBodyStyle(),
	)

	fun toResult(): ExcelResult {
		val byteArrayOutputStream = ByteArrayOutputStream()
		generate().use { workbook ->
			workbook.write(byteArrayOutputStream)
		}
		return ExcelResult(
			fileName = metadata.excelFileName,
			bytes = byteArrayOutputStream.toByteArray(),
		)
	}

	private fun generate(): SXSSFWorkbook {
		val headerCreator = ExcelHeaderCreator(sheet, metadata, styles)
		val bodyCreator = ExcelBodyCreator<T>(sheet, metadata, styles)

		val lastHeaderRow = headerCreator.createHeader()
		bodyCreator.createBody(dataList, lastHeaderRow)
		return workbook
	}

	private fun write(outputStream: OutputStream) {
		generate().use { workbook ->
			workbook.write(outputStream)
		}
	}
}
