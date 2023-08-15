package io.astronout.core.utils

import android.graphics.Bitmap
import androidx.annotation.CheckResult
import java.io.ByteArrayOutputStream

@CheckResult
internal fun Bitmap.toJpgByteArray(quality: Int = 80): ByteArray =
    ByteArrayOutputStream().use {
        this.compress(Bitmap.CompressFormat.JPEG, quality, it)
        it.flush()
        it.toByteArray()
    }