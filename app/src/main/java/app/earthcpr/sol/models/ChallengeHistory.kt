package app.earthcpr.sol.models

import java.time.LocalDateTime

data class ChallengeHistory(
    val id: Long? = null,
    val name: String,
    val info: String,
    val type: Long? = null,
    val verification: Long? = null,
    val localDateTimeList: List<LocalDateTime>? = null
)