package day1.part1

import java.io.File

fun main() {

    var totalEfficient = 0
    File("resources/day1/input.txt").forEachLine {
        //println(it)
        val first : Char? =  it.find{ c: Char -> c.isDigit()}
        val last : Char? = it.findLast{ c: Char -> c.isDigit()}

        val fusion: String = first.toString().plus(last)
        totalEfficient += fusion.toInt()
    }

    println(totalEfficient);

}