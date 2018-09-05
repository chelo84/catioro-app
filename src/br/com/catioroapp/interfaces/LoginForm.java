package br.com.catioroapp.interfaces;

import com.codename1.components.InfiniteProgress;
import com.codename1.io.Preferences;
import com.codename1.ui.Button;
import com.codename1.ui.CheckBox;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Font;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextComponent;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.TextModeLayout;
import com.codename1.ui.validation.LengthConstraint;
import com.codename1.ui.validation.Validator;

import br.com.catioroapp.globals.Globals;
import br.com.catioroapp.interfaces.util.MeuForm;
import br.com.catioroapp.service.UsuarioService;

public class LoginForm extends MeuForm {
	
	public LoginForm() {
		super("CatioroApp");
		setFormContent();
	}
	
	private void setFormContent() {
        Font fnt = Font.createTrueTypeFont("fontello", "fontello.ttf");
    	int size = Display.getInstance().convertToPixels(16);
    	FontImage fm;
    	fm = FontImage.createFixed("\uE801", fnt, 0x000000, size, size);
    	Label logoLb = new Label(fm);
    	logoLb.getAllStyles().setAlignment(Component.CENTER);
    	
    	TextModeLayout tl = new TextModeLayout(4,1);
    	this.setLayout(BoxLayout.y());
    	
    	this.add(logoLb);
    	
    	Container loginCtnr = new Container(tl);
    	
    	Preferences.setPreferencesLocation(Globals.CN1_PREFERENCES);
    	TextComponent usuarioField = new TextComponent().label("Nome de usuário").text(Preferences.get("lembrarUsuario", null))
    													.constraint(TextField.USERNAME);
    	loginCtnr.add(usuarioField);
    	
    	Label erro = new Label("Credenciais inválidas");
    	erro.setVisible(false);
    	erro.setUIID("ErroLabel");
    	this.add(erro);
    	
    	TextComponent senhaField = new TextComponent().label("Senha").constraint(TextField.PASSWORD);
    	loginCtnr.add(senhaField);
    	
    	CheckBox lembrarCB = new CheckBox("Lembrar de mim");
    	lembrarCB.setSelected(Preferences.get("lembrar", false));
    	lembrarCB.setOppositeSide(false);
    	lembrarCB.setUIID("LembrarCB");
    	loginCtnr.add(lembrarCB);
    	
    	this.add(loginCtnr);
    	Preferences.setPreferencesLocation(Globals.LOGIN_PREFERENCES);
    	Button loginBtn = new Button("Log in");
    	loginBtn.addActionListener((ae) -> {
    		senhaField.getField().stopEditing();
    		final Dialog ip = new InfiniteProgress().showInfiniteBlocking();
    		
    		UsuarioService.loginComNomeDeUsuario(usuarioField.getText(), senhaField.getText(), (value) -> {
    			
    			String token = Preferences.get("token", null);
    			
    			if(token != null) {
    				Form afterLoginForm = new PosLoginForm();
    				afterLoginForm.getToolbar().setBackCommand(new Command("Voltar") {
    					@Override
    					public void actionPerformed(ActionEvent evt) {
    						Preferences.setPreferencesLocation(Globals.LOGIN_PREFERENCES);
    						Preferences.clearAll();
    						LoginForm.this.showBack();
    					}
    				});
    				ip.dispose();
    				afterLoginForm.show();
    			} else {
    				ip.dispose();
    				erro.setVisible(true);
    				revalidate();
    			}
    			
    		}, (sender, err, errorCode, errorMessage) -> {
    			ip.dispose();
    			Dialog.show("Erro", "Algo deu errado", "Ok", "Cancel");
    			revalidate();
    		});
    		
    		Preferences.setPreferencesLocation(Globals.CN1_PREFERENCES);
    		if(lembrarCB.isSelected()) {
    			Preferences.set("lembrarUsuario", usuarioField.getText());
    			Preferences.set("lembrar", true);
    		}
    	});
    	loginBtn.setUIID("LoginButton");
    	loginCtnr.add(loginBtn);
    	
    	Validator v = new Validator();
    	v.addConstraint(usuarioField, new LengthConstraint(5, "Nome de usuário inválido"))
    		.addConstraint(senhaField, new LengthConstraint(5, "Senha inválida"));
    	Validator.setValidateOnEveryKey(false);
    	
    	Button criarContaBtn = new Button("Criar conta");
    	criarContaBtn.addActionListener((ae) -> {
    		Form criarContaForm = new CriarContaForm();
    		criarContaForm.getToolbar().setBackCommand(new Command("Voltar") {
    			@Override
    			public void actionPerformed(ActionEvent evt) {
    				new LoginForm().showBack();
    			}
    		});
    		criarContaForm.show();
    	});
    	criarContaBtn.setUIID("LoginButton");
    	loginCtnr.add(criarContaBtn);
	}
}
