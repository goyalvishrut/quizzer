package com.example.quizzer.repo

import com.example.quizzer.model.Quiz
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface QuizDao : CrudRepository<Quiz, Int>
