package com.jlahougue.dndcharactersheet.ui.fragments.stats

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.jlahougue.dndcharactersheet.databinding.FragmentStatsBinding
import com.jlahougue.dndcharactersheet.ui.main.MainActivity

class StatsFragment : Fragment() {

    private var _binding: FragmentStatsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val statsViewModel: StatsViewModel  by lazy {
        ViewModelProvider.AndroidViewModelFactory
            .getInstance(requireActivity().application)
            .create(StatsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as MainActivity).mainViewModel.characterID.observe(viewLifecycleOwner) {
            if (it == 0L) return@observe
            statsViewModel.characterID = it
        }

        _binding = FragmentStatsBinding.inflate(inflater, container, false)

        statsViewModel.stats.observe(viewLifecycleOwner) {
            if (it == null) return@observe

            binding.editValueArmorClass.setText(it.armorClass.toString())
            binding.editValueSpeed.setText(it.speed.toString())
            //binding.editValueSpeed.setText(it.speed.toString())

            statsViewModel.stats.removeObservers(viewLifecycleOwner)
        }

        statsViewModel.health.observe(viewLifecycleOwner) {
            if (it == null) return@observe

            binding.columnHealth.editCurrent.setText(it.currentHp.toString())
            binding.columnHealth.editMax.setText(it.maxHp.toString())
            binding.columnHealth.editTemporaryHealth.setText(it.temporaryHp.toString())

            statsViewModel.health.removeObservers(viewLifecycleOwner)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}