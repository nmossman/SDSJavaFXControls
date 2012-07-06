package com.stddata.fx.uicontrols;


import java.util.Calendar;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.Window;


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

public class TextFieldMask extends HBox
{
    protected TextFieldMaskControl control = null;
    protected Button lookupButton = null;
    protected Popup popup;
    
    public TextFieldMask ()
    {
        this (new EditMaskSpecification ());
    }        
    
    public TextFieldMask (EditMaskSpecification specification)
    {
        super ();
        control = new TextFieldMaskControl (specification);
        getChildren().add(control);
        if (specification.getMaskStyle()==EditMaskFactory.STYLE_DATE_TIME ||
            specification.getMaskStyle()==EditMaskFactory.STYLE_DATE)
        {    
            setLookupCallback(getDateTimePopupCallback());
        }
        
        if (control.isLookupEnabled())
            setLookupButton ();
    }    
    
    protected final void setLookupButton ()
    {
        if (lookupButton == null)
        {
            lookupButton = new Button ("...");
            lookupButton.setOnAction(new EventHandler<ActionEvent>()
            {

                @Override
                public void handle(ActionEvent event) 
                {
                    control.getLookupCallback().call(control,control.getText(),false);

                }
            });
            getChildren().add(lookupButton);

        }   
        
        
        if (control.isLookupEnabled())
            lookupButton.disableProperty().set(false);
        else
           lookupButton.disableProperty().set(true); 
    }        
    
    public void setValue (Object value)
    {
        control.setValue(value);
    }   
    
    public Object getValue ()
    {
        return control.getValue();
    }        
    
    @Override
    public void requestFocus ()
    {
        control.requestFocus();
    }        
    
    public final void setLookupCallback(TextFieldMaskLookupCallBack arg0 )
    {
        control.setLookupCallback(arg0);
    }         
    
    public TextFieldMaskLookupCallBack getLookupCallback ()
    {
        return control.getLookupCallback();
    }        
  
    /**
     * @return the lookupEnabled
     */
    public boolean isLookupEnabled()
    {
        return control.isLookupEnabled();
    }

    /**
     * @param lookupEnabled the lookupEnabled to set
     */
    public void setLookupEnabled(boolean lookupEnabled)
    {
        control.setLookupEnabled(lookupEnabled);
        if (lookupButton != null)
        {    
            if (lookupEnabled) 
                lookupButton.disableProperty().set(false);
            else
                lookupButton.disableProperty().set(true);
        } 
        else
        if (lookupEnabled)    
        {
            setLookupButton();
        }    
    }

    public final TextFieldMaskLookupCallBack getDateTimePopupCallback ()
    {
        return  new TextFieldMaskLookupCallBack () 
        {

            @Override
            public void call(TextFieldMaskControl control, String editText, boolean focusCall)
            {
                Stage s = null;
                Scene sc = getParent ().getScene();
                Window w = sc.getWindow();
                if (w instanceof Stage)
                {
                    s = (Stage)w;
                }
                
                if (s!= null)
                {
                    Calendar c = Calendar.getInstance();

                    c = EditMaskValueFactory.getDateTime(editText, control.getSpecification(), false);  
                    if (c==null)
                        c = EditMaskValueFactory.getDateTime(editText, control.getSpecification(), true);  
                    if (c==null)
                        c = Calendar.getInstance();
                    
                    DatePickerDialog dpd = new DatePickerDialog (s,c,control,control.getSpecification().getLocale());
                    Point2D point = lookupButton.localToScene(0, 0);
                    Parent p = getParent();
                    final double layoutX = p.getScene().getWindow().getX() + p.getScene().getX() + point.getX();
                    final double layoutY = p.getScene().getWindow().getY() + p.getScene().getY() + point.getY();
                    dpd.setX(layoutX);
                    dpd.setY(layoutY);

                    dpd.show();
                    
                }    
                    
	    }
	
        };        
        
        
    }    
    
    @Override
    public void setUserData (Object o)
    {
        super.setUserData(o);
        control.setUserData(o);
    }        
    
    public Point2D getLookupWindowXY ()
    {
        Point2D point = lookupButton.localToScene(0, 0);
        Parent p = getParent();
        final double layoutX = p.getScene().getWindow().getX() + p.getScene().getX() + point.getX();
        final double layoutY = p.getScene().getWindow().getY() + p.getScene().getY() + point.getY();
        
        Point2D result = new Point2D (layoutX,layoutY);
        return result;
    }        
    
}
