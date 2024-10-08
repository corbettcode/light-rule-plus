package rule

import org.junit.jupiter.api.Assertions.assertSame
import org.junit.jupiter.api.Test

class RuleEngineTest {
    val engine: RuleEngine<TestUser> = RuleEngine.getInstance()

    @Test
    fun `add a rule to rule engine and fire`() {
        val rule1 = rule<TestUser>("com.networknt.rule0001-1.0.0") {
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
        engine.addRule(rule1)
        assertSame(engine.fireRule("com.networknt.rule0001-1.0.0", TestUser(firstName = "Steve", lastName = "Hu")), RuleState.NEXT)
    }

    @Test
    fun `add a rule set to rule engine and fire`() {
        val testUser = TestUser(firstName = "Steve", lastName = "Hu", activated = false)
        val s = ruleSet<TestUser> {
            setId = "com.networknt.set001-1.0.0"
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
        engine.addRuleSet(s)
        assertSame(engine.fireRuleSet("com.networknt.set001-1.0.0", testUser), RuleState.NEXT)
    }


}