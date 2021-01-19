package uz.suhrob.movieinfoapp.other

inline fun <T> safeCall(action: () -> Resource<T>): Resource<T> {
    return try {
        action()
    } catch(e: Exception) {
        Resource.Error(e.message ?: "An unknown error occurred")
    }
}

fun getImageUrl(key: String) = "https://image.tmdb.org/t/p/w500/$key"

fun formatTime(timeInMinutes: Int) = "${timeInMinutes / 60}:${timeInMinutes % 60}"