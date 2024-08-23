package app.earthcpr.sol.models

data class SavingMonth(
    val month: String,
    val onClick: () -> Unit,
    val isSelected: Boolean

)