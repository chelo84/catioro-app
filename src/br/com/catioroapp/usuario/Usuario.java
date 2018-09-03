package br.com.catioroapp.usuario;

import java.util.List;

import com.codename1.properties.BooleanProperty;
import com.codename1.properties.DoubleProperty;
import com.codename1.properties.FloatProperty;
import com.codename1.properties.LongProperty;
import com.codename1.properties.Property;
import com.codename1.properties.PropertyBusinessObject;
import com.codename1.properties.PropertyIndex;

import br.com.catioroapp.cachorro.Cachorro;

public class Usuario implements PropertyBusinessObject {
	public final LongProperty<Usuario> id = new LongProperty<>("id");
	public final Property<String, Usuario> nomeDeUsuario = new Property<>("nomeDeUsuario");
	public final Property<String, Usuario> senha = new Property<>("senha");
	public final Property<String, Usuario> nome = new Property<>("nome");
	public final Property<String, Usuario> sobrenome = new Property<>("sobrenome");
	public final Property<byte[], Usuario> avatar = new Property<>("avatar");
	public final Property<String, Usuario> telefone = new Property<>("telefone");
	public final Property<String, Usuario> email = new Property<>("email");
	public final Property<String, Usuario> cpf = new Property<>("cpf");
	public final Property<String, Usuario> authToken = new Property<>("authToken");
	public final DoubleProperty<Usuario> latitude = new DoubleProperty<>("latitude");
	public final DoubleProperty<Usuario> longitude = new DoubleProperty<>("longitude");
	public final FloatProperty<Usuario> direcao = new FloatProperty<>("direcao");
	public final BooleanProperty<Usuario> passeador = new BooleanProperty<>("passeador");
	public final Property<List<Cachorro>, Usuario> cachorros = new Property<>("cachorros");
	public final BooleanProperty<Usuario> procurando = new BooleanProperty<>("procurando");
	public final LongProperty<Usuario> usuarioAtribuido = new LongProperty<>("usuarioAtribuido");
	public final DoubleProperty<Usuario> ratingAtual = new DoubleProperty<>("ratingAtual");
	public final Property<byte[], Usuario> documento= new Property<>("documento");
	public final Property<byte[], Usuario> comprovanteResidencia = new Property<>("comprovanteResidencia");

	private final PropertyIndex idx = new PropertyIndex(this, "Usuario", id, nomeDeUsuario, senha,
														nome, sobrenome, avatar, telefone, email, cpf, authToken, latitude, longitude, direcao,
														passeador, cachorros, procurando, usuarioAtribuido, ratingAtual, documento, comprovanteResidencia);
	
	@Override
	public PropertyIndex getPropertyIndex() {
		return idx;
	}
}
