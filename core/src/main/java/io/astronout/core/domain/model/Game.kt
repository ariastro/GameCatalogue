package io.astronout.core.domain.model

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import io.astronout.core.data.source.remote.model.GamesResponse
import kotlinx.parcelize.Parcelize

@Parcelize
data class Game(
    val id: Int,
    val slug: String,
    val name: String,
    val released: String,
    val tba: Boolean,
    val backgroundImage: String,
    val rating: Double,
    val ratingTop: Int,
    val ratings: List<Rating>,
    val ratingsCount: Int,
    val reviewsTextCount: Int,
    val added: Int,
    val addedByStatus: AddedByStatus,
    val metacritic: Int,
    val playtime: Int,
    val suggestionsCount: Int,
    val updated: String,
    val reviewsCount: Int,
    val saturatedColor: String,
    val dominantColor: String,
    val parentPlatforms: List<String>,
    val genres: List<String>,
    val stores: List<String>,
    val tags: List<String>,
    val esrbRating: String,
    val shortScreenshots: List<String>
): Parcelable {

    constructor(data: GamesResponse.Game?): this(
        id = data?.id ?: 0,
        slug = data?.slug.orEmpty(),
        name = data?.name.orEmpty(),
        released = data?.released.orEmpty(),
        tba = data?.tba ?: false,
        backgroundImage = data?.backgroundImage.orEmpty(),
        rating = data?.rating ?: 0.0,
        ratingTop = data?.ratingTop ?: 0,
        ratings = data?.ratings?.map { Rating(it) }.orEmpty(),
        ratingsCount = data?.ratingsCount ?: 0,
        reviewsTextCount = data?.reviewsTextCount ?: 0,
        added = data?.added ?: 0,
        addedByStatus = AddedByStatus(data?.addedByStatus),
        metacritic = data?.metacritic ?: 0,
        playtime = data?.playtime ?: 0,
        suggestionsCount = data?.suggestionsCount ?: 0,
        updated = data?.updated.orEmpty(),
        reviewsCount = data?.reviewsCount ?: 0,
        saturatedColor = data?.saturatedColor.orEmpty(),
        dominantColor = data?.dominantColor.orEmpty(),
        parentPlatforms = data?.parentPlatforms?.map { it.platform?.name.orEmpty() }.orEmpty(),
        genres = data?.genres?.map { it.name.orEmpty() }.orEmpty(),
        stores = data?.stores?.map { it.store?.name.orEmpty() }.orEmpty(),
        tags = data?.tags?.map { it.name.orEmpty() }.orEmpty(),
        esrbRating = data?.esrbRating?.name.orEmpty(),
        shortScreenshots = data?.shortScreenshots?.map { it.image.orEmpty() }.orEmpty()
    )

    @Parcelize
    data class Rating(
        val title: String,
        val count: Int,
        val percent: Double
    ): Parcelable {
        constructor(data: GamesResponse.Game.Rating?): this(
            title = data?.title.orEmpty(),
            count = data?.count ?: 0,
            percent = data?.percent ?: 0.0
        )
    }

    @Parcelize
    data class AddedByStatus(
        val yet: Int,
        val owned: Int,
        val beaten: Int,
        val toplay: Int,
        val dropped: Int,
        val playing: Int
    ): Parcelable {
        constructor(data: GamesResponse.Game.AddedByStatus?): this(
            yet = data?.yet ?: 0,
            owned = data?.owned ?: 0,
            beaten = data?.beaten ?: 0,
            toplay = data?.toplay ?: 0,
            dropped = data?.dropped ?: 0,
            playing = data?.playing ?: 0
        )
    }
}