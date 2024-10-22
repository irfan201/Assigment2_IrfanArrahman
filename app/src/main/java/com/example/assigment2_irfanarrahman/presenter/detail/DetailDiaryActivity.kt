package com.example.assigment2_irfanarrahman.presenter.detail

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.example.assigment2_irfanarrahman.R
import com.example.assigment2_irfanarrahman.databinding.ActivityDetailDiaryBinding
import com.example.assigment2_irfanarrahman.data.source.local.room.DiaryDatabase
import com.example.assigment2_irfanarrahman.data.model.DiaryEntities
import com.example.assigment2_irfanarrahman.domain.model.DiaryStateDetail
import com.example.assigment2_irfanarrahman.presenter.update.UpdateDiaryActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailDiaryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailDiaryBinding

    private val viewModel: DetailViewModel by viewModels()
    private var idUser: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailDiaryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        idUser = intent.getIntExtra(USER_ID, 0)
        viewModel.getDiaryDetail(idUser)
        lifecycleScope.launch {
            viewModel.diaryState.collect(object : FlowCollector<DiaryStateDetail> {
                override suspend fun emit(value: DiaryStateDetail) {
                    when (value) {
                        is DiaryStateDetail.Error -> {
                            Toast.makeText(
                                this@DetailDiaryActivity,
                                value.message,
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        DiaryStateDetail.Loading -> {}
                        is DiaryStateDetail.Success -> {
                            initData(value.diary)
                        }
                    }
                }

            })
        }

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Detail"


    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_detail, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.btn_edit -> {
                editDiary(idUser)
                true
            }

            android.R.id.home -> {
                finish()
                true
            }

            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun editDiary(idDiary: Int) {
        val intent = Intent(this, UpdateDiaryActivity::class.java)
        intent.putExtra(UpdateDiaryActivity.ID_DIARY, idDiary)
        startActivity(intent)
    }

    private fun initData(diaryEntities: DiaryEntities) {
        binding.apply {
            tvDetailTittle.text = diaryEntities.tittle
            tvDetailDate.text = diaryEntities.date
            tvDetailDesc.text = diaryEntities.note
            ivExpresionDetail.setImageDrawable(
                ContextCompat.getDrawable(
                    this@DetailDiaryActivity,
                    diaryEntities.expressionImage
                )
            )
        }
    }

    companion object {
        val USER_ID = "user_id"
    }
}