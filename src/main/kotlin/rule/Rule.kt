package rule

@DslMarker
annotation class RuleMarker

class Rule<T> constructor(
        val id: String = "",
        val name: String?,
        val priority: Int = 0,
        var description: String?,
        val condition: Predicate<T>,
        val action: () -> RuleState
) {

    fun fire(t: T): RuleState {
        if (condition(t)) {
            return action()
        }
        return RuleState.NEXT
    }

    @RuleMarker
    class Builder<T>(val id: String) {
        var name: String? = null
        var priority: Int = 0
        var description: String? = null
        var condition: Predicate<T> = Condition<T>{false}
        var action: () -> RuleState = {
            RuleState.NEXT
        }

//        fun setName(name: String?) = apply { this.name = name }
//        fun setPriority(priority: Int) = apply { this.priority = priority }
//        fun setDescription(description: String?) = apply { this.description = description }
//        fun setCondition(block: Predicate<T>) = apply { this.condition = block}
//        fun setAction(block: () -> RuleState) = apply { this.action = block }
        fun build(): Rule<T> {
            return Rule(id, name, priority, description, condition, action)
        }
    }
}

fun <T> rule(id: String, fn: Rule.Builder<T>.() -> Unit): Rule<T> {
    return Rule.Builder<T>(id).apply(fn).build()
}
