package com.agritech.pejantaraapp.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class TutorialModel (
    var photo: String = "",
    var title: String = ""
) : Parcelable