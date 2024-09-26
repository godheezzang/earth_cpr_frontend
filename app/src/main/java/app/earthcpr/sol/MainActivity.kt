package app.earthcpr.sol

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import app.earthcpr.sol.screens.challengehistory.ChallengeHistoryScreen
import app.earthcpr.sol.screens.challengeverification.EatUpVerificationScreen
import app.earthcpr.sol.screens.challengeverification.EatUpVerificationSuccessScreen
import app.earthcpr.sol.screens.challengeverification.MiracleMorningVerificationSuccessScreen
import app.earthcpr.sol.screens.challengeverification.TumblerVerificationScreen
import app.earthcpr.sol.screens.challengeverification.TumblerVerificationSuccessScreen
import app.earthcpr.sol.screens.challengeverification.WorkOutVerificationScreen
import app.earthcpr.sol.screens.challengeverification.WorkOutVerificationSuccessScreen
import app.earthcpr.sol.screens.friend.FriendRankingScreen
import app.earthcpr.sol.screens.friend.MyViewModel
import app.earthcpr.sol.screens.join.JoinScreen
import app.earthcpr.sol.screens.login.LoginScreen
import app.earthcpr.sol.screens.home.HomeScreen
import app.earthcpr.sol.screens.savings.accountdetail.SavingAccountDetailScreen
import app.earthcpr.sol.screens.savings.accountlist.ProductListScreen
import app.earthcpr.sol.screens.savings.money.SavingCreateMoneyByAccountUniqueNoScreen
import app.earthcpr.sol.screens.savings.month.SavingMonthSelectScreen
import app.earthcpr.sol.screens.savings.myaccountlist.MySavingAccountListScreen
import app.earthcpr.sol.screens.savings.mydepositaccountlist.MyDepositAccountListScreen
import app.earthcpr.sol.screens.savings.success.SavingCreateAccountSuccessScreen
import app.earthcpr.sol.ui.theme.SolApplicationTheme
import app.earthcpr.sol.utils.PreferenceUtil
import com.example.ht_practice.complete.MiracleMorningVerificationScreen
//import com.example.ht_practice.friend.FriendRankingScreen
//import com.example.ht_practice.friend.MyViewModel

class MainActivity : ComponentActivity() {
    private val TAG = "MainActivity"

    companion object {
        lateinit var preferences: PreferenceUtil
        lateinit var loginId: String

         // userUuid 없을 때 초기화 하는 코드
        fun initUserUuidIfNull() {
            loginId = preferences.getString("loginId", "")
        }
    }

    fun logout() {
        preferences.clear()
        setContent {
            SolApplicationTheme {
                MyApp()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        preferences = PreferenceUtil(applicationContext)
        loginId = preferences.getString("loginId", "")
        super.onCreate(savedInstanceState)

//        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
//            if (!task.isSuccessful) {
//                Log.d(TAG, "Fetching FCM registration token failed", task.exception)
//                return@OnCompleteListener
//            }
//
//            // Get new FCM registration token
//            val token = task.result
//
//            // Log and toast
//            Log.d(TAG, token)
//            preferences.setString("token", token)
//            // todo 서버에 보내기
//        })

        setContent {
            SolApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MyApp()
                }
            }
        }
    }
}

@Composable
fun MyApp() {
    val navController = rememberNavController()
    var startDestination = "loginScreen"                // 첫 화면

    NavHost(navController = navController, startDestination = startDestination) {
        composable(route = "loginScreen") {
            LoginScreen(
                navigationToHomeScreen = { navController.navigate("homeScreen") },

                navigationToJoinScreen = { navController.navigate("joinScreen") },
            )
        }
        composable(route = "joinScreen") {
            JoinScreen(
                navigationToLoginScreen = { navController.navigate("loginScreen") },
            )
        }
        composable("homeScreen") {
            HomeScreen(
                navigationToAccountDetailScreen = { accountNo ->
                    navController.navigate("savingAccountDetailScreen?accountNo=$accountNo")
                },
                navigationToProductListScreen = { navController.navigate("productListScreen") },
                navigationToChallengeHistoryScreen = { navController.navigate("challengeHistoryScreen")},
                navigationToMiracleMorningVerificactionScreen = {navController.navigate("MiracleMorningVerificationScreen")},
                navigationToTumblerVerificationScreen = {navController.navigate("TumblerVerification")},
                navigationToWorkOutVerificationScreen = {navController.navigate("WorkOutVerificationScreen")},

            )
        }
        composable("productListScreen") {
            ProductListScreen(
                navigationToHomeScreen = { navController.navigate("homeScreen") },
                navigationToMyDepositAccountListScreen = { accountTypeUniqueNo ->
                    navController.navigate("myDepositAccountListScreen?accountTypeUniqueNo=$accountTypeUniqueNo")
                },
            )
        }
        composable("challengeHistoryScreen") {
            ChallengeHistoryScreen(
                navigationToHomeScreen = { navController.navigate("homeScreen") },
            )
        }
        composable("myDepositAccountListScreen?accountTypeUniqueNo={accountTypeUniqueNo}")
        { backStackEntry ->
            val accountTypeUniqueNo =
                backStackEntry.arguments?.getString("accountTypeUniqueNo") ?: ""

            MyDepositAccountListScreen(
                navigationToHomeScreen = { navController.navigate("homeScreen") },
                navigationToSavingCreateScreen = { accountTypeUniqueNo, accountNo ->
                    navController.navigate("savingCreateMoneyByAccountUniqueNoScreen?accountTypeUniqueNo=${accountTypeUniqueNo}&accountNo=${accountNo}")
                },
                accountTypeUniqueNo = accountTypeUniqueNo
            )
        }
        composable(
            "savingCreateMoneyByAccountUniqueNoScreen?accountTypeUniqueNo={accountTypeUniqueNo}&accountNo={accountNo}",
            arguments = listOf(
                navArgument("accountTypeUniqueNo") { type = NavType.StringType },
                navArgument("accountNo") { type = NavType.StringType })

        ) { backStackEntry ->
            val accountTypeUniqueNo =
                backStackEntry.arguments?.getString("accountTypeUniqueNo") ?: ""
            val accountNo =
                backStackEntry.arguments?.getString("accountNo") ?: ""

            SavingCreateMoneyByAccountUniqueNoScreen(
                navigationToHomeScreen = { navController.navigate("homeScreen") },
                navigationToMonthSelectScreen = { savingMoneyText, accountTypeUniqueNo, accountNo ->
                    navController.navigate("savingMonthSelectScreen?savingMoneyText=$savingMoneyText&accountTypeUniqueNo=$accountTypeUniqueNo&accountNo=$accountNo")
                },
                accountTypeUniqueNo = accountTypeUniqueNo,
                accountNo = accountNo
            )
        }
        composable(
            "savingMonthSelectScreen?savingMoneyText={savingMoneyText}&accountTypeUniqueNo={accountTypeUniqueNo}&accountNo={accountNo}",
            arguments = listOf(
                navArgument("savingMoneyText") { type = NavType.StringType },
                navArgument("accountTypeUniqueNo") { type = NavType.StringType },
                navArgument("accountNo") { type = NavType.StringType })

        ) { backStackEntry ->
            val savingMoneyText = backStackEntry.arguments?.getString("savingMoneyText") ?: ""
            val accountTypeUniqueNo = backStackEntry.arguments?.getString("accountTypeUniqueNo") ?: ""
            val accountNo = backStackEntry.arguments?.getString("accountNo") ?: ""

            SavingMonthSelectScreen(
                navigationToHomeScreen = { navController.navigate("homeScreen") },
                navigationToSavingAccountSuccessScreen = { navController.navigate("savingCreateAccountSuccessScreen") },
                depositBalance = savingMoneyText,
                accountTypeUniqueNo = accountTypeUniqueNo,
                accountNo = accountNo
            )
        }
        composable(
            "savingCreateAccountSuccessScreen"
        ) {
            SavingCreateAccountSuccessScreen(
                navigationToHomeScreen = { navController.navigate("homeScreen") },
                navigationToMyAccountListScreen = { navController.navigate("mySavingAccountListScreen") },
            )
        }
        composable(
            "mySavingAccountListScreen"
        ) {
            MySavingAccountListScreen(
                navigationToHomeScreen = { navController.navigate("homeScreen") },
                // todo 변경필요
                navigationToAccountDetailScreen = { navController.navigate("savingAccountDetailScreen")
                },
            )
        }
        composable(
            "savingAccountDetailScreen?accountNo={accountNo}",
            arguments = listOf(navArgument("accountNo") { type = NavType.StringType })

        ) { backStackEntry ->
            val accountNo = backStackEntry.arguments?.getString("accountNo") ?: ""

            SavingAccountDetailScreen(
                navigationToHomeScreen = { navController.navigate("homeScreen") },
                accountNo = accountNo,
                navigationToChallengeHistoryScreen = { navController.navigate("challengeHistoryScreen") },
            )
        }
        
        // 여기서부터 윤호코드

        composable("EatUpVerificationScreen" ){ EatUpVerificationScreen(navController) } // 빈그릇
        composable("EatUpVerificationSuccessScreen" ){ EatUpVerificationSuccessScreen(navController) } // 빈그릇

        composable("TumblerVerification" ){ TumblerVerificationScreen(navController) } // 텀블러
        composable("TumblerVerificationSuccessScreen" ){ TumblerVerificationSuccessScreen(navController) } // 텀블러

        composable("MiracleMorningVerificationScreen" ){ MiracleMorningVerificationScreen(navController) } // 텀블러
        composable("MiracleMorningVerificationSuccessScreen" ){ MiracleMorningVerificationSuccessScreen(navController) } // 텀블러

        composable("WorkOutVerificationScreen" ){ WorkOutVerificationScreen(navController) } // 텀블러
        composable("WorkOutVerificationSuccessScreen" ){ WorkOutVerificationSuccessScreen(navController) } // 텀블러
        // 친구랭킹 ( 일단 작성만 해두고 기능 추가는 안했음 언제든 버튼 하나만 만들면 이동 가능)
        composable("FriendRankingScreen" ){ FriendRankingScreen(MyViewModel() , navController) } // 친구 순위   >> 보류.... 무시하세요


    }
}