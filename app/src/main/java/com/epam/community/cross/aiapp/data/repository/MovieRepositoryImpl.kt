package com.epam.community.cross.aiapp.data.repository

import com.epam.community.cross.aiapp.domain.model.Movie
import com.epam.community.cross.aiapp.domain.repository.MovieRepository
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.random.Random

@Singleton
class MovieRepositoryImpl @Inject constructor() : MovieRepository {
    override suspend fun regionMovies(): List<Movie> =
        getMovieList(movieName = "Region Movie", images = images)

    override suspend fun nationalMovies(): List<Movie> =
        getMovieList(movieName = "National Movie", images = images.reversed())

    override suspend fun globalMovies(): List<Movie> =
        getMovieList(movieName = "Global Movie", images = images.sorted())

    private fun getMovieList(movieName: String, images: List<String>): List<Movie> {
        return (0..19).map {
            Movie(
                id = it,
                name = "$it. $movieName",
                username = "@username",
                rate = Random.nextInt(0, 1000),
                previousRate = Random.nextInt(0, 1000),
                imageUrl = images[it]
            )
        }.sortedByDescending { it.rate }
    }

    private val images = listOf(
        "https://firebasestorage.googleapis.com/v0/b/testmodule-12b1c.appspot.com/o/rick.png?alt=media&token=89bee564-a6d8-45d5-b346-db06ac9aa2a8",
        "https://firebasestorage.googleapis.com/v0/b/testmodule-12b1c.appspot.com/o/friends.png?alt=media&token=82b88dc8-8995-4892-b208-6b530ce253b4",
        "https://firebasestorage.googleapis.com/v0/b/testmodule-12b1c.appspot.com/o/lebovski.png?alt=media&token=10cc5e75-7c54-4c0f-ba2e-61b87e2598a3",
        "https://firebasestorage.googleapis.com/v0/b/testmodule-12b1c.appspot.com/o/dontlookup.png?alt=media&token=c524e1f1-416e-41c1-ad00-82ade64112dc",
        "https://firebasestorage.googleapis.com/v0/b/testmodule-12b1c.appspot.com/o/things.png?alt=media&token=9b491613-ba34-4356-b022-3ece693872db",
        "https://firebasestorage.googleapis.com/v0/b/testmodule-12b1c.appspot.com/o/mirror.png?alt=media&token=372ea59e-cb48-4988-b19a-2ac3c39aa6d1",
        "https://firebasestorage.googleapis.com/v0/b/testmodule-12b1c.appspot.com/o/dark.png?alt=media&token=4a7aab02-fd6e-4a97-9879-c0c032941058",
        "https://firebasestorage.googleapis.com/v0/b/testmodule-12b1c.appspot.com/o/joe.png?alt=media&token=6bdaf306-865e-483f-8f03-45852d19eb8a",
        "https://firebasestorage.googleapis.com/v0/b/testmodule-12b1c.appspot.com/o/matrix.png?alt=media&token=67830c8c-3606-4414-8448-afef2cd01268",
        "https://firebasestorage.googleapis.com/v0/b/testmodule-12b1c.appspot.com/o/ghost.png?alt=media&token=31f8a23d-caef-4343-a106-bc745627f1cd",
        "https://firebasestorage.googleapis.com/v0/b/testmodule-12b1c.appspot.com/o/country.png?alt=media&token=7c56f343-51c1-40fa-8ac9-9abfb58d3e7e",
        "https://firebasestorage.googleapis.com/v0/b/testmodule-12b1c.appspot.com/o/bladerunner.png?alt=media&token=37d471f9-2e4c-42e3-8fd8-575d613004b7",
        "https://firebasestorage.googleapis.com/v0/b/testmodule-12b1c.appspot.com/o/watchmen.png?alt=media&token=bd655825-9dc3-4704-87b2-8d63d01adc55",
        "https://firebasestorage.googleapis.com/v0/b/testmodule-12b1c.appspot.com/o/bigbang.png?alt=media&token=5f1cb89a-ed37-4710-ae66-f866bfd2cea2",
        "https://firebasestorage.googleapis.com/v0/b/testmodule-12b1c.appspot.com/o/soul.png?alt=media&token=6dd954f3-ebb6-4848-99ac-079af32f29e3",
        "https://firebasestorage.googleapis.com/v0/b/testmodule-12b1c.appspot.com/o/queen.png?alt=media&token=a3835e24-8345-4b75-9573-608168630328",
        "https://firebasestorage.googleapis.com/v0/b/testmodule-12b1c.appspot.com/o/southpark.png?alt=media&token=9c8348e9-2f68-417d-8020-b065a0dfef1b",
        "https://firebasestorage.googleapis.com/v0/b/testmodule-12b1c.appspot.com/o/spaceforce.png?alt=media&token=590ec88a-de5b-4746-9a47-227a912d080f",
        "https://firebasestorage.googleapis.com/v0/b/testmodule-12b1c.appspot.com/o/breakingbad.png?alt=media&token=a00d4e28-5cc3-4ba1-a67c-bb4576b6727f",
        "https://firebasestorage.googleapis.com/v0/b/testmodule-12b1c.appspot.com/o/gatsby.png?alt=media&token=c500ba7b-833d-4393-926a-968906ebc18e"
    )
}