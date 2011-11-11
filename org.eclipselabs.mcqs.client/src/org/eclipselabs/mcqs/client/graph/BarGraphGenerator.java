/*******************************************************************************
 * Copyright 2011 Jeremie Bresson
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

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public final class BarGraphGenerator {
  public static String[] COLORS = new String[]{"yellow", "salmon", "lightgreen", "blanchedalmond", "thistle", "tan"};

  public static List<String> generate(List<BarGraph> values) {
    ArrayList<String> cnt = new ArrayList<String>();

    int nb = values.size();
    int svgHeight = 30 + (nb * 30);
    int line1y = svgHeight - 20;
    int line2y = svgHeight - 15;
    int texty = svgHeight - 10;

    cnt.add("<?xml version=\"1.0\" standalone=\"no\"?>");
    cnt.add("<svg xmlns:xlink=\"http://www.w3.org/1999/xlink\" xmlns=\"http://www.w3.org/2000/svg\" width=\"600\" height=\"" + svgHeight + "\" version=\"1.1\" onload=\"init()\">");
    cnt.add("  <defs>");
    cnt.add("    <clipPath id=\"canvas\">");
    cnt.add("      <rect width=\"100%\" height=\"100%\" fill=\"rgb(240,240,240)\" stroke-width=\"1px\" stroke=\"rgb(0,0,0)\"/>");
    cnt.add("    </clipPath>");
    cnt.add("  </defs>");
    cnt.add("  <g clip-path=\"url(#canvas)\">");
    cnt.add("    <rect width=\"100%\" height=\"100%\" fill=\"rgb(240,240,240)\" stroke-width=\"1px\" stroke=\"rgb(0,0,0)\"/>");
//		cnt.add("    <path d=\"M16 130L590 130M16 100L590 100M16 70L590 70M16 40L590 40M16 10L590 10M16 130L16 10M44.7 130L44.7 10M73.4 130L73.4 10M102.1 130L102.1 10M130.8 130L130.8 10M159.5 130L159.5 10M188.2 130L188.2 10M216.9 130L216.9 10M245.6 130L245.6 10M274.3 130L274.3 10M303 130L303 10M331.7 130L331.7 10M360.4 130L360.4 10M389.1 130L389.1 10M417.8 130L417.8 10M446.5 130L446.5 10M475.2 130L475.2 10M503.9 130L503.9 10M532.6 130L532.6 10M561.3 130L561.3 10M590 130L590 10\" stroke=\"rgb(220,220,220)\"/>");
    addRect(cnt, values);
//		cnt.add("    <path d=\"M16 130l-3 0M16 100l-3 0M16 70l-3 0M16 40l-3 0M16 10l-3 0M16 130l0 3M44.7 130l0 3M73.4 130l0 3M102.1 130l0 3M130.8 130l0 3M159.5 130l0 3M188.2 130l0 3M216.9 130l0 3M245.6 130l0 3M274.3 130l0 3M303 130l0 3M331.7 130l0 3M360.4 130l0 3M389.1 130l0 3M417.8 130l0 3M446.5 130l0 3M475.2 130l0 3M503.9 130l0 3M532.6 130l0 3M561.3 130l0 3M590 130l0 3\" stroke-width=\"1px\" stroke=\"rgb(0,0,0)\"/>");
    cnt.add("    <g stroke-width=\"2px\" stroke=\"rgb(0,0,0)\">");
    cnt.add("      <line x1=\"11\" x2=\"595\" y2=\"" + line1y + "\" y1=\"" + line1y + "\"/>");
    cnt.add("      <line x1=\"16\" x2=\"16\" y2=\"" + line2y + "\" y1=\"5\"/>");
    cnt.add("    </g>");
    cnt.add("    <g font-size=\"10px\" font-family=\"monospace\" fill=\"rgb(0,0,0)\">");
    cnt.add("      <g text-anchor=\"middle\">");
    cnt.add("        <text x=\"16\" y=\"" + texty + "\">0</text>");
    cnt.add("        <text x=\"44.7\" y=\"" + texty + "\">5</text>");
    cnt.add("        <text x=\"73.4\" y=\"" + texty + "\">10</text>");
    cnt.add("        <text x=\"102.1\" y=\"" + texty + "\">15</text>");
    cnt.add("        <text x=\"130.8\" y=\"" + texty + "\">20</text>");
    cnt.add("        <text x=\"159.5\" y=\"" + texty + "\">25</text>");
    cnt.add("        <text x=\"188.2\" y=\"" + texty + "\">30</text>");
    cnt.add("        <text x=\"216.9\" y=\"" + texty + "\">35</text>");
    cnt.add("        <text x=\"245.6\" y=\"" + texty + "\">40</text>");
    cnt.add("        <text x=\"274.3\" y=\"" + texty + "\">45</text>");
    cnt.add("        <text x=\"303\" y=\"" + texty + "\">50</text>");
    cnt.add("        <text x=\"331.7\" y=\"" + texty + "\">55</text>");
    cnt.add("        <text x=\"360.4\" y=\"" + texty + "\">60</text>");
    cnt.add("        <text x=\"389.1\" y=\"" + texty + "\">65</text>");
    cnt.add("        <text x=\"417.8\" y=\"" + texty + "\">70</text>");
    cnt.add("        <text x=\"446.5\" y=\"" + texty + "\">75</text>");
    cnt.add("        <text x=\"475.2\" y=\"" + texty + "\">80</text>");
    cnt.add("        <text x=\"503.9\" y=\"" + texty + "\">85</text>");
    cnt.add("        <text x=\"532.6\" y=\"" + texty + "\">90</text>");
    cnt.add("        <text x=\"561.3\" y=\"" + texty + "\">95</text>");
    cnt.add("        <text x=\"590\" y=\"" + texty + "\">100</text>");
    cnt.add("      </g>");
    cnt.add("      <g text-anchor=\"start\">");
    addNames(cnt, values);
    cnt.add("      </g>");
    cnt.add("    </g>");
    cnt.add("  </g>");
    cnt.add("</svg>");

    return cnt;
  }

  private static void addRect(ArrayList<String> cnt, List<BarGraph> values) {
    int nb = values.size();
    int i = 1;
    for (BarGraph e : values) {
      int y = 15 + (30 * (nb - i));
      double d = 574.0 * (e.getValue() / 100.0);
      BigDecimal bd = new BigDecimal(d).setScale(1, RoundingMode.HALF_EVEN);
      String width = String.valueOf(bd.doubleValue());
      width = width.replaceAll(".0$", "");
      String[] cs = new String[]{
          //          "salmon",
          "lightpink",
          "honeydew",
          "lightcyan",
          "lightgreen",
          "blanchedalmond",
          "khaki",
          //                    "thistle",
          "mintcream"};
//      String color = COLORS[i % COLORS.length];
      String color = cs[i % cs.length];
      cnt.add("    <rect height=\"20\" y=\"" + y + "\" x=\"16\" width=\"" + width + "\" id=\"e" + i + "\" style=\"stroke:rgb(0,0,0);stroke-width:1px;fill:" + color + ";\"/>");
      i = i + 1;
    }
  }

  private static void addNames(ArrayList<String> cnt, List<BarGraph> values) {
    int nb = values.size();
    int i = 1;
    for (BarGraph e : values) {
      int y = 28 + (30 * (nb - i));
      cnt.add("        <text x=\"25\" y=\"" + y + "\">" + e.getName() + "</text>");
      i = i + 1;
    }
  }

  /**
   * @param generate
   * @return
   */
  public static InputStream convertToInputStream(List<String> cnt) {
    StringBuilder sb = new StringBuilder();
    for (String l : cnt) {
      sb.append(l + "\n");
    }
    byte[] bytes = sb.toString().getBytes();
    InputStream is = new ByteArrayInputStream(bytes);
    return is;
  }

}
