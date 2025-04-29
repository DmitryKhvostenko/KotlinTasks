import kotlinx.coroutines.*

suspend fun fetchGradesFromServer(): List<Int> {
    delay(2000)
    return List(5) { (1..10).random() }
}

fun main() = runBlocking {

    val student1 = Student(_name = "John Doe", _age = 18, grades = listOf(2, 4, 6))
    val student2 = Student(_name = "Jane Smith", _age = 22, grades = listOf(3, 5, 6))

    println("До оновлення оцінок:")
    println("Середній бал студента 1: ${student1.getAverage()}")
    println("Середній бал студента 2: ${student2.getAverage()}")

    val mergedGrades = student1 + student2
    println("Об'єднані оцінки: $mergedGrades")

    val multipliedGrades = student1 * 2
    println("Оцінки студента 1 після множення на 2: $multipliedGrades")

    val areEqual = student1 == student2
    println("Чи рівні студенти: $areEqual")

    println("Статус студента 1: ${student1.status}")
    println("Статус студента 2: ${student2.status}")

    println("\nОновлення оцінок асинхронно...")
    val newGrades = async { fetchGradesFromServer() }
    val fetchedGrades = newGrades.await()
    student1.updateGrades(fetchedGrades)
    println("Оновлені оцінки студента 1: ${student1.grades}")

    val group = Group(student1, student2)

    val topStudent = group.getTopStudent()
    println("Студент з найвищим середнім балом: ${topStudent?.name} з балом ${topStudent?.getAverage()}")
}

