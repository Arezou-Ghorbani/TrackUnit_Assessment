package com.example.trackunit_assessment.utils

import com.example.trackunit_assessment.App


fun readRawTextFile(resourceId: Int): String? {
    App.appContext?.let {
        return it.resources?.openRawResource(resourceId)?.bufferedReader().use { it?.readText() }
    }
    return null
}
