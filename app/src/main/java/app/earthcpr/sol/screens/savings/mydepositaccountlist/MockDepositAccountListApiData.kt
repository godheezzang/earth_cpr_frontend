package app.earthcpr.sol.screens.savings.mydepositaccountlist

import app.earthcpr.sol.models.DepositAccount

object MockDepositAccountListApiData {

    val depositAccountList = listOf(
        DepositAccount(
            bankCode = "001",
            accountNo = "001-61746-48358792",
            accountName = "신한 입출금 통장 1",
            accountBalance = 1000000L,
        ),
        DepositAccount(
            bankCode = "001",
            accountNo = "002-61746-4812312",
            accountName = "신한 입출금 통장 2",
            accountBalance = 2000000L,
        )
    )
}