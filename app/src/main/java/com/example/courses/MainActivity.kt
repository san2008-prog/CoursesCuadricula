package com.example.courses


import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.courses.data.Datasource
import com.example.courses.model.Topic
import com.example.courses.ui.theme.CoursesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CoursesTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    GridOfTopics()
                }
            }
        }
    }
}


@OptIn(ExperimentalAnimationApi::class)
@Composable

fun GridOfTopics() {
    val listOfTopics = Datasource.topics
    var visible by remember { mutableStateOf(false) }
    var identificatorOfCard by remember { mutableStateOf(0) }
  //var visible by remember { mutableStateOf(false) }
  //
   // var gridFocusable by remember { mutableStateOf(true) }

    val clickingCard: (Boolean) -> Unit = {
        visible = !it
    }


    Box() {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(8.dp, 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(listOfTopics) { topic ->

                TopicShowing(
                    topic,
                    modifier = Modifier.clickable(onClick = {
                        clickingCard(visible)
                        // visible = !visible
                        identificatorOfCard = topic.hashCode()
                        //Log.d("Expanded", "$expanded")
                        //Log.d("HashCodeCard", "$identificatorOfCard")

                    })

                )

            }
        }
        if(visible==true) {
            Box(modifier = Modifier.fillMaxSize().background(Color.Gray.copy(alpha=0.7f)).zIndex(2f))
        }
      //  if (expanded == true) {
            listOfTopics.forEach {
                if (it.hashCode() == identificatorOfCard) {

                    ElectedTopicShowing(
                        it, modifier = Modifier.zIndex(3f)
                            .align(Alignment.Center)
                            .fillMaxWidth(),
                        visible,
                        clickingCard
                    )
                }
            }

       // }

    }
}


@SuppressLint("SuspiciousIndentation")
@Composable
fun TopicShowing(topic: Topic, modifier: Modifier) {

    val textStyleHeight =
        MaterialTheme.typography.labelSmall // Estilo de Texto de la Cantidad de Cursos
    val textHeightDp: Dp =
        textStyleHeight.lineHeight.value.dp // Tamaño del estilo de texto para usarlo en Sizing el Row

    val courseQuantity = topic.courseQuantity.toString()


        Card(
            modifier = modifier,
            elevation = CardDefaults.cardElevation(6.dp),
            shape = RoundedCornerShape(6.dp)
        ) {
            Row(modifier = modifier) {

                Image(
                    painter = painterResource(id = topic.imageResourceId),
                    contentDescription = null,
                    modifier = Modifier
                        .size(68.dp, 68.dp)
                )
                Column() {
                    Text(
                        text = stringResource(id = topic.titleResourceId),
                        modifier = Modifier.padding(
                            start = 16.dp,
                            top = 16.dp,
                            end = 16.dp,
                            bottom = 8.dp
                        ),
                        //style = MaterialTheme.typography.bodySmall
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Row(
                        /*modifier = Modifier
                            .padding(top = 8.dp)
                           // .background(Color.Yellow)
                            .requiredHeight(textHeightDp),*/
                        verticalAlignment = Alignment.CenterVertically,
                    ) {

                        Icon(
                            painter = painterResource(id = R.drawable.ic_grain),
                            contentDescription = null,
                            modifier = Modifier
                                .padding(start = 16.dp)
                                .align(Alignment.Top)
                            /*  .padding(
                                  top = 2.dp,
                                  bottom = 2.dp
                              )*///El Padding interno es para achicar el Icono
                        )

                        Text(
                            text = courseQuantity,
                            modifier = Modifier
                                .padding(start = 8.dp),
                            //style = textStyleHeight
                            style = MaterialTheme.typography.labelMedium
                        )

                    }

                }

            }
        }


}


@OptIn(ExperimentalAnimationApi::class)
@SuppressLint("SuspiciousIndentation")
@Composable
fun ElectedTopicShowing(topic: Topic, modifier: Modifier, visible: Boolean, changingState: (Boolean) -> Unit) {

    val courseQuantity = topic.courseQuantity.toString()
   // var visible by remember { mutableStateOf(false) }

        Card(
            modifier = modifier,
            elevation = CardDefaults.cardElevation(6.dp),
            shape = RoundedCornerShape(6.dp)
        ) {
            AnimatedVisibility(visible = visible)//, enter = fadeIn(animationSpec = tween(2000)),
              //  exit = fadeOut(animationSpec = tween(2000)))
           {
            Row(modifier = Modifier
                .fillMaxWidth()
                .animateEnterExit(
                    enter = expandVertically(), //Esto funciona mejor que colocr los enter y exit dentro de AnimatedVisibility
                    exit = shrinkHorizontally()
                ) //Esto funciona mejor que colocr los enter y exit dentro de AnimatedVisibility
            ) {
                Image(
                    painter = painterResource(id = topic.imageResourceId),
                    contentDescription = null,
                    modifier = Modifier
                        .size(140.dp, 140.dp)
                )
                Column() {
                    Text(
                        text = stringResource(id = topic.titleResourceId),
                        modifier = Modifier.padding(
                            start = 16.dp,
                            top = 16.dp,
                            end = 16.dp,
                            bottom = 8.dp
                        ),
                        style = MaterialTheme.typography.displayMedium
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_grain),
                            contentDescription = null,
                            modifier = Modifier
                                .padding(start = 16.dp)
                        )
                        Text(
                            text = courseQuantity,
                            modifier = Modifier
                                .padding(start = 8.dp),
                            style = MaterialTheme.typography.displayMedium
                        )
                    }
                }
                Spacer(modifier = Modifier.weight(1f))
                CloseButton(visible,changingState)
            }
        }
    }
}

@Composable
fun CloseButton(visible: Boolean, changingState: (Boolean)-> Unit) {
    IconButton(onClick = {changingState(visible)}) {
        Icon(imageVector = Icons.Default.Close, contentDescription = "Cerrar Ventana" )
    }
}


@Preview(showBackground = true)
@Composable
fun TopicPreview() {
    CoursesTheme {
        GridOfTopics()
    }
}