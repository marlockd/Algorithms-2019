@file:Suppress("UNUSED_PARAMETER")
package lesson6
import java.lang.Math.max

/**
 * трудоёмкость = O(m*n)
 * ресурсоёмкость = O(m*n)
 *
 * Наибольшая общая подпоследовательность.
 * Средняя
 *
 * Дано две строки, например "nematode knowledge" и "empty bottle".
 * Найти их самую длинную общую подпоследовательность -- в примере это "emt ole".
 * Подпоследовательность отличается от подстроки тем, что её символы не обязаны идти подряд
 * (но по-прежнему должны быть расположены в исходной строке в том же порядке).
 * Если общей подпоследовательности нет, вернуть пустую строку.
 * Если есть несколько самых длинных общих подпоследовательностей, вернуть любую из них.
 * При сравнении подстрок, регистр символов *имеет* значение.
 */
fun longestCommonSubSequence(first: String, second: String): String {
    var length1 = first.length + 1
    var length2 = second.length + 1
    val sequence = StringBuilder()
    val matrix = Array(length1) { IntArray(length2) }
    for (i in 1 until length1) {
        for (j in 1 until length2) {
            val ch1 = first[i - 1]
            val ch2 = second[j - 1]
            if (ch1 == ch2) {
                matrix[i][j] = matrix[i - 1][j - 1] + 1
            } else {
                matrix[i][j] = max(matrix[i - 1][j], matrix[i][j - 1])
            }
        }
    }
    length1--
    length2--
    while (length1 > 0 && length2 > 0) {
        val ch1 = first[length1 - 1]
        val ch2 = second[length2 - 1]
        if (ch1 == ch2) {
            sequence.append(ch1)
            length1--
            length2--
        } else if (matrix[length1 - 1][length2] >= matrix[length1][length2 - 1]) {
            length1--
        } else {
            length2--
        }
    }
    return sequence.reverse().toString()
}

/**
 * Наибольшая возрастающая подпоследовательность
 * Сложная
 *
 * Дан список целых чисел, например, [2 8 5 9 12 6].
 * Найти в нём самую длинную возрастающую подпоследовательность.
 * Элементы подпоследовательности не обязаны идти подряд,
 * но должны быть расположены в исходном списке в том же порядке.
 * Если самых длинных возрастающих подпоследовательностей несколько (как в примере),
 * то вернуть ту, в которой числа расположены раньше (приоритет имеют первые числа).
 * В примере ответами являются 2, 8, 9, 12 или 2, 5, 9, 12 -- выбираем первую из них.
 */
fun longestIncreasingSubSequence(list: List<Int>): List<Int> {
    TODO()
}

/**
 * Самый короткий маршрут на прямоугольном поле.
 * Средняя
 *
 * В файле с именем inputName задано прямоугольное поле:
 *
 * 0 2 3 2 4 1
 * 1 5 3 4 6 2
 * 2 6 2 5 1 3
 * 1 4 3 2 6 2
 * 4 2 3 1 5 0
 *
 * Можно совершать шаги длиной в одну клетку вправо, вниз или по диагонали вправо-вниз.
 * В каждой клетке записано некоторое натуральное число или нуль.
 * Необходимо попасть из верхней левой клетки в правую нижнюю.
 * Вес маршрута вычисляется как сумма чисел со всех посещенных клеток.
 * Необходимо найти маршрут с минимальным весом и вернуть этот минимальный вес.
 *
 * Здесь ответ 2 + 3 + 4 + 1 + 2 = 12
 */
fun shortestPathOnField(inputName: String): Int {
    val lines = java.io.FileReader(inputName).readLines()
    val input = ArrayList<ArrayList<Int>>()
    for (i in lines.indices) {
        val line = lines[i].split(" ")
        input.add(arrayListOf())
        for (j in line.indices) {
            input[i].add(line[j].toInt())
        }
    }
    val height = input.size
    val width = input[0].size
    val result = Array(height) { IntArray(width) }
    result[0][0] = input[0][0]
    for (i in 1 until height) {
        result[i][0] = result[i - 1][0] + input[i][0]
    }
    for (i in 1 until width) {
        result[0][i] = result[0][i - 1] + input[0][i]
    }
    for (i in 1 until height) {
        for (j in 1 until width) {
            val up = result[i - 1][j] + input[i][j]
            val left = result[i][j - 1] + input[i][j]
            val back = result[i - 1][j - 1] + input[i][j]
            val value = minOf(up, left, back)
            result[i][j] = value
        }
    }
    return result[height - 1][width - 1]
}


// Задачу "Максимальное независимое множество вершин в графе без циклов"
// смотрите в уроке 5