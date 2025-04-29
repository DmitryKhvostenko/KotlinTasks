class Student(private var _name: String, private var _age: Int, public var grades: List<Int>) {

    init {
        println("Створено об'єкт студента: $_name, $_age років")
    }

    constructor(name: String) : this(name, 0, listOf())

    var name: String = _name
        set(value) {
            field = value.trim().replaceFirstChar { it.uppercase() }
        }

    var age: Int = _age
        set(value) {
            if (value >= 0) field = value
        }

    val isAdult: Boolean
        get() = age >= 18

    val status: String by lazy {
        if (isAdult) "Adult" else "Minor"
    }

    fun getAverage(): Double {
        return grades.average()
    }

    fun processGrades(operation: (Int) -> Int) {
        grades = grades.map(operation)
    }

    fun updateGrades(grades: List<Int>) {
        this.grades = grades
    }

    operator fun plus(other: Student): List<Int> {
        return this.grades + other.grades
    }

    operator fun times(multiplier: Int): List<Int> {
        return grades.map { it * multiplier }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Student) return false
        return this.name == other.name && this.getAverage() == other.getAverage()
    }
}
