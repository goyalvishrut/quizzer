package com.example.quizzer

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class QuizzerApplication

fun main(args: Array<String>) {
    runApplication<QuizzerApplication>(*args)
}
