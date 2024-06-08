package dev.juanrincon.trips.presentation.models

data class UITrip(
    val id: Int,
    val name: String,
    val status: UITripStatus,
    val active: Boolean,
    val items: List<UITripItem>
) {
    companion object {
        val default = UITrip(
            0,
            "",
            UITripStatus.Destination,
            active = true,
            items = listOf()
        )
    }
}