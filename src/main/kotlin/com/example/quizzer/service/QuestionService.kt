package com.example.quizzer.service

import com.example.quizzer.model.AddQuestion
import com.example.quizzer.model.Response
import com.example.quizzer.model.RestApiError
import com.example.quizzer.model.UpdateQuestion
import com.example.quizzer.model.ViewQuestion
import com.example.quizzer.model.toQuestion
import com.example.quizzer.model.toViewQuestion
import com.example.quizzer.repo.QuestionDao
import com.example.quizzer.util.capitalizeFirstWord
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class QuestionService(private val questionDao: QuestionDao) {

    fun getAllQuestions(): ResponseEntity<Response<List<ViewQuestion>>> {
        return try {
            ResponseEntity(
                Response.Success(
                    body = questionDao.findAll().toList().map { it.toViewQuestion() },
                ),
                HttpStatus.OK,
            )
        } catch (e: Exception) {
            handleExemption(e) as ResponseEntity<Response<List<ViewQuestion>>>
        }
    }

    fun getAllQuestionsByCategory(category: String): ResponseEntity<Response<List<ViewQuestion>>> {
        return try {
            return ResponseEntity(
                Response.Success(
                    body = questionDao.findByCategory(category.capitalizeFirstWord()).map { it.toViewQuestion() },
                ),
                HttpStatus.OK,
            )
        } catch (e: Exception) {
            handleExemption(e) as ResponseEntity<Response<List<ViewQuestion>>>
        }
    }

    fun addQuestion(question: AddQuestion): ResponseEntity<Response<Int>> {
        return try {
            val q = question.toQuestion()
            ResponseEntity(
                Response.Success(questionDao.save(q).toViewQuestion().id),
                HttpStatus.CREATED,
            )
        } catch (e: Exception) {
            handleExemption(e) as ResponseEntity<Response<Int>>
        }
    }

    fun deleteQuestionById(id: Int): ResponseEntity<Response<String>> {
        return try {
            ResponseEntity(
                questionDao.deleteById(id).let { Response.Success("Success") },
                HttpStatus.OK,
            )
        } catch (e: Exception) {
            handleExemption(e) as ResponseEntity<Response<String>>
        }
    }

    fun updateQuestionById(id: Int, updateQuestion: UpdateQuestion): ResponseEntity<Response<ViewQuestion>> {
        return try {
            val q = questionDao.findById(id).get()
            val newCopy = q.copy(
                question = updateQuestion.question ?: q.question,
                answer = updateQuestion.answer ?: q.answer,
                option1 = updateQuestion.option1 ?: q.option1,
                option2 = updateQuestion.option2 ?: q.option2,
                option3 = updateQuestion.option3 ?: q.option3,
                option4 = updateQuestion.option4 ?: q.option4,
                difficulty = updateQuestion.difficulty ?: q.difficulty,
                category = updateQuestion.category ?: q.category,
            )
            ResponseEntity(Response.Success(questionDao.save(newCopy).toViewQuestion()), HttpStatus.OK)
        } catch (e: Exception) {
            handleExemption(e) as ResponseEntity<Response<ViewQuestion>>
        }
    }

    private fun handleExemption(e: Exception): ResponseEntity<Response<Any>> {
        println(e.stackTrace)
        return ResponseEntity(
            Response.Error(error = RestApiError(message = e.message, code = null)),
            HttpStatus.BAD_REQUEST,
        )
    }
}
