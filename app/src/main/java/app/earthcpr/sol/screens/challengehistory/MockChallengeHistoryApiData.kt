package app.earthcpr.sol.screens.challengehistory

import app.earthcpr.sol.models.ChallengeHistory
import java.time.LocalDateTime


object MockChallengeHistoryApiData {
    val challengeHistoryList1 = listOf(
        ChallengeHistory(
            id = 1,
            name = "빈그릇 챌린지",
            info = "음식 남기지 않기",
            type = 1,
            verification = 0,
            localDateTimeList = listOf(
                LocalDateTime.of(2023, 5, 1, 12, 0), // 2023년 5월 1일 12시 0분
                LocalDateTime.of(2023, 5, 2, 13, 30), // 2023년 5월 2일 13시 30분
                LocalDateTime.of(2023, 5, 2, 18, 30), // 2023년 5월 2일 13시 30분
                LocalDateTime.of(2023, 5, 3, 11, 45),  // 2023년 5월 3일 11시 45분
                LocalDateTime.of(2023, 5, 3, 18, 45),  // 2023년 5월 3일 18시 45분
            )
        ),
        ChallengeHistory(
            id = 1,
            name = "텀블러 챌린지",
            info = "영수증 텀블러 사용",
            type = 1,
            verification = 0,
            localDateTimeList = listOf(
                LocalDateTime.of(2023, 5, 4, 9, 0),  // 2023년 5월 4일 9시 0분
                LocalDateTime.of(2023, 5, 4, 15, 0),  // 2023년 5월 4일 15시 0분
                LocalDateTime.of(2023, 5, 4, 21, 0),  // 2023년 5월 4일 21시 0분
                LocalDateTime.of(2023, 5, 5, 17, 15) // 2023년 5월 5일 17시 15분
            )
        ),
    )
}

