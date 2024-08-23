package app.earthcpr.sol.models.api.response

data class ApiResponse<T>(
    val result: T? = null,
    val error: ErrorResponse? = null
)

