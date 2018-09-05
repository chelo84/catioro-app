package br.com.catioroapp.interfaces;

import com.codename1.io.Preferences;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.layouts.BoxLayout;

import br.com.catioroapp.interfaces.util.MeuForm;
import br.com.catioroapp.service.UsuarioService;

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
			SolicitarServicoForm ssf = new SolicitarServicoForm();
			ssf.getToolbar().addCommandToLeftBar("", FontImage.createMaterial(FontImage.MATERIAL_EXIT_TO_APP, "TitleCommand", 5).rotate180Degrees(true),
														new Command("logout") {
				@Override
				public void actionPerformed(ActionEvent ae) {
					if(Dialog.show("Log out", "Você tem certeza que deseja fazer o logout?", "Sim", "Não")) {
						UsuarioService.logout();
						new LoginForm().showBack();
					}
				}
			});
			
			ssf.show();
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
