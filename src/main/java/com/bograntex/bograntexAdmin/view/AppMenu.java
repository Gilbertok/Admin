package com.bograntex.bograntexAdmin.view;

import com.bograntex.bograntexAdmin.component.ProfilePreferencesWindow;
import com.bograntex.bograntexAdmin.domain.User;
import com.bograntex.bograntexAdmin.event.DashboardEvent.PostViewChangeEvent;
import com.bograntex.bograntexAdmin.event.DashboardEvent.ProfileUpdatedEvent;
import com.bograntex.bograntexAdmin.event.DashboardEvent.ReportsCountUpdatedEvent;
import com.bograntex.bograntexAdmin.event.DashboardEvent.UserLoggedOutEvent;
import com.bograntex.bograntexAdmin.event.DashboardEventBus;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.UI;
import com.vaadin.ui.themes.ValoTheme;

public final class AppMenu extends CustomComponent {

	private static final long serialVersionUID = -3344185421353509438L;
	
	public static final String ID = "dashboard-menu";
	public static final String REPORTS_BADGE_ID = "dashboard-menu-reports-badge";
	public static final String NOTIFICATIONS_BADGE_ID = "dashboard-menu-notifications-badge";
	private static final String STYLE_VISIBLE = "valo-menu-visible";
	private Label reportsBadge;
	private MenuItem settingsItem;

	public AppMenu() {
		setPrimaryStyleName("valo-menu");
		setId(ID);
		setSizeUndefined();
		DashboardEventBus.register(this);
		setCompositionRoot(buildContent());
	}

	private Component buildContent() {
		final CssLayout menuContent = new CssLayout();
		menuContent.addStyleName("sidebar");
		menuContent.addStyleName(ValoTheme.MENU_PART);
		menuContent.addStyleName("no-vertical-drag-hints");
		menuContent.addStyleName("no-horizontal-drag-hints");
		menuContent.setWidth(null);
		menuContent.setHeight("100%");

		menuContent.addComponent(buildTitle());
		menuContent.addComponent(buildUserMenu());
		menuContent.addComponent(buildToggleButton());
		menuContent.addComponent(buildMenuItems());
		return menuContent;
	}

	private Component buildTitle() {
		Label logo = new Label("<strong>Bograntex</strong>", ContentMode.HTML);
		logo.setSizeUndefined();
		HorizontalLayout logoWrapper = new HorizontalLayout(logo);
		logoWrapper.setComponentAlignment(logo, Alignment.MIDDLE_CENTER);
		logoWrapper.addStyleName("valo-menu-title");
		logoWrapper.setSpacing(false);
		return logoWrapper;
	}

	private User getCurrentUser() {
		return (User) VaadinSession.getCurrent().getAttribute(User.class.getName());
	}

	@SuppressWarnings("serial")
	private Component buildUserMenu() {
		final MenuBar settings = new MenuBar();
		settings.addStyleName("user-menu");
		final User user = getCurrentUser();
		settingsItem = settings.addItem("", new ThemeResource("img/profile-pic-300px.jpg"), null);
		updateUserName(null);
		settingsItem.addItem("Perfil", new Command() {
			@Override
			public void menuSelected(final MenuItem selectedItem) {
				ProfilePreferencesWindow.open(user, false);
			}
		});
		settingsItem.addSeparator();
		settingsItem.addItem("Sign Out", new Command() {
			@Override
			public void menuSelected(final MenuItem selectedItem) {
				DashboardEventBus.post(new UserLoggedOutEvent());
			}
		});
		return settings;
	}

	@SuppressWarnings("serial")
	private Component buildToggleButton() {
		Button valoMenuToggleButton = new Button("Menu", new ClickListener() {
			@Override
			public void buttonClick(final ClickEvent event) {
				if (getCompositionRoot().getStyleName().contains(STYLE_VISIBLE)) {
					getCompositionRoot().removeStyleName(STYLE_VISIBLE);
				} else {
					getCompositionRoot().addStyleName(STYLE_VISIBLE);
				}
			}
		});
		valoMenuToggleButton.setIcon(VaadinIcons.LIST);
		valoMenuToggleButton.addStyleName("valo-menu-toggle");
		valoMenuToggleButton.addStyleName(ValoTheme.BUTTON_BORDERLESS);
		valoMenuToggleButton.addStyleName(ValoTheme.BUTTON_SMALL);
		return valoMenuToggleButton;
	}

	private Component buildMenuItems() {
		CssLayout menuItemsLayout = new CssLayout();
		menuItemsLayout.addStyleName("valo-menuitems");

		for (final AppViewType view : AppViewType.values()) {
			Component menuItemComponent = new ValoMenuItemButton(view);

			menuItemsLayout.addComponent(menuItemComponent);
		}
		return menuItemsLayout;
	}

	@Override
	public void attach() {
		super.attach();
	}

	public void postViewChange(final PostViewChangeEvent event) {
		getCompositionRoot().removeStyleName(STYLE_VISIBLE);
	}

	public void updateReportsCount(final ReportsCountUpdatedEvent event) {
		reportsBadge.setValue(String.valueOf(event.getCount()));
		reportsBadge.setVisible(event.getCount() > 0);
	}

	public void updateUserName(final ProfileUpdatedEvent event) {
		User user = getCurrentUser();
		settingsItem.setText(user.getFirstName() + " " + user.getLastName());
	}

	public final class ValoMenuItemButton extends Button {

		private static final long serialVersionUID = 1L;

		private static final String STYLE_SELECTED = "selected";

		private final AppViewType view;

		@SuppressWarnings("serial")
		public ValoMenuItemButton(final AppViewType view) {
			this.view = view;
			setPrimaryStyleName("valo-menu-item");
			setIcon(view.getIcon());
			setCaption(view.getViewName().substring(0, 1).toUpperCase() + view.getViewName().substring(1));
			DashboardEventBus.register(this);
			addClickListener(new ClickListener() {
				@Override
				public void buttonClick(final ClickEvent event) {
					UI.getCurrent().getNavigator().navigateTo(view.getViewName());
				}
			});
		}

		public void postViewChange(final PostViewChangeEvent event) {
			removeStyleName(STYLE_SELECTED);
			if (event.getView() == view) {
				addStyleName(STYLE_SELECTED);
			}
		}
	}
}
