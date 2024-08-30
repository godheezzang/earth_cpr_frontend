package app.earthcpr.sol.screens.savings.productlist

import app.earthcpr.sol.models.Challenge
import app.earthcpr.sol.models.ProductAccount

object MockProductListApiData {
    // 챌린지 목록
    private val challengeList1 = listOf(
        Challenge(
            id = 1,
            name = "걸음수 인증하기",
            info = "챌린지 설명",
            type = 1,
            verification = 1
        ),
        Challenge(
            id = 2,
            name = "대중교통 이용 인증",
            info = "챌린지 설명2",
            type = 2,
            verification = 2
        ),
        Challenge(
            id = 3,
            name = "음식물 줄이기 활동 인증",
            info = "챌린지 설명3",
            type = 2,
            verification = 2
        ),
        Challenge(
            id = 4,
            name = "ESG 관련 퀴즈 제출",
            info = "챌린지 설명4",
            type = 2,
            verification = 2
        )
    )

    private val challengeList2 = listOf(
        Challenge(
            id = 3,
            name = "챌린지 이름3",
            info = "챌린지 설명3",
            type = 1,
            verification = 2
        ),
        Challenge(
            id = 4,
            name = "챌린지 이름4",
            info = "챌린지 설명4",
            type = 2,
            verification = 1
        )
    )

    // ProductAccount 인스턴스 배열
    val productAccounts = listOf(
        ProductAccount(
            increaseInterestRate = "0.01",
            challengeList = challengeList1,
            accountName = "ESG 챌린지 적금",
            accountTypeUniqueNo = "accountTypeUniqueNo 1",
            bankCode = "001",
            accountDescription = "일상 속에서 ESG를 실천하고, \n챌린지에 참가할수록 더 큰 보상을 받을 수 있는 ESG 저축 상품",
            subscriptionPeriod = "365",
            minSubscriptionBalance = 10000,
            maxSubscriptionBalance = 1000000,
            interestRate = "10",
            rateDescription = "10% 이자를 지급합니다. 챌린지 달성시 추가 0.01% 이자를 지급합니다."
        ),
        ProductAccount(
            increaseInterestRate = "0.02",
            challengeList = challengeList2,
            accountName = "웰니스 챌린지 적금",
            accountTypeUniqueNo = "accountTypeUniqueNo 2",
            bankCode = "002",
            accountDescription = "건강과 이율을 둘 다 잡아보세요. 챌린지에 참가할 수록 더 큰 이득이 따라오는 웰니스 적금",
            subscriptionPeriod = "365",
            minSubscriptionBalance = 20000,
            maxSubscriptionBalance = 2000000,
            interestRate = "12",
            rateDescription = "12% 이자를 지급합니다. 챌린지 달성시 추가 0.02% 이자를 지급합니다."
        )
    )
}