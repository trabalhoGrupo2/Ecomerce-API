package org.serratec.h2.grupo2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

	@Autowired
    private JavaMailSender mailSender;
	
	public void emailPraAtivacaoDaConta(String email, String nomeUsuario, String token) {
	    String link = "http://localhost:8080/cliente/ativarConta/" + email + "/" + token;

	    String corpo = String.format(
	        """
	        Olá %s, tudo bem?

	        Seja muito bem-vindo(a) ao nosso sistema! 🎉

	        Para concluir seu cadastro e ativar sua conta, por favor clique no link abaixo:

	        ➤ %s

	        Caso não consiga clicar diretamente no link, você também pode copiar e colar o endereço no seu navegador.

	        Se preferir ativar manualmente, acesse:
	        ➤ http://localhost:8080/cliente/ativarConta/seuemail/seuToken
	        e utilize este código de ativação: %s

	        Se você não realizou esse cadastro, pode ignorar este e-mail com segurança.

	        Atenciosamente,
	        Equipe de Suporte
	        """, nomeUsuario, link, token
	    );

	    SimpleMailMessage mensagem = new SimpleMailMessage();
	    mensagem.setTo(email);
	    mensagem.setSubject("Ativação da Conta - Confirme seu cadastro");
	    mensagem.setText(corpo);
	    mensagem.setFrom("seuemail@gmail.com");

	    mailSender.send(mensagem);
	}
	
	public void emailContaCriadaComSucesso(String email, String nomeUsuario) {
	    String corpo = String.format(
	        """
	        Olá %s! 💖

	        Sua conta foi criada com sucesso e já está prontinha para ser usada! ✨

	        Estamos muito felizes em ter você com a gente. 🎉🎉🎉

	        Agora é só aproveitar todos os recursos disponíveis na plataforma. Se tiver qualquer dúvida, estaremos por aqui para ajudar. 😊

	        Seja bem-vindo(a) de coração! 💫

	        Com carinho,  
	        Equipe de Suporte 🤗
	        """, nomeUsuario
	    );

	    SimpleMailMessage mensagem = new SimpleMailMessage();
	    mensagem.setTo(email);
	    mensagem.setSubject("Conta criada com sucesso! 🥳");
	    mensagem.setText(corpo);
	    mensagem.setFrom("seuemail@gmail.com");

	    mailSender.send(mensagem);
	}
}
