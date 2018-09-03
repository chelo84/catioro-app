package br.com.catioroapp.interfaces;

import com.codename1.components.InfiniteProgress;
import com.codename1.ui.Button;
import com.codename1.ui.Dialog;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.validation.RegexConstraint;
import com.codename1.ui.validation.Validator;

import br.com.catioroapp.interfaces.util.MeuForm;
import br.com.catioroapp.service.UsuarioService;
import br.com.catioroapp.usuario.Usuario;
import br.com.catioroapp.validador.ValidadorUsuario;

public class CriarContaForm extends MeuForm {
	public CriarContaForm() {
		super("Criar Conta");
		setFormContent();
	}
	
	private void setFormContent() {
		this.setLayout(BoxLayout.y());
		
		Label erroNomeDeUsuario = new Label("Nome de usuario inválido, tente outro!");
		erroNomeDeUsuario.setUIID("erro");
		erroNomeDeUsuario.setVisible(false);
		this.add(erroNomeDeUsuario);
		
		TextField nomeDeUsuario = new TextField();
		nomeDeUsuario.setUIID("CriarContaTextField");
		nomeDeUsuario.setHint("Nome de usuario");
		nomeDeUsuario.setConstraint(TextField.USERNAME);
		this.add(nomeDeUsuario);
		
		Label erroSenha = new Label("Senha inválida!");
		erroSenha.setUIID("erro");
		erroSenha.setVisible(false);
		this.add(erroSenha);
		
		TextField senha = new TextField();
		senha.setUIID("CriarContaTextField");
		senha.setHint("Senha");
		senha.setConstraint(TextField.PASSWORD);
		this.add(senha);
		
		Label erroConfirmaSenha = new Label("Senha e confirmação não são iguais!");
		erroConfirmaSenha.setUIID("erro");
		erroConfirmaSenha.setVisible(false);
		this.add(erroConfirmaSenha);
		
		TextField confirmaSenha = new TextField();
		confirmaSenha.setUIID("CriarContaTextField");
		confirmaSenha.setHint("Confirmar senha");
		confirmaSenha.setConstraint(TextField.PASSWORD);
		this.add(confirmaSenha);
		
		Label erroNome = new Label("Nome inválido");
		erroNome.setUIID("erro");
		erroNome.setVisible(false);
		this.add(erroNome);
		
    	TextField nome = new TextField();
    	nome.setUIID("CriarContaTextField");
    	nome.setHint("Nome");
    	this.add(nome);
    	
		Label erroSobrenome = new Label("Sobrenome inválido");
		erroSobrenome.setUIID("erro");
		erroSobrenome.setVisible(false);
		this.add(erroSobrenome);
    	
    	TextField sobrenome = new TextField();
    	sobrenome.setUIID("CriarContaTextField");
    	sobrenome.setHint("Sobrenome");
    	this.add(sobrenome);
    	
		Label erroTelefone = new Label("Telefone invalido. Ex: 4812345678");
		erroTelefone.setUIID("erro");
		erroTelefone.setVisible(false);
		this.add(erroTelefone);
    	
    	TextField telefone = new TextField();
    	telefone.setUIID("CriarContaTextField");
    	telefone.setHint("Telefone");
    	telefone.setConstraint(TextField.PHONENUMBER);
    	this.add(telefone);
    	
		Label erroEmail = new Label("E-mail inválido, tente outro!");
		erroEmail.setUIID("erro");
		erroEmail.setVisible(false);
		this.add(erroEmail);
    	
    	TextField email = new TextField();
    	email.setUIID("CriarContaTextField");
    	email.setHint("E-mail");
    	email.setConstraint(TextField.EMAILADDR);
    	this.add(email);
    	
		Label erroCpf = new Label("Cpf inválido!");
		erroCpf.setUIID("erro");
		erroCpf.setVisible(false);
		this.add(erroCpf);
    	
    	TextField cpf = new TextField();
    	cpf.setUIID("CriarContaTextField");
    	cpf.setHint("CPF");
    	cpf.setConstraint(TextField.NUMERIC);
    	this.add(cpf);
    	
    	Button criarContaBtn = new Button("Criar conta");
    	criarContaBtn.addActionListener((ae) -> {
    		final Dialog ip = new InfiniteProgress().showInfiniteBlocking();
    		
    		ValidadorUsuario vu = UsuarioService.usuarioExiste(nomeDeUsuario.getText(), cpf.getText(), email.getText(), telefone.getText());
    		if(vu.existe.get()) {
    			erroNomeDeUsuario.setVisible(vu.existeNomeDeUsuario.get());
    			erroEmail.setVisible(vu.existeEmail.get());
    			erroCpf.setVisible(vu.existeCpf.get());
    			erroTelefone.setVisible(vu.existeTelefone.get());
    			
    			ip.dispose();
    			return;
    		}
    		erroEmail.setVisible(RegexConstraint.validEmail().isValid(email));
    		erroConfirmaSenha.setVisible(!senha.getText().equals(confirmaSenha.getText()));
    		this.revalidate();
    		
    		if(UsuarioService.addNovoUsuario(new Usuario()
    									  .nomeDeUsuario.set(nomeDeUsuario.getText())
    									  .senha.set(senha.getText())
    									  .nome.set(nome.getText())
    									  .sobrenome.set(sobrenome.getText())
    									  .telefone.set(telefone.getText())
    									  .cpf.set(cpf.getText())
    									  .email.set(email.getText()))) {
    			Dialog dlg = new Dialog() {
    				public void dispose() {
    					Form loginForm = new LoginForm();
    					super.dispose();

    					loginForm.show();
    				}
    			};
    	        TextArea popupBody = new TextArea("Conta criada com sucesso, clique fora desta mensagem para voltar para a tela de login", 5, 20);
    	        popupBody.setUIID("PopupBody");
    	        popupBody.setEditable(false);
    	        dlg.setLayout(new BorderLayout());
    	        dlg.add(BorderLayout.CENTER, popupBody);
    	        dlg.setDisposeWhenPointerOutOfBounds(true);
    			ip.dispose();
    			dlg.show();
    		} else {
    			
    		}
    		
    		ip.dispose();
    	});
    	criarContaBtn.setUIID("LoginButton");
    	this.add(criarContaBtn);
	}
}
