package com.bograntex.bograntexAdmin.view.contabilidade;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.Set;

import com.bograntex.bograntexAdmin.event.DashboardEvent.BrowserResizeEvent;
import com.bograntex.bograntexAdmin.model.BalancoLojaModel;
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
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("serial")
public final class ContabilidadeView extends VerticalLayout implements View {

    private final Grid<BalancoLojaModel> grid;
    private Button createReport;
    private static final DecimalFormat DECIMALFORMAT = new DecimalFormat("#.##");
    private static final Set<Column<BalancoLojaModel, ?>> collapsibleColumns = new LinkedHashSet<>();

    
    public ContabilidadeView() {
        setSizeFull();
        addStyleName("transactions");
        setMargin(false);
        setSpacing(false);
        addComponent(buildToolbar());

        grid = buildGridTeste();
        addComponent(grid);
        setExpandRatio(grid, 1);
    }

    @Override
    public void detach() {
    	super.detach();
    }
    
    @Override
    public void attach() {
    	super.attach();
        try {
			grid.setItems(new BalancoLojaModel().geraBalancoLojaMes());
		} catch (SQLException e) {
			e.printStackTrace();
		}
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
    
    private Grid<BalancoLojaModel> buildGridTeste() {
    	Grid<BalancoLojaModel> grid = new Grid<>();
    	grid.setSelectionMode(SelectionMode.SINGLE);
    	grid.setSizeFull();
    	grid.addColumn(BalancoLojaModel::getCnpjLojaFormat).setCaption("CNPJ");
//    	grid.addColumn(BalancoLojaModel::getItem).setCaption("Item");
    	grid.addColumn(BalancoLojaModel::getEan13).setCaption("Ean13");
    	grid.addColumn(BalancoLojaModel::getMesAno).setCaption("Mes");
    	grid.addColumn(BalancoLojaModel::getQtdeEstoque).setCaption("Quantidade");
    	grid.addColumn(BalancoLojaModel::getPrecoMedio).setCaption("Preço Médio");
    	return grid;
    }

    private Grid<BalancoLojaModel> buildGrid() {
        Grid<BalancoLojaModel> grid = new Grid<>();
        grid.setSelectionMode(SelectionMode.SINGLE);
        grid.setSizeFull();

        collapsibleColumns.add(grid.addColumn(BalancoLojaModel::getCnpjLojaFormat).setId("Cnpj"));
//        collapsibleColumns.add(grid.addColumn(BalancoLojaModel::getItem).setId("Item"));
        collapsibleColumns.add(grid.addColumn(BalancoLojaModel::getEan13).setId("Ean13"));
        grid.addColumn(transaction -> "$" + DECIMALFORMAT.format(transaction.getPrecoMedio())).setId("PrecoMedio").setHidable(true);
        grid.setColumnReorderingAllowed(true);
        
        ListDataProvider<BalancoLojaModel> dataProvider;
		try {
//			dataProvider = com.vaadin.data.provider.DataProvider.ofCollection(new BalancoLojaModel().geraBalancoLojaMes());
//			dataProvider.addSortComparator(Comparator.comparing(BalancoLojaModel::getMes).reversed()::compare);
//			grid.setDataProvider(dataProvider);
//			grid.addSelectionListener(event -> createReport.setEnabled(!singleSelect.isEmpty()));
	        grid.setItems(new BalancoLojaModel().geraBalancoLojaMes());
		} catch (SQLException e) {
			e.printStackTrace();
		}
        return grid;
    }

    private boolean defaultColumnsVisible() {
        boolean result = true;
        for (Column<BalancoLojaModel, ?> column : collapsibleColumns) {
            if (column.isHidden() == Page.getCurrent().getBrowserWindowWidth() < 800) {
                result = false;
            }
        }
        return result;
    }

    @Subscribe
    public void browserResized(final BrowserResizeEvent event) {
        if (defaultColumnsVisible()) {
            for (Column<BalancoLojaModel, ?> column : collapsibleColumns) {
                column.setHidden(Page.getCurrent().getBrowserWindowWidth() < 800);
            }
        }
    }

    @Override
    public void enter(final ViewChangeEvent event) {
    }
}
