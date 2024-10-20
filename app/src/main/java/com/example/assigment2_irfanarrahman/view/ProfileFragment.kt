package com.example.assigment2_irfanarrahman.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.example.assigment2_irfanarrahman.databinding.FragmentProfileBinding
import com.example.assigment2_irfanarrahman.preference.PreferenceDataStore
import com.example.assigment2_irfanarrahman.preference.dataStore
import kotlinx.coroutines.launch


class ProfileFragment : Fragment() {

    private var _binding :FragmentProfileBinding? =null
    private val binding get() = _binding
    private val pref by lazy {
        activity?.applicationContext?.dataStore?.let { PreferenceDataStore.getInstance(it) }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater,container,false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        binding?.apply {
            lifecycleScope.launch {
                tvEmail.text = pref?.getEmail()

            }
            btnLogout.setOnClickListener {
                lifecycleScope.launch {
                    pref?.puLogin(false)
                }
                startActivity(Intent(requireContext(), LoginActivity::class.java))
                activity?.finish()
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}