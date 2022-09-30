package duiban.badmintonbuddy.models

data class ValidationResult(
    val successful: Boolean,
    val errorMessage: String? = null
)