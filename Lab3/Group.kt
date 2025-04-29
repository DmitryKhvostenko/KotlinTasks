import kotlin.collections.get

class Group(vararg students: Student) {
    private val students = students.toList()

    operator fun get(index: Int): Student {
        return students[index]
    }

    fun getTopStudent(): Student? {
        return students.maxByOrNull { it.getAverage() }
    }
}