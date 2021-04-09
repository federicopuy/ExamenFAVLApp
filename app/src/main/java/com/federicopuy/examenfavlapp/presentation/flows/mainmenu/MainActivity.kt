package com.federicopuy.examenfavlapp.presentation.flows.mainmenu

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.federicopuy.examenfavlapp.R
import com.federicopuy.examenfavlapp.core.CATEGORY_EXTRA
import com.federicopuy.examenfavlapp.core.updateDBManager.UpdateDBManager
import com.federicopuy.examenfavlapp.data.model.Category
import com.federicopuy.examenfavlapp.presentation.flows.question.QuestionComposeActivity
import com.federicopuy.examenfavlapp.presentation.theme.ExamenFAVLAppTheme
import com.federicopuy.examenfavlapp.presentation.theme.Typography
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import javax.inject.Inject

@ExperimentalCoroutinesApi
@FlowPreview
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<MainViewModel>()

    @Inject
    lateinit var updateDBManager: UpdateDBManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.initViewModel(updateDBManager.shouldUpdateDB(this))

        setContent {
            ExamenFAVLAppTheme {
                Surface {
                    MainActivityScreen(viewModel)
                }
            }
        }
    }
}

@FlowPreview
@ExperimentalCoroutinesApi
@Composable
fun MainActivityScreen(viewModel: MainViewModel) {
    val items: List<Category> by viewModel.categories.observeAsState(listOf())
    val clickedCategoryId = viewModel.clickedCategoryId

    if (clickedCategoryId >= 0) {
        IntentHandler(categoryId = clickedCategoryId,
            resetClickedCategory = { viewModel.resetClickedCategory() }
        )
    }
    MainScreen(
        items = items,
        onItemClicked = viewModel::onCategoryClicked,
        onFabClicked = { viewModel.onFabClicked() }
    )
}

@FlowPreview
@ExperimentalCoroutinesApi
@Composable
fun IntentHandler(categoryId: Long, resetClickedCategory: () -> Unit) {
    val context = LocalContext.current
    val intent = Intent(context, QuestionComposeActivity::class.java)
    intent.putExtra(CATEGORY_EXTRA, categoryId)
    context.startActivity(intent)
    resetClickedCategory()
}

@FlowPreview
@ExperimentalCoroutinesApi
@Composable
fun MainScreen(
    items: List<Category>,
    onItemClicked: (Category) -> Unit,
    onFabClicked: () -> Unit,
) {
    Scaffold(
        topBar = { AppBar() }
    ) {
        Column {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                items.forEach {
                    CategoryRow(
                        it.title,
                        Modifier
                            .fillMaxWidth()
                            .clickable { onItemClicked(it) }
                            .padding(horizontal = 20.dp, vertical = 20.dp)
                    )
                }
            }
            FloatingActionButton(
                onClick = { onFabClicked() },
                content = ({ FABIcon() }),
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(16.dp)
            )
        }
    }
}

@Composable
private fun AppBar() {
    TopAppBar(
        title = { Text(text = stringResource(R.string.app_name)) },
        navigationIcon = {
            Image(
                painter = painterResource(id = R.drawable.logo_favl),
                contentDescription = null,
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                colorFilter = ColorFilter.tint(Color.White)
            )
        }
    )
}

@Composable
fun CategoryRow(title: String, modifier: Modifier) {
    Column {
        Row(
            modifier = modifier,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = title,
                style = Typography.h6
            )
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = null,
                modifier = Modifier.size(28.dp)
            )
        }
        Divider(modifier = Modifier.padding(horizontal = 16.dp))
    }
}

@Composable
fun FABIcon() {
    Icon(
        imageVector = Icons.Default.Star,
        contentDescription = null
    )
}

@FlowPreview
@ExperimentalCoroutinesApi
@Preview
@Composable
fun PreviewMainScreen() {
    val items = listOf(
        Category(1, "Meteorología", listOf()),
        Category(2, "Técnicas de Vuelo", listOf()),
        Category(3, "Material", listOf()),
    )
    MainScreen(items, {}, {})
}



