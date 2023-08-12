package com.example.courses.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Topic(
   @StringRes val titleResourceId: Int,
    val courseQuantity: Int,
   @DrawableRes val imageResourceId: Int,
)
