package rule

data class TestUser(
        val id: Int = 0,
        val firstName: String = "",
        val lastName: String = "",
        val email: String? = null,
        val gender: Gender = Gender.MALE,
        val age: Int? = null,
        val activated: Boolean = false,
        val address: Address? = null
)

data class Address(
        val address1: String? = null,
        val address2: String? = null,
        val city: String? = null,
        val province: String? = null,
        val state: String? = null,
        val zip: String? = null,
        val postcode: String? = null,
        val country: String = "CA"
)

enum class Gender{
    MALE,
    FEMALE
}

fun withLastName(lastName: String): Condition<TestUser> = Condition{it.lastName.equals(lastName)}
fun withFirstName(firstName: String): Condition<TestUser> = Condition{it.firstName.equals(firstName)}
fun withGender(gender: Gender): Condition<TestUser> = Condition{it.gender == gender}
fun withActivated(activated: Boolean): Condition<TestUser> = Condition{it.activated == activated}

