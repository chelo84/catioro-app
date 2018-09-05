package br.com.catioroapp.interfaces;

import com.codename1.components.InfiniteProgress;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Form;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextComponent;
import com.codename1.ui.TextField;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.TextModeLayout;
import com.codename1.ui.validation.LengthConstraint;
import com.codename1.ui.validation.RegexConstraint;
import com.codename1.ui.validation.Validator;

import br.com.catioroapp.interfaces.util.MeuForm;
import br.com.catioroapp.service.UsuarioService;
import br.com.catioroapp.usuario.Usuario;
import br.com.catioroapp.validador.ConfirmaSenhaConstraint;
import br.com.catioroapp.validador.ValidadorUsuario;

public class CriarContaForm extends MeuForm {
	public CriarContaForm() {
		super("Criar Conta");
		setFormContent();
	}
	
	private void setFormContent() {
		TextModeLayout tl = new TextModeLayout(10, 2);
		this.setLayout(tl);
		
		TextComponent nomeDeUsuario = new TextComponent().label("Nome de usuário");
		nomeDeUsuario.constraint(TextField.USERNAME);
		this.add(tl.createConstraint().horizontalSpan(2), nomeDeUsuario);
		
		TextComponent senha = new TextComponent().label("Senha");
		senha.constraint(TextField.PASSWORD);
		this.add(tl.createConstraint().horizontalSpan(2), senha);
		
		TextComponent confirmaSenha = new TextComponent().label("Confirma senha");
		confirmaSenha.constraint(TextField.PASSWORD);
		this.add(tl.createConstraint().horizontalSpan(2), confirmaSenha);
		
    	TextComponent nome = new TextComponent().label("Nome");
    	this.add(tl.createConstraint().horizontalSpan(2), nome);
    	
    	TextComponent sobrenome = new TextComponent().label("Sobrenome");
    	this.add(tl.createConstraint().horizontalSpan(2), sobrenome);
    	
    	TextComponent ddd = new TextComponent().label("DDD");
    	ddd.constraint(TextField.PHONENUMBER);
    	this.add(tl.createConstraint().widthPercentage(25), ddd);
    	
    	TextComponent telefone = new TextComponent().label("Telefone");
    	telefone.constraint(TextField.PHONENUMBER);
    	this.add(tl.createConstraint().widthPercentage(75), telefone);
    	
    	TextComponent email = new TextComponent().label("E-mail");
    	email.constraint(TextField.EMAILADDR);
    	this.add(tl.createConstraint().horizontalSpan(2), email);
    	
    	TextComponent cpf = new TextComponent().label("CPF");
    	cpf.constraint(TextField.NUMERIC);
    	this.add(tl.createConstraint().horizontalSpan(2), cpf);
    	
    	Button criarContaBtn = new Button("Criar conta");
    	criarContaBtn.addActionListener((ae) -> {
    		final Dialog ip = new InfiniteProgress().showInfiniteBlocking();
    		
    		ValidadorUsuario vu = UsuarioService.usuarioExiste(nomeDeUsuario.getText(), cpf.getText(), email.getText(), telefone.getText());
    		if(vu.existe.get()) {
    			ip.dispose();
    			Dialog.show("Informações inválidas", ((vu.existeNomeDeUsuario.get())? "Nome de usuário já utilizado, tente outro!\n" : "") + 
    												((vu.existeEmail.get())? "E-mail já utilizado, tente outro!\n" : "") +
    												((vu.existeCpf.get())? "CPF já utilizado!\n" : "") +
    												((vu.existeTelefone.get())? "Telefone já utilizado, tente outro!" : ""), "Ok", "Cancel");
    			return;
    		}
    		this.revalidate();
    		
    		if(UsuarioService.addNovoUsuario(new Usuario()
    									  .nomeDeUsuario.set(nomeDeUsuario.getText())
    									  .senha.set(senha.getText())
    									  .nome.set(sobrenome.getText())
    									  .sobrenome.set(sobrenome.getText())
    									  .telefone.set(ddd.getText() +""+ telefone.getText())
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
    	this.add(tl.createConstraint().horizontalSpan(2), criarContaBtn);
    	
    	Validator v = new Validator();
    	v.addConstraint(nomeDeUsuario, new LengthConstraint(5, "Nome de usuário inválido, minimo 5 caracteres!"))
    		.addConstraint(senha, new LengthConstraint(5, "Senha inválida, minimo 5 caracteres!"))
    		.addConstraint(confirmaSenha, new ConfirmaSenhaConstraint(senha, "Senha inválida!"))
    		.addConstraint(nome, new LengthConstraint(3, "Nome inválido, minimo 3 caracteres!"))
    		.addConstraint(sobrenome, new LengthConstraint(5, "Senha inválida, minimo 5 caracteres!"))
    		.addConstraint(telefone, new RegexConstraint("(\\d){8,12}", "Telefone inválido, Ex: 4812345678"))
    		.addConstraint(email, RegexConstraint.validEmail("E-mail inválido"))
    		.addConstraint(cpf, new RegexConstraint("(\\d){11}", "CPF inválido, precisa ter 11 digitos"));
    	
    	Validator.setValidateOnEveryKey(true);
    	System.err.println(Validator.isValidateOnEveryKey());
    	System.err.println(ddd.getText() +""+ telefone.getText());
    	
    	v.setShowErrorMessageForFocusedComponent(true);
    	v.addSubmitButtons(criarContaBtn);
	}
}
