package com.stddata.fx.uicontrols;

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

public class TextFieldMaskContent
{
    protected StringBuilder data;
    protected EditMaskSpecification specification;


    public TextFieldMaskContent (EditMaskSpecification specification)
    {
        this.specification = specification;
        data = EditMaskHelper.makeEmptyField(specification.getEditMask());
    }   
    
    public StringBuilder getEditMask ()
    {
        return specification.getEditMask();
    } 
    
    

    /**
        * makeEmptyField creates an empty data string with only the literals
        * shown
        */

    public void makeEmptyField ()
    {
        data = EditMaskHelper.makeEmptyField(specification.getEditMask());
    }        

    
    public String get(int arg0, int arg1)
    {
        return data.substring(arg0, arg1);
    }

    


    public String get()
    {
        return data.toString();
    }    
    
    /****
     * This method sets text and overrides the input mask checking
     * It has been added to allow easy of switching date/time values
     * between input and display masks
     * @param data 
     */
    
    public void set(String data)
    {
        this.data = new StringBuilder (data);
    }        
    /****
     * This method sets text and overrides the input mask checking
     * It has been added to allow easy of switching date/time values
     * between input and display masks
     * @param data 
     */
    public void set (StringBuilder data)
    {
        this.data = data;
    }        
  
}
