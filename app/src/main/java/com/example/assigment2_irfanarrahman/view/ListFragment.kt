package com.example.assigment2_irfanarrahman.view


import android.content.DialogInterface
import android.content.DialogInterface.OnClickListener
import android.content.Intent
import android.icu.util.Calendar
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.assigment2_irfanarrahman.DiaryListener
import com.example.assigment2_irfanarrahman.R
import com.example.assigment2_irfanarrahman.adapter.DiaryAdapter
import com.example.assigment2_irfanarrahman.databinding.FragmentListBinding
import com.example.assigment2_irfanarrahman.room.DiaryDatabase
import com.example.assigment2_irfanarrahman.room.DiaryEntities
import kotlinx.coroutines.launch


class ListFragment : Fragment(), DiaryListener {
    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding

    private lateinit var diaryDatabase: DiaryDatabase
    private lateinit var adapter: DiaryAdapter
    private val selectedDate = Calendar.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        diaryDatabase = DiaryDatabase.getDatabase(requireContext())

        lifecycleScope.launch {
            val data = diaryDatabase.diaryDao().getDiary()
            if (data.isEmpty()){
                binding?.ivEmpty?.isVisible = true
                binding?.tvEmpty?.isVisible = true
                binding?.rvDiary?.isVisible = false
            } else{
                binding?.ivEmpty?.isVisible = false
                binding?.tvEmpty?.isVisible = false
                binding?.rvDiary?.isVisible = true
            }
            initRecycleView(data)
        }

        binding?.fabAdd?.setOnClickListener {
            startActivity(Intent(requireContext(), UpdateDiaryActivity::class.java))
        }

        binding?.etSearch?.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                lifecycleScope.launch {
                    adapter.updateDiary(diaryDatabase.diaryDao().getDiaryTittle(s.toString()))
                }
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                lifecycleScope.launch {
                    adapter.updateDiary(diaryDatabase.diaryDao().getDiaryTittle(s.toString()))
                }
            }

            override fun afterTextChanged(s: Editable?) {
                lifecycleScope.launch {
                    adapter.updateDiary(diaryDatabase.diaryDao().getDiaryTittle(s.toString()))
                }
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
                lifecycleScope.launch {
                    diaryDatabase.diaryDao().deleteDiary(diaryEntities)
                    adapter.updateDiary(diaryDatabase.diaryDao().getDiary())
                    if (diaryDatabase.diaryDao().getDiary().isEmpty()){
                        binding?.ivEmpty?.isVisible = true
                        binding?.tvEmpty?.isVisible = true
                        binding?.rvDiary?.isVisible = false
                    }
                }
            }
        })
        deleteDialog.show()
    }


        override fun onClick(id: Int) {
            val intent = Intent(requireContext(), DetailDiaryActivity::class.java)
            intent.putExtra(DetailDiaryActivity.USER_ID,id)
            startActivity(intent)
            activity?.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

}
