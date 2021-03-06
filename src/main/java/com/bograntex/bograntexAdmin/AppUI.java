package com.bograntex.bograntexAdmin;

import java.util.Locale;

import com.bograntex.bograntexAdmin.data.DataProvider;
import com.bograntex.bograntexAdmin.data.UserData;
import com.bograntex.bograntexAdmin.domain.User;
import com.bograntex.bograntexAdmin.dummy.DummyDataProvider;
import com.bograntex.bograntexAdmin.event.DashboardEvent.BrowserResizeEvent;
import com.bograntex.bograntexAdmin.event.DashboardEvent.CloseOpenWindowsEvent;
import com.bograntex.bograntexAdmin.event.DashboardEvent.UserLoggedOutEvent;
import com.bograntex.bograntexAdmin.event.DashboardEvent.UserLoginRequestedEvent;
import com.bograntex.bograntexAdmin.event.DashboardEventBus;
import com.bograntex.bograntexAdmin.view.LoginView;
import com.bograntex.bograntexAdmin.view.MainView;
import com.bograntex.bograntexAdmin.view.RegisterView;
import com.google.common.eventbus.Subscribe;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.server.Page;
import com.vaadin.server.Page.BrowserWindowResizeEvent;
import com.vaadin.server.Page.BrowserWindowResizeListener;
import com.vaadin.server.Responsive;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

@Theme("dashboard")
@Title("Bograntex Admin")
@SuppressWarnings("serial")
public class AppUI extends UI {
	
	private final DataProvider dataProvider = new DummyDataProvider();
    private final DashboardEventBus dashboardEventbus = new DashboardEventBus();

    @Override
    protected void init(final VaadinRequest request) {
        setLocale(Locale.US);

        DashboardEventBus.register(this);
        Responsive.makeResponsive(this);
        addStyleName(ValoTheme.UI_WITH_MENU);
        updateContent();

        // Some views need to be aware of browser resize events so a
        // BrowserResizeEvent gets fired to the event bus on every occasion.
        Page.getCurrent().addBrowserWindowResizeListener(new BrowserWindowResizeListener() {
            @Override
            public void browserWindowResized(final BrowserWindowResizeEvent event) {
                DashboardEventBus.post(new BrowserResizeEvent());
            }
        });
    }

    /**
     * Updates the correct content for this UI based on the current user status.
     * If the user is logged in with appropriate privileges, main view is shown.
     * Otherwise login view is shown.
     */
    private void updateContent() {
        User user = (User) VaadinSession.getCurrent().getAttribute(User.class.getName());
        if(user == null) {
        	String page = (String) VaadinSession.getCurrent().getAttribute("NAVIGATE_PAGE");
        	if(page != null && page.equals("PAGE_PRIMEIRO_ACESSO")) {
        		setContent(new RegisterView());
                addStyleName("loginview");
                VaadinSession.getCurrent().setAttribute("NAVIGATE_PAGE", "");
        	} else if (page != null && page.equals("PAGE_LOGIN")) {
        		setContent(new LoginView());
                addStyleName("loginview");
                VaadinSession.getCurrent().setAttribute("NAVIGATE_PAGE", "");
        	} else {
        		setContent(new LoginView());
        		addStyleName("loginview");
        	}
        } else {
        	if ("admin".equals(user.getRole())) {
                // Authenticated user
                setContent(new MainView());
                removeStyleName("loginview");
                getNavigator().navigateTo(getNavigator().getState());
            }
        }
    }

    @Subscribe
    public void userLoginRequested(final UserLoginRequestedEvent event) throws Exception {
    	String page = (String) VaadinSession.getCurrent().getAttribute("NAVIGATE_PAGE");
    	if(page==null) {
    		UserData.authenticate(event.getUserName(), event.getPassword());
    		User user = (User) VaadinSession.getCurrent().getAttribute("USER_LOGIN");
    		if (user == null) {
    			Notification.show("Erro", "Usuário ou senha não conferem!", Notification.Type.ERROR_MESSAGE);
    		} else {
    			VaadinSession.getCurrent().setAttribute(User.class.getName(), user);
    			updateContent();
    		}
    	} else {
    		updateContent();
    	}
    }

    @Subscribe
    public void userLoggedOut(final UserLoggedOutEvent event) {
        // When the user logs out, current VaadinSession gets closed and the
        // page gets reloaded on the login screen. Do notice the this doesn't
        // invalidate the current HttpSession.
        VaadinSession.getCurrent().close();
        Page.getCurrent().reload();
    }

    @Subscribe
    public void closeOpenWindows(final CloseOpenWindowsEvent event) {
        for (Window window : getWindows()) {
            window.close();
        }
    }

    /**
     * @return An instance for accessing the (dummy) services layer.
     */
    public static DataProvider getDataProvider() {
        return ((AppUI) getCurrent()).dataProvider;
    }

    public static DashboardEventBus getDashboardEventbus() {
        return ((AppUI) getCurrent()).dashboardEventbus;
    }
    
}
