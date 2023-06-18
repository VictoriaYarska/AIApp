package com.epam.community.cross.aiapp.domain.model

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Stable
import com.epam.community.cross.aiapp.R

@Stable
data class Movie(
    val id: Int,
    val name: String,
    val username: String,
    val rate: Int,
    private val previousRate: Int,
    val imageUrl: String
) {
    private val isRateRising: Boolean get() = rate > previousRate

    @DrawableRes
    val iconRate: Int = if (isRateRising) R.drawable.ic_rate_up else R.drawable.ic_rate_down

}
