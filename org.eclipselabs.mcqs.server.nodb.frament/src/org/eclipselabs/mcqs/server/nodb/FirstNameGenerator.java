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
package org.eclipselabs.mcqs.server.nodb;

/**
 * @author jbr
 */
public class FirstNameGenerator {

  private static final String[] FIRST_NAME_GENERATOR = new String[]{
      "Xaviera",
      "Zia",
      "Leigh",
      "Brenda",
      "Teagan",
      "Lucas",
      "Rebecca",
      "Bethany",
      "Pamela",
      "Amal",
      "Cara",
      "Ishmael",
      "Audrey",
      "Fay",
      "Roary",
      "Ivan",
      "Castor",
      "Brooke",
      "Boris",
      "Lenore",
      "Jamal",
      "Lysandra",
      "Nigel",
      "Chester",
      "Palmer",
      "Kasimir",
      "Uta",
      "Justina",
      "Sarah",
      "Katelyn",
      "Joel",
      "Ria",
      "Kaseem",
      "Aspen",
      "Uma",
      "Ignacia",
      "Mollie",
      "Noah",
      "Kato",
      "Ifeoma",
      "Peter",
      "Morgan",
      "Sonya",
      "Sage",
      "Zorita",
      "Mira",
      "Dorian",
      "Cassandra",
      "Risa",
      "Harrison",
      "Portia",
      "Diana",
      "Aaron",
      "Jaquelyn",
      "Jamal",
      "Paloma",
      "Yuri",
      "Hasad",
      "Darrel",
      "Ross",
      "Tiger",
      "Oren",
      "Macaulay",
      "Ima",
      "Ria",
      "Kennan",
      "Shannon",
      "Jessica",
      "Davis",
      "Hakeem",
      "Teagan",
      "Zorita",
      "Anika",
      "Quinlan",
      "Sade",
      "Angela",
      "Isadora",
      "Berk",
      "Craig",
      "Deanna",
      "Jin",
      "Yuli",
      "Karyn",
      "Joan",
      "Ivan",
      "Yen",
      "Cullen",
      "Ina",
      "Grant",
      "Jayme",
      "David",
      "Donna",
      "Ciaran",
      "Solomon",
      "Nelle",
      "Abigail",
      "Hilda",
      "Kiayada",
      "Jocelyn",
      "Maryam"
  };
  private int m_firstNameGeneratorSequence = -1;

  public String generate() {
    m_firstNameGeneratorSequence = (m_firstNameGeneratorSequence + 1) % FIRST_NAME_GENERATOR.length;
    return FIRST_NAME_GENERATOR[m_firstNameGeneratorSequence];
  }
}
