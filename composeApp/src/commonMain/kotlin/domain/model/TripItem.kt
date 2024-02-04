package domain.model

data class TripItem(
    val id: Int,
    val item: Item,
    val done: Boolean
)