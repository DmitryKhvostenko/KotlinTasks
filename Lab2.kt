class InvalidExpenseException(message: String) : Exception(message)

fun main() {
    val expenses = mutableListOf<Double>()

    println("Enter your expenses for 7 days:");

    for (i in 1..7) {
        print("Day $i: ")
        val input = readLine()?.toDoubleOrNull() ?: throw InvalidExpenseException("Invalid input")
        if (input < 0) throw InvalidExpenseException("Expense cannot be negative")
        expenses.add(input)
    }

    val total = expenses.sum()
    val maxExpense = expenses.maxOrNull() ?: 0.0
    val maxDay = expenses.indexOf(maxExpense) + 1
    val average = total / expenses.size

    println("\nTotal expenses: $total")
    println("Day with highest expense: Day $maxDay ($maxExpense)")
    println("Average expense: $average")

    when {
        total < 500 -> println("Evaluation: Economical")
        total in 500.0..1000.0 -> println("Evaluation: Moderate")
        total > 1000 -> println("Evaluation: Too much")
    }
}
