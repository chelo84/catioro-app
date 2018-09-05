package br.com.catioroapp.validador;

import com.codename1.ui.TextComponent;
import com.codename1.ui.validation.Constraint;

public class ConfirmaSenhaConstraint implements Constraint {
	private TextComponent senha;
	private String errorMessage = "";
	
	public ConfirmaSenhaConstraint(TextComponent senha) {
		this.senha = senha;
	}
	
	public ConfirmaSenhaConstraint(TextComponent senha, String errorMessage) {
		this.senha = senha;
		this.errorMessage = errorMessage;
	}
	
	@Override
	public boolean isValid(Object value) {
		String confirmaSenha = (String) value;
		
		return senha.getText().equals(confirmaSenha);
	}

	@Override
	public String getDefaultFailMessage() {
		return errorMessage;
	}
}
