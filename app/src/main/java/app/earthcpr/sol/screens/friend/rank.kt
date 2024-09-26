package app.earthcpr.sol.screens.friend

// REST API 통신 후 받아온 데이터를
// 리스트로 뿌려줘야함

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.colorspace.WhitePoint
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import app.earthcpr.sol.screens.topbar.HomeScreenButton
import app.earthcpr.sol.screens.topbar.LineOf1Dp
import app.earthcpr.sol.screens.topbar.TopBar
import app.earthcpr.sol.ui.theme.newFontFamily
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

// api 클래스
//data class Post(
//    val userId: Int?,
//    val id: Int?,
//    val title: String?,
//    val completed: Boolean?
//)
//
//interface ApiService {
//    // 친구 목록
//    @GET("todos")   // 추후 변경
//    suspend fun getPosts(): List<Post>// List<Post>
//
//    // 친구 삭제
//    @DELETE("friends/{id}")
//    suspend fun deleteFriend(@Path("id") friendId: Int): Response<Unit>
//}
//
//
//
//object RetrofitInstance {
//    private const val BASE_URL = "https://jsonplaceholder.typicode.com/" // 추후 변경  http://ec2-52-78-171-40.ap-northeast-2.compute.amazonaws.com:8080/
//
//    val api: ApiService by lazy {
//        Retrofit.Builder()
//            .baseUrl(BASE_URL)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//            .create(ApiService::class.java)
//    }
//}

data class Friend(val name: String, val achievement: String) // Friend
 {
     // 임시 데이터
//     val post = mutableListOf(  // friends
//         Friend("김신한", "챌린지 달성률 78%"),
//         Friend("신용재", "챌린지 달성률 98%"),
//         Friend("허각", "챌린지 달성률 97%"),
//         Friend("김민석", "챌린지 달성률 50%"),
//         Friend("이범진", "챌린지 달성률 45%"),
//         Friend("이지은", "챌린지 달성률 67%"),
//         Friend("박효신", "챌린지 달성률 84%"),
//         Friend("김범수", "챌린지 달성률 95%"),
//         Friend("유나얼", "챌린지 달성률 25%"),
//         Friend("전광철", "챌린지 달성률 32%"),
//         Friend("윤민수", "챌린지 달성률 85%"),
//         Friend("임한별", "챌린지 달성률 99%"),
//     )
}



class MyViewModel : ViewModel() {

    // 임시 데이터
//    val post = mutableListOf(  // friends
//        Friend("김신한", "챌린지 달성률 78%"),
//        Friend("신용재", "챌린지 달성률 98%"),
//        Friend("허각", "챌린지 달성률 97%"),
//        Friend("김민석", "챌린지 달성률 50%"),
//        Friend("이범진", "챌린지 달성률 45%"),
//        Friend("이지은", "챌린지 달성률 67%"),
//        Friend("박효신", "챌린지 달성률 84%"),
//        Friend("김범수", "챌린지 달성률 95%"),
//        Friend("유나얼", "챌린지 달성률 25%"),
//        Friend("전광철", "챌린지 달성률 32%"),
//        Friend("윤민수", "챌린지 달성률 85%"),
//        Friend("임한별", "챌린지 달성률 99%"),
//    )

//    private val _posts = MutableStateFlow<List<Friend>>(emptyList())
//    val posts: StateFlow<List<Friend>> = _posts

    private val _posts = MutableStateFlow<List<Friend>>(listOf(
        Friend("김신한", "챌린지 달성률 78%"),
        Friend("신용재", "챌린지 달성률 98%"),
        Friend("허각", "챌린지 달성률 97%"),
        Friend("김민석", "챌린지 달성률 50%"),
        Friend("이범진", "챌린지 달성률 45%"),
        Friend("이지은", "챌린지 달성률 67%"),
        Friend("박효신", "챌린지 달성률 84%"),
        Friend("김범수", "챌린지 달성률 95%"),
        Friend("유나얼", "챌린지 달성률 25%"),
        Friend("전광철", "챌린지 달성률 32%"),
        Friend("윤민수", "챌린지 달성률 85%"),
        Friend("임한별", "챌린지 달성률 99%")
    ))

    val posts: StateFlow<List<Friend>> = _posts

//     친구 목록
//    fun fetchPosts() {
//        viewModelScope.launch {
//            try {
//                val response = RetrofitInstance.api.getPosts()
//                _posts.value = response
//            } catch (e: Exception) {
//                Log.d("TAG" , "REST API 통신 실패")
//            }
//        }
//    }
//     친구 삭제
//    fun deleteFriend(friendId: Int) {
//        viewModelScope.launch {
//            try {
//                val response = RetrofitInstance.api.deleteFriend(friendId)
//                if (response.isSuccessful) {
//                    // 성공 시 상태에서 친구 삭제
//                    _posts.value = _posts.value.filter { it.id != friendId }
//                } else {
//                    Log.d("TAG", "친구 삭제 실패")
//                }
//            } catch (e: Exception) {
//                Log.d("TAG", "친구 삭제 요청 실패: ${e.message}")
//            }
//        }
//    }

    // 임시로 인덱스에 해당하는 항목 삭제
    fun removeItemAtIndex(index: Int) {
        val currentList = _posts.value.toMutableList()
        if (index in currentList.indices) {
            currentList.removeAt(index)
            _posts.value = currentList
        }
    }
}

@Composable
fun FriendRankingScreen(viewModel: MyViewModel = viewModel() , navController: NavController) {
    val posts by viewModel.posts.collectAsState()

//    LaunchedEffect(Unit) {
//        viewModel.fetchPosts()
//    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Top Bar ( 위 컬럼을 Scaffold로 감싸주고 아래를 tOPbar로 대체?
//        TopBar(title = "친구 랭킹"  ) {
//
//        }
        TopBarRank(title = "친구 랭킹"){
            navController.navigate("homeScreen")
        }
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(vertical = 58.dp, horizontal = 12.dp),
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            Icon(
//                imageVector = Icons.Filled.ArrowBack,
//                contentDescription = "Back",
//                modifier = Modifier.size(24.dp)
//            )
//            Spacer(modifier = Modifier.weight(1f))
//            Text(
//                text = "친구 랭킹",
//                fontSize = 18.sp,
//                fontWeight = FontWeight.Bold
//            )
//            Spacer(modifier = Modifier.weight(1f))
//            Icon(
//                imageVector = Icons.Filled.Menu,
//                contentDescription = "Menu",
//                modifier = Modifier.size(24.dp)
//            )
//        }

        // Search Bar
        Spacer(modifier = Modifier.height(8.dp))
        SearchBar()
        Spacer(modifier = Modifier.height(16.dp))

        // API로 받아올 데이터에 날짜, 시간이 없다면 기존에 준비해둔 함수를 적용할 예정
        Text(
            text = "${getCurrentDateTime()} 기준",   // 시간
            fontSize = 12.sp,
            color = Color.Gray,
            modifier = Modifier.padding(start = 16.dp)
        )

        // Ranking List using LazyColumn
        Spacer(modifier = Modifier.height(8.dp))
        Row {
//            Card(modifier = Modifier.fillMaxWidth()) {
            Spacer(modifier = Modifier.width(15.dp))
            Text(text = "랭킹")
            Spacer(modifier = Modifier.width(60.dp))
            Text(text = "이름")
//            }
        }
        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
//            items(posts) { post ->
//                RankingItem(name = post.title.toString(), achievement = post.id.toString())
//            }
            items(posts.size) { index ->
                val post = posts[index]

                Card( elevation = CardDefaults.cardElevation(defaultElevation = 8.dp) ,
                    modifier = Modifier
                        .padding(horizontal = 12.dp)
                        .background(color = Color.White)) {
                    RankingItem(
                        rank = index + 1,
                        name = post.name.toString(),
                        achievement = post.achievement.toString(),
                        onRemove = { viewModel.removeItemAtIndex(index) })
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun SearchBar() {  // user_id를 입력하면 친구 추가 요청을 보낼 API 코드 필요 >> 명세서
    // 방법1 , 추가한 친구의 ID만 보내준다  방법2. FE에서 LIST에 APPEND하고 리스트를 통째로 보낸다.
    var text by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clip(CircleShape)
            .background(Color(0xFFF2F2F2))//0xFFF2F2F2
            .padding(horizontal = 12.dp, vertical = 10.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = "Search Icon",
                tint = Color.Gray,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            BasicTextField(
                value = text,
                onValueChange = { text = it },
                singleLine = true,
                textStyle = TextStyle(fontSize = 14.sp, color = Color.Black),
                modifier = Modifier.weight(1f),
                decorationBox = { innerTextField ->
                    if (text.isEmpty()) {
                        Text(
                            text = "친구를 검색해보세요",
                            color = Color.Gray,
                            fontSize = 14.sp
                        )
                    }
                    innerTextField()
                }
            )
        }
    }
}

@Composable
fun RankingItem(rank : Int  , name: String, achievement: String , onRemove : () -> Unit ) {    // 친구 순위 보여주는 함수

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
//        Icon(
//            imageVector = Icons.Filled.Close,
//            contentDescription = "Trophy Icon",
//            modifier = Modifier.size(24.dp)
//        )
        val rankText = when (rank) {
            1 -> "🥇"  // 금메달 이모지
            2 -> "🥈"  // 은메달 이모지
            3 -> "🥉"  // 동메달 이모지
            else -> "${rank}위"  // 나머지 순위는 숫자로 표시
        }

        Text(
            text = rankText,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.width(40.dp) // 순위와 아이콘 사이에 간격 추가
        )

        Spacer(modifier = Modifier.width(12.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(text = name, fontSize = 16.sp, fontWeight = FontWeight.Medium)
            Text(text = achievement, fontSize = 14.sp, color = Color.Gray)
        }

        Row {
            Button(
                onClick = {  },    // 깨우기 기능  FCM 기능 구현에 따라 삭제 여부 결정
                modifier = Modifier
                    .height(36.dp)
                    .clip(CircleShape),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0044FF))
            ) {
                Text(text = "깨우기", color = Color.White, fontSize = 14.sp)
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = {  onRemove()  },   // 삭제버튼 기능 구현을 할 수 있을지 모르기 때문에 일단 보류, REMOVE로 대체 가능
                modifier = Modifier
                    .height(36.dp)
                    .clip(CircleShape),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF44336))
            ) {
                Text(text = "삭제", color = Color.White, fontSize = 14.sp)
            }
        }
    }
}

// 현재 날짜와 시간을 가져오는 함수
fun getCurrentDateTime(): String {
    val sdf = SimpleDateFormat("yyyy년 MM월 dd일 HH:mm", Locale.getDefault())
    return sdf.format(Date())
}

@Preview(showBackground = true, apiLevel = 33 )//apiLevel = 30
@Composable
fun PreviewFriendRankingScreen() {
    FriendRankingScreen(viewModel = MyViewModel() , navController =  rememberNavController())
}




@Composable
fun TopBarRank(
    title: String,
    showHomeButton: Boolean = true,
    navigationToHomeScreen: (() -> Unit)? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(68.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box {
            if (showHomeButton) {
                HomeScreenButton(navigationToHomeScreen)
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = newFontFamily,
                    color = Color(0xFF171D1B)
                )
            }

        }
    }
}

//@Composable
//fun PostListScreen(viewModel: MyViewModel = viewModel()) {
//    val posts by viewModel.posts.collectAsState()
//
//    LaunchedEffect(Unit) {
//        viewModel.fetchPosts()
//    }

//    LazyC@Composable
//fun PostListScreen(viewModel: MyViewModel = viewModel()) {
//    val posts by viewModel.posts.collectAsState()
//
//    LaunchedEffect(Unit) {
//        viewModel.fetchPosts()
//    }olumn {
//        items(posts) { post ->
//            Column(modifier = Modifier.padding(10.dp)) {
//                Row {
//                    Text(text = post.userId.toString())
//                    Spacer(modifier = Modifier.width(10.dp))
//                    Text(text = post.id.toString())
//                    Spacer(modifier = Modifier.width(10.dp))
//                    Text(text = post.title)
//                    Spacer(modifier = Modifier.width(10.dp))
//                    Text(text = post.completed.toString())
//                }
//            }
//
//
//
//        }
//    }
//}



// 시간 불러오는 메소드

//@Composable
//fun CurrentDateTime() {
//    var currentDateTime by remember { mutableStateOf(getCurrentDateTime()) }
//
//    // 시간 갱신을 위한 LaunchedEffect
//    LaunchedEffect(Unit) {
//        while (true) {
//            currentDateTime = getCurrentDateTime()
//            delay(1000L)
//        }
//    }
//
//    Text(
//        text =  " 🕛 $currentDateTime 기준 ",
//        fontSize = 14.sp,
//        modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
//    )
//}



//         Icon(
//            painter = painterResource(id = R.drawable.ic_gold_medal), // 금메달 아이콘
//            contentDescription = "Gold Medal",
//            modifier = Modifier.size(24.dp)
//        )
//if (rank == 1) {
//    Text(
//        text = "",
//        fontSize = 16.sp,
//        fontWeight = FontWeight.Bold,
//        modifier = Modifier.width(40.dp) // 순위와 아이콘 사이에 간격 추가
//    )
//} else if (rank == 2) {
//    Text(
//        text = "${rank}등",
//        fontSize = 16.sp,
//        fontWeight = FontWeight.Bold,
//        modifier = Modifier.width(40.dp) // 순위와 아이콘 사이에 간격 추가
//    )
//} else if (rank == 3) {
//    Text(
//        text = "${rank}등",
//        fontSize = 16.sp,
//        fontWeight = FontWeight.Bold,
//        modifier = Modifier.width(40.dp) // 순위와 아이콘 사이에 간격 추가
//    )
//} else {
//    Text(
//        text = "${rank}등",
//        fontSize = 16.sp,
//        fontWeight = FontWeight.Bold,
//        modifier = Modifier.width(40.dp) // 순위와 아이콘 사이에 간격 추가
//    )
//}