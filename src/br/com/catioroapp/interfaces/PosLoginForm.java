package br.com.catioroapp.interfaces;

import com.codename1.ui.Button;
import com.codename1.ui.layouts.BoxLayout;

import br.com.catioroapp.interfaces.util.MeuForm;

public class PosLoginForm extends MeuForm {
	public PosLoginForm() {
		super();
		setFormContent();
	}

	private void setFormContent() {
		this.setLayout(BoxLayout.y());
		
		Button solicitarServico = new Button("Solicitar Serviço");
		solicitarServico.setUIID("posLoginBtn");
		solicitarServico.addActionListener((ae) -> {
			
		});
		this.add(solicitarServico);
		
		Button passeador = new Button("Passeador");
		passeador.setUIID("posLoginBtn");
		passeador.addActionListener((ae) -> {
			
		});
		this.add(passeador);
		
		Button addAmiguinho = new Button("Adicionar novo amiguinho");
		addAmiguinho.setUIID("posLoginBtn");
		addAmiguinho.addActionListener((ae) -> {
			
		});
		this.add(addAmiguinho);
	}
}
