package day1.part2

import java.io.File


val one = "one"
val two = "two"
val three = "three"
val four = "four"
val five = "five"
val six = "six"
val seven = "seven"
val eight = "eight"
val nine = "nine"

val straightNumbersMapByFirstLetter = mapOf(
    'o' to arrayOf(one),
    't' to arrayOf(two, three),
    'f' to arrayOf(four, five),
    's' to arrayOf(six, seven),
    'e' to arrayOf(eight),
    'n' to arrayOf(nine)
)

val reversedNumbersMapByLastLetter = mapOf(
    'e' to arrayOf(one.reversed(), three.reversed(), five.reversed(), nine.reversed()),
    'o' to arrayOf(two.reversed()),
    'r' to arrayOf(four.reversed()),
    'x' to arrayOf(six.reversed()),
    'n' to arrayOf(seven.reversed()),
    't' to arrayOf(eight.reversed())
)


val numbersBinding = mapOf(
    one to 1,
    two to 2,
    three to 3,
    four to 4,
    five to 5,
    six to 6,
    seven to 7,
    eight to 8,
    nine to 9
)


fun main() {
    var totalEfficient = 0

    val before = System.currentTimeMillis();

    File("resources/day1/input.txt").forEachLine { line ->
        val first: Char = findFirstNumberFromLine(line, straightNumbersMapByFirstLetter, false)

        val reversedLine = line.reversed();
        val last: Char = findFirstNumberFromLine(reversedLine, reversedNumbersMapByLastLetter, true)

        val fusion: String = first.toString().plus(last)
        totalEfficient += fusion.toInt()
    }

    val after = System.currentTimeMillis();
    println(totalEfficient);
    println("time in " + (after - before) + " ms.")

}

private fun findFirstNumberFromLine(
    line: String,
    numbersInCharacterMapping: Map<Char, Array<String>>,
    needToReverseWord: Boolean
): Char {

    var firstNumberFound: Char = Char.MIN_VALUE
    var numberToConstruct = ""

    for ((index, currentCharacter) in line.toCharArray().withIndex()) {
        if (currentCharacter.isDigit()) {
            firstNumberFound = currentCharacter
            break
        } else {
            if (numbersInCharacterMapping.containsKey(currentCharacter)) {
                numberToConstruct = numberToConstruct.plus(currentCharacter)
                val listToCompare = numbersInCharacterMapping.get(currentCharacter)!!
                var findAMatch = false
                for (word in listToCompare) {
                    //We already have the first letter
                    for (wordIndex in 1..word.toCharArray().size - 1) {
                        val currentCharacterInWord = word.get(wordIndex)
                        if (currentCharacterInWord == line.get(index + wordIndex)) {
                            numberToConstruct = numberToConstruct.plus(currentCharacterInWord)
                        } else {
                            break
                        }
                    }
                    if (word == numberToConstruct) {
                        firstNumberFound = changeWordToDigit(word, needToReverseWord)
                        findAMatch = true
                        break
                    } else {
                        //We flush the number to construct, we try with another word.
                        numberToConstruct = numberToConstruct.removeRange(1, numberToConstruct.length)
                    }
                }
                if (!findAMatch) {
                    numberToConstruct = ""
                }
            }
        }

        if (firstNumberFound.isDigit()) {
            break
        }
    }
    return firstNumberFound
}

private fun changeWordToDigit(
    word: String,
    needToReverse: Boolean,
): Char {
    var wordReadable = word
    if (needToReverse) {
        wordReadable = word.reversed()
    }
    return numbersBinding.get(wordReadable)!!.toString()!!.get(0)
}

