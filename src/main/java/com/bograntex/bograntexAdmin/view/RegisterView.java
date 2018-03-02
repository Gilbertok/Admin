package com.bograntex.bograntexAdmin.view;

import java.sql.SQLException;

import org.apache.commons.mail.EmailException;

import com.bograntex.bograntexAdmin.data.erp.UserERP;
import com.bograntex.bograntexAdmin.domain.User;
import com.bograntex.bograntexAdmin.event.DashboardEventBus;
import com.bograntex.bograntexAdmin.event.DashboardEvent.UserLoginRequestedEvent;
import com.bograntex.bograntexAdmin.utils.EmailUtils;
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
import com.vaadin.ui.Notification;
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
        
        final Button retornarAoLogin = new Button("Retorna ao início");
        retornarAoLogin.addStyleName(ValoTheme.BUTTON_LINK);
        retornarAoLogin.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				VaadinSession.getCurrent().setAttribute("NAVIGATE_PAGE", "PAGE_LOGIN");
				DashboardEventBus.post(new UserLoginRequestedEvent(username.getValue(), null));
			}
        });
        
        signin.setClickShortcut(KeyCode.ENTER);
        fields.addComponents(imgEmpresa, username, signin, retornarAoLogin);
        fields.setComponentAlignment(imgEmpresa, Alignment.MIDDLE_CENTER);
        signin.addClickListener(new ClickListener() {
            @Override
            public void buttonClick(final ClickEvent event) {
            	if (username.getValue().isEmpty()) {
            		Notification.show("Atenção", "Insira o nome de usuário!", Notification.Type.WARNING_MESSAGE);
            	} else {
            		envioEmailUsuario(username.getValue());
            	}
            }
        });
        return fields;
    }
    
    private void envioEmailUsuario(String usuario) {
    	try {
    		User usuarioNovo = new User();
			usuarioNovo.gerarUsuario(usuario);
			
			EmailUtils email = new EmailUtils(UserERP.getEmailUsuarioERP(usuario), usuario);
			String mensagem = "Esta é sua nova senha: " +usuarioNovo.getPassword()+", Não esqueça de altera-lá!";
			email.Enviar("Nova Senha", mensagem);
			Notification.show("Envio de E-mail", "Foi enviado um e-mail para o endereço configurado neste usuário!", Notification.Type.TRAY_NOTIFICATION);
		} catch (Exception e) {
			Notification.show("Erro", "Ouve um erro ao gerar uma nova senha!", Notification.Type.ERROR_MESSAGE);
			e.printStackTrace();
		}
    }
    
}
