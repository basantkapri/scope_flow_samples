package com.example.samples.datesamples

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.samples.R
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*

private const val databaseFormat = "yyyy-MM-dd'T'hh:mm:ss"
private const val databaseFormatDay = "yyyy-MM-dd"
private const val firstDayOfUse = "2021-09-16"
private const val MILLISECONDS_IN_A_DAY = 86400000L

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //getData(1639562400000)
        //getData(1639562400000)

        //getDayMillisecondsWithoutTime(1639657362552)
        //getFirstMillisecondOfTheDayWithoutTime(1639657362552)
        //val AM_6  = getAnHourMilliSecondForTheDay(6)
        //val PM_10 = getAnHourMilliSecondForTheDay(20)
        //Timber.d("AM_6 -> $AM_6 || PM_10 -> $PM_10")

        val list = getTodayRange(18, 11, 2021)
        Timber.d("list -> $list")
    }
}

public fun getTodayRange(day: Int, month: Int, year: Int): MutableList<Long> {
    val list = mutableListOf<Long>()
    val calendar = getCalendar(day, month, year)

    val toMilli   = calendar.timeInMillis + (2 * MILLISECONDS_IN_A_DAY)
    val fromMilli = calendar.timeInMillis - (2 * MILLISECONDS_IN_A_DAY)

    list.add(fromMilli)
    list.add(toMilli)

    return list
}

public fun getDayMillisecondsWithoutTime(millisecondTime: Long): Long {
    val utcTimeZone = SimpleDateFormat(databaseFormatDay)
    utcTimeZone.timeZone = TimeZone.getDefault()
    val outputFormat = SimpleDateFormat(databaseFormatDay, Locale.US)
    val date = utcTimeZone.format(millisecondTime)
    val firstMiilliOfTheDay = outputFormat.parse(date).time
    Timber.d("firstMiilliOfTheDay -> $firstMiilliOfTheDay ")
    return firstMiilliOfTheDay
}


public fun getFirstMillisecondOfTheDayWithoutTime(millisecondTime: Long): Long {
    val utcTimeZone = SimpleDateFormat(databaseFormatDay)
    utcTimeZone.timeZone = TimeZone.getTimeZone("UTC")
    val date = utcTimeZone.format(millisecondTime)

    val firstMilliSecondOfTheDay = utcTimeZone.parse(date)
    Timber.d("firstMiilliOfTheDay -> ${firstMilliSecondOfTheDay.time} ")
    return firstMilliSecondOfTheDay.time
}

fun getAnHourMilliSecondForTheDay(hour : Int) : Long{
    val calendar = Calendar.getInstance()
    calendar.set(Calendar.HOUR_OF_DAY, hour)
    calendar.set(Calendar.MINUTE, 0)
    calendar.set(Calendar.SECOND, 0)
    calendar.set(Calendar.MILLISECOND, 0)
    return calendar.timeInMillis
}

public fun getData(milli: Long): Long {
    val outputFormat = SimpleDateFormat(databaseFormat)
    outputFormat.timeZone = TimeZone.getTimeZone("America/Adak")
    val localDate = outputFormat.format(milli)
    Log.e("localDate", "localDate -> $localDate")
    return milli
}

public fun getDateDiff(date: String, day: Int, month: Int, year: Int): Long {
    val currentCalendar = getCalendar(day, month, year)
    val calendar = Calendar.getInstance(Locale.US)
    val outputFormat = SimpleDateFormat(databaseFormat)
    calendar.timeInMillis = outputFormat.parse(date).time

    val noOfDaysPassedInCurrentWeek = calendar.get(Calendar.DAY_OF_WEEK)
    Log.e(
        "noOfDaysPassedInCurrentWeek",
        "noOfDaysPassedInCurrentWeek US  -> $noOfDaysPassedInCurrentWeek"
    )
    if (noOfDaysPassedInCurrentWeek > 1) {
        val diff = 7 - noOfDaysPassedInCurrentWeek + 1
        Log.e("noOfDaysPassedInCurrentWeek", "noOfDaysPassedInCurrentWeek diff -> $diff")
        calendar.add(Calendar.DAY_OF_YEAR, diff)
    }
    val final = outputFormat.format(calendar.time)
    Log.e("noOfDaysPassedInCurrentWeek", "noOfDaysPassedInCurrentWeek final -> $final")
    return (currentCalendar.timeInMillis - calendar.timeInMillis) / MILLISECONDS_IN_A_DAY
}

public fun getCalendar(day: Int, month: Int, year: Int): Calendar {
    val calendar = Calendar.getInstance()
    calendar.set(Calendar.YEAR, year)
    calendar.set(Calendar.MONTH, month)
    calendar.set(Calendar.DAY_OF_MONTH, day)
    return calendar
}