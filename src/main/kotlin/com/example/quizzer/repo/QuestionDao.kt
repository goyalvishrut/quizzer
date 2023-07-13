package com.example.quizzer.repo

import com.example.quizzer.model.Question
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface QuestionDao : CrudRepository<Question, Int> {

    fun findByCategory(category: String): List<Question>

    @Query(
        value = "SELECT * FROM questions q Where q.catagory=:category ORDER BY RANDOM() LIMIT :noOfQuestion",
        nativeQuery = true,
    )
    fun findRandomQuestionByCategory(category: String, noOfQuestion: Int): List<Question>
}
