package com.example.pinboard

import android.graphics.Bitmap
import com.example.pinboard.cache.MemoryCache
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test

class MockkTest {

    private val url: String = "https://banners.adfox.ru/181203/adfox/877909/2778712.jpg";

    private lateinit var remoteBitmap: Bitmap

    @Before
    fun setUp() {
        remoteBitmap = mockk<Bitmap>()
    }

    @Test
    fun checkRealBitmap() {
        //io.mockk.MockKException: no answer found for: MemoryCache(#2). So we use 'relaxed = true'
        val mockMemCache = mockk<MemoryCache>(relaxed = true)
        mockMemCache.put(url, remoteBitmap!!)

        every { mockMemCache.get(url) } returns remoteBitmap

        assertNotNull(remoteBitmap)
    }

}