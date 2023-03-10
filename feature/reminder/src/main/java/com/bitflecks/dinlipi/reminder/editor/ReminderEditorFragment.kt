package com.bitflecks.dinlipi.reminder.editor

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bitflecks.dinlipi.reminder.R

class ReminderEditorFragment : Fragment() {

    companion object {
        fun newInstance() = ReminderEditorFragment()
    }

    private lateinit var viewModel: ReminderEditorViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_reminder_editor, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ReminderEditorViewModel::class.java)
        // TODO: Use the ViewModel
    }

}