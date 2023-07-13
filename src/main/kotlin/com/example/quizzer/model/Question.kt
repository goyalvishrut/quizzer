package com.example.quizzer.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity(name = "questions")
data class Question(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int = 0,

    val question: String,
    val option1: String,
    val option2: String,
    val option3: String,
    val option4: String,
    val answer: String,
    @Column(name = "catagory")
    val category: String,
    @Column(name = "dificulty")
    val difficulty: String,
)

data class ViewQuestion(
    val id: Int,
    val question: String,
    val option1: String,
    val option2: String,
    val option3: String,
    val option4: String,
    val answer: String,
    val category: String,
    val difficulty: String,
)

data class AddQuestion(
    val question: String,
    val option1: String,
    val option2: String,
    val option3: String,
    val option4: String,
    val answer: String,
    val category: String,
    val difficulty: String,
)

data class ViewQuizQuestion(
    val id: Int,
    val question: String,
    val option1: String,
    val option2: String,
    val option3: String,
    val option4: String,
)

fun Question.toViewQuestion() =
    ViewQuestion(id, question, option1, option2, option3, option4, answer, category, difficulty)

fun AddQuestion.toQuestion() =
    Question(
        question = question,
        option1 = option1,
        option2 = option2,
        option3 = option3,
        option4 = option4,
        answer = answer,
        category = category,
        difficulty = difficulty,
    )

data class UpdateQuestion(
    val question: String? = null,
    val option1: String? = null,
    val option2: String? = null,
    val option3: String? = null,
    val option4: String? = null,
    val answer: String? = null,
    val category: String? = null,
    val difficulty: String? = null,
)

fun Question.toViewQuizQuestion() = ViewQuizQuestion(id, question, option1, option2, option3, option4)
