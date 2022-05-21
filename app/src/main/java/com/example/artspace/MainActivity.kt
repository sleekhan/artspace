package com.example.artspace

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.artspace.ui.theme.ArtSpaceTheme
import com.skydoves.landscapist.glide.GlideImage

data class Paint(val url: String, val title: String, val artist: String, val year: String)

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        initializeData()

        super.onCreate(savedInstanceState)
        setContent {
            ArtSpaceTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    ArtSpace()
                }
            }
        }
    }
}

val painters = ArrayList<Paint>()
var currentIndex = 0
fun initializeData() {
    painters.add(
        Paint(
            "https://pds.joongang.co.kr/news/component/htmlphoto_mmdata/202105/18/48de97ef-2cd2-4f23-86af-0602a76550ce.jpg",
            "Golden State - Bright Warriors",
            "Curry",
            "2021"
        )
    )
    painters.add(
        Paint(
            "https://www.mercurynews.com/wp-content/uploads/2021/12/BNG-L-WARRIORS-1209-47.jpg?w=821",
            "Golden State",
            "Jordan Poole",
            "2022"
        )
    )
    painters.add(
        Paint(
            "https://thumbnews.nateimg.co.kr/view610///onimg.nate.com/orgImg/ss/2018/12/31/201884821546247411.jpg",
            "어게인 마이 라이프",
            "차주영",
            "2022"
        )
    )

}

@Composable
fun ArtSpace() {
    val paint = remember { mutableStateOf(painters[currentIndex]) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .fillMaxHeight()
            .padding(20.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ArtworkWall(paint.value)
        ArtworkDescriptor(paint.value)
        DisplayController(onPrevClick = {
            if( currentIndex > 0 ) {
                paint.value =  painters[--currentIndex]
            }
        }, onNextClick = {
            if( currentIndex < painters.size - 1 ) {
                paint.value =  painters[++currentIndex]
            }

        })
    }
}

@Composable
fun ArtworkWall(paint: Paint) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,

        ) {

        Surface(
            shape = RectangleShape,
            color = Color.White,
            elevation = 12.dp,
            modifier = Modifier.border(3.dp, Color.Gray)
        ) {
            Row(
                modifier = Modifier
                    .padding(30.dp)
            ) {

                GlideImage(
                    imageModel = paint.url,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .width(300.dp)
                        .height(400.dp)

                )
            }
        }
    }

}

@Composable
fun ArtworkDescriptor(paint: Paint) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(top = 20.dp)
    ) {
        Surface(
            shape = RectangleShape,
            color = Color.White,
            elevation = 12.dp,
            modifier = Modifier.width(350.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(15.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                Text(paint.title, fontSize = 30.sp)
                Text("${paint.artist} (${paint.year})")
            }

        }
    }

}

@Composable
fun DisplayController(onPrevClick: () -> Unit, onNextClick: () -> Unit) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(top = 20.dp)
    ) {
        Column(modifier = Modifier.padding(5.dp)) {
            Button(onClick = onPrevClick, modifier = Modifier.width(100.dp)) {
                Text(text = "Previous")
            }

        }
        Column(modifier = Modifier.padding(5.dp)) {
            Button(onClick = onNextClick, modifier = Modifier.width(100.dp)) {
                Text(text = "Next")
            }

        }
    }

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ArtSpaceTheme {
        ArtSpace()
    }
}