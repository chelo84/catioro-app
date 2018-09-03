package br.com.catioroapp.interfaces;

import com.codename1.components.InfiniteProgress;
import com.codename1.io.Preferences;
import com.codename1.properties.PreferencesObject;
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
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.layouts.BoxLayout;

import br.com.catioroapp.interfaces.util.MeuForm;
import br.com.catioroapp.service.UsuarioService;
import br.com.catioroapp.usuario.Usuario;

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
    	
    	this.setLayout(BoxLayout.y());
    	this.add(logoLb);
    	
    	Container loginCtnr = new Container(BoxLayout.y());
    	Label erro = new Label("Login");
    	erro.setUIID("erro");
    	erro.setVisible(false);
    	loginCtnr.add(erro);
    	
    	Preferences.setPreferencesLocation("CN1Preferences");
    	TextField usuarioField = new TextField();
    	usuarioField.setConstraint(TextField.USERNAME);
    	usuarioField.setText(Preferences.get("lembrarUsuario", null));
    	usuarioField.getAllStyles().setMargin(0, 0, 5, 5);
    	usuarioField.setHint("Usuário");
    	loginCtnr.add(usuarioField);
    	
    	TextField senhaField = new TextField();
    	senhaField.setConstraint(TextField.PASSWORD);
    	senhaField.getAllStyles().setMargin(2, 0, 5, 5);
    	senhaField.setHint("Senha");
    	loginCtnr.add(senhaField);
    	
    	CheckBox lembrarCB = new CheckBox("Lembrar de mim");
    	lembrarCB.setSelected(Preferences.get("lembrar", false));
    	lembrarCB.setOppositeSide(false);
    	lembrarCB.setUIID("lembrarCB");
    	loginCtnr.add(lembrarCB);
    	
    	this.add(loginCtnr);
    	Preferences.setPreferencesLocation("loginPreferences");
    	Button loginBtn = new Button("Log in");
    	loginBtn.addActionListener((ae) -> {
    		senhaField.stopEditing();
    		final Dialog ip = new InfiniteProgress().showInfiniteBlocking();
    		
    		UsuarioService.loginComNomeDeUsuario(usuarioField.getText(), senhaField.getText(), (value) -> {
    			
    			String token = Preferences.get("token", null);
    			
    			if(token != null) {
    				Form afterLoginForm = new PosLoginForm();
    				afterLoginForm.getToolbar().setBackCommand(new Command("Voltar") {
    					@Override
    					public void actionPerformed(ActionEvent evt) {
    						Preferences.setPreferencesLocation("loginPreferences");
    						Preferences.clearAll();
    						LoginForm.this.showBack();
    					}
    				});
    				ip.dispose();
    				afterLoginForm.show();
    			} else {
    				ip.dispose();
    				erro.setText("Credenciais invalidas");
    				erro.setVisible(true);
    			}
    			
    		}, (sender, err, errorCode, errorMessage) -> {
    			ip.dispose();
    			erro.setText("Credenciais invalidas");
    			erro.setVisible(true);
    			revalidate();
    		});
    		
    		Preferences.setPreferencesLocation("CN1Preferences");
    		if(lembrarCB.isSelected()) {
    			Preferences.set("lembrarUsuario", usuarioField.getText());
    			Preferences.set("lembrar", true);
    		}
    	});
    	loginBtn.setUIID("LoginButton");
    	loginCtnr.add(loginBtn);
    	
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
