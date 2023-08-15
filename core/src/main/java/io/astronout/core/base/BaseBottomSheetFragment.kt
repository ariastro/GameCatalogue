package io.astronout.core.base

import android.os.Bundle
import android.view.View
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import io.astronout.core.R

abstract class BaseBottomSheetFragment: BottomSheetDialogFragment() {

    open fun initData() {}
    open fun initUI() {}
    open fun initAction() {}
    open fun initObserver() {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initData()
        initUI()
        initAction()
        initObserver()

    }

}