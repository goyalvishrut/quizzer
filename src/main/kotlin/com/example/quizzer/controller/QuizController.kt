package com.example.quizzer.controller

import com.example.quizzer.model.Response
import com.example.quizzer.model.RestApiError
import com.example.quizzer.model.UserResponse
import com.example.quizzer.model.ViewQuiz
import com.example.quizzer.service.QuizService
import com.example.quizzer.util.capitalizeFirstWord
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("quiz")
class QuizController(private val quizService: QuizService) {

    @PostMapping("create")
    fun createQuiz(
        @RequestParam category: String,
        @RequestParam noOfQuestion: Int,
        @RequestParam title: String,
    ): ResponseEntity<Response<Int>> {
        return try {
            quizService.createQuiz(category.capitalizeFirstWord(), noOfQuestion, title)
        } catch (e: Exception) {
            println(e.stackTrace)
            ResponseEntity(
                Response.Error(RestApiError(message = e.localizedMessage, code = null)),
                HttpStatus.BAD_REQUEST,
            )
        }
    }

    @GetMapping("get/{id}")
    fun getQuizQuestion(@PathVariable id: Int): ResponseEntity<Response<ViewQuiz>> {
        return try {
            quizService.getQuizQuestion(id)
        } catch (e: Exception) {
            println(e.stackTrace)
            ResponseEntity(
                Response.Error(RestApiError(message = e.localizedMessage, code = null)),
                HttpStatus.BAD_REQUEST,
            )
        }
    }

    @PostMapping("submit/{id}")
    fun getQuizScore(
        @PathVariable id: Int,
        @RequestBody userResponse: List<UserResponse>,
    ): ResponseEntity<Response<Int>> {
        return try {
            quizService.getQuizScore(id, userResponse)
        } catch (e: Exception) {
            println(e.stackTrace)
            ResponseEntity(
                Response.Error(RestApiError(message = e.localizedMessage, code = null)),
                HttpStatus.BAD_REQUEST,
            )
        }
    }
}
