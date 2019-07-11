package com.example.pinboard.cache

import android.graphics.Bitmap

interface IPictureCache {

    fun get(url: String): Bitmap?

    fun put(url: String, bitmap: Bitmap)

    fun clear()
}