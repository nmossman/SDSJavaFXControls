package com.stddata.fx.uitest.lookup;



import com.stddata.fx.uicontrols.SDSDateChooserPanel;
import com.stddata.fx.uicontrols.TextFieldMaskControl;
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
public class LookupListDialog extends Stage
{
    protected LookupListPanel lookupListPanel = null;
    protected String selectedValue = null;
    


    public LookupListDialog (Stage owner, String defaultValue,final TextFieldMaskControl control)
    {        
        super();
        initOwner(owner);
        setIconified(false);
        Modality m =  Modality.WINDOW_MODAL;
        initModality(m);
        setOpacity(1.0);
        
        lookupListPanel = new LookupListPanel ();
        Scene scene = new Scene(lookupListPanel, lookupListPanel.getPrefWidth(), lookupListPanel.getPrefHeight(), Color.WHITE);
        setScene(scene);
        setResizable(true);


        lookupListPanel.getSelectedValueProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> ov,
                            String oldValue, String newValue) 
            {
                if (newValue == null)
                    newValue = "";

                selectedValue = newValue;
                close();
                if (control !=null)
                {
                    control.clear();
                    control.setValue(newValue);
                }    
            }
        });
        
        lookupListPanel.getCloseImmediateProperty().addListener(new ChangeListener<Boolean>() {

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
     * @return the selectedValue
     */
    public String getSelectedValue()
    {
        return selectedValue;
    }

    /**
     * @param selectedValue the selectedValue to set
     */
    public final void setSelectedValue(String selectedValue)
    {
        this.selectedValue = selectedValue;
    }
    

}
