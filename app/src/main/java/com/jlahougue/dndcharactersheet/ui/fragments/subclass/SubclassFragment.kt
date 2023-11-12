package com.jlahougue.dndcharactersheet.ui.fragments.subclass

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.jlahougue.dndcharactersheet.databinding.TestBinding
import com.jlahougue.dndcharactersheet.ui.main.MainActivity

class SubclassFragment : Fragment() {

    private var _binding: TestBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val main: MainActivity by lazy { activity as MainActivity }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = TestBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}