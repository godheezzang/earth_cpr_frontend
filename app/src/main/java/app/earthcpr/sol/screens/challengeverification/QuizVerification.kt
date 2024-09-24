package app.earthcpr.sol.screens.challengeverification

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// Data Class
data class Todo(
    val userId: Int,
    val id: Int,
    val title: String,
    val completed: Boolean
)

object RetrofitInstance {
    private const val BASE_URL = "https://jsonplaceholder.typicode.com/"

    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}

@Composable
fun QuizVerification(quizViewModel: QuizViewModel = viewModel()) {
    var isDataLoaded: Boolean by remember { mutableStateOf(false) }
    var isAnswerCorrect by remember { mutableStateOf<Boolean?>(null) }

    LaunchedEffect(Unit) {
        quizViewModel.fetchQuizData { isSuccess ->
            isDataLoaded = isSuccess
        }
    }

    if (!isDataLoaded) {
        Text("Loading...")
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 질문 텍스트
            Text(
                text = quizViewModel.currentQuestion?.title ?: "Question",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // 보기 1 버튼
            Button(
                onClick = {
                    isAnswerCorrect = quizViewModel.checkAnswer(quizViewModel.options[0])
                },
                modifier = Modifier.padding(bottom = 8.dp)
            ) {
                Text(text = quizViewModel.options[0].toString())
            }

            // 보기 2 버튼
            Button(
                onClick = {
                    isAnswerCorrect = quizViewModel.checkAnswer(quizViewModel.options[1])
                }
            ) {
                Text(text = quizViewModel.options[1].toString())
            }

            // 정답 확인 메시지
            Spacer(modifier = Modifier.height(16.dp))

            isAnswerCorrect?.let { isCorrect ->
                if (isCorrect) {
                    Text("정답입니다!", color = MaterialTheme.colorScheme.primary)
                } else {
                    Text("틀렸습니다.", color = MaterialTheme.colorScheme.error)
                }
            }
        }
    }
}

class QuizViewModel : ViewModel() {
    private val retrofitInstance = RetrofitInstance.api

    var currentQuestion: Todo? = null
    var options = listOf<Int>()

    // 퀴즈 데이터 가져오기
    fun fetchQuizData(onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val response: Response<List<Todo>> = retrofitInstance.getTodos()

            if (response.isSuccessful) {
                val todos = response.body()

                if (!todos.isNullOrEmpty()) {
                    // 총 2문제 출제
                    setQuestion(todos.shuffled().take(2))
                    onResult(true)
                } else {
                    onResult(false)
                }
            } else {
                onResult(false)
            }
        }
    }

    // 문제 설정
    private fun setQuestion(quizList: List<Todo>) {
        val questionTodo = quizList[0] // 첫 번째 문제
        val option1 = quizList[0].id
        val option2 = quizList[1].id

        currentQuestion = questionTodo
        options = listOf(option1, option2).shuffled() // 보기 섞기
    }

    // 정답 검증
    fun checkAnswer(selectedId: Int): Boolean {
        return currentQuestion?.id == selectedId
    }
}

@Preview
@Composable
fun pphsas(){
    return QuizVerification(quizViewModel = QuizViewModel())
}