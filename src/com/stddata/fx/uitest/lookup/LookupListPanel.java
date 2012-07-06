package com.stddata.fx.uitest.lookup;

import java.util.ArrayList;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToolBar;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

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
public class LookupListPanel extends VBox
{
    MasterListTableView mltv;
    private ObjectProperty<String> selectedValueProperty;  
    private SimpleBooleanProperty closeImmediateProperty;    
    
    public  LookupListPanel ()
    {        
        super();
        selectedValueProperty = new SimpleObjectProperty ("");
        closeImmediateProperty = new SimpleBooleanProperty (false);
        mltv = new MasterListTableView ();
        mltv.setMaxWidth(800);
        mltv.setPrefWidth(400);
        setOpacity(1);

        getChildren().add(mltv);
        
        ToolBar tb = new ToolBar ();   
        Button selectButton = new Button ("Select");
        selectButton.setUserData("SelectBtn");
        selectButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                selectButton_actionPerformed(event);
            }
        });
   
        
        Button closeButton= new Button ("Close");
        closeButton.setUserData("CloseBtn");
        closeButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                closeButton_actionPerformed(event);
            }
        });    
        
       tb.getItems().addAll(selectButton,closeButton);
                   
        
       getChildren().add (tb);
    }
    
    protected void selectButton_actionPerformed (ActionEvent e)
    {
        Object o = mltv.getSelectionModel().getSelectedItem();
        if (o != null && o instanceof FXResultObject)
        {
            FXResultObject fxro = (FXResultObject)o;
            selectedValueProperty.set(fxro.getField0()); 
        }    
        
    }      
    
    protected void closeButton_actionPerformed (ActionEvent e)
    {
        closeImmediateProperty.set(true);
    }          
    
   /**
   * @return DateProperty to bind to parent node 
   */
   public ObjectProperty<String> getSelectedValueProperty()
   {
      return selectedValueProperty;
   } 
   
   public SimpleBooleanProperty getCloseImmediateProperty ()
   {
       return closeImmediateProperty;
   }            
    
    public class MasterListTableView extends TableView 
    {
        String [][] pm = new String [][] {
            {"Clement Attlee","1945–1951","Labour"},
            {"Margaret Thatcher","1979–1990","Conservative"},
            {"Tony Blair","1997–2007","Labour"},
            {"Harold Macmillan","1957–1963","Conservative"},
            {"Harold Wilson","1964–1970, 1974–1976","Labour"},
            {"Winston Churchill","1940–1945, 1951–1955","Conservative"},
            {"James Callaghan","1976–1979","Labour"},
            {"John Major","1990–1997","Conservative"},
            {"Edward Heath","1970–1974","Conservative"},
            {"Gordon Brown","2007–2010","Labour"},
            {"Alec Douglas-Home","1963–1964","Conservative"},
            {"Anthony Eden","1955–1957","Conservative"}};
        String [] colTitles = new String []{"Prime Minister","In Office","Party"};
                
        ObservableList<FXResultObject> data = FXCollections.observableArrayList();

        
        public MasterListTableView ()
        {
            super ();


            ArrayList <TableColumn> tc = new ArrayList <> ();

            int c = 0;
            for (c=0; c < colTitles.length; ++ c)
            {
                TableColumn tcc = new TableColumn ();
                tcc.setResizable(true);
                tcc.setText(colTitles[c]);
                tcc.setEditable(false);
                String pn = "field"+Integer.toString(c);
                tcc.setCellValueFactory(new PropertyValueFactory(pn));
                tc.add(tcc);
            }   

            for (c =0; c < pm.length; ++ c)
            {
                data.add (new FXResultObject (pm[c]));
            }    
            
            setItems(data);
            getColumns().setAll(tc);
            setUserData ("LookupTable");
        } 
    }
    
    public class FXResultObject
    {
        String [] data;

        public FXResultObject (String [] data)
        {
            this.data = data;
        }        
        
        public String getField0 ()
        {
            return data[0];
        }         
        
        public String getField1 ()
        {
            return data[1];
        }                 
        
        public String getField2 ()
        {
            return data[2];
        }                 
    }
}
