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
import app.earthcpr.sol.screens.join.JoinScreen
import app.earthcpr.sol.screens.login.LoginScreen
import app.earthcpr.sol.screens.savings.accountdetail.SavingAccountDetailScreen
import app.earthcpr.sol.screens.savings.money.SavingCreateScreen
import app.earthcpr.sol.screens.savings.month.SavingMonthSelectScreen
import app.earthcpr.sol.screens.savings.myaccountlist.MySavingAccountListScreen
import app.earthcpr.sol.screens.savings.success.SavingCreateAccountSuccessScreen
import app.earthcpr.sol.ui.theme.SolApplicationTheme
import app.earthcpr.sol.utils.PreferenceUtil

class MainActivity : ComponentActivity() {
    private val TAG = "MainActivity"

    companion object {
        lateinit var preferences: PreferenceUtil
        lateinit var userId: String

        // userUuid 없을 때 초기화 하는 코드
        fun initUserUuidIfNull() {
            userId = preferences.getString("userId", "")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        preferences = PreferenceUtil(applicationContext)
        userId = preferences.getString("userId", "")
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
    var startDestination = "loginScreen"

    NavHost(navController = navController, startDestination = startDestination) {
        composable(route = "loginScreen") {
            LoginScreen(
//                navigationToHomeScreen = { navController.navigate("homeScreen") },
                navigationToHomeScreen = { accountTypeUniqueNo ->
                    navController.navigate("savingCreateScreen?accountTypeUniqueNo=$accountTypeUniqueNo")
                },

                navigationToJoinScreen = { navController.navigate("joinScreen") },
            )
        }
        composable(route = "joinScreen") {
            JoinScreen(
                navigationToLoginScreen = { navController.navigate("loginScreen") },
            )
        }
        composable(
            "savingCreateScreen?accountTypeUniqueNo={accountTypeUniqueNo}",
            arguments = listOf(navArgument("accountTypeUniqueNo") { type = NavType.StringType })

        ) { backStackEntry ->
            val accountTypeUniqueNo =
                backStackEntry.arguments?.getString("accountTypeUniqueNo") ?: ""

            SavingCreateScreen(
                navigationToHomeScreen = { navController.navigate("loginScreen") },
                navigationToMonthSelectScreen = { savingMoneyText ->
                    navController.navigate("savingMonthSelectScreen?savingMoneyText=$savingMoneyText")
                },
                accountTypeUniqueNo = accountTypeUniqueNo
            )
        }
        composable(
            "savingMonthSelectScreen?savingMoneyText={savingMoneyText}",
            arguments = listOf(navArgument("savingMoneyText") { type = NavType.StringType })

        ) { backStackEntry ->
            val savingMoneyText = backStackEntry.arguments?.getString("savingMoneyText") ?: ""

            SavingMonthSelectScreen(
                navigationToHomeScreen = { navController.navigate("loginScreen") },
                navigationToSavingAccountSuccessScreen = { navController.navigate("savingCreateAccountSuccessScreen") },
                depositBalance = savingMoneyText
            )
        }
        composable(
            "savingCreateAccountSuccessScreen"
        ) {
            SavingCreateAccountSuccessScreen(
                navigationToHomeScreen = { navController.navigate("loginScreen") },
                navigationToMyAccountListScreen = { navController.navigate("mySavingAccountListScreen") },
            )
        }
        composable(
            "mySavingAccountListScreen"
        ) {
            MySavingAccountListScreen(
                navigationToHomeScreen = { navController.navigate("loginScreen") },
                // todo 변경필요
                navigationToAccountDetailScreen = { accountNo ->
                    navController.navigate("savingAccountDetailScreen?accountNo=$accountNo")
                }
            )
        }
        composable(
            "savingAccountDetailScreen?accountNo={accountNo}",
            arguments = listOf(navArgument("accountNo") { type = NavType.StringType })

        ) { backStackEntry ->
            val accountNo = backStackEntry.arguments?.getString("accountNo") ?: ""

            SavingAccountDetailScreen(
                navigationToHomeScreen = { navController.navigate("loginScreen") },
                accountNo = accountNo
            )
        }
    }
}