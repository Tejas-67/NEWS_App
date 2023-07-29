package com.example.news.DataModel

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName="source", foreignKeys = [ForeignKey(entity = Article::class, parentColumns = ["id"], childColumns = ["ID_SOURCEE"])])

data class Source(
    @PrimaryKey(autoGenerate = false) val ID_SOURCEE: Int=0,
    @SerializedName("id") val id_source: String,
    val name: String?
    ):Parcelable