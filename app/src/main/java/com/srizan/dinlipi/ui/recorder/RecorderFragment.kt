package com.srizan.dinlipi.ui.recorder

import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.srizan.dinlipi.R
import com.srizan.dinlipi.databinding.RecorderFragmentBinding

class RecorderFragment : Fragment() {

    private val viewModel: RecorderViewModel by viewModels()
    private lateinit var binding: RecorderFragmentBinding

    private val recorderPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            if (isGranted){
                Toast.makeText(requireContext(),"Granted", Toast.LENGTH_SHORT).show()
                binding.textView.text = "Granted"
            } else {
                Toast.makeText(requireContext(),"Not Granted", Toast.LENGTH_SHORT).show()
                binding.textView.text = "Not Granted"
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.recorder_fragment, container, false )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        if(ContextCompat.checkSelfPermission(requireContext(),
                android.Manifest.permission.RECORD_AUDIO)
            == PackageManager.PERMISSION_GRANTED){
                binding.textView.text = "Already Granted"
            requestPermission()
        }
        //requestPermission()
    }


    private fun requestPermission(){
        when{
            ContextCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.RECORD_AUDIO)
                    == PackageManager.PERMISSION_GRANTED -> {
                //Record Start
            }
            shouldShowRequestPermissionRationale(android.Manifest.permission.RECORD_AUDIO)->{
                binding.textView.text = "We need permission"
                launchRequestDialog()
            }
            else ->{
                launchRequestDialog()
            }
        }
    }

    private fun launchRequestDialog(){
        recorderPermissionLauncher.launch(android.Manifest.permission.RECORD_AUDIO)
    }
}