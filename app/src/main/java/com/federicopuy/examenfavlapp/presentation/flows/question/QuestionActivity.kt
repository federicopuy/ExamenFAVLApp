package com.federicopuy.examenfavlapp.presentation.flows.question

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.federicopuy.examenfavlapp.R
import com.federicopuy.examenfavlapp.core.CATEGORY_EXTRA
import com.federicopuy.examenfavlapp.core.getResourceFromString
import com.federicopuy.examenfavlapp.data.model.Answer
import com.federicopuy.examenfavlapp.data.model.Question
import com.federicopuy.examenfavlapp.presentation.theme.ExamenFAVLAppTheme
import com.federicopuy.examenfavlapp.presentation.theme.Typography
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview


@ExperimentalCoroutinesApi
@FlowPreview
@AndroidEntryPoint
class QuestionComposeActivity : ComponentActivity() {

    private val viewModel: QuestionViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val extra = intent.getLongExtra(CATEGORY_EXTRA, 0)
        viewModel.initViewModel(extra)

        setContent {
            ExamenFAVLAppTheme {
                Surface {
                    QuestionActivityScreen(viewModel, extra) { finish() }
                }
            }
        }
    }
}

@Composable
fun NoMoreQuestions(categoryId: Long, finish: () -> Unit) {
    val textToDisplay =
        if (categoryId == 0L) stringResource(R.string.no_saved_questions) else stringResource(
            R.string.no_more_questions
        )
    val context = LocalContext.current
    Toast.makeText(context, textToDisplay, Toast.LENGTH_SHORT).show()
    finish()
}

@ExperimentalCoroutinesApi
@FlowPreview
@Composable
fun QuestionActivityScreen(
    viewModel: QuestionViewModel,
    categoryId: Long,
    finish: () -> Unit
) {
    val noMoreQuestions = viewModel.noMoreQuestions
    if (noMoreQuestions) {
        NoMoreQuestions(categoryId, finish)
    }

    val question = viewModel.currentQuestion ?: return

    QuestionScreen(
        title = question.title,
        imagePath = question.image,
        answers = question.answers,
        onAnswerClicked = { viewModel.onAnswerClicked(it) },
        onPrimaryButtonClicked = { viewModel.onPrimaryButtonClicked() },
        viewModel.isAnswered,
        viewModel.isFavorite,
        onFavoriteClicked = { viewModel.onFavoriteClicked() },
        onFinish = { finish() }
    )
}

@Composable
fun QuestionScreen(
    title: String,
    imagePath: String?,
    answers: List<Answer>,
    onAnswerClicked: (Answer) -> Unit,
    onPrimaryButtonClicked: () -> Unit,
    isAnswered: Boolean,
    isFavorite: Boolean,
    onFavoriteClicked: () -> Unit,
    onFinish: () -> Unit
) {
    val primaryButtonTitle =
        if (isAnswered) {
            stringResource(R.string.next_question)
        } else {
            stringResource(R.string.answer_question)
        }
    Scaffold(
        topBar = {
            AppBar(
                starValue = isFavorite,
                onStarClicked = onFavoriteClicked,
                onNavigationIconClicked = onFinish
            )
        }
    ) {
        Column {
            Text(
                text = title,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                style = Typography.h6
            )
            imagePath?.let {
                val drawable = getResourceFromString(it)
                val painter = painterResource(drawable)

                Image(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .size(256.dp)
                        .align(Alignment.CenterHorizontally),
                    painter = painter,
                    contentDescription = null,
                )
            }
            Column(
                modifier = Modifier.weight(1f)
            ) {
                answers.forEach {
                    val backgroundColor =
                        if (isAnswered && it.score >= 0) MaterialTheme.colors.error else MaterialTheme.colors.background

                    val modifier = Modifier
                        .fillMaxWidth()
                        .background(backgroundColor)
                        .padding(horizontal = 20.dp, vertical = 20.dp)
                    AnswerRow(it, onAnswerClicked, isAnswered, modifier)
                }
            }
            Button(
                onClick = onPrimaryButtonClicked,
                modifier = Modifier
                    .padding(16.dp)
                    .height(48.dp)
                    .fillMaxWidth(),
                shape = MaterialTheme.shapes.large
            ) {
                Text(primaryButtonTitle)
            }
        }
    }
}

@Composable
private fun AppBar(
    starValue: Boolean,
    onStarClicked: () -> Unit,
    onNavigationIconClicked: () -> Unit
) {
    TopAppBar(
        title = {
            Text(text = stringResource(R.string.app_name))
        },
        navigationIcon = {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = null,
                modifier = Modifier
                    .padding(12.dp)
                    .clickable { onNavigationIconClicked() }
            )
        },
        actions = {
            val starIcon = if (starValue) Icons.Filled.Star else Icons.Outlined.StarBorder
            Icon(
                imageVector = starIcon,
                contentDescription = null,
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .clickable { onStarClicked() }
            )
        }
    )
}

@Composable
fun AnswerRow(
    answer: Answer,
    onAnswerClicked: (Answer) -> Unit,
    isAnswered: Boolean,
    modifier: Modifier
) {
    Column(
        Modifier.clickable {
            onAnswerClicked(answer)
        }
    ) {
        Row(modifier) {
            RadioButton(
                onClick = { onAnswerClicked(answer) },
                selected = answer.pressed,
                modifier = Modifier.align(Alignment.CenterVertically),
            )
            Text(
                text = answer.title,
                modifier = Modifier
                    .padding(PaddingValues(8.dp, 0.dp, 32.dp, 0.dp))
                    .fillMaxWidth(0.9f)
                    .wrapContentHeight()
                    .align(Alignment.CenterVertically),
                style = Typography.subtitle2

            )
            Spacer(modifier = Modifier.weight(1f))
            ScoreDisplay(
                isAnswered = isAnswered,
                scoreValue = answer.score.toString(),
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .align(Alignment.CenterVertically)
            )
        }
        Divider(modifier = Modifier.padding(horizontal = 16.dp))
    }
}

@Composable
fun ScoreDisplay(isAnswered: Boolean, scoreValue: String, modifier: Modifier) {
    if (isAnswered) {
        Text(
            text = scoreValue,
            modifier = modifier,
            style = Typography.subtitle2
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    val answers: List<Answer> = listOf(
        Answer(
            0L,
            "un freno aerodinámico, un freno aerodinámico,,un freno aerodinámico,,un freno aerodinámico, ",
            6,
            true
        ),
        Answer(1L, "un planeador ultraliviano", 6),
        Answer(2L, "un paracaídas", -6)
    )
    val question = Question(0L, "Un ala de vuelo libre es:", null, false, answers)
    QuestionScreen(question.title, "circuit.png", question.answers, { }, { }, true, true, {}, {})
}

@Preview(showBackground = true)
@Composable
fun DefaultDarkPreview() {
    val answers: List<Answer> = listOf(
        Answer(
            0L,
            "un freno aerodinámico, un freno aerodinámico,,un freno aerodinámico,,un freno aerodinámico, ",
            6,
            true
        ),
        Answer(1L, "un planeador ultraliviano", 6),
        Answer(2L, "un paracaídas", -6)
    )
    val question = Question(0L, "Un ala de vuelo libre es:", null, false, answers)
    ExamenFAVLAppTheme(darkTheme = true) {
        QuestionScreen(question.title, null, question.answers, { }, { }, true, false, {}, {})
    }
}