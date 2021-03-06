package com.bograntex.bograntexAdmin.view;

import com.bograntex.bograntexAdmin.event.DashboardEvent.UserLoginRequestedEvent;
import com.bograntex.bograntexAdmin.domain.User;
import com.bograntex.bograntexAdmin.event.DashboardEventBus;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.Responsive;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.Image;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("serial")
public class LoginView extends VerticalLayout {

    public LoginView() {
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
//        loginPanel.addComponent(new CheckBox("Remember me", true));
        return loginPanel;
    }

    private Component buildFields() {
    	VerticalLayout fields = new VerticalLayout();
        fields.addStyleName("fields");
        
        Image imgEmpresa = new Image(null, new ThemeResource("img/logo-bograntex-grande.jpg"));
		imgEmpresa.setWidth(170.0f, Unit.PIXELS);
        
        final TextField username = new TextField("Login");
        username.setIcon(VaadinIcons.USER);
        username.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);

        final PasswordField password = new PasswordField("Senha");
        password.setIcon(VaadinIcons.LOCK);
        password.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);

        final Button signin = new Button("Entrar");
        signin.addStyleName(ValoTheme.BUTTON_DANGER);
        signin.setClickShortcut(KeyCode.ENTER);
        fields.addComponents(imgEmpresa, username, password, signin);
        fields.setComponentAlignment(imgEmpresa, Alignment.MIDDLE_CENTER);
        
        final Button primeiroAcesso = new Button("Primeiro Acesso");
        primeiroAcesso.addStyleName(ValoTheme.BUTTON_LINK);
        primeiroAcesso.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				VaadinSession.getCurrent().setAttribute("NAVIGATE_PAGE", "PAGE_PRIMEIRO_ACESSO");
				DashboardEventBus.post(new UserLoginRequestedEvent(username.getValue(), password.getValue()));
			}
        });
        
        signin.setClickShortcut(KeyCode.ENTER);
        fields.addComponents(imgEmpresa, username, password, signin, primeiroAcesso);
        fields.setComponentAlignment(imgEmpresa, Alignment.MIDDLE_CENTER);
        signin.addClickListener(new ClickListener() {
            @Override
            public void buttonClick(final ClickEvent event) {
                DashboardEventBus.post(new UserLoginRequestedEvent(username.getValue(), password.getValue()));
            }
        });
        return fields;
    }
    
}
