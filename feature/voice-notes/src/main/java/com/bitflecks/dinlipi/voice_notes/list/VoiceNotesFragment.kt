package com.bitflecks.dinlipi.voice_notes.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bitflecks.dinlipi.voice_notes.R

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