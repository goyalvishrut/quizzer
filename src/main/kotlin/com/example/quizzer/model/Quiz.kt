package com.example.quizzer.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToMany

@Entity(name = "quiz")
data class Quiz(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int = 0,

    val title: String,
    @ManyToMany
    val questions: List<Question>,
)

data class ViewQuiz(
    val quizId: Int,
    val title: String,
    val questions: List<ViewQuizQuestion>,
)
