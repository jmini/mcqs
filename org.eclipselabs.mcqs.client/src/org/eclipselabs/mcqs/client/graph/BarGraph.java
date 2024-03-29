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

public class BarGraph {
  private int id;
  private String name;
  private double value;

  public BarGraph(int id, String name, double value) {
    super();
    this.id = id;
    this.name = name;
    this.value = value;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  /**
   * @return value in percent (range: [0, 1])
   */
  public double getValue() {
    return value;
  }
}
