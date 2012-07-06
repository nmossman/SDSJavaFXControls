package com.stddata.fx.uicontrols;



import java.util.Calendar;
import java.util.Locale;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
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
public class DatePickerDialog extends Stage
{
    protected SDSDateChooserPanel dateChooser = null;
    protected Calendar selectedDate = null;
    

    public DatePickerDialog (Stage owner, Locale locale)
    {
        this (owner,null,locale);
    }        
    
    public DatePickerDialog (Stage owner, TextFieldMaskControl control,Locale locale)
    {
        this (owner, Calendar.getInstance(locale),control,locale);

    }        
    public DatePickerDialog (Stage owner, Calendar defaultDate,final TextFieldMaskControl control,Locale locale)
    {        
        super();
        initOwner(owner);
        setIconified(false);
        Modality m =  Modality.WINDOW_MODAL;
        initModality(m);
        setOpacity(1.0);
        
        dateChooser = new SDSDateChooserPanel (locale);
        Scene scene = new Scene(dateChooser, dateChooser.getPrefWidth(), dateChooser.getPrefHeight(), Color.WHITE);
        setScene(scene);
        setResizable(false);

        dateChooser.setDate(defaultDate);  //avoids firing the listener        
        dateChooser.getDateProperty().addListener(new ChangeListener<Calendar>() {

            @Override
            public void changed(ObservableValue<? extends Calendar> ov,
                            Calendar oldDate, Calendar newDate) 
            {
                if (newDate == null)
                    newDate = Calendar.getInstance();

                selectedDate = newDate;
                close();
                if (control !=null)
                {
                    control.setValue(newDate);
                }    
            }
        });
        
        dateChooser.getCloseImmediateProperty().addListener(new ChangeListener<Boolean>() {

            @Override
            public void changed(ObservableValue<? extends Boolean> ov,
                            Boolean oldValue, Boolean newValue) 
            {
                if (newValue)
                    close();

            }
        });        
        

    }

    /**
     * @return the selectedDate
     */
    public Calendar getSelectedDate()
    {
        return selectedDate;
    }

    /**
     * @param selectedDate the selectedDate to set
     */
    public final void setSelectedDate(Calendar selectedDate)
    {
        this.selectedDate = selectedDate;
        dateChooser.setDate(selectedDate);        
    }
    

}
