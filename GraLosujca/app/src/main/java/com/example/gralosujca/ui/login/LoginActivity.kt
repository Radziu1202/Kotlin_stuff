package com.example.gralosujca.ui.login

import android.app.Activity
import android.content.Intent
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.example.gralosujca.DB.DBHelper
import com.example.gralosujca.ListActivity
import com.example.gralosujca.MainActivity

import com.example.gralosujca.R
import com.example.gralosujca.RegisterActivity


class LoginActivity : AppCompatActivity() {

    private lateinit var loginViewModel: LoginViewModel

    companion object{
        lateinit var user: String
         lateinit var db: DBHelper
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        db= DBHelper(this)
        setContentView(R.layout.activity_login)

        val username = findViewById<EditText>(R.id.username)
        val password = findViewById<EditText>(R.id.password)
        val login = findViewById<Button>(R.id.login)

        val register_button=findViewById<Button>(R.id.signup)
        val ranks_button = findViewById<Button>(R.id.ranks)

        register_button.setOnClickListener(){
            Thread(){
                run{
                }
                runOnUiThread(){
                    val  intent = Intent(this, RegisterActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
                    startActivity(intent)
                }
            }.start()

            Thread(){
                run{
                    Thread.sleep(1000)
                }
                runOnUiThread(){
                    onPause()
                }
            }
        }

        ranks_button.setOnClickListener(){
            Thread(){
                run{
                }
                runOnUiThread(){
                    val  intent = Intent(this, ListActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
                    startActivity(intent)
                }
            }.start()

            Thread(){
                run{
                    Thread.sleep(1000)
                }
                runOnUiThread(){
                    onPause()
                }
            }
        }

        loginViewModel = ViewModelProvider(this, LoginViewModelFactory())
            .get(LoginViewModel::class.java)

        loginViewModel.loginFormState.observe(this@LoginActivity, Observer {
            val loginState = it ?: return@Observer

            // disable login button unless both username / password is valid
            login.isEnabled = loginState.isDataValid

            if (loginState.usernameError != null) {
                username.error = getString(loginState.usernameError)
            }
            if (loginState.passwordError != null) {
                password.error = getString(loginState.passwordError)
            }
        })

        loginViewModel.loginResult.observe(this@LoginActivity, Observer {
            val loginResult = it ?: return@Observer

            if (loginResult.error != null) {
                showLoginFailed(loginResult.error)
            }

            setResult(Activity.RESULT_OK)

            //Complete and destroy login activity once successful
            finish()
        })



        

        username.afterTextChanged {
            loginViewModel.loginDataChanged(
                username.text.toString(),
                password.text.toString()
            )
        }

        password.apply {
            afterTextChanged {
                loginViewModel.loginDataChanged(
                    username.text.toString(),
                    password.text.toString()
                )
            }



            login.setOnClickListener {
                user = username.text.toString()
                if(db.checkLogin(username.text.toString(),password.text.toString())){
                        updateUiWithUser()
                    }
                else{
                    Toast.makeText(
                        applicationContext,
                        "Niepoprawne logowanie",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }



    private fun updateUiWithUser() {
        val welcome = getString(R.string.welcome)




        Toast.makeText(
            applicationContext,
            "$welcome",
            Toast.LENGTH_LONG
        ).show()

        Thread(){
            run{
            }
            runOnUiThread(){
                val  intent = Intent(this, MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
                startActivity(intent)
            }
        }.start()

        Thread(){
            run{
                Thread.sleep(1000)
            }
            runOnUiThread(){
                onPause()
            }
        }

    }

    private fun showLoginFailed(@StringRes errorString: Int) {
        Toast.makeText(applicationContext, errorString, Toast.LENGTH_SHORT).show()
    }


    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()

    }

}

/**
 * Extension function to simplify setting an afterTextChanged action to EditText components.
 */
fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}

