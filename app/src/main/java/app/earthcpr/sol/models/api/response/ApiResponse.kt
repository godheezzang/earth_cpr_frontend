package app.earthcpr.sol.models.api.response

import coil.compose.AsyncImagePainter

data class ApiResponse<T>(
    val success: Boolean,
    val data: T? = null,
    val error: ErrorResponse? = null
)

