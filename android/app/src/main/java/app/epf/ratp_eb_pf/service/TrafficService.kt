package app.epf.ratp_eb_pf.service

import retrofit2.http.GET
import retrofit2.http.Path

// Basé sur le JSON du traffic d'une ligne en particulier

interface TrafficService {

    @GET("traffic/{type}")
    suspend fun getTrafficService(
        @Path("type") type: String // Type de ligne. Ex :metro

    ): GetTrafficResult
}//,@Path("code") code: String // Code de la ligne. Ex: 1 pour metro 1

data class GetTrafficResult(
    val result: ResultTraffic,
    val metadata: MetadataTraffic
)

data class MetadataTraffic(
    val call: String,
    val date: String,
    val version: Long
)

data class ResultTraffic(
    val metros: List<Traffic>
)

data class Traffic(
    val line: String,
    val slug: String,
    val title: String,
    val message: String
)