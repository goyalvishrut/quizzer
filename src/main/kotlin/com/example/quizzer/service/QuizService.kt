package com.example.quizzer.service

import com.example.quizzer.model.Quiz
import com.example.quizzer.model.Response
import com.example.quizzer.model.RestApiError
import com.example.quizzer.model.UserResponse
import com.example.quizzer.model.ViewQuiz
import com.example.quizzer.model.toViewQuizQuestion
import com.example.quizzer.repo.QuestionDao
import com.example.quizzer.repo.QuizDao
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class QuizService(
    private val quizDao: QuizDao,
    private val questionDao: QuestionDao,
) {

    fun createQuiz(category: String, noOfQuestion: Int, title: String): ResponseEntity<Response<Int>> {
        return try {
            val questions = questionDao.findRandomQuestionByCategory(category, noOfQuestion)
            val quiz = Quiz(
                title = title,
                questions = questions,
            )
            ResponseEntity(Response.Success(body = quizDao.save(quiz).id), HttpStatus.CREATED)
        } catch (e: Exception) {
            handleException(e) as ResponseEntity<Response<Int>>
        }
    }

    fun getQuizQuestion(id: Int): ResponseEntity<Response<ViewQuiz>> {
        return try {
            val quiz = quizDao.findById(id)
            ResponseEntity(
                Response.Success(
                    body =
                    ViewQuiz(
                        quizId = quiz.get().id,
                        title = quiz.get().title,
                        questions = quiz.get().questions.map { it.toViewQuizQuestion() },
                    ),
                ),
                HttpStatus.OK,
            )
        } catch (e: Exception) {
            handleException(e) as ResponseEntity<Response<ViewQuiz>>
        }
    }

    fun getQuizScore(id: Int, userResponse: List<UserResponse>): ResponseEntity<Response<Int>> {
        return try {
            val quiz = quizDao.findById(id).get()
            var score = 0
            quiz.questions.forEach { question ->
                val correctAnswer = question.answer
                val userAnswer = userResponse.firstOrNull { it.id == question.id }?.answer
                if (correctAnswer == userAnswer) {
                    score += 1
                }
            }
            ResponseEntity(Response.Success(body = score), HttpStatus.OK)
        } catch (e: Exception) {
            return handleException(e) as ResponseEntity<Response<Int>>
        }
    }

    private fun handleException(e: Exception): ResponseEntity<Response<Any>> {
        printException(e)
        return ResponseEntity(
            Response.Error(error = RestApiError(message = e.localizedMessage, code = null)),
            HttpStatus.BAD_REQUEST,
        )
    }

    private fun printException(e: Exception) {
        println(e.localizedMessage)
    }
}
