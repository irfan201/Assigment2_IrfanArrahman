package com.example.assigment2_irfanarrahman.view

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.DatePicker.OnDateChangedListener
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.assigment2_irfanarrahman.DiaryListener
import com.example.assigment2_irfanarrahman.adapter.DiaryAdapter
import com.example.assigment2_irfanarrahman.databinding.FragmentCalendarListBinding
import com.example.assigment2_irfanarrahman.room.DiaryDatabase
import com.example.assigment2_irfanarrahman.room.DiaryEntities
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

class CalendarListFragment : Fragment(), DiaryListener {
    private var _binding: FragmentCalendarListBinding? = null
    private val binding get() = _binding
    private lateinit var diaryDatabase: DiaryDatabase
    private lateinit var diaryAdapter: DiaryAdapter
    private lateinit var date :String


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCalendarListBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        diaryDatabase = DiaryDatabase.getDatabase(requireContext())
        binding?.apply {
            ivEmpty.isVisible = true
            tvEmpty.isVisible = true
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                binding?.datePicker?.setOnDateChangedListener(object : OnDateChangedListener {
                    override fun onDateChanged(
                        view: DatePicker?,
                        year: Int,
                        monthOfYear: Int,
                        dayOfMonth: Int
                    ) {
                        val datePick = LocalDate.of(year, monthOfYear + 1, dayOfMonth)
                        val dateFormat =
                            DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale.getDefault())
                         date = dateFormat.format(datePick)
                        lifecycleScope.launch {
                            tvDateTittle.text = date
                            val data = diaryDatabase.diaryDao().getDiaryDate(date)
                            if (data.isEmpty()) {
                                ivEmpty.isVisible = true
                                tvEmpty.isVisible = true
                                rvDate.isVisible = false
                            } else {
                                ivEmpty.isVisible = false
                                tvEmpty.isVisible = false
                                rvDate.isVisible = true
                            }
                            initData(data)
                        }
                    }

                })
            }

        }
    }

    private fun initData(listDiary: List<DiaryEntities>) {
        diaryAdapter = DiaryAdapter(listDiary, this)
        binding?.rvDate?.layoutManager = LinearLayoutManager(requireContext())
        binding?.rvDate?.adapter = diaryAdapter
        diaryAdapter.updateDiary(listDiary)
    }

    override fun onDelete(diaryEntities: DiaryEntities) {
        val deleteDialog = AlertDialog.Builder(requireContext())
        deleteDialog.setTitle("Konfirmasi Hapus Diary")
        deleteDialog.setMessage("Apakah anda yakin ingin menghapusnya")
        deleteDialog.setNegativeButton(
            "tidak"
        ) { dialog, which -> }
        deleteDialog.setPositiveButton(
            "iya"
        ) { dialog, which ->
            lifecycleScope.launch {
                diaryDatabase.diaryDao().deleteDiary(diaryEntities)
                val data = diaryDatabase.diaryDao().getDiaryDate(date)
                diaryAdapter.updateDiary(data)
                if (data.isEmpty()) {
                    binding?.ivEmpty?.isVisible = true
                    binding?.tvEmpty?.isVisible = true
                    binding?.rvDate?.isVisible = false
                }

            }
        }
        deleteDialog.show()
    }


    override fun onClick(id: Int) {
        val intent = Intent(requireContext(), DetailDiaryActivity::class.java)
        intent.putExtra(DetailDiaryActivity.USER_ID, id)
        startActivity(intent)
    }

}