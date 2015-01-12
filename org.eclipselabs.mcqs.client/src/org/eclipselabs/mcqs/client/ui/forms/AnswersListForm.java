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
package org.eclipselabs.mcqs.client.ui.forms;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.scout.commons.BooleanUtility;
import org.eclipse.scout.commons.annotations.FormData;
import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.basic.table.AbstractTable;
import org.eclipse.scout.rt.client.ui.basic.table.ITable;
import org.eclipse.scout.rt.client.ui.basic.table.ITableRow;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractDoubleColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractLongColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractOkButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.integerfield.AbstractIntegerField;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.client.ui.form.fields.tabbox.AbstractTabBox;
import org.eclipse.scout.rt.client.ui.form.fields.tablefield.AbstractTableField;
import org.eclipse.scout.rt.client.ui.messagebox.MessageBox;
import org.eclipse.scout.rt.shared.ScoutTexts;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.ui.UserAgentUtility;
import org.eclipse.scout.service.SERVICES;
import org.eclipse.scout.svg.client.SVGUtility;
import org.eclipse.scout.svg.client.svgfield.AbstractSvgField;
import org.eclipselabs.mcqs.client.graph.BarGraph;
import org.eclipselabs.mcqs.client.graph.BirtBarGraphGenerator;
import org.eclipselabs.mcqs.client.ui.forms.AnswersListForm.MainBox.AnswersTabsBox;
import org.eclipselabs.mcqs.client.ui.forms.AnswersListForm.MainBox.AnswersTabsBox.GraphBox;
import org.eclipselabs.mcqs.client.ui.forms.AnswersListForm.MainBox.AnswersTabsBox.GraphBox.GraphField;
import org.eclipselabs.mcqs.client.ui.forms.AnswersListForm.MainBox.AnswersTabsBox.ListBox;
import org.eclipselabs.mcqs.client.ui.forms.AnswersListForm.MainBox.AnswersTabsBox.ListBox.AnswersField;
import org.eclipselabs.mcqs.client.ui.forms.AnswersListForm.MainBox.AnswersTabsBox.StatisticsBox;
import org.eclipselabs.mcqs.client.ui.forms.AnswersListForm.MainBox.AnswersTabsBox.StatisticsBox.StatisticsField;
import org.eclipselabs.mcqs.client.ui.forms.AnswersListForm.MainBox.ContentBox.QuestionNrField;
import org.eclipselabs.mcqs.client.ui.forms.AnswersListForm.MainBox.ContentBox.QuestionTextField;
import org.eclipselabs.mcqs.client.ui.forms.AnswersListForm.MainBox.OkButton;
import org.eclipselabs.mcqs.client.ui.templates.AbstractExportToExcelMenu;
import org.eclipselabs.mcqs.shared.services.process.AnswersListFormData;
import org.eclipselabs.mcqs.shared.services.process.IAnswerProcessService;
import org.eclipselabs.mcqs.shared.services.process.IAnswersListProcessService;

@FormData(value = AnswersListFormData.class, sdkCommand = FormData.SdkCommand.CREATE)
public class AnswersListForm extends AbstractForm {

  private Boolean m_multipleChoices;

  public AnswersListForm() throws ProcessingException {
    super();
  }

  @Override
  protected boolean getConfiguredAskIfNeedSave() {
    return false;
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("Answers");
  }

  public void startDisplay() throws ProcessingException {
    startInternal(new AnswersListForm.DisplayHandler());
  }

  public AnswersField getAnswersField() {
    return getFieldByClass(AnswersField.class);
  }

  public AnswersTabsBox getAnswersTabsBox() {
    return getFieldByClass(AnswersTabsBox.class);
  }

  public GraphBox getGraphBox() {
    return getFieldByClass(GraphBox.class);
  }

  public GraphField getGraphField() {
    return getFieldByClass(GraphField.class);
  }

  public ListBox getListBox() {
    return getFieldByClass(ListBox.class);
  }

  public MainBox getMainBox() {
    return getFieldByClass(MainBox.class);
  }

  public OkButton getOkButton() {
    return getFieldByClass(OkButton.class);
  }

  public QuestionNrField getQuestionNrField() {
    return getFieldByClass(QuestionNrField.class);
  }

  public QuestionTextField getQuestionTextField() {
    return getFieldByClass(QuestionTextField.class);
  }

  public StatisticsBox getStatsBox() {
    return getFieldByClass(StatisticsBox.class);
  }

  public StatisticsField getStatisticsField() {
    return getFieldByClass(StatisticsField.class);
  }

  private void handleMultiple() {
    boolean isMulipleChoices = isMultipleChoices();

    getStatisticsField().getTable().getResultColumn().setDisplayable(!isMulipleChoices);
    getStatisticsField().getTable().getResultYesColumn().setDisplayable(isMulipleChoices);
    getStatisticsField().getTable().getResultNoColumn().setDisplayable(isMulipleChoices);
  }

  private void reloadGraph(AnswersListFormData formData) throws ProcessingException {
    List<BarGraph> values = new ArrayList<BarGraph>();
    int nb = formData.getStatistics().getRowCount();
    for (int i = 0; i < nb; i++) {
      int j = nb - i - 1;
      Double result;
      if (isMultipleChoices()) {
        result = formData.getStatistics().getResultYes(j);
      }
      else {
        result = formData.getStatistics().getResult(j);
      }
      if (result != null) {
        values.add(new BarGraph(j, formData.getStatistics().getChoice(j), result.doubleValue()));
      }
    }
    if (values.size() > 0) {
      byte[] content = BirtBarGraphGenerator.generate(values, isMultipleChoices());
      InputStream in = new ByteArrayInputStream(content);
      getGraphField().setSvgDocument(SVGUtility.readSVGDocument(in));
    }
  }

  private void reloadStatistics() throws ProcessingException {
    IAnswersListProcessService service = SERVICES.getService(IAnswersListProcessService.class);
    AnswersListFormData formData = new AnswersListFormData();
    exportFormData(formData);
    formData = service.loadStatistics(formData);
    importFormData(formData);

    reloadGraph(formData);
  }

  @Order(10.0)
  public class MainBox extends AbstractGroupBox {

    @Order(10.0)
    public class ContentBox extends AbstractGroupBox {

      @Order(10.0)
      public class QuestionNrField extends AbstractIntegerField {

        @Override
        protected boolean getConfiguredEnabled() {
          return false;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Question");
        }
      }

      @Order(20.0)
      public class QuestionTextField extends AbstractStringField {

        @Override
        protected boolean getConfiguredEnabled() {
          return false;
        }

        @Override
        protected int getConfiguredGridH() {
          return 2;
        }

        @Override
        protected int getConfiguredGridW() {
          return 2;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Question");
        }

        @Override
        protected boolean getConfiguredMultilineText() {
          return true;
        }

        @Override
        protected boolean getConfiguredWrapText() {
          return true;
        }
      }

    }

    @Order(30.0)
    public class AnswersTabsBox extends AbstractTabBox {

      @Order(10.0)
      public class GraphBox extends AbstractGroupBox {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Graph");
        }

        @Override
        protected void execInitField() throws ProcessingException {
          setVisible(UserAgentUtility.isDesktopDevice());
        }

        @Order(10.0)
        public class GraphField extends AbstractSvgField {

          @Override
          protected int getConfiguredGridH() {
            return 15;
          }

          @Override
          protected boolean getConfiguredLabelVisible() {
            return false;
          }
        }
      }

      @Order(20.0)
      public class StatisticsBox extends AbstractGroupBox {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Statistics");
        }

        @Order(10.0)
        public class StatisticsField extends AbstractTableField<StatisticsField.Table> {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Statistics");
          }

          @Override
          protected boolean getConfiguredLabelVisible() {
            return false;
          }

          @Override
          protected boolean execIsSaveNeeded() throws ProcessingException {
            return false;
          }

          @Order(10.0)
          public class Table extends AbstractTable {

            @Override
            protected boolean getConfiguredAutoResizeColumns() {
              return true;
            }

            public ResultYesColumn getResultYesColumn() {
              return getColumnSet().getColumnByClass(ResultYesColumn.class);
            }

            public ResultNoColumn getResultNoColumn() {
              return getColumnSet().getColumnByClass(ResultNoColumn.class);
            }

            public ChoiceColumn getChoiceColumn() {
              return getColumnSet().getColumnByClass(ChoiceColumn.class);
            }

            public ResultColumn getResultColumn() {
              return getColumnSet().getColumnByClass(ResultColumn.class);
            }

            @Order(10.0)
            public class ChoiceColumn extends AbstractStringColumn {

              @Override
              protected String getConfiguredHeaderText() {
                return TEXTS.get("Choice");
              }

              @Override
              protected int getConfiguredWidth() {
                return 250;
              }
            }

            @Order(20.0)
            public class ResultColumn extends AbstractDoubleColumn {

              @Override
              protected String getConfiguredHeaderText() {
                return TEXTS.get("Result");
              }

              @Override
              protected int getConfiguredMultiplier() {
                return 100;
              }

              @Override
              protected boolean getConfiguredPercent() {
                return true;
              }
            }

            @Order(30.0)
            public class ResultYesColumn extends AbstractDoubleColumn {

              @Override
              protected String getConfiguredHeaderText() {
                return TEXTS.get("ResultYes");
              }

              @Override
              protected int getConfiguredMultiplier() {
                return 100;
              }

              @Override
              protected boolean getConfiguredPercent() {
                return true;
              }
            }

            @Order(40.0)
            public class ResultNoColumn extends AbstractDoubleColumn {

              @Override
              protected String getConfiguredHeaderText() {
                return TEXTS.get("ResultNo");
              }

              @Override
              protected int getConfiguredMultiplier() {
                return 100;
              }

              @Override
              protected boolean getConfiguredPercent() {
                return true;
              }
            }

            @Order(10.0)
            public class ExportToExcelMenu extends AbstractExportToExcelMenu {

              @Override
              protected String provideTitle() {
                return TEXTS.get("Statistics");
              }

              @Override
              protected ITable provideTable() {
                return getTable();
              }
            }
          }
        }
      }

      @Order(30.0)
      public class ListBox extends AbstractGroupBox {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("List");
        }

        @Order(10.0)
        public class AnswersField extends AbstractTableField<AnswersField.Table> {

          @Override
          protected int getConfiguredGridH() {
            return 15;
          }

          @Override
          protected int getConfiguredGridW() {
            return 2;
          }

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Answers");
          }

          @Override
          protected boolean getConfiguredLabelVisible() {
            return false;
          }

          @Override
          protected boolean execIsSaveNeeded() throws ProcessingException {
            return false;
          }

          @Order(10.0)
          public class Table extends AbstractTable {

            @Override
            protected boolean getConfiguredAutoDiscardOnDelete() {
              return true;
            }

            @Override
            protected boolean getConfiguredAutoResizeColumns() {
              return true;
            }

            public AnswerNrColumn getAnswerNrColumn() {
              return getColumnSet().getColumnByClass(AnswerNrColumn.class);
            }

            public NameColumn getNameColumn() {
              return getColumnSet().getColumnByClass(NameColumn.class);
            }

            @Order(10.0)
            public class AnswerNrColumn extends AbstractLongColumn {

              @Override
              protected String getConfiguredHeaderText() {
                return TEXTS.get("No_");
              }

              @Override
              protected boolean getConfiguredPrimaryKey() {
                return true;
              }

              @Override
              protected boolean getConfiguredVisible() {
                return false;
              }
            }

            @Order(20.0)
            public class NameColumn extends AbstractStringColumn {

              @Override
              protected String getConfiguredHeaderText() {
                return ScoutTexts.get("Name");
              }
            }

            @Order(10.0)
            public class AddAnAnswerMenu extends AbstractMenu {

              @Override
              protected boolean getConfiguredEmptySpaceAction() {
                return true;
              }

              @Override
              protected boolean getConfiguredSingleSelectionAction() {
                return false;
              }

              @Override
              protected String getConfiguredText() {
                return TEXTS.get("AddAnAnswer");
              }

              @Override
              protected void execAction() throws ProcessingException {
                AnswerForm form = new AnswerForm();
                form.getQuestionNrField().setValue(getQuestionNrField().getValue());
                form.startNew();
                form.waitFor();
                if (form.isFormStored()) {
                  ITableRow newRow = createRow();
                  getAnswerNrColumn().setValue(newRow, form.getAnswerNr());
                  getNameColumn().setValue(newRow, form.getYourNameField().getValue());
                  addRow(newRow);
                  reloadStatistics();
                }
              }
            }

            @Order(20.0)
            public class EditAnswerMenu extends AbstractMenu {

              @Override
              protected String getConfiguredText() {
                return TEXTS.get("EditAnswer");
              }

              @Override
              protected void execAction() throws ProcessingException {
                AnswerForm form = new AnswerForm();
                form.setAnswerNr(getAnswerNrColumn().getSelectedValue());
                form.startModify();
                form.waitFor();
                if (form.isFormStored()) {
                  getNameColumn().setValue(getSelectedRow(), form.getYourNameField().getValue());
                  reloadStatistics();
                }
              }
            }

            @Order(30.0)
            public class DeleteAnswerMenu extends AbstractMenu {

              @Override
              protected String getConfiguredText() {
                return TEXTS.get("DeleteAnswer");
              }

              @Override
              protected void execAction() throws ProcessingException {
                ITableRow r = getSelectedRow();
                if (MessageBox.showDeleteConfirmationMessage(TEXTS.get("Answers"), getNameColumn().getValue(r))) {
                  //Submit directly:
                  SERVICES.getService(IAnswerProcessService.class).delete(getAnswerNrColumn().getValue(r));
                  deleteRow(r);
                  reloadStatistics();
                }
              }
            }

            @Order(10.0)
            public class ExportToExcelMenu extends AbstractExportToExcelMenu {

              @Override
              protected String provideTitle() {
                return TEXTS.get("Answers");
              }

              @Override
              protected ITable provideTable() {
                return getTable();
              }
            }
          }
        }
      }
    }

    @Order(40.0)
    public class OkButton extends AbstractOkButton {
    }
  }

  public class DisplayHandler extends AbstractFormHandler {

    @Override
    public void execLoad() throws ProcessingException {
      IAnswersListProcessService service = SERVICES.getService(IAnswersListProcessService.class);
      AnswersListFormData formData = new AnswersListFormData();
      exportFormData(formData);
      formData = service.load(formData);
      importFormData(formData);

      handleMultiple();
      reloadGraph(formData);
    }
  }

  @FormData
  public Boolean getMultipleChoices() {
    return m_multipleChoices;
  }

  private boolean isMultipleChoices() {
    return BooleanUtility.nvl(getMultipleChoices());
  }

  @FormData
  public void setMultipleChoices(Boolean multipleChoices) {
    m_multipleChoices = multipleChoices;
  }
}
