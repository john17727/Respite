package dev.juanrincon.respite.presentation.extensions

import androidx.compose.foundation.lazy.LazyListLayoutInfo
import kotlin.math.max

fun LazyListLayoutInfo.normalizedItemPosition(key: Any): Float =
    visibleItemsInfo
        .firstOrNull { it.key == key }
        ?.let {
            val startRange = viewportStartOffset + it.size
            val startPercentage = -1 * ((it.offset.toFloat() + (it.size / 3)) / startRange)
            val endRange = viewportEndOffset - (viewportEndOffset - it.size)
            val endOffsetInRange = it.offset.toFloat() - (viewportEndOffset - (it.size - (it.size / 3))) 
            val endPercentage = endOffsetInRange / endRange
            max(startPercentage, endPercentage)
        }
        ?: 0F