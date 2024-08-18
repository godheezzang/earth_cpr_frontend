package app.earthcpr.sol.models

data class ApiResponse<T>(
    val result: T? = null,
    val error: ErrorResponse? = null
)

