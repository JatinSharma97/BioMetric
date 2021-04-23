package com.contact.biometric

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import java.util.concurrent.Executor

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val executor = ContextCompat.getMainExecutor(this)
        val biometricManager = BiometricManager.from(this)

        when (biometricManager.canAuthenticate()) {
            BiometricManager.BIOMETRIC_SUCCESS ->
                authUser(executor)
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE ->
                Toast.makeText(
                    this,
                    "No biometric hardware",
                    Toast.LENGTH_LONG
                ).show()
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE ->
                Toast.makeText(
                    this,
                    "Biometric hardware unavailable",
                    Toast.LENGTH_LONG
                ).show()
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED ->
                Toast.makeText(
                    this,
                    "Biometric not setup",
                    Toast.LENGTH_LONG
                ).show()
        }
  
    }

    private fun authUser(executor: Executor) {
// 1
        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            // 2
            .setTitle("Authentication Required!")
            // 3
            .setSubtitle("Important authentication")
            // 4
            .setDescription("Please use fingerprint to be able to login your account")
            // 5
            .setDeviceCredentialAllowed(true)
            // 6
            .build()
        // 1
        val biometricPrompt = BiometricPrompt(this, executor,
            object : BiometricPrompt.AuthenticationCallback() {
                // 2
                override fun onAuthenticationSucceeded(
                    result: BiometricPrompt.AuthenticationResult
                ) {
                    super.onAuthenticationSucceeded(result)
                }

                // 3
                override fun onAuthenticationError(
                    errorCode: Int, errString: CharSequence
                ) {
                    super.onAuthenticationError(errorCode, errString)
//                    Toast.makeText(requireContext(), errString, Toast.LENGTH_SHORT).show()
                }

                // 4
                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()

                }
            })
        biometricPrompt.authenticate(promptInfo)


    }
}