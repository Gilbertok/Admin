package com.bograntex.bograntexAdmin.view;

import com.bograntex.bograntexAdmin.view.contabilidade.ContabilidadeView;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.server.Resource;

public enum AppViewType {
    
	CONTABILIDADE("contabilidade", ContabilidadeView.class, VaadinIcons.MONEY_EXCHANGE, true),
	DEVEDORES("devedores", ContabilidadeView.class, VaadinIcons.MONEY_EXCHANGE, false);

    private final String viewName;
    private final Class<? extends View> viewClass;
    private final Resource icon;
    private final boolean stateful;

    private AppViewType(final String viewName, final Class<? extends View> viewClass, final Resource icon, final boolean stateful) {
        this.viewName = viewName;
        this.viewClass = viewClass;
        this.icon = icon;
        this.stateful = stateful;
    }

    public boolean isStateful() {
        return stateful;
    }

    public String getViewName() {
        return viewName;
    }

    public Class<? extends View> getViewClass() {
        return viewClass;
    }

    public Resource getIcon() {
        return icon;
    }

    public static AppViewType getByViewName(final String viewName) {
        AppViewType result = null;
        for (AppViewType viewType : values()) {
            if (viewType.getViewName().equals(viewName)) {
                result = viewType;
                break;
            }
        }
        return result;
    }

}
