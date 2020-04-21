package org.vaadin.example.bookstore.ui.dummy;

import org.vaadin.example.bookstore.ui.MainLayout;

import com.vaadin.flow.component.Direction;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.ChartType;
import com.vaadin.flow.component.charts.model.Configuration;
import com.vaadin.flow.component.charts.model.Crosshair;
import com.vaadin.flow.component.charts.model.ListSeries;
import com.vaadin.flow.component.charts.model.Tooltip;
import com.vaadin.flow.component.charts.model.XAxis;
import com.vaadin.flow.component.charts.model.YAxis;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route(value = "empty")
public class EmptyView extends Div {
    public EmptyView() {
        createChart();
    }

    private void createChart() {
        UI.getCurrent().setDirection(Direction.RIGHT_TO_LEFT);
        Chart chart = new Chart();

        Configuration configuration = chart.getConfiguration();
        chart.getConfiguration().getChart().setType(ChartType.COLUMN);

        configuration.addSeries(new ListSeries("Tokyo", 49.9));

        XAxis x = new XAxis();
        x.setCrosshair(new Crosshair());
        x.setCategories("Jan");
        configuration.addxAxis(x);

        YAxis y = new YAxis();
        y.setMin(0);
        y.setTitle("Rainfall (mm)");
        configuration.addyAxis(y);

//        Tooltip tooltip = new Tooltip();
//        tooltip.setShared(true);
//        configuration.setTooltip(tooltip);

        add(chart);
    }

}
