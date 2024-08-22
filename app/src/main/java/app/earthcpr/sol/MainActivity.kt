package app.earthcpr.sol

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import app.earthcpr.sol.screens.join.JoinScreen
import app.earthcpr.sol.screens.login.LoginScreen
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
                navigationToHomeScreen = { navController.navigate("navMainScreen") },
                navigationToJoinScreen = { navController.navigate("joinScreen") },
            )
        }
        composable(route = "joinScreen") {
            JoinScreen(
                navigationToLoginScreen = { navController.navigate("loginScreen") },
            )
        }
    }
}