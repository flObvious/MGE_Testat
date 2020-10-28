package ch.ost.rj.mge.bemerkt.model

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "note")
@Parcelize
data class Note (
    @NonNull
    @PrimaryKey(autoGenerate = true) var id:Int = 0,
    var title:String,
    var desc:String
) : Parcelable