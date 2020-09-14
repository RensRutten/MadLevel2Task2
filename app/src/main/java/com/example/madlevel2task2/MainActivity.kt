package com.example.madlevel2task2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.madlevel2task2.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.questions_place.*

class MainActivity : AppCompatActivity() {

    private val Question = arrayListOf<Quiz>()
    private val questionAdapter = QuestionAdapter(Question)
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
    }

    private fun initViews() {
        binding.rvQuiz.layoutManager =
            LinearLayoutManager(this@MainActivity, RecyclerView.VERTICAL, false)
        binding.rvQuiz.adapter = questionAdapter
        binding.rvQuiz.addItemDecoration(
            DividerItemDecoration(
                this@MainActivity,
                DividerItemDecoration.VERTICAL
            )
        )

        Question.add(Quiz("A 'val' and 'var' are the same.", false))
        Question.add(Quiz("Mobile Application Developments grants 12 ECTS.", false))
        Question.add(Quiz("A Unit in Kotlin corresponds to a void in Java", true))
        Question.add(Quiz("In Kotlin 'when' replaces the 'switch' operation in Java.", true))
        Question.add(Quiz("Android 8.0 Oreo is the latest Android version.", false))
        Question.add(Quiz("Dp stands for density-independent pixels.", true))
        Question.add(Quiz("View binding is the same as findViewById", false))

        questionAdapter.notifyDataSetChanged()
        createItemTouchHelper().attachToRecyclerView(rvQuiz)
    }


    private fun createItemTouchHelper(): ItemTouchHelper {
        var callback = object :
            ItemTouchHelper.SimpleCallback(
                0, ItemTouchHelper.LEFT or
                        ItemTouchHelper.RIGHT
            ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false;
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition;
                val swipedQuestion: Quiz = Question[position]

                if (direction == ItemTouchHelper.RIGHT) {
                    if (swipedQuestion.isTrue) {
                        Question.removeAt(position)
                        questionAdapter.notifyItemRemoved(position)
                    } else {
                        WrongMessage();
                        questionAdapter.notifyItemChanged(position)
                    }

                    if (direction == ItemTouchHelper.LEFT) {
                        if (swipedQuestion.isTrue) {
                            Question.removeAt(position)
                            questionAdapter.notifyItemRemoved(position)
                        } else {
                            WrongMessage()
                            questionAdapter.notifyItemChanged(position)
                        }
                    }
                }
            }

        }
        return ItemTouchHelper(callback)
    }

        private fun WrongMessage() {
            var snackbarMessage: String = "The Answer is Wrong, the Question will not be removed "
            Snackbar.make(binding.root, snackbarMessage, Snackbar.LENGTH_SHORT).show()
        }


    }












