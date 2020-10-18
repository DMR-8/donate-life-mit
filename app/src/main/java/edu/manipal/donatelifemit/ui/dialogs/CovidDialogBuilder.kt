package edu.manipal.donatelifemit.ui.dialogs

import androidx.fragment.app.FragmentManager

class CovidDialogBuilder() {

    private lateinit var fragmentManager: FragmentManager

    fun setParentFragmentManager(fragmentManager: FragmentManager): CovidDialogBuilder {
        this.fragmentManager = fragmentManager
        return this
    }
    fun build() {
        var inviteDialogFragment =  CovidDialog()
        inviteDialogFragment?.show(fragmentManager, "Invite")
    }
}
