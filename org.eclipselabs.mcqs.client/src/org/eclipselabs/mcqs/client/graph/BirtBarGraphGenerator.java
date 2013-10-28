/*******************************************************************************
 * Copyright 2013 Jeremie Bresson
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package org.eclipselabs.mcqs.client.graph;

import java.io.ByteArrayOutputStream;
import java.util.List;

import org.eclipse.birt.chart.api.ChartEngine;
import org.eclipse.birt.chart.device.EmptyUpdateNotifier;
import org.eclipse.birt.chart.device.IDeviceRenderer;
import org.eclipse.birt.chart.exception.ChartException;
import org.eclipse.birt.chart.factory.GeneratedChartState;
import org.eclipse.birt.chart.factory.Generator;
import org.eclipse.birt.chart.factory.RunTimeContext;
import org.eclipse.birt.chart.model.Chart;
import org.eclipse.birt.chart.model.ChartWithAxes;
import org.eclipse.birt.chart.model.ChartWithoutAxes;
import org.eclipse.birt.chart.model.attribute.AxisType;
import org.eclipse.birt.chart.model.attribute.Bounds;
import org.eclipse.birt.chart.model.attribute.ChartDimension;
import org.eclipse.birt.chart.model.attribute.DataPointComponent;
import org.eclipse.birt.chart.model.attribute.DataPointComponentType;
import org.eclipse.birt.chart.model.attribute.IntersectionType;
import org.eclipse.birt.chart.model.attribute.Orientation;
import org.eclipse.birt.chart.model.attribute.Position;
import org.eclipse.birt.chart.model.attribute.TickStyle;
import org.eclipse.birt.chart.model.attribute.impl.BoundsImpl;
import org.eclipse.birt.chart.model.attribute.impl.ColorDefinitionImpl;
import org.eclipse.birt.chart.model.attribute.impl.DataPointComponentImpl;
import org.eclipse.birt.chart.model.attribute.impl.JavaNumberFormatSpecifierImpl;
import org.eclipse.birt.chart.model.component.Axis;
import org.eclipse.birt.chart.model.component.Series;
import org.eclipse.birt.chart.model.component.impl.SeriesImpl;
import org.eclipse.birt.chart.model.data.NumberDataSet;
import org.eclipse.birt.chart.model.data.SeriesDefinition;
import org.eclipse.birt.chart.model.data.TextDataSet;
import org.eclipse.birt.chart.model.data.impl.NumberDataSetImpl;
import org.eclipse.birt.chart.model.data.impl.SeriesDefinitionImpl;
import org.eclipse.birt.chart.model.data.impl.TextDataSetImpl;
import org.eclipse.birt.chart.model.impl.ChartWithAxesImpl;
import org.eclipse.birt.chart.model.impl.ChartWithoutAxesImpl;
import org.eclipse.birt.chart.model.layout.Legend;
import org.eclipse.birt.chart.model.layout.Plot;
import org.eclipse.birt.chart.model.type.BarSeries;
import org.eclipse.birt.chart.model.type.PieSeries;
import org.eclipse.birt.chart.model.type.impl.BarSeriesImpl;
import org.eclipse.birt.chart.model.type.impl.PieSeriesImpl;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.shared.TEXTS;

import com.ibm.icu.util.ULocale;

/**
 * @author jbr
 */
public class BirtBarGraphGenerator {

  public static final BirtBarGraphGenerator INSTANCE = new BirtBarGraphGenerator();

  public static byte[] generate(List<BarGraph> barGraphs, boolean multipleChoices) throws ProcessingException {
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    if (barGraphs.size() > 0) {
      Chart chart = createChart(barGraphs, multipleChoices);
      try {
        RunTimeContext rtc = new RunTimeContext();
        rtc.setULocale(ULocale.getDefault());
        ChartEngine ce = ChartEngine.instance();
        IDeviceRenderer idr = ce.getRenderer("dv.SVG");

        Generator gr = Generator.instance();
        GeneratedChartState gcs = null;
        Bounds bo = BoundsImpl.create(0, 0, 500, 300);
        gcs = gr.build(idr.getDisplayServer(), chart, bo, null, rtc, null);

        idr.setProperty(IDeviceRenderer.FILE_IDENTIFIER, out);
        idr.setProperty(
            IDeviceRenderer.UPDATE_NOTIFIER,
            new EmptyUpdateNotifier(chart, gcs.getChartModel()));

        gr.render(idr, gcs);
      }
      catch (ChartException e) {
        throw new ProcessingException("Error during generation of the chart", e);
      }
    }
    return out.toByteArray();
  }

  private static Chart createChart(List<BarGraph> barGraphs, boolean multipleChoices) {
    // Data Set:
    String[] names = new String[barGraphs.size()];
    double[] valuesYes = new double[barGraphs.size()];
    double[] valuesNo = new double[barGraphs.size()];
    for (int i = 0; i < barGraphs.size(); i++) {
      BarGraph barGraph = barGraphs.get(i);
      names[i] = barGraph.getName();
      valuesYes[i] = barGraph.getValue();
      valuesNo[i] = 1 - barGraph.getValue();
    }
    TextDataSet categoryValues = TextDataSetImpl.create(names);
    NumberDataSet yesSerieValues = NumberDataSetImpl.create(valuesYes);

    if (multipleChoices) {
      NumberDataSet noSerieValues = NumberDataSetImpl.create(valuesNo);
      return createBarChart(categoryValues, yesSerieValues, noSerieValues);
    }
    else {
      return createPieChart(categoryValues, yesSerieValues);
    }
  }

  private static Chart createBarChart(TextDataSet categoryValues, NumberDataSet yesSerieValues, NumberDataSet noSerieValues) {
    // bart charts are based on charts that contain axes
    ChartWithAxes cwaBar = ChartWithAxesImpl.create();
    cwaBar.getBlock().setBackground(ColorDefinitionImpl.WHITE());
    cwaBar.getBlock().getOutline().setVisible(true);
    cwaBar.setDimension(ChartDimension.TWO_DIMENSIONAL_LITERAL);
    cwaBar.setOrientation(Orientation.HORIZONTAL_LITERAL);

    // customize the plot
    Plot p = cwaBar.getPlot();
    p.getClientArea().setBackground(ColorDefinitionImpl.create(255, 255, 225));
    p.getOutline().setVisible(false);

    cwaBar.getTitle().getLabel().getCaption().setValue(TEXTS.get("Answers"));

    // customize the legend
    Legend lg = cwaBar.getLegend();
    lg.setVisible(false);

    // customize the X-axis
    Axis xAxisPrimary = cwaBar.getPrimaryBaseAxes()[0];
    xAxisPrimary.setType(AxisType.TEXT_LITERAL);
    xAxisPrimary.getMajorGrid().setTickStyle(TickStyle.BELOW_LITERAL);
    xAxisPrimary.getOrigin().setType(IntersectionType.VALUE_LITERAL);
    xAxisPrimary.getTitle().setVisible(false);

    // customize the Y-axis
    Axis yAxisPrimary = cwaBar.getPrimaryOrthogonalAxis(xAxisPrimary);
    yAxisPrimary.getMajorGrid().setTickStyle(TickStyle.LEFT_LITERAL);
    yAxisPrimary.setType(AxisType.LINEAR_LITERAL);
    yAxisPrimary.getLabel().getCaption().getFont().setRotation(90);
    yAxisPrimary.setPercent(true);

    // create the category base series
    Series seCategory = SeriesImpl.create();
    seCategory.setDataSet(categoryValues);

    // Y-Series
    BarSeries bs1 = (BarSeries) BarSeriesImpl.create();
    bs1.setSeriesIdentifier(TEXTS.get("ResultYes"));
    bs1.setDataSet(yesSerieValues);
    bs1.setStacked(true);
    bs1.getLabel().setVisible(true);
    bs1.setLabelPosition(Position.INSIDE_LITERAL);

    DataPointComponent dpc1 = DataPointComponentImpl.create(DataPointComponentType.ORTHOGONAL_VALUE_LITERAL, JavaNumberFormatSpecifierImpl.create("##.##%"));
    bs1.getDataPoint().getComponents().clear();
    bs1.getDataPoint().getComponents().add(dpc1);

    BarSeries bs2 = (BarSeries) BarSeriesImpl.create();
    bs2.setSeriesIdentifier("ResultNo");
    bs2.setDataSet(noSerieValues);
    bs2.setStacked(true);
    bs2.getLabel().setVisible(true);
    bs2.setLabelPosition(Position.INSIDE_LITERAL);

    DataPointComponent dpc2 = DataPointComponentImpl.create(DataPointComponentType.ORTHOGONAL_VALUE_LITERAL, JavaNumberFormatSpecifierImpl.create("##.##%"));
    bs2.getDataPoint().getComponents().clear();
    bs2.getDataPoint().getComponents().add(dpc2);

    // wrap the base series in the X-axis series definition
    SeriesDefinition sdX = SeriesDefinitionImpl.create();
    sdX.getSeriesPalette().shift(0); // set the colors in the palette
    xAxisPrimary.getSeriesDefinitions().add(sdX);
    sdX.getSeries().add(seCategory);

    // wrap the orthogonal series in the X-axis series definition
    SeriesDefinition sdY = SeriesDefinitionImpl.create();
    sdY.getSeriesPalette().shift(0);
    yAxisPrimary.getSeriesDefinitions().add(sdY);
    sdY.getSeries().add(bs1);
    sdY.getSeries().add(bs2);

    return cwaBar;
  }

  private static Chart createPieChart(TextDataSet categoryValues, NumberDataSet serieValues) {
    ChartWithoutAxes cwoaPie = ChartWithoutAxesImpl.create();
    cwoaPie.setDimension(ChartDimension.TWO_DIMENSIONAL_LITERAL);
//    cwoaPie.setType("Pie Chart");
//    cwoaPie.setSubType("Standard Pie Chart");

    // Plot
    cwoaPie.setSeriesThickness(10);

    // Legend
    Legend lg = cwoaPie.getLegend();
    lg.getOutline().setVisible(true);

    // Title
    cwoaPie.getTitle().setVisible(false);

    // Base Series
    Series seCategory = SeriesImpl.create();
    seCategory.setDataSet(categoryValues);

    SeriesDefinition sd = SeriesDefinitionImpl.create();
    cwoaPie.getSeriesDefinitions().add(sd);
    sd.getSeriesPalette().shift(0);
    sd.getSeries().add(seCategory);

    // Orthogonal Series
    PieSeries sePie = (PieSeries) PieSeriesImpl.create();
    sePie.setDataSet(serieValues);
    sePie.setSeriesIdentifier(TEXTS.get("Answers"));
    sePie.setExplosion(3);

    DataPointComponent dpc = DataPointComponentImpl.create(DataPointComponentType.PERCENTILE_ORTHOGONAL_VALUE_LITERAL, JavaNumberFormatSpecifierImpl.create("##.##%"));
    sePie.getDataPoint().getComponents().clear();
    sePie.getDataPoint().getComponents().add(dpc);

    SeriesDefinition sdAnswers = SeriesDefinitionImpl.create();
    sd.getSeriesDefinitions().add(sdAnswers);
    sdAnswers.getSeries().add(sePie);

    return cwoaPie;
  }
}
