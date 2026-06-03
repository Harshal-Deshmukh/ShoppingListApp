package eu.tutorials.theshoppinnglist

data class LocationData(
    val latitude: Double,
    val longitude: Double
)

data class GeoCodingResult(
    val formatted_address: String
)
