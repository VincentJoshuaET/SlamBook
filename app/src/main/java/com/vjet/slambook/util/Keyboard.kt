package com.vjet.slambook.util

import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Keyboard @Inject constructor(private val inputMethodManager: InputMethodManager) {

    fun hide(fragment: Fragment) =
        inputMethodManager.hideSoftInputFromWindow(fragment.requireView().applicationWindowToken, 0)

}