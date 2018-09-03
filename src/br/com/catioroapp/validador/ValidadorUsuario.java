package br.com.catioroapp.validador;

import com.codename1.properties.BooleanProperty;
import com.codename1.properties.PropertyBusinessObject;
import com.codename1.properties.PropertyIndex;

public class ValidadorUsuario implements PropertyBusinessObject {
	public final BooleanProperty<ValidadorUsuario> existe = new BooleanProperty<>("existe");
	public final BooleanProperty<ValidadorUsuario> existeNomeDeUsuario = new BooleanProperty<>("existeNomeDeUsuario");
	public final BooleanProperty<ValidadorUsuario> existeCpf = new BooleanProperty<>("existeCpf");
	public final BooleanProperty<ValidadorUsuario> existeEmail = new BooleanProperty<>("existeEmail");
	public final BooleanProperty<ValidadorUsuario> existeTelefone = new BooleanProperty<>("existeTelefone");
	private final PropertyIndex idx = new PropertyIndex(this, "Usuario", existe, existeNomeDeUsuario, existeCpf, existeEmail, existeTelefone);
	
	@Override
	public PropertyIndex getPropertyIndex() {
		return this.idx;
	}
}
