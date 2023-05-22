package ua.dokser.akmiamb.viewModel

import android.content.Context
import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.json.JSONObject
import ua.dokser.akmiamb.mainItemsList
import ua.dokser.akmiamb.models.Item
import ua.dokser.akmiamb.models.MainItem
import java.io.IOException


val favoriteItems = arrayListOf<Item>()

@Composable
fun ViewMainItem(context: Context,mainItemsList: ArrayList<MainItem>){

    LazyColumn(
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .fillMaxHeight()
    )
    {
        items (count = mainItemsList.size){
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Blue)
                    .padding(5.dp),
                shape = RoundedCornerShape(10.dp),
                elevation = 10.dp

            ) {
                Text(
                    text = mainItemsList[it].title,
                    fontSize = 24.sp,
                    modifier = Modifier
                        .padding(10.dp)
                        .clickable
                        {
                            Log.d("MyLog", "CLICK")
                        }

                )
            }

        }

    }


}

@Composable
fun ViewItem( item: Item ) {
    //test

    val item: Item = Item(item.cod,item.name)
    var isFavoriteState by remember { mutableStateOf(item.isFavorite) }



    val paddingModifier = Modifier.padding(10.dp)
    Card(
        modifier = paddingModifier
    ) {
        Column() {
            Row(horizontalArrangement = Arrangement.SpaceBetween) {
                Text(text = item.cod,
                    color = Color.Blue,
                    modifier = Modifier
                        .fillMaxWidth(0.6f)
                        .padding(10.dp))
                Image( modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .clickable {
                        changeFavorite(item)
                        isFavoriteState = item.isFavorite
                    },
                    alignment = Alignment.Center,
                    painter =  if (isFavoriteState)
                        painterResource(ua.dokser.akmiamb.R.drawable.favorite)
                    else painterResource(ua.dokser.akmiamb.R.drawable.not_favorite),
                    contentDescription = "img",
                )
            }

            Text(text = item.name,
                color = Color.Black,
                modifier = paddingModifier)


        }
    }

}

fun changeFavorite(item: Item) {

    item.isFavorite = !item.isFavorite

    if (item.isFavorite == true)
        favoriteItems.add(item)
    else favoriteItems.remove(item)
}

fun getMainItemsList(){
    val mainItems = arrayListOf<MainItem>()
}

fun getData(context: Context, fileName: String = "data.json"): JSONObject? {
    val jsonString: String
    try {
        jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
    } catch (ioException: IOException) {
        ioException.printStackTrace()
        return null
    }
    return JSONObject(jsonString)
}
