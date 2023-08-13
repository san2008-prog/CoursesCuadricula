package com.example.courses


import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredHeightIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
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


@Composable

fun GridOfTopics() {
    val listOfTopics = Datasource.topics
    var expanded by remember { mutableStateOf(false) }
    var identificatorOfCard by remember { mutableStateOf(0) }

    Box(contentAlignment = Alignment.Center) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(8.dp, 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(listOfTopics) { topic ->

                TopicShowing(topic, modifier=Modifier.clickable(onClick = {expanded=!expanded ;identificatorOfCard = topic.hashCode(); Log.d("Expanded", "$expanded");Log.d("HashCodeCard", "$identificatorOfCard")}))

            }
        }
        if(expanded==true) {
            listOfTopics.forEach{
                if(it.hashCode()==identificatorOfCard){
                    TopicShowing(it) // Aqui debe ir otra composable function para que muestre el Card mas grande que tenga un boton cerrar.
                }
            }
        }


    }
}


@Composable
fun TopicShowing(topic: Topic, modifier: Modifier=Modifier) {

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
    Row() {

        Image(
            painter = painterResource(id = topic.imageResourceId),
            contentDescription = null,
            modifier = Modifier
                .size(68.dp, 68.dp)
        )
        Column() {
            Text(
                text = stringResource(id = topic.titleResourceId),
                modifier = Modifier.padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom= 8.dp),
                //style = MaterialTheme.typography.bodySmall
                style = MaterialTheme.typography.bodyMedium
            )
            Row(
                /*modifier = Modifier
                    .padding(top = 8.dp)
                   // .background(Color.Yellow)
                    .requiredHeight(textHeightDp),
                horizontalArrangement = Arrangement.Start,*/
                verticalAlignment = Alignment.CenterVertically,
            ) {

                Icon(
                    painter = painterResource(id = R.drawable.ic_grain),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(start = 16.dp)
                      /*  .padding(
                            top = 2.dp,
                            bottom = 2.dp
                        )*///El Padding interno es para achicar el Icono
                )

                Text(
                    text = courseQuantity,
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .fillMaxHeight(),
                    //style = textStyleHeight
                    style = MaterialTheme.typography.labelMedium
                )

            }

        }

    }
}

}

@Preview(showBackground = true)
@Composable
fun TopicPreview() {
    CoursesTheme {
        GridOfTopics()
    }
}