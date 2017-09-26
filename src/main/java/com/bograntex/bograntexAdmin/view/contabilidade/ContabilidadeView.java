package com.bograntex.bograntexAdmin.view.contabilidade;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.Set;

import com.bograntex.bograntexAdmin.domain.Transaction;
import com.bograntex.bograntexAdmin.event.DashboardEvent.BrowserResizeEvent;
import com.google.common.eventbus.Subscribe;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.server.Responsive;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.SingleSelect;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.renderers.NumberRenderer;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("serial")
public final class ContabilidadeView extends VerticalLayout implements View {

    private final Grid<Transaction> grid;
    private SingleSelect<Transaction> singleSelect;
    private Button createReport;
    private static final DateFormat DATEFORMAT = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");
    private static final DecimalFormat DECIMALFORMAT = new DecimalFormat("#.##");
    private static final Set<Column<Transaction, ?>> collapsibleColumns = new LinkedHashSet<>();

    public ContabilidadeView() {
        setSizeFull();
        addStyleName("transactions");
        setMargin(false);
        setSpacing(false);
        addComponent(buildToolbar());

        grid = buildGrid();
        singleSelect = grid.asSingleSelect();
        addComponent(grid);
        setExpandRatio(grid, 1);
    }

    @Override
    public void detach() {
        super.detach();
    }

    private Component buildToolbar() {
        HorizontalLayout header = new HorizontalLayout();
        header.addStyleName("viewheader");
        Responsive.makeResponsive(header);

        Label title = new Label("Valorização Estoque");
        title.setSizeUndefined();
        title.addStyleName(ValoTheme.LABEL_H1);
        title.addStyleName(ValoTheme.LABEL_NO_MARGIN);
        header.addComponent(title);

        createReport = buildCreateReport();
        HorizontalLayout tools = new HorizontalLayout(createReport);
        tools.addStyleName("toolbar");
        header.addComponent(tools);

        return header;
    }

    private Button buildCreateReport() {
        final Button createReport = new Button("Gerar Relárotio");
        createReport.setDescription("Create a new report from the selected transactions");
        createReport.setEnabled(false);
        return createReport;
    }

    private Grid<Transaction> buildGrid() {
        final Grid<Transaction> grid = new Grid<>();
        grid.setSelectionMode(SelectionMode.SINGLE);
        grid.setSizeFull();

        Column<Transaction, String> time = grid.addColumn(transaction -> DATEFORMAT.format(transaction.getTime()));
        time.setId("Time").setHidable(true);

        collapsibleColumns.add(grid.addColumn(Transaction::getCountry).setId("Country"));
        collapsibleColumns.add(grid.addColumn(Transaction::getCity).setId("City"));
        collapsibleColumns.add(grid.addColumn(Transaction::getTheater).setId("Theater"));
        collapsibleColumns.add(grid.addColumn(Transaction::getRoom).setId("Room"));
        collapsibleColumns.add(grid.addColumn(Transaction::getRoom).setId("Title"));
        collapsibleColumns.add(grid.addColumn(Transaction::getSeats, new NumberRenderer()).setId("Seats"));
        grid.addColumn(transaction -> "$" + DECIMALFORMAT.format(transaction.getPrice())).setId("Price").setHidable(true);

        grid.setColumnReorderingAllowed(true);
        ListDataProvider<Transaction> dataProvider = com.vaadin.data.provider.DataProvider.ofCollection(new ArrayList<>());
//        ListDataProvider<Transaction> dataProvider = com.vaadin.data.provider.DataProvider.ofCollection(AppUI.getDataProvider().getRecentTransactions(200));
        dataProvider.addSortComparator(Comparator.comparing(Transaction::getTime).reversed()::compare);
        grid.setDataProvider(dataProvider);

        grid.addSelectionListener(event -> createReport.setEnabled(!singleSelect.isEmpty()));
        return grid;
    }

    private boolean defaultColumnsVisible() {
        boolean result = true;
        for (Column<Transaction, ?> column : collapsibleColumns) {
            if (column.isHidden() == Page.getCurrent().getBrowserWindowWidth() < 800) {
                result = false;
            }
        }
        return result;
    }

    @Subscribe
    public void browserResized(final BrowserResizeEvent event) {
        if (defaultColumnsVisible()) {
            for (Column<Transaction, ?> column : collapsibleColumns) {
                column.setHidden(Page.getCurrent().getBrowserWindowWidth() < 800);
            }
        }
    }

    @Override
    public void enter(final ViewChangeEvent event) {
    }
}
