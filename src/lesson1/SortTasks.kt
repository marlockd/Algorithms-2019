@file:Suppress("UNUSED_PARAMETER")

package lesson1
import java.io.*
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

private val random = Random(Calendar.getInstance().timeInMillis)

private fun partition(elements: Array<Date?>, min: Int, max: Int): Int {
    val x = elements[min + random.nextInt(max - min + 1)]
    var left = min
    var right = max
    while (left <= right) {

        while (elements[left]!!.compareTo(x) < 0) {
            left++
        }

        while (elements[right]!!.compareTo(x) > 0) {
            right--

        }

        if (left <= right) {
            val temp = elements[left]
            elements[left] = elements[right]
            elements[right] = temp
            left++
            right--

        }
    }

    return right
}

private fun quickSort(elements: Array<Date?>, min: Int, max: Int) {
    if (min < max) {
        val border = partition(elements, min, max)
        quickSort(elements, min, border)
        quickSort(elements, border + 1, max)
    }
}

fun quickSort(elements: Array<Date?>) {
    quickSort(elements, 0, elements.size - 1)
}

private fun getTimeToString(time: Date): String {
    var meridiem = "AM"
    var h = time.hours
    val m = time.minutes
    val s = time.seconds
    if (h >= 12 && h != 24) {
        h -= 12
        meridiem = "PM"
    }
    if (h == 0) h = 12
    val ss = if (s < 10) "0$s" else "" + s
    val sm = if (m < 10) "0$m" else "" + m
    val sh = if (h < 10) "0$h" else "" + h
    return "$sh:$sm:$ss $meridiem"
}

/**
 * Сортировка времён
 *
 * Простая
 * (Модифицированная задача с сайта acmp.ru)
 *
 * Во входном файле с именем inputName содержатся моменты времени в формате ЧЧ:ММ:СС AM/PM,
 * каждый на отдельной строке. См. статью википедии "12-часовой формат времени".
 *
 * Пример:
 *
 * 01:15:19 PM
 * 07:26:57 AM
 * 10:00:03 AM
 * 07:56:14 PM
 * 01:15:19 PM
 * 12:40:31 AM
 *
 * Отсортировать моменты времени по возрастанию и вывести их в выходной файл с именем outputName,
 * сохраняя формат ЧЧ:ММ:СС AM/PM. Одинаковые моменты времени выводить друг за другом. Пример:
 *
 * 12:40:31 AM
 * 07:26:57 AM
 * 10:00:03 AM
 * 01:15:19 PM
 * 01:15:19 PM
 * 07:56:14 PM
 *
 * В случае обнаружения неверного формата файла бросить любое исключение.
 */
fun sortTimes(inputName: String, outputName: String) {
    val array: Array<Date?> = arrayOfNulls(1000)
    var size = 0
    val fileReader = FileReader(inputName)
    val bufferedReader = BufferedReader(fileReader)
    var string: String?
    try {
        do {
            string = bufferedReader.readLine()
            if (string != null) {
                val time: Date?
                try {
                    val sdf = SimpleDateFormat("hh:mm:ss a")
                    time = sdf.parse(string)
                } catch (pe: ParseException) {
                    throw IllegalArgumentException("wrong time")
                }

                if (time != null) {
                    array[size] = time
                    size++
                }
            }
        } while (string != null)
    } catch (e: IOException) {
        e.printStackTrace()
    }

    require(size != 0) { "file is empty" }
    quickSort(array)
    try {
        val fileWriter = FileWriter(outputName)
        val bufferedWriter = BufferedWriter(fileWriter)
        for (i in array.size - size until array.size) {
            if (array[i] != null) {
                bufferedWriter.write(array[i]?.let { getTimeToString(it) } + "\n")
            }
        }
        bufferedWriter.close()
    } catch (e: IOException) {
        e.printStackTrace()
    }
}


/**
 * Сортировка адресов
 *
 * Средняя
 *
 * Во входном файле с именем inputName содержатся фамилии и имена жителей города с указанием улицы и номера дома,
 * где они прописаны. Пример:
 *
 * Петров Иван - Железнодорожная 3
 * Сидоров Петр - Садовая 5
 * Иванов Алексей - Железнодорожная 7
 * Сидорова Мария - Садовая 5
 * Иванов Михаил - Железнодорожная 7
 *
 * Людей в городе может быть до миллиона.
 *
 * Вывести записи в выходной файл outputName,
 * упорядоченными по названию улицы (по алфавиту) и номеру дома (по возрастанию).
 * Людей, живущих в одном доме, выводить через запятую по алфавиту (вначале по фамилии, потом по имени). Пример:
 *
 * Железнодорожная 3 - Петров Иван
 * Железнодорожная 7 - Иванов Алексей, Иванов Михаил
 * Садовая 5 - Сидоров Петр, Сидорова Мария
 *
 * В случае обнаружения неверного формата файла бросить любое исключение.
 */
fun sortAddresses(inputName: String, outputName: String) {
    TODO()
}

/**
 * Сортировка температур
 *
 * Средняя
 * (Модифицированная задача с сайта acmp.ru)
 *
 * Во входном файле заданы температуры различных участков абстрактной планеты с точностью до десятых градуса.
 * Температуры могут изменяться в диапазоне от -273.0 до +500.0.
 * Например:
 *
 * 24.7
 * -12.6
 * 121.3
 * -98.4
 * 99.5
 * -12.6
 * 11.0
 *
 * Количество строк в файле может достигать ста миллионов.
 * Вывести строки в выходной файл, отсортировав их по возрастанию температуры.
 * Повторяющиеся строки сохранить. Например:
 *
 * -98.4
 * -12.6
 * -12.6
 * 11.0
 * 24.7
 * 99.5
 * 121.3
 */
fun sortTemperatures(inputName: String, outputName: String) {
    val arrayList = ArrayList<Double>()
    val array: DoubleArray
    var fileReader: FileReader? = null
    try {
        fileReader = FileReader(inputName)
    } catch (e: FileNotFoundException) {
        e.printStackTrace()
    }

    val bufferedReader = BufferedReader(fileReader!!)
    var line: String?
    try {
        do {
            line = bufferedReader.readLine()
            if (line != null) {
                arrayList.add(java.lang.Double.valueOf(line))
            }
        } while (line != null)
    } catch (e: IOException) {
        e.printStackTrace()
    }
    array = arrayList.toDoubleArray()
    quickSort(array)
    try {
        val fileWriter = FileWriter(outputName)
        val bufferedWriter = BufferedWriter(fileWriter)
        for (value in array) {
            bufferedWriter.write(value.toString() + "\n")
        }
        bufferedWriter.close()
    } catch (e: IOException) {
        e.printStackTrace()
    }

}

private fun partition(elements: DoubleArray, min: Int, max: Int): Int {
    val x = elements[min + random.nextInt(max - min + 1)]
    var left = min
    var right = max
    while (left <= right) {
        while (elements[left].compareTo(x) < 0) {
            left++
        }
        while (elements[right].compareTo(x) > 0) {
            right--
        }
        if (left <= right) {
            val temp = elements[left]
            elements[left] = elements[right]
            elements[right] = temp
            left++
            right--
        }
    }
    return right
}

private fun quickSort(elements: DoubleArray, min: Int, max: Int) {
    if (min < max) {
        val border = partition(elements, min, max)
        quickSort(elements, min, border)
        quickSort(elements, border + 1, max)
    }
}

fun quickSort(elements: DoubleArray) {
    quickSort(elements, 0, elements.size - 1)
}

/**
 * Сортировка последовательности
 *
 * Средняя
 * (Задача взята с сайта acmp.ru)
 *
 * В файле задана последовательность из n целых положительных чисел, каждое в своей строке, например:
 *
 * 1
 * 2
 * 3
 * 2
 * 3
 * 1
 * 2
 *
 * Необходимо найти число, которое встречается в этой последовательности наибольшее количество раз,
 * а если таких чисел несколько, то найти минимальное из них,
 * и после этого переместить все такие числа в конец заданной последовательности.
 * Порядок расположения остальных чисел должен остаться без изменения.
 *
 * 1
 * 3
 * 3
 * 1
 * 2
 * 2
 * 2
 */
fun sortSequence(inputName: String, outputName: String) {
    var maxValue = 0
    val array: Array<Int>
    val arrayList = ArrayList<Int>()
    var fileReader: FileReader? = null
    try {
        fileReader = FileReader(inputName)
    } catch (e: FileNotFoundException) {
        e.printStackTrace()
    }
    val bufferedReader = BufferedReader(fileReader!!)
    var string: String?
    try {
        do {
            string = bufferedReader.readLine()
            if (string != null) {
                arrayList.add(Integer.valueOf(string))
            }
        } while (string != null)
    } catch (e: IOException) {
        e.printStackTrace()
    }
    array = arrayList.toTypedArray()
    val countArray = IntArray(Int.MAX_VALUE/10)
    for (value in array) {
        countArray[value]++
        if (value > maxValue) maxValue = value
    }
    var max = 0
    var value = 0
    for (i in countArray.indices) {
        if (countArray[i] > max) {
            max = countArray[i]
            value = i
        }
    }
    var j = 0
    for (i in array.indices) {
        if (array[i] != value) {
            if (j < i) {
                val temp = array[j]
                array[j] = array[i]
                array[i] = temp
            }
            j++
        }
    }
    try {
        val fileWriter = FileWriter(outputName)
        val bufferedWriter = BufferedWriter(fileWriter)
        for (value in array) {
            bufferedWriter.write(value.toString() + "\n")
        }
        bufferedWriter.close()
    } catch (e: IOException) {
        e.printStackTrace()
    }
}

/**
 * Соединить два отсортированных массива в один
 *
 * Простая
 *
 * Задан отсортированный массив first и второй массив second,
 * первые first.size ячеек которого содержат null, а остальные ячейки также отсортированы.
 * Соединить оба массива в массиве second так, чтобы он оказался отсортирован. Пример:
 *
 * first = [4 9 15 20 28]
 * second = [null null null null null 1 3 9 13 18 23]
 *
 * Результат: second = [1 3 4 9 9 13 15 20 23 28]
 */
fun <T : Comparable<T>> mergeArrays(first: Array<T>, second: Array<T?>) {
    TODO()
}

