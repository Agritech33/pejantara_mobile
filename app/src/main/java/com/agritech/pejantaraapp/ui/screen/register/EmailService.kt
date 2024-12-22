package com.agritech.pejantaraapp.ui.screen.register

import com.sendgrid.*
import com.sendgrid.helpers.mail.Mail
import com.sendgrid.helpers.mail.objects.Content
import com.sendgrid.helpers.mail.objects.Email
import java.io.IOException

class EmailService(private val sendGridApiKey: String) {

    fun sendEmail(recipient: String, subject: String, body: String) {
        val email = Mail(
            Email("bintang.safna@gmail.com"),
            subject,
            Email(recipient),
            Content("text/plain", body)
        )

        val sendGrid = SendGrid(sendGridApiKey)
        val request = Request()
        try {
            request.method = Method.POST
            request.endpoint = "mail/send"
            request.body = email.build()
            val response = sendGrid.api(request)

            println("Response status code: ${response.statusCode}")
            println("Response body: ${response.body}")
            println("Response headers: ${response.headers}")
        } catch (ex: IOException) {
            println("Error sending email: ${ex.message}")
        }
    }
}

