package com.task.composelistviewwithroomdb.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = tableName)
data class DataEntity(
    @PrimaryKey(autoGenerate = true)
    @SerialName("id")
    val id: Int = 0,

    @SerialName("data")
    val data: String = "",

    @SerialName("descryption")
    val descryption: String = "",

    @SerialName("storeData")
    val storeDataPassOrFail: Boolean = true,

    )
const val tableName = "DataInfoEntity"