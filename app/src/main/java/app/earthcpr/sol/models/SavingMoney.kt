package app.earthcpr.sol.models

data class SavingMoney(
    val money: String,
    val onClick: () -> Unit,
    val isSelected: Boolean
)