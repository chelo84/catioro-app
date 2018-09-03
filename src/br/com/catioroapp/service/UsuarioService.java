package br.com.catioroapp.service;

import static br.com.catioroapp.globals.Globals.SERVER_URL;

import java.util.Map;

import com.codename1.io.Preferences;
import com.codename1.io.rest.Response;
import com.codename1.io.rest.Rest;
import com.codename1.properties.PreferencesObject;
import com.codename1.util.Callback;
import com.codename1.util.FailureCallback;
import com.codename1.util.SuccessCallback;

import br.com.catioroapp.usuario.Usuario;
import br.com.catioroapp.validador.ValidadorUsuario;

public class UsuarioService {
	private static Usuario eu;
	
	public static void loadUsuario() {
		Preferences.setPreferencesLocation("loginPreferences");
		eu = new Usuario();
		
		PreferencesObject.create(eu).bind();
	}
	
	public static void logout() {
		Preferences.setPreferencesLocation("loginPreferences");
		Preferences.clearAll();
	}
	
	public static boolean isLoggedIn() {
		Preferences.setPreferencesLocation("loginPreferences");
		return Preferences.get("token", null) != null;
	}
	
	public static ValidadorUsuario usuarioExiste(String nomeDeUsuario, String cpf, String email, String telefone) {
		Preferences.setPreferencesLocation("loginPreferences");
		Response<Map> response = Rest.get(SERVER_URL +"usuario/existe")
										.acceptJson()
										.queryParam("nomeDeUsuario", nomeDeUsuario)
										.queryParam("cpf", cpf)
										.queryParam("email", email)
										.queryParam("telefone", telefone)
										.getAsJsonMap();
		
		ValidadorUsuario vu = new ValidadorUsuario();
		vu.getPropertyIndex().populateFromMap(response.getResponseData());
		
		return vu;
	}
	
	public static boolean addNovoUsuario(Usuario usuario) {
		Preferences.setPreferencesLocation("loginPreferences");
		Response<String> token = Rest.post(SERVER_URL +"usuario/add")
									 .jsonContent()
									 .body(usuario.getPropertyIndex().toJSON())
									 .getAsString();
		if(token.getResponseCode() != 200) return false;
		
		Preferences.set("token", token.getResponseData());
		return true;
	}
	
	@SuppressWarnings("deprecation")
	public static void loginComNomeDeUsuario(String nomeDeUsuario, String senha,
											  final SuccessCallback<Usuario> onSuccess,
											  final FailureCallback<Object> onError) {
		Rest.get(SERVER_URL +"usuario/login")
			.acceptJson()
			.queryParam("senha", senha)
			.queryParam("nomeDeUsuario", nomeDeUsuario)
			.getAsJsonMapAsync(new Callback<Response<Map>>() {
				public void onSucess(Response<Map> value) {
					Preferences.setPreferencesLocation("loginPreferences");
					eu = new Usuario();
					
					eu.getPropertyIndex().populateFromMap(value.getResponseData());
					Preferences.set("token", eu.authToken.get());
					
					PreferencesObject.create(eu).bind();
					onSuccess.onSucess(eu);
				}
				
				public void onError(Object sender, Throwable err, int errorCode, String errorMessage) {
					onError.onError(null, err, errorCode, errorMessage);
				}
			});
	}
}
