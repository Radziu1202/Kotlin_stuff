package com.example.gralosujca

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.brgame.message.message
import com.example.gralosujca.DB.DBHelper
import com.example.gralosujca.ui.login.LoginActivity
import com.example.gralosujca.ui.login.LoginActivity.Companion.db
import kotlin.random.Random


class MainActivity : AppCompatActivity(){
    var n_of_guesses=0
    var drawn_number=0
    var score=0

    companion object{
        var loggedin=false;
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        game_start()

        val button_guess=findViewById<Button>(R.id.guess_button)
        val actualScore=findViewById<TextView>(R.id.score)
        val your_n_of_guesses=findViewById<TextView>(R.id.n_of_g)
        val input= findViewById<EditText>(R.id.inputText)
        val info=findViewById<TextView>(R.id.hint)
        val new_game_button=findViewById<Button>(R.id.newGameB)
        val rankings_button=findViewById<Button>(R.id.viewListButton)
        val logout_button=findViewById<Button>(R.id.logout)
        loggedin=true

        setRecord(LoginActivity.user,actualScore);

        button_guess.setOnClickListener(){
            if (input.text.length>0){
                val guessed=input.text.toString().toInt()
                guess(guessed,your_n_of_guesses,actualScore,info)
                input.text.clear()
            }else{
                dialogShow("Error", "Insert your number")
            }
        }

        logout_button.setOnClickListener(){
            loggedin=false

            this.finish()
        }



        new_game_button.setOnClickListener(){
            clear_points(actualScore)
            info.text=""
            your_n_of_guesses.text="Number of guesses: "
            game_start()
        }

        rankings_button.setOnClickListener(){
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

    }


    fun game_start(){
        n_of_guesses=0;
        drawn_number= Random.nextInt(0,20)
        Toast.makeText(applicationContext,"New game begins!",Toast.LENGTH_LONG).show()
    }

    private fun guess(guessed: Int,your_n_of_guesses: TextView, actualScore: TextView,hint: TextView){
        if(guessed < 0 || guessed > 20){
            dialogShow("Error", "number must be between 0 and 20")
        }else{
            if(guessed == drawn_number){
                n_of_guesses++
                punktacja()
                actualScore.text=" Your score: $score"
                your_n_of_guesses.text=" Number of guesses:"
                game_start()
                hint.text=""
                val mess=message(LoginActivity.user,score,10)
                db.addMessage(mess)
            }else{
                if(guessed<drawn_number){
                    hint.text= "You guessed $guessed. Go higher"

                }else{
                    hint.text="You guessed $guessed. Go lower"
                }
                n_of_guesses++
            }
            if(n_of_guesses>10){
                you_lost(n_of_guesses,actualScore,hint)
            }
            your_n_of_guesses.text=" Number of guesses: $n_of_guesses"

        }


    }

    fun you_lost(pkt:Int,actualScore: TextView,hint: TextView){

        dialogShow("You lost", "Your score: $score");
        clear_points(actualScore);
        hint.text=""
        game_start();
    }


    fun punktacja(){
        var pkt=5
        when(n_of_guesses){
            1->pkt=5
            2->pkt=3
            3->pkt=3
            4->pkt=3
            5->pkt=2
            6->pkt=2
            7->pkt=1
            8->pkt=1
            9->pkt=1
            10->pkt=1



        }
        this.score =score+pkt;
        dialogShow("You did it!","You gain $pkt points in $n_of_guesses moves")
    }

    fun dialogShow(title:String,msg:String){
        val builder =AlertDialog.Builder(this)
        builder.setTitle(title)
        builder.setMessage(msg)

        builder.setPositiveButton("OK"){
            dialogInterface: DialogInterface, i: Int ->
        }
        val dialog:AlertDialog=builder.create()
        dialog.show()
    }

    fun clear_points( actualScore: TextView){
        score=0
        actualScore.text="Your score: "
    }




    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()

    }


    fun setRecord(nick:String,yourScore: TextView){
        val sharedscore=db.getRecord(nick)
        score=sharedscore
        yourScore.text="Your score: " + score;

    }


}
