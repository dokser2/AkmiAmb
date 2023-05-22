package ua.dokser.akmiamb.models

data class Item(
    val cod:String,
    val name:String,
    var isFavorite:Boolean = false
) {
}
