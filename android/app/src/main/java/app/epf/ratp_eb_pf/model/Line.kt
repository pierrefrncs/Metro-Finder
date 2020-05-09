package app.epf.ratp_eb_pf.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

@Entity(tableName = "lines")
data class Line(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val code: String,
    val name: String,
    val directions: String,
    val idRatp: Int,
    var favoris: Boolean
) : Serializable {
    companion object {
        val all = (1..20)
            .map {
                Line(
                    it, "Code$it", "Nom$it", "Directions$it", it + 10, false
                )
            }.toMutableList()
    }
}