package com.stddata.fx.uitest;


import com.stddata.fx.uicontrols.EditMaskSpecification;
import com.stddata.fx.uicontrols.EditMaskFactory;
import com.stddata.fx.uicontrols.TextFieldMaskLookupCallBack;
import com.stddata.fx.uicontrols.TextFieldMaskControl;
import com.stddata.fx.uicontrols.TextFieldMask;
import com.stddata.fx.uitest.lookup.LookupListDialog;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Locale;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

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

public class UiControlDemoStage extends Stage
{
    protected Group root;
    protected GridPane gridpane;
    public UiControlDemoStage (final Stage owner)
    {         
        super();
        initOwner(owner);
        Modality m =  Modality.NONE;
        initModality(m);
        setOpacity(.90);
        setTitle("SDS FX UI Control Demo");
        root = new Group();
        root.setFocusTraversable(false);
        root.setAutoSizeChildren(true);

        Scene scene = new Scene(root, Color.WHITE);// 350, 180, Color.WHITE);
        setScene(scene);

        gridpane = new GridPane();
        gridpane.setPadding(new Insets(10));
        gridpane.setHgap(5);
        gridpane.setVgap(5);
        gridpane.setFocusTraversable(false);
   
        
        TextField normal = new TextField ();
        normal.setUserData("Normal");
        
        Label labelBasic = new Label ();
        TextFieldMask basic = new TextFieldMask ();
        basic.setUserData("Basic");
        
        Label labelGLAccount = new Label ();
        EditMaskSpecification gls = new EditMaskSpecification (
                            EditMaskFactory.ALPHA_NUMERIC_FORCEUPPERCASE,"-",new int []{3, 3,2});
        EditMaskSpecification moneys = new EditMaskSpecification (18,2,".",",",EditMaskFactory.NEG_FMT_BRACKETS);    
        EditMaskSpecification dates = new EditMaskSpecification (Locale.UK,EditMaskFactory.STYLE_DATE);
        EditMaskSpecification frdate = new EditMaskSpecification (Locale.FRANCE, EditMaskFactory.STYLE_DATE);
        EditMaskSpecification times = new EditMaskSpecification (Locale.FRANCE, EditMaskFactory.STYLE_TIME);        
        EditMaskSpecification lookups = new EditMaskSpecification (50,EditMaskFactory.ALPHA_NUMERIC_ANYCASE);
        
        try
        {    
            EditMaskFactory.getMask(gls);
            EditMaskFactory.getMask(moneys);
            EditMaskFactory.getDateTimeMask(dates);
            EditMaskFactory.getDateTimeMask(frdate);
            EditMaskFactory.getDateTimeMask(times);
            EditMaskFactory.getMask(lookups);
        }
        catch (Exception x)
        {
            
        }    
        TextFieldMask GLCode = new TextFieldMask (gls); 
        GLCode.setUserData("GL Code");

        TextFieldMask money = new TextFieldMask (moneys);
        money.setUserData("Money");
        money.setValue(new BigDecimal (66443322.67));
        

        TextFieldMask maskDate = new TextFieldMask (dates);
        maskDate.setUserData("Mask Date");
        Calendar c = Calendar.getInstance();
        c.set(2010, 2, 13, 7, 3, 28);
        maskDate.setValue(c);
        
        TextFieldMask maskFRDate = new TextFieldMask (frdate);
        maskFRDate.setUserData("FR Mask Date");
        maskFRDate.setValue(c);        
        
        TextFieldMask maskTime = new TextFieldMask (times);
        maskTime.setUserData("Mask Time");
        Calendar t = Calendar.getInstance(times.getLocale());
        t.set(0, 0, 0, 19, 23, 59);
        maskTime.setValue(t);                
        
        TextFieldMask maskLookup = new TextFieldMask (lookups);
        maskLookup.setUserData("MaskLookup");
        maskLookup.setLookupCallback(new TextFieldMaskLookupCallBack (){

            @Override
            public void call(TextFieldMaskControl control, String editText, boolean focusCall)
            {
                LookupListDialog luld = new LookupListDialog (owner,editText,control);
                TextFieldMask tfm = control.getParentTextFieldMask ();
                Point2D xy = tfm.getLookupWindowXY();
                luld.setX(xy.getX());
                luld.setY(xy.getY());
                luld.show ();             
            }
        } );
        maskLookup.setLookupEnabled(true);
        
        Label labelValue = new Label ();
        Label labelDate = new Label ();
        Label labelFRDate = new Label ();
        Label labelNormal = new Label ();
        Label labelTime = new Label ();
        Label labelLookup = new Label ();
        
        labelBasic.setText("Basic Field");
        labelGLAccount.setText("GL Account Field");
        labelValue.setText("GBP Value");
        labelDate.setText("Date"); 
        labelFRDate.setText ("FR Date");
        labelNormal.setText ("Std. Textfield");
        labelTime.setText ("Time");
        labelLookup.setText("PM Lookup");
        
        Button OK = new Button("OK");
        OK.setUserData("OKBtn");
        OK.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                close();
            }
        });
        GridPane.setHalignment(labelBasic, HPos.LEFT);
        GridPane.setHalignment(labelGLAccount, HPos.LEFT);
        GridPane.setHalignment(labelValue, HPos.LEFT);
        GridPane.setHalignment(labelDate, HPos.LEFT);        
        GridPane.setHalignment(OK, HPos.LEFT);                
        
        gridpane.add(OK, 1, 9);
        gridpane.add(labelBasic, 0, 1);
        gridpane.add(basic, 1,1);
        gridpane.add(labelGLAccount, 0, 2);
        gridpane.add(GLCode,1,2);
        gridpane.add(labelValue,0, 3);
        gridpane.add(money,1,3);
        gridpane.add(labelDate, 0, 4);
        gridpane.add(maskDate,1,4);
        gridpane.add(labelFRDate, 0, 5);
        gridpane.add(maskFRDate,1,5);   
        gridpane.add(labelTime, 0, 6);
        gridpane.add(maskTime,1,6);           
        gridpane.add(labelLookup,0,7);
        gridpane.add(maskLookup,1,7);        
        gridpane.add(labelNormal,0,8);
        gridpane.add(normal,1,8);
        
        
        root.getChildren().add(gridpane);
        
        TextFieldMaskLookupCallBack cb = new TextFieldMaskLookupCallBack () {

            @Override
            public void call(TextFieldMaskControl control, String editText,boolean focusCall)
            {

            }
        };

        
        
        
        GLCode.setLookupCallback(cb);
        TableView tv = new TableView ();


        gridpane.autosize();
        root.autosize();
    }    
}
