package com.bignerdranch.android.criminalintent

import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.DialogFragment
import java.io.File

class ZoomImageDialogFragment : DialogFragment() {

    private var imagePath: String? = null

    companion object {
        private const val ARG_IMAGE_PATH = "imagePath"

        fun newInstance(imagePath: String): ZoomImageDialogFragment {
            val args = Bundle().apply {
                putString(ARG_IMAGE_PATH, imagePath)
            }
            return ZoomImageDialogFragment().apply {
                arguments = args
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        imagePath = arguments?.getString(ARG_IMAGE_PATH)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.zoom_fragment, container, false)
        val imageView = view.findViewById<ImageView>(R.id.zoomedImageView)

        imagePath?.let {
            val imageFile = File(requireContext().filesDir, it)
            if (imageFile.exists()) {
                val displayMetrics = DisplayMetrics()
                requireActivity().windowManager.defaultDisplay.getMetrics(displayMetrics)
                val scaledBitmap = getScaledBitmap(imageFile.absolutePath, displayMetrics.widthPixels, displayMetrics.heightPixels)
                imageView.setImageBitmap(scaledBitmap)
            } else {
                Log.e("ImageZoomDialogFragment", "File not found: $it")
            }
        }


        return view
    }
}