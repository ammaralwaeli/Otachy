package com.srit.otachy

import org.threeten.bp.format.DateTimeFormatter
import java.util.*

val dateTimeDisFormatter:DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm a", Locale("ar"))

val dateTimeBackendFormatter:DateTimeFormatter= DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")