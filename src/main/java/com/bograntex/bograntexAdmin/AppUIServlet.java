package com.bograntex.bograntexAdmin;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinServlet;

@WebServlet(urlPatterns = "/*", name = "AppUIServlet", asyncSupported = true)
@VaadinServletConfiguration(ui = AppUI.class, productionMode = false)
public class AppUIServlet extends VaadinServlet {
	
	private static final long serialVersionUID = -740577975915036545L;

	@Override
    protected final void servletInitialized() throws ServletException {
        super.servletInitialized();
        getService().addSessionInitListener(new AppSessionInitListener());
    }

}
