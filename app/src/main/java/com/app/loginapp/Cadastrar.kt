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
import com.app.loginapp.databinding.ActivityCadastrarBinding
import com.app.loginapp.databinding.ActivityMainBinding
import com.app.loginapp.models.User

class Cadastrar : AppCompatActivity() {
    private lateinit var binding: ActivityCadastrarBinding
    private val handler = Handler(Looper.getMainLooper())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCadastrarBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar!!.title = "Cadastro"
        // inteção de login
        binding.LoginBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        // ouvido meu btnCadastro com uma verificação se os campos estão em brancos

        binding.btnCadastro.setOnClickListener {
        if (binding.Nome.text.toString().isBlank() || binding.CadastroEmail.text.toString().isBlank() ||
            binding.CadastroSenha.text.toString().isBlank()
            ){
            Toast.makeText(this, "Campo em branco", Toast.LENGTH_SHORT).show()
        }else{
            binding.progressoCadatro.visibility = View.VISIBLE
            handler.postDelayed(object : Runnable{
                override fun run() {
                    cadastro(User(binding.Nome.text.toString(),
                        binding.CadastroEmail.text.toString(),
                        binding.CadastroSenha.text.toString())).apply {
                        binding.progressoCadatro.visibility = View.GONE
                        Toast.makeText(this@Cadastrar, "Usuario cadastrado com sucesso", Toast.LENGTH_SHORT).show()
                    }
                }
            },4000)
        }
        }
    }
    private fun cadastro(user: User){
        val sharedprefereces = this.getSharedPreferences("usuarios", Context.MODE_PRIVATE)

        val editor = sharedprefereces.edit()
        editor.putString("nome", user.nome)
        editor.putString("email", user.email)
        editor.putString("senha", user.senha)

        editor.apply()
    }
}