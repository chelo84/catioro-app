package br.com.catioroapp.interfaces.util;

import com.codename1.ui.Form;
import com.codename1.ui.layouts.Layout;

public abstract class MeuForm extends Form {
	
	public MeuForm() {
		this.setTitle("CatioroApp");
		this.getToolbar().setTitleCentered(true);
		this.getToolbar().setUIID("Title");
	}
	
	public MeuForm(String title) {
		this();
		this.setTitle(title);
	}
	

	public MeuForm(Layout layout) {
		this();
		this.setLayout(layout);
	}
	
	public MeuForm(String title, Layout layout) {
		this();
		this.setTitle(title);
		this.setLayout(layout);
	}
}
