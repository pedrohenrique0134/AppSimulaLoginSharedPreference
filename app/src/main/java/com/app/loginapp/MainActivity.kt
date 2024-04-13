package com.app.loginapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.app.loginapp.databinding.ActivityMainBinding
import com.app.loginapp.models.User

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /** meu binding esta sendo instaciado **/
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar!!.title = "Login"
        /** Essa funcão de clique, onde me leva para a tela de cadastro **/

        binding.Cadastro.setOnClickListener {

            /** essa tem minha variavel intencao me leva de onde eu estou, para outra class **/
            val intent = Intent(this, Cadastrar::class.java)

            // ativa minha intent
            startActivity(intent)
        }

        // meu btnLogin esta sendo ouvido com uma verificaçção se esta em campos em brancos

        binding.BtnLogin.setOnClickListener {

            if (binding.Email.text.toString().isBlank()|| binding.Senha.text.toString().isBlank()){

                Toast.makeText(this, "Campos em brancos", Toast.LENGTH_SHORT).show()
            }else{
          //meu progresso esta em execução quando os campos estao prenchidos
                binding.progressoLogin.visibility = View.VISIBLE

                /**
                 * meu handler esta dando um time de 5 segundos, enquanto eu simulo uma verificação o
                 * ser o usuario exist.
                * */
                handler.postDelayed(object : Runnable{
                    override fun run() {
                        login(User(email = binding.Email.text.toString(),
                            senha = binding.Senha.text.toString())).apply {
                            binding.progressoLogin.visibility = View.GONE
                        }
                    }

                },4000)
            }
        }
    }

    /**
     *  logo meu sharedprefereces pra acessar no meu banco local meu cadastro e verficar a
     *  autencidade meu email e senha
     *
     * uma função de login que advem, com uma verificação, se estão com email e senha validos,
     **/

    private fun login(user: User) {
        /**
        dizendo ao meu variavel sharedPreferecens esta sendo instaciada com minha key usuarios,
        no mode privado.
         **/
        val sharedprefereces = this.getSharedPreferences("usuarios", Context.MODE_PRIVATE)

        // setando dentro do meu sharePrefereces meu email e senha pra depois verificar mais em baixo.

        val email = sharedprefereces.getString("email", "")
        val senha = sharedprefereces.getString("senha", "")

        // verificação de autencidade

        if (email.toString() == user.email && senha.toString() == user.senha){
            Toast.makeText(this, "sucesso ao fazer login", Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(this, "verifique o email ou senha invalido", Toast.LENGTH_SHORT).show()
        }
    }
}