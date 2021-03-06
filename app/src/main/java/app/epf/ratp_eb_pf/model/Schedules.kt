package app.epf.ratp_eb_pf.model


import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "schedules")
data class Schedules(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val message: String,
    val destination: String,
    val uuid: String = message + destination  // Pour avoir un uuid unique
) : Serializable

