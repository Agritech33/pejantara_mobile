package com.agritech.pejantaraapp.data.retrofit.response

import com.google.gson.annotations.SerializedName

data class TrashPredictResponse(
    @field:SerializedName("confidence")
    val confidence: Float,

    @field:SerializedName("prediction")
    val prediction: String
)


//data class TrashPredictResponse(
//
//    @field:SerializedName("predicted_class")
//    val predictedClass: PredictedClass,
//)
//
//data class Data(
//
//    @field:SerializedName("nama")
//    val nama: String,
//
//    @field:SerializedName("artikel")
//    val artikel: String,
//
//    @field:SerializedName("id")
//    val id: Int,
//
//    @field:SerializedName("video")
//    val video: String,
//)
//
//data class PredictedClass(
//
//    @field:SerializedName("data")
//    val data: Data,
//)
