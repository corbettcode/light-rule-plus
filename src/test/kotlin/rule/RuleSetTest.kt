package rule

import org.junit.jupiter.api.Assertions.assertSame
import org.junit.jupiter.api.Test

class RuleSetTest {

    @Test
    fun `a simple set with two rules`() {
        val testUser = TestUser(firstName = "Steve", lastName = "Hu", activated = false)
        val s = ruleSet<TestUser> {
            setId = "set001"
            description = "this is the first set of rules"
            rules {
                rule("com.networknt.r0001-1.0.0") {
                    name = "rule 0001"
                    description = "This is the first rule for testing with one condition and one action"
                    condition = {
                        it.firstName == "Steve" && it.lastName == "Hu"
                    }
                    action = {
                        println("Rule 1 is fired")
                        RuleState.NEXT
                    }
                }
                rule("com.networknt.r0002-1.0.0") {
                    name = "rule 0002"
                    description = "This is the second rule for testing with one condition and one action"
                    condition = or (
                            withFirstName("Steve"),
                            withLastName("Hu"),
                            withGender(Gender.MALE)
                    )
                    action = {
                        println("Rule 2 is fired")
                        RuleState.NEXT
                    }
                }
            }
        }
        assertSame(RuleState.NEXT, s.fire(testUser))
        assertSame(2, s.ruleCount)
    }

    @Test
    fun `a set with two rules with break`() {
        val testUser = TestUser(firstName = "Paul", lastName = "Corbett", activated = false)
        val s = ruleSet<TestUser> {
            setId = "set002"
            description = "this is the first set of rules"
            rules {
                rule("com.corbett.r0001-1.0.0") {
                    name = "rule 0001"
                    description = "This is the first rule for testing with one condition and one action"
                    condition = {
                        it.firstName == "Paul" && it.lastName == "Corbett"
                    }
                    action = {
                        println("Rule 1 is fired")
                        RuleState.BREAK
                    }
                }
                rule("com.corbett.r0002-1.0.0") {
                    name = "rule 0002"
                    description = "This is the second rule for testing with one condition and one action"
                    condition = or (
                        withFirstName("Paul"),
                        withLastName("Corbett"),
                        withGender(Gender.MALE)
                    )
                    action = {
                        println("Rule 2 is fired")
                        RuleState.NEXT
                    }
                }
            }
        }
        assertSame(RuleState.BREAK, s.fire(testUser))
        assertSame(1, s.ruleCount)
    }

    @Test
    fun `a set with two priority rules`() {
        val testUser = TestUser(firstName = "Paul", lastName = "Corbett", activated = false)
        val s = ruleSet<TestUser> {
            setId = "set003"
            description = "this is the first set of rules"
            rules {
                rule("com.corbett.r0001-1.0.0") {
                    name = "rule 0001"
                    description = "This should be the second rule for testing with one condition and one action"
                    priority = 2
                    condition = or (
                        withFirstName("Paul"),
                        withLastName("Corbett"),
                        withGender(Gender.MALE)
                    )
                    action = {
                        println("Rule with priority 2 is fired")
                        RuleState.NEXT
                    }
                }
                rule("com.corbett.r0002-1.0.0") {
                    name = "rule 0002"
                    description = "This should be the first rule for testing with one condition and one action"
                    priority = 1
                    condition = {
                        it.firstName == "Paul" && it.lastName == "Corbett"
                    }
                    action = {
                        println("Rule with priority 1 is fired")
                        RuleState.NEXT
                    }
                }
            }
        }
        assertSame(RuleState.NEXT, s.fire(testUser))
        assertSame(2, s.ruleCount)
    }
}