package com.example.assigment2_irfanarrahman.presenter.list


import android.content.DialogInterface
import android.content.DialogInterface.OnClickListener
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.assigment2_irfanarrahman.presenter.adapter.DiaryListener
import com.example.assigment2_irfanarrahman.R
import com.example.assigment2_irfanarrahman.presenter.adapter.DiaryAdapter
import com.example.assigment2_irfanarrahman.databinding.FragmentListBinding
import com.example.assigment2_irfanarrahman.data.model.DiaryEntities
import com.example.assigment2_irfanarrahman.domain.model.DiaryState
import com.example.assigment2_irfanarrahman.presenter.detail.DetailDiaryActivity
import com.example.assigment2_irfanarrahman.presenter.update.UpdateDiaryActivity
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.launch


class ListFragment : Fragment(), DiaryListener {
    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding

    private val viewModel: ListDiaryViewModel by activityViewModels()


    private lateinit var adapter: DiaryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel.getDiary()

        lifecycleScope.launch {
            viewModel.diaryState.collect(object : FlowCollector<DiaryState> {
                override suspend fun emit(value: DiaryState) {
                    when (value) {
                        is DiaryState.Error -> {
                            Toast.makeText(requireContext(), value.message, Toast.LENGTH_SHORT)
                                .show()
                        }

                        DiaryState.Loading -> {
                        }

                        is DiaryState.Success -> {
                            if (value.diary.isEmpty()) {
                                binding?.ivEmpty?.isVisible = true
                                binding?.tvEmpty?.isVisible = true
                                binding?.rvDiary?.isVisible = false
                            } else {
                                binding?.ivEmpty?.isVisible = false
                                binding?.tvEmpty?.isVisible = false
                                binding?.rvDiary?.isVisible = true
                            }
                            initRecycleView(value.diary)
                        }
                    }
                }
            })
        }

        binding?.fabAdd?.setOnClickListener {
            startActivity(Intent(requireContext(), UpdateDiaryActivity::class.java))
        }

        binding?.etSearch?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
               viewModel.getDiaryTittle(s.toString())
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.getDiaryTittle(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {
                viewModel.getDiaryTittle(s.toString())
            }
        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun initRecycleView(listDiary: List<DiaryEntities>) {
        adapter = DiaryAdapter(listDiary, this)
        binding?.rvDiary?.layoutManager = LinearLayoutManager(requireContext())
        binding?.rvDiary?.adapter = adapter
    }

    override fun onDelete(diaryEntities: DiaryEntities) {
        val deleteDialog = AlertDialog.Builder(requireContext())
        deleteDialog.setTitle("Konfirmasi Hapus Diary")
        deleteDialog.setMessage("Apakah anda yakin ingin menghapusnya")
        deleteDialog.setNegativeButton(
            "tidak"
        ) { dialog, which -> }
        deleteDialog.setPositiveButton("iya", object : OnClickListener {
            override fun onClick(dialog: DialogInterface?, which: Int) {
                viewModel.deleteDiary(diaryEntities)
            }
        })
        deleteDialog.show()
    }


    override fun onClick(id: Int) {
        val intent = Intent(requireActivity(), DetailDiaryActivity::class.java)
        intent.putExtra(DetailDiaryActivity.USER_ID, id)
        startActivity(intent)
        activity?.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }

}
