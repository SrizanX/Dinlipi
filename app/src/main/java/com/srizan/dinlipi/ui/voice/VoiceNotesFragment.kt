package com.srizan.dinlipi.ui.voice

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.srizan.dinlipi.R

class VoiceNotesFragment : Fragment() {

    companion object {
        fun newInstance() = VoiceNotesFragment()
    }

    private lateinit var viewModel: VoiceNotesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.voice_notes_fragment, container, false)
    }

}