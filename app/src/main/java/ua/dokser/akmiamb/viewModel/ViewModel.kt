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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.json.JSONObject
import ua.dokser.akmiamb.R
import ua.dokser.akmiamb.models.Item
import ua.dokser.akmiamb.models.MainItem
import java.io.IOException


val favoriteItemsCod = arrayListOf<String>()
var items = arrayListOf<Item>()
lateinit var item:Item
val cardModifier = Modifier
    .fillMaxWidth()
    .padding(10.dp)
@Composable
fun ViewMainItem(mainItemsList: ArrayList<MainItem>, onClick: () -> Unit){

    LazyColumn(
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .fillMaxHeight()
    )
    {
        items (count = mainItemsList.size){
            Card(
                modifier = cardModifier
                    .clickable
                    {
                        onClick()
                        items = mainItemsList[it].items
                        Log.d("MyLog", "CLICK")
                    },
                shape = RoundedCornerShape(10.dp),
                elevation = 10.dp

            ) {
                Text(
                    text = mainItemsList[it].title,
                    fontSize = 24.sp,
                    modifier = cardModifier

                )
            }

        }

    }


}

@Composable
fun ViewItems(onClick: () -> Unit){
    LazyColumn(
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .fillMaxHeight()
    )
    {
        items (count = items.size){
            val isInFavorList = favoriteItemsCod.contains(items[it].cod)
            val itemInList = Item(items[it].cod,items[it].name,isInFavorList)

            Card(
                modifier = cardModifier.clickable {
                    onClick()
                    item = items[it]
                }
            ) {
                Column {
                    Row(horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(text = itemInList.cod,
                            color = Color.Blue,
                            modifier = Modifier
                                .fillMaxWidth(0.6f)
                                .padding(10.dp)
                                )
                        Image( modifier = cardModifier,
                            alignment = Alignment.Center,
                            painter =  if (itemInList.isFavorite)
                                painterResource(R.drawable.favorite)
                            else painterResource(R.drawable.not_favorite),
                            contentDescription = "img",
                        )
                    }

                    Text(text = itemInList.name,
                        color = Color.Black,
                        modifier = cardModifier)
                }
            }
        }
    }
}

@Composable
fun ViewItem() {

    var isFavoriteState by remember { mutableStateOf(item.isFavorite) }
    val paddingModifier = Modifier.padding(10.dp)

    Card(
        modifier = paddingModifier
    ) {
        Column {
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
                        painterResource(R.drawable.favorite)
                    else painterResource(R.drawable.not_favorite),
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

    if (item.isFavorite)
        favoriteItemsCod.add(item.cod)
    else favoriteItemsCod.remove(item.cod)
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
//fun addToFavoriteListFile(){
//
//    var str = ""
//    favoriteItemsCod.forEach { str = str + it +"," }
//    File("favoriteList.txt").writeText(str,Charsets.UTF_8)
//}
