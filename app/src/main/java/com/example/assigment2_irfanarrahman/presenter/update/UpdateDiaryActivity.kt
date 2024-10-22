package com.example.assigment2_irfanarrahman.presenter.update

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.DatePicker
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.lifecycleScope
import com.example.assigment2_irfanarrahman.R
import com.example.assigment2_irfanarrahman.databinding.ActivityUpdateDiaryBinding
import com.example.assigment2_irfanarrahman.databinding.CustomExpressionDialogBinding
import com.example.assigment2_irfanarrahman.data.model.DiaryEntities
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale

@AndroidEntryPoint
class UpdateDiaryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUpdateDiaryBinding
    private val viewModel: UpdateDiaryViewModel by viewModels()
    private val selectedDate = Calendar.getInstance()
    private var selectedImageId: Int = R.drawable.happy
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateDiaryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val dataId = intent.getIntExtra(ID_DIARY, 0)


        if (dataId != 0) {
            lifecycleScope.launch {
                viewModel.getDiaryDetail(dataId)
            }
            val data = viewModel.diaryData.value
            addDiary(dataId, data)
            Log.d("data", "kemana $dataId")
        } else {
            Log.d("data", "kemana $dataId")
            addDiary(null, null)
        }


        binding.ivExpresion.setOnClickListener {
            showExpressionDialog()
        }

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Update"

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {

            android.R.id.home -> {
                finish()
                true
            }

            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun addDiary(index: Int?, diaryEntities: DiaryEntities?) {
        Log.d("data", diaryEntities.toString())
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            binding.apply {
                etDate.setText(
                    DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale.getDefault())
                        .format(LocalDate.now())
                )
                if (diaryEntities != null && index != null) {
                    btnAdd.text = "Edit"
                    etTittle.setText(diaryEntities.tittle)
                    etDesc.setText(diaryEntities.note)
                    etDate.setText(diaryEntities.date)
                    ivExpresion.setImageDrawable(
                        ResourcesCompat.getDrawable(
                            resources,
                            diaryEntities.expressionImage,
                            null
                        )
                    )
                }
                etDate.setOnClickListener {
                    setDate()
                }

                btnAdd.setOnClickListener {
                    if (validate()) {
                        val tittle = etTittle.text.toString()
                        val desc = etDesc.text.toString()

                        lifecycleScope.launch {
                            if (diaryEntities != null) {
                                Log.d("data", "$tittle $desc")
                                viewModel.updateDiary(
                                    DiaryEntities(
                                        id = index ?: 0,
                                        tittle = tittle,
                                        date = etDate.text.toString(),
                                        note = desc,
                                        expressionImage = selectedImageId
                                    )
                                )
                            } else {
                                viewModel.insertDiary(
                                    DiaryEntities(
                                        tittle = tittle,
                                        date = etDate.text.toString(),
                                        note = desc,
                                        expressionImage = selectedImageId
                                    )
                                )
                            }

                        }
                        finish()
                    }

                }
            }


        }
    }

    private fun showExpressionDialog() {
        val dialogBinding = CustomExpressionDialogBinding.inflate(layoutInflater)
        val dialog = AlertDialog.Builder(this).create()
        dialog.setView(dialogBinding.root)
        dialog.show()

        dialogBinding.ivLove.setOnClickListener {
            initExpression(R.drawable.love)
            dialog.dismiss()
        }
        dialogBinding.ivSad.setOnClickListener {
            initExpression(R.drawable.sad)
            dialog.dismiss()
        }
        dialogBinding.ivNormal.setOnClickListener {
            initExpression(R.drawable.normal)
            dialog.dismiss()
        }
        dialogBinding.ivAngry.setOnClickListener {
            initExpression(R.drawable.angry)
            dialog.dismiss()
        }

        dialogBinding.ivHappy.setOnClickListener {
            initExpression(R.drawable.happy)
            dialog.dismiss()
        }

        dialogBinding.ivShock.setOnClickListener {
            initExpression(R.drawable.shocked)
            dialog.dismiss()
        }

    }

    private fun setDate() {
        val datePickerDialog = DatePickerDialog(
            this,
            object : OnDateSetListener {
                override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        val datePick = LocalDate.of(year, month + 1, dayOfMonth)
                        val dateFormat =
                            DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale.getDefault())
                        val date = dateFormat.format(datePick)
                        selectedDate.set(year, month, dayOfMonth)
                        binding.etDate.setText(date)
                    }

                }
            },
            selectedDate.get(Calendar.YEAR),
            selectedDate.get(Calendar.MONTH),
            selectedDate.get(Calendar.DAY_OF_MONTH)
        )
        val datePicker = datePickerDialog.datePicker
        datePicker.maxDate = System.currentTimeMillis()
        datePickerDialog.show()
    }

    private fun initExpression(drawable: Int) {
        binding.ivExpresion.setImageDrawable(
            ResourcesCompat.getDrawable(
                resources, drawable, null
            )
        )
        selectedImageId = drawable
    }

    private fun validate(): Boolean {
        binding.apply {
            if (etTittle.text.toString().isEmpty()) {
                etTittle.error = "harap isi judul terlebih dahulu"
                return false
            }
            if (etDesc.text.toString().isEmpty()) {
                etTittle.error = "harap isi catatan diarynya terlebih dahulu"
                return false
            }
        }
        return true

    }

    companion object {
        val ID_DIARY = "id_diary"
    }
}