package com.bograntex.bograntexAdmin.view;

import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.Responsive;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.Image;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("serial")
public class RegisterView extends VerticalLayout {

    public RegisterView() {
        setSizeFull();
        setMargin(false);
        setSpacing(false);

        Component loginForm = buildLoginForm();
        addComponent(loginForm);
        setComponentAlignment(loginForm, Alignment.MIDDLE_CENTER);
        addStyleName("login-background");
    }

    private Component buildLoginForm() {
        final VerticalLayout loginPanel = new VerticalLayout();
        loginPanel.setSizeUndefined();
        loginPanel.setMargin(false);
        Responsive.makeResponsive(loginPanel);
        loginPanel.addStyleName("login-panel");
        loginPanel.addComponent(buildFields());
        return loginPanel;
    }

    private Component buildFields() {
    	VerticalLayout fields = new VerticalLayout();
        fields.addStyleName("fields");
        
        Image imgEmpresa = new Image(null, new ThemeResource("img/logo-bograntex-grande.jpg"));
		imgEmpresa.setWidth(170.0f, Unit.PIXELS);
        
        final TextField username = new TextField("Usuário Sistema");
        username.setIcon(VaadinIcons.USER);
        username.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);

        final Button signin = new Button("Enviar Senha");
        signin.addStyleName(ValoTheme.BUTTON_FRIENDLY);
        signin.setClickShortcut(KeyCode.ENTER);
        fields.addComponents(imgEmpresa, username, signin);
        fields.setComponentAlignment(imgEmpresa, Alignment.MIDDLE_CENTER);
        
        signin.setClickShortcut(KeyCode.ENTER);
        fields.addComponents(imgEmpresa, username, signin);
        fields.setComponentAlignment(imgEmpresa, Alignment.MIDDLE_CENTER);
        signin.addClickListener(new ClickListener() {
            @Override
            public void buttonClick(final ClickEvent event) {
                
            }
        });
        return fields;
    }
    
}
