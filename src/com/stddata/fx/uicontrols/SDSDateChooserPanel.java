package com.stddata.fx.uicontrols;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.*;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

/**
 *  This file is part of SDSJavaFXControls.
 *
 *  SDSJavaFXControls is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  SDSJavaFXControls is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with SDSJavaFXControls.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * Copyright 2012 Standard Data Systems Limited
 * @author nigelm
 */
public class SDSDateChooserPanel  extends BorderPane
{
  private BorderPane titlePanel = new BorderPane();  
  private GridPane dayPanel = new GridPane();
  private GridPane monthYearPanel = new GridPane();
  private HBox todayPanel = new HBox();
  private Button todayButton = new Button();
  private Button lessOneButton = new Button();
  private Label monthYearSpacer = new Label (" ");
  private ChoiceBox monthBox = new ChoiceBox();
  private Label yearLabel = new Label();
  private Button plusOneButton = new Button();
  private Label[][] days = new Label[7][7];  
  private Calendar today;
  private int day = 0;
  private int month =0;
  private int year =0;
  private int selectedLine = 0;
  private int selectedCol = 0;
  private int dayTextWidth =3;
  private ObjectProperty<Calendar> dateProperty;  
  private Locale locale;
  private DateFormatSymbols sym;
  private boolean changeUnderway = false;
  private SimpleBooleanProperty closeImmediateProperty;
  
  public String dayHeaderStyle = "-fx-background-color: grey;";
  public String daySelectedStyle = "-fx-background-color: deepskyblue";
  public String dayNormalStyle = "-fx-background-color: white";
  public String panelStyle="-fx-background-color: white";

  public SDSDateChooserPanel(Locale locale)
  {
  
    int line = 0;
    int col  = 0;

    this.locale = locale;
    today = Calendar.getInstance(locale);    
    closeImmediateProperty = new SimpleBooleanProperty (false);
    dateProperty = new SimpleObjectProperty<>(Calendar.getInstance(locale));    

    /*setMinSize(260,206);
    setMaxSize(260, 206);
    setPrefSize(260, 206);    */
    setPadding(new Insets(7));
    setStyle(panelStyle);
            
    dayPanel.setHgap(8);
    dayPanel.setVgap(5);
    todayButton.setText("");


    todayButton.setOnAction(new EventHandler<ActionEvent>() 
    {
       @Override
       public void handle(ActionEvent ae)
       {
            todayButton_action();
        }
    });
    todayButton.setOnKeyPressed(new EventHandler <KeyEvent> () {

            @Override
            public void handle(KeyEvent arg0)
            {
                if (!arg0.isAltDown() && !arg0.isControlDown() &&!arg0.isMetaDown() &&
                    !arg0.isShiftDown() && !arg0.isShortcutDown() && !changeUnderway &&
                     arg0.getCode()==KeyCode.ENTER)
                {
                    arg0.consume();
                    enterKeyPressed ();
                }    
            }   
    });    

    
    lessOneButton.setText("");
    lessOneButton.setGraphic(SDSDateChooserResources.getPriorImage());
    lessOneButton.setOnAction(new EventHandler<ActionEvent>() 
    {
        @Override
        public void handle(ActionEvent event) {
            lessMonth(1);
        }
    });    
    lessOneButton.setOnKeyPressed(new EventHandler <KeyEvent> () {

            @Override
            public void handle(KeyEvent arg0)
            {
                if (!arg0.isAltDown() && !arg0.isControlDown() &&!arg0.isMetaDown() &&
                    !arg0.isShiftDown() && !arg0.isShortcutDown() && !changeUnderway &&
                     arg0.getCode()==KeyCode.ENTER)
                {
                    arg0.consume();
                    enterKeyPressed ();
                }    
            }   
    });    
    

    plusOneButton.setText("");
    plusOneButton.setGraphic(SDSDateChooserResources.getNextImage());    
    plusOneButton.setOnAction(new EventHandler<ActionEvent>() 
    {
        @Override
        public void handle(ActionEvent event) {
            plusMonth(1);
        }
    });        
    
    plusOneButton.setOnKeyPressed(new EventHandler <KeyEvent> () {

            @Override
            public void handle(KeyEvent arg0)
            {
                if (!arg0.isAltDown() && !arg0.isControlDown() &&!arg0.isMetaDown() &&
                    !arg0.isShiftDown() && !arg0.isShortcutDown() && !changeUnderway &&
                     arg0.getCode()==KeyCode.ENTER)
                {
                    arg0.consume();
                    enterKeyPressed ();
                }    
            }   
    });

    dayPanel.setPadding(new Insets (5,0,2,0));
    setCenter(dayPanel);

    yearLabel.setOnScroll(new EventHandler<ScrollEvent>() {

            @Override
            public void handle(ScrollEvent arg0)
            {
                if (!changeUnderway)
                {    
                    double y = arg0.getDeltaY();
                    arg0.consume();
                    if (y < 0)
                    {
                        lessOneYear ();
                    }   
                    else
                    if (y > 0)    
                    {
                        plusOneYear();
                    }    
                }    
            }
        });
    
    GridPane.setValignment(monthBox, VPos.TOP);
    GridPane.setValignment(monthBox, VPos.CENTER);    
    GridPane.setHalignment(monthBox, HPos.CENTER);    
    GridPane.setHalignment(yearLabel, HPos.CENTER); 
    monthYearPanel.setPadding(new Insets(0));
    monthYearPanel.add(monthBox, 0, 0);
    monthYearPanel.add(monthYearSpacer, 1, 0);    
    monthYearPanel.add(yearLabel, 2, 0);    
    monthYearPanel.setAlignment(Pos.CENTER);
    monthYearPanel.setPadding (new Insets(0,2,0,2));


    titlePanel.setLeft(lessOneButton);
    titlePanel.setCenter(monthYearPanel);
    titlePanel.setRight(plusOneButton);
    setAlignment(monthYearPanel, Pos.CENTER);
    setTop(titlePanel);


    todayPanel.setPadding(new Insets (0));
    todayPanel.getChildren().add(todayButton);
    todayPanel.setAlignment(Pos.CENTER);
    setBottom(todayPanel);
    
    DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM, locale);
    SimpleDateFormat sdf = (SimpleDateFormat)df;
    sym = sdf.getDateFormatSymbols();  
    List <String>months = new ArrayList ();
    ObservableList<String> monthsList =  FXCollections.observableList(months);
    for (line = 0; line < sym.getMonths().length;++line)
    {

        if (!sym.getMonths()[line].trim().isEmpty())
        {
            monthsList.add (sym.getMonths()[line].trim());            
        }    
    }    
    monthBox.setItems(monthsList);
    monthBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2)
            {
                if (!changeUnderway)
                {    
                    //arg2 is the new value
                    if (arg2.intValue() > arg1.intValue())
                        plusMonth (arg2.intValue()-arg1.intValue());
                    else
                    if (arg2.intValue() < arg1.intValue())
                        lessMonth (arg1.intValue()-arg2.intValue());                    
                }    
            }
        });

    
    
    for (line = 0; line < 7; ++ line)
    {
      for (col = 0; col < 7; ++ col)
      {
        days[line][col] = new Label ();
        days[line][col].setText("XX");

        if (line == 0)
        {
          days[line][col].setStyle(dayHeaderStyle);
            
        }
        else
        {
          days[line][col].setOnMouseClicked(new EventHandler<MouseEvent>() 
          {
             @Override
             public void handle(MouseEvent me) 
             {
                 dayLabel_mouseClicked (me);
                 me.consume();
             }
          });
        }
        dayPanel.add(days[line][col],col,line);
        
      }
    }
    
    year = today.get(Calendar.YEAR);
    month= today.get(Calendar.MONTH);
    day  = today.get(Calendar.DAY_OF_MONTH);
   
    
    for (col = 0; col < 7; ++ col)
    {
      String s = " "+sym.getShortWeekdays()[col+1]+" ";
      dayTextWidth = s.length();
      days[0][col].setText(s);
      
    }
    
    setMonthDisplay (month);
    setYearDisplay (year);
    setDayDisplay (true);
    displayToday ();

    todayButton.requestFocus();

    setOnKeyPressed(new EventHandler <KeyEvent> () {

            @Override
            public void handle(KeyEvent arg0)
            {
                if (!arg0.isAltDown() && !arg0.isControlDown() &&!arg0.isMetaDown() &&
                    !arg0.isShortcutDown() && !changeUnderway)
                {
                    if (arg0.isShiftDown())
                    {    
                        if (arg0.getCode()==KeyCode.ADD)
                        {    
                            plusOneYear ();
                            arg0.consume();
                        }    
                        else
                        if (arg0.getCode()==KeyCode.SUBTRACT)    
                        {    
                            lessOneYear ();
                            arg0.consume();
                        }    
                    }
                    else
                    if (arg0.getCode()==KeyCode.ADD && !changeUnderway)
                    {    
                        plusDay (1);
                        arg0.consume();
                    }    
                    else
                    if (arg0.getCode()==KeyCode.SUBTRACT && !changeUnderway)    
                    {    
                        minusDay (1);
                        arg0.consume();
                    }    
                    else
                    if (arg0.getCode()==KeyCode.ESCAPE)    
                    {
                        closeImmediateProperty.set(true);
                        arg0.consume();
                    }    
                    else
                    if (arg0.getCode()==KeyCode.ENTER)    
                    {
                        arg0.consume();
                        enterKeyPressed ();
                    }                        
                }
            }
        });
    
    autosize();
    
  }
  
  private void setMonthDisplay (int m)
  {
    monthBox.getSelectionModel().select(m);
  }
  
  private void setYearDisplay (int y)
  {
    yearLabel.setText(String.format("%1$04d",y));
  }
  
  private void setDayDisplay(boolean fullRefresh)
  {
    int line = 0;
    int col = 0;
    
    if (fullRefresh)
    {
        for (line = 1; line < 7; ++ line)
        {
          for (col = 0; col < 7; col++)
          {
            days[line][col].setText("");
            days[line][col].setStyle(dayNormalStyle);
          }
          
        }
    }
    else
    if (selectedLine > 0)
    {
       days[selectedLine][selectedCol].setStyle(daySelectedStyle);
    }
    Calendar c = Calendar.getInstance(locale);
    c.set(Calendar.DATE, 1);
    c.set(Calendar.MONTH, month + Calendar.JANUARY);
    c.set(Calendar.YEAR, year);

    col = c.get(Calendar.DAY_OF_WEEK) - Calendar.SUNDAY;
    line = 1;
    int lastDay = c.getActualMaximum(Calendar.DATE);
    
    String fmt = "%1$"+Integer.toString(dayTextWidth)+"d";
    
    for (int i = 0; i < lastDay; i++)
    {
      if (col > 6)
      {
          ++ line;
          col = 0;
      }
      days[line][col].setStyle(dayNormalStyle);
      days[line][col].setText(String.format (fmt,i+1));
      if (i+1 == day)
      {
        selectedLine = line;
        selectedCol = col;
        days[line][col].setStyle(daySelectedStyle);
      }
      ++col;
    }

  }  
  
  
  private void displayToday ()
  {
      DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM, locale);
      todayButton.setText(df.format(today.getTime()));
  }

  private void plusMonth(int plus)
  {
    changeUnderway=true;  
    if (plus < 0)
        plus = 1;
    
    month += plus;
    if (month > 11)
    {
      if (year < 2100)
        ++year;
      else
      {
        month =11;
      }
      if (month > 11)
        month = 0;
    }
    
    Calendar c = Calendar.getInstance(locale);
    c.set(year,month,1);
    if (day > c.getActualMaximum(Calendar.DATE))
      day = 1;
      
    setMonthDisplay (month);
    setYearDisplay (year);
    setDayDisplay (true);
    displayToday ();    
    changeUnderway = false;
  }

  private void lessMonth(int less)
  {
    changeUnderway = true;

    if (less < 0)
       less = 1; 
    month -= less;
    if (month < 0)
    {
      if (year > 1900)
        --year;
      else
      {
        month=11;
      }
      if (month < 0)
        month = 11;
    }
    
    Calendar c = Calendar.getInstance(locale);
    c.set(year,month,1);
    if (day > c.getActualMaximum(Calendar.DATE))
      day = c.getActualMaximum(Calendar.DATE);
      
    setMonthDisplay (month);
    setYearDisplay (year);
    setDayDisplay (true);
    displayToday ();    
    changeUnderway = false;  
  }

  private void plusOneYear()
  {
    changeUnderway=true;  
    if (year < 2100)
        ++ year;
    

    setMonthDisplay (month);
    setYearDisplay (year);
    setDayDisplay (true);
    displayToday ();    
    changeUnderway = false;
  }

  private void lessOneYear()
  {
    changeUnderway = true;

    if (year > 1900)
      --year;
    
      
    setMonthDisplay (month);
    setYearDisplay (year);
    setDayDisplay (true);
    displayToday ();    
    changeUnderway = false;  
  }  
  
  private void plusDay (int plus)
  {
     if (plus < 1)  
         plus = 1;
    
     Calendar c = Calendar.getInstance(locale);
    c.set(year,month,day);
    
    c.add(Calendar.DAY_OF_YEAR, plus);
    month = c.get(Calendar.MONTH);
    year = c.get(Calendar.YEAR);
    day = c.get(Calendar.DAY_OF_MONTH);

    changeUnderway=true;
    setYearDisplay(year);
    setMonthDisplay(month);
    setDayDisplay(true);
    changeUnderway=false;     
  }        
  
  private void minusDay (int minus)
  {
    if (minus < 1)  
         minus = 1;
    
    minus = minus * -1;
    Calendar c = Calendar.getInstance(locale);
    c.set(year,month,day);
    
    c.add(Calendar.DAY_OF_YEAR, minus);
    month = c.get(Calendar.MONTH);
    year = c.get(Calendar.YEAR);
    day = c.get(Calendar.DAY_OF_MONTH);
    changeUnderway=true;    
    setYearDisplay(year);
    setMonthDisplay(month);
    setDayDisplay(true);
    changeUnderway=false;     
  }          
  
  private void todayButton_action()
  {
    //todayButton
    year = today.get(Calendar.YEAR);
    month= today.get(Calendar.MONTH);
    day  = today.get(Calendar.DAY_OF_MONTH);    
    setMonthDisplay (month);
    setYearDisplay (year);
    setDayDisplay (true);
    displayToday ();     
    
    Calendar c = Calendar.getInstance(locale);
    c.set(year, month, day, 0,0,0);
    dateProperty.set(c);    
    
  }
  
  private void dayLabel_mouseClicked(MouseEvent e)
  {
    //user clicked on a day label;
    if (e.getSource() instanceof Label)
    {
      Label l = (Label)e.getSource();
      if (l.getText().trim().length() > 0)
      {
        day = Integer.parseInt(l.getText().trim());
        setDayDisplay(false);
      }
      Calendar c = Calendar.getInstance(locale);
      c.set(year, month, day, 0,0,0);
      dateProperty.set(c);

    }
  }  
  
  private void enterKeyPressed ()
  {
      Calendar c = Calendar.getInstance(locale);
      c.set(year, month, day, 0,0,0);
      dateProperty.set(c);      
  }        
  
  
  public void setDate (java.util.Date newDate)
  {
    Calendar c = Calendar.getInstance(locale);
    c.setTime(newDate);
    setDate (c);
  }
  
  public void setDate (Calendar newDate)
  {
    changeUnderway = true;
    year = newDate.get(Calendar.YEAR);
    month= newDate.get(Calendar.MONTH);
    day  = newDate.get(Calendar.DAY_OF_MONTH);    
    setMonthDisplay (month);
    setYearDisplay (year);
    setDayDisplay (true);
    dateProperty.setValue(newDate);
    changeUnderway = false;
  }

  
  /**
   * @return DateProperty to bind to parent node 
   */
   public ObjectProperty<Calendar> getDateProperty()
   {
      return dateProperty;
   } 
   
   public SimpleBooleanProperty getCloseImmediateProperty ()
   {
       return closeImmediateProperty;
   }        
  
}

