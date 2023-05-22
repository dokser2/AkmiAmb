package ua.dokser.akmiamb

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.json.JSONObject
import ua.dokser.akmiamb.models.Item
import ua.dokser.akmiamb.models.MainItem
import ua.dokser.akmiamb.ui.theme.AkmiAmbTheme
import ua.dokser.akmiamb.viewModel.ViewItem
import ua.dokser.akmiamb.viewModel.ViewMainItem
import ua.dokser.akmiamb.viewModel.getData
import ua.dokser.akmiamb.viewModel.getMainItemsList

lateinit var jsonObject: JSONObject
var mainItemsList = arrayListOf<MainItem>()


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AkmiAmbTheme {
                // A surface container using the 'background' color from the theme
                jsonObject = getData(this)!!
                mainItemsList = getMainItemsList(jsonObject)

                ViewMainItem(context = this, mainItemsList = mainItemsList)

}




            
            }
        }
    }


fun getMainItemsList(jsonObject: JSONObject):ArrayList<MainItem>

{
    for (index in 1 ..11){
        val str = "9.$index"
        val jsonObjectArray = jsonObject.getJSONArray(str)
        val itemsList = arrayListOf<Item>()
        val title = when(str){
            "9.1" -> "Консультування"
            "9.2" -> "Основні лабораторні дослідження"
            "9.3" -> "Специфічні лабораторні дослідження"
            "9.4" -> "Інші лабораторні обстеження"
            "9.5" -> "Інструментальна діагностика із використанням рентгенологічних (скопічних), ультразвукових, ендоскопічних методів дослідження"
            "9.6" -> "Інструментальна діагностика із використанням компʼютерної томографії (КТ), магнітно-резонансної томографії (МРТ) та методів радіонуклідної діагностики"
            "9.7" -> "Інша інструментальна діагностика"
            "9.8" -> "Хірургічні втручання"
            "9.9" -> "Лікувально-профілактичні процедури"
            "9.10" -> "Лікувально-діагностичні процедури"
            "9.11" -> "Інші діагностичні процедури"
            else -> ""
        }
             for ( index in 0 until  jsonObjectArray.length()){
                    val cod = jsonObjectArray.getJSONObject(index).getString(title)
                    val name = jsonObjectArray.getJSONObject(index).getString("Column2")
                    itemsList.add(Item(cod, name))
             }

         mainItemsList.add(MainItem(title,itemsList))

        }

    return mainItemsList
}

