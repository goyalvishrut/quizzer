package com.example.quizzer.controller

import com.example.quizzer.model.AddQuestion
import com.example.quizzer.model.Response
import com.example.quizzer.model.RestApiError
import com.example.quizzer.model.UpdateQuestion
import com.example.quizzer.model.ViewQuestion
import com.example.quizzer.service.QuestionService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("questions")
class QuestionController(private val questionService: QuestionService) {

    @GetMapping("allQuestions")
    fun getAllQuestions(): ResponseEntity<Response<List<ViewQuestion>>> {
        return try {
            questionService.getAllQuestions()
        } catch (e: Exception) {
            println(e.stackTrace)
            ResponseEntity(
                Response.Error(RestApiError(message = e.localizedMessage, code = null)),
                HttpStatus.BAD_REQUEST,
            )
        }
    }

    @GetMapping("category/{category}")
    fun getAllQuestionsByCategory(@PathVariable("category") category: String): ResponseEntity<Response<List<ViewQuestion>>> {
        return try {
            questionService.getAllQuestionsByCategory(category)
        } catch (e: Exception) {
            println(e.stackTrace)
            ResponseEntity(
                Response.Error(RestApiError(message = e.localizedMessage, code = null)),
                HttpStatus.BAD_REQUEST,
            )
        }
    }

    @GetMapping("/filter")
    fun getAllQuestionsByCategoryOther(@RequestParam("category") category: String): ResponseEntity<Response<List<ViewQuestion>>> {
        return try {
            questionService.getAllQuestionsByCategory(category)
        } catch (e: Exception) {
            println(e.stackTrace)
            ResponseEntity(
                Response.Error(RestApiError(message = e.localizedMessage, code = null)),
                HttpStatus.BAD_REQUEST,
            )
        }
    }

    @PostMapping("/add")
    fun addQuestion(@RequestBody question: AddQuestion): ResponseEntity<Response<Int>> {
        return try {
            questionService.addQuestion(question)
        } catch (e: Exception) {
            println(e.stackTrace)
            ResponseEntity(
                Response.Error(RestApiError(message = e.localizedMessage, code = null)),
                HttpStatus.BAD_REQUEST,
            )
        }
    }

    @DeleteMapping("delete/{id}")
    fun deleteQuestionById(@RequestParam("id") id: Int): ResponseEntity<Response<String>> {
        return try {
            questionService.deleteQuestionById(id)
        } catch (e: Exception) {
            println(e.stackTrace)
            ResponseEntity(
                Response.Error(RestApiError(message = e.localizedMessage, code = null)),
                HttpStatus.BAD_REQUEST,
            )
        }
    }

    @PutMapping("update")
    fun updateQuestionById(
        @RequestParam("id") id: Int,
        @RequestBody updateQuestion: UpdateQuestion,
    ): ResponseEntity<Response<ViewQuestion>> {
        return try {
            questionService.updateQuestionById(id, updateQuestion)
        } catch (e: Exception) {
            println(e.stackTrace)
            ResponseEntity(
                Response.Error(RestApiError(message = e.localizedMessage, code = null)),
                HttpStatus.BAD_REQUEST,
            )
        }
    }
}
