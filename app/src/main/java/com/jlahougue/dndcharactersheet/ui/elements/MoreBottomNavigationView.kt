package com.jlahougue.dndcharactersheet.ui.elements

import android.content.Context
import android.util.AttributeSet
import com.google.android.material.bottomnavigation.BottomNavigationView

class MoreBottomNavigationView(context: Context, attrs: AttributeSet) : BottomNavigationView(context, attrs) {
    override fun getMaxItemCount(): Int {
        return 10
    }
}