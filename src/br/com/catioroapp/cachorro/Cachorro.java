package br.com.catioroapp.cachorro;

import com.codename1.properties.LongProperty;
import com.codename1.properties.Property;
import com.codename1.properties.PropertyBusinessObject;
import com.codename1.properties.PropertyIndex;

import br.com.catioroapp.usuario.Usuario;

public class Cachorro implements PropertyBusinessObject {
	public final LongProperty<Cachorro> id = new LongProperty<>("id");
	public final Property<String, Cachorro> nome = new Property<>("nome");
	public final Property<byte[], Cachorro> imagem = new Property<>("imagem");
	public final Property<Temperamento, Cachorro> temperamento = new Property<>("temperamento");
	public final Property<Porte, Cachorro> porte = new Property<>("porte");
	public final Property<Usuario, Cachorro> dono = new Property<>("dono");
	private final PropertyIndex idx = new PropertyIndex(this, "Cachorro", id, nome, imagem, temperamento, porte, dono);
	
	@Override
	public PropertyIndex getPropertyIndex() {
		return idx;
	}
}
