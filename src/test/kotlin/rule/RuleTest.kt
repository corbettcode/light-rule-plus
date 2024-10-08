package rule

import org.junit.jupiter.api.Assertions.assertSame
import org.junit.jupiter.api.Test

class RuleTest {

    @Test
    fun `a simple rule with a string fact`() {
        val fact = "Kotlin"

        val condition1: Predicate<String> = {
            it.startsWith("Kot")
        }

        val action1: () -> RuleState = {
            println("Rule 1 is fired")
            RuleState.NEXT
        }

        val rule = rule<String>("com.networknt.rule0001-1.0.0") {
            name = "rule 1"
            description = "This is the first rule for testing"
            condition = condition1
            action = action1
        }

        val result = rule.fire(fact)
        assertSame(result, RuleState.NEXT)
        println(result)
    }

    @Test
    fun `a simple rule with one condition and one action`() {
        val rule = rule<TestUser>("com.networknt.r0001-1.0.0") {
            name = "rule 0001"
            description = "This is the first rule for testing with one condition and one action"
            condition = {
                it.firstName == "Steve" && it.lastName == "Hu"
            }
            action = {
                println("Rule 1 is fired")
                println("Second action is executed")
                RuleState.NEXT
            }

        }
        val result = rule.fire(TestUser(firstName = "Steve", lastName = "Hu"))
        println(result)
    }

    @Test
    fun `a rule with or conditions`() {
        val rule = rule<TestUser>("com.networknt.r0002-1.0.0") {
            name = "rule 0002"
            description = "This is the rule for testing with multiple conditions compound with or"
            condition = or (
                withFirstName("Steve"),
                withLastName("Hu"),
                withGender(Gender.MALE)
            )
            action = {
                println("Rule 2 is fired")
                println("Second action is executed")
                RuleState.NEXT
            }

        }
        val result = rule.fire(TestUser(firstName = "Steve", lastName = "Hu"))
        assertSame(result, RuleState.NEXT)
        println(result)

    }

    @Test
    fun `a rule with and conditions`() {
        val rule = rule<TestUser>("com.networknt.r0003-1.0.0") {
            name = "rule 0003"
            description = "This is the rule for testing with multiple conditions compound with and"
            condition = and (
                withFirstName("Steve"),
                withLastName("Hu"),
                withGender(Gender.MALE),
                withActivated(true)  // this condition fails.
            )
            action = {
                println("Rule 3 is fired")
                println("Second action is executed")
                RuleState.NEXT
            }
        }
        val result = rule.fire(TestUser(firstName = "Steve", lastName = "Hu"))
        assertSame(result, RuleState.NEXT)
        println(result)
    }

    @Test
    fun `a simple rule where action returns BREAK`() {
        val rule = rule<TestUser>("com.corbettcode.r0004-1.0.0") {
            name = "rule 0003"
            description = "This is the rule for testing a return rule-state of break"
            condition = {
                true
            }
            action = {
                println("Rule 4 is fired")
                println("Return a rule-state of BREAK")
                RuleState.BREAK
            }
        }
        val result = rule.fire(TestUser(firstName = "Steve", lastName = "Hu"))
        assertSame(result, RuleState.BREAK)
        println("returned: $result")
    }
}
