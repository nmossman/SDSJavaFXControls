package com.stddata.fx.uicontrols;

import java.text.DateFormat;
import java.util.Locale;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;

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
public class EditMaskFactoryTest
{
    
    public EditMaskFactoryTest()
    {
    }

    @BeforeClass
    public static void setUpClass() throws Exception
    {
    }

    @AfterClass
    public static void tearDownClass() throws Exception
    {
    }
    
    @Before
    public void setUp()
    {
    }

    @Test
    public void testGetDocumentFilterGLAccount()
    {
    }

    @Test
    public void testGetDocumentFilterNumber()
    {
    }

    @Test
    public void testGetDocumentFilter_int()
    {
    }

    @Test
    public void testGetDocumentFilter_int_int()
    {
    }

    @Test
    public void testGetDocumentFilter_3args()
    {
    }

    @Test
    public void testGetFieldSizeBasedOnMask()
    {
    }
    
    @Test
    public void testGetDateTimeMask_UK ()
    {
        Locale l = Locale.UK;
        EditMaskSpecification ems = new EditMaskSpecification (l,EditMaskFactory.STYLE_DATE);
        ems.setForceFourDigitYears(true);
        
        String exp = "DD"+EditMaskFactory.ESCAPE+"/DD"+EditMaskFactory.ESCAPE+"/DDDD";
        try
        {    
            EditMaskFactory.getDateTimeMask(ems);
            assertEquals (exp,ems.getEditMask().toString());
            assertEquals (0,ems.getDayStart());
            assertEquals (3,ems.getMonthStart());
            assertEquals (6,ems.getYearStart());
            assertEquals (0,ems.getHourStart()+ems.getMinuteStart()+ems.getSecondStart());
            
            ems.setMaskStyle(EditMaskFactory.STYLE_DATE_TIME);
            exp = exp+EditMaskFactory.ESCAPE+" DD"+EditMaskFactory.ESCAPE+":DD"+EditMaskFactory.ESCAPE+":DD";
            
            EditMaskFactory.getDateTimeMask(ems);
            assertEquals (exp,ems.getEditMask().toString());
            assertEquals (0,ems.getDayStart());
            assertEquals (3,ems.getMonthStart());
            assertEquals (6,ems.getYearStart());
            assertEquals (11,ems.getHourStart());
            assertEquals (14,ems.getMinuteStart());
            assertEquals (17,ems.getSecondStart());     
            
            ems.setDateInput(DateFormat.MEDIUM);
            exp="DD"+EditMaskFactory.ESCAPE+"-ULL"+EditMaskFactory.ESCAPE+"-DDDD"+
                    EditMaskFactory.ESCAPE+" DD"+EditMaskFactory.ESCAPE+":DD"+
                    EditMaskFactory.ESCAPE+":DD";
            EditMaskFactory.getDateTimeMask(ems);
            assertEquals (exp,ems.getEditMask().toString());
            assertEquals (0,ems.getDayStart());
            assertEquals (3,ems.getMonthStart());
            assertEquals (7,ems.getYearStart());
            assertEquals (12,ems.getHourStart());
            assertEquals (15,ems.getMinuteStart());
            assertEquals (18,ems.getSecondStart());                         
            
            
            
        }
        catch (Exception x)
        {
            assertEquals ("",x.toString());
        }    
        
        
    }  
    
    //English (United States)Short: 4/25/12 Medium: Jan 25, 2012 Pattern: MMM dd, yyyy Dec
    @Test
    public void testGetDateTimeMask_USA ()
    {
        Locale l = Locale.US;
        EditMaskSpecification ems = new EditMaskSpecification (l,EditMaskFactory.STYLE_DATE);
        ems.setForceFourDigitYears(true);
        
        String exp = "DD"+EditMaskFactory.ESCAPE+"/DD"+EditMaskFactory.ESCAPE+"/DDDD";
        try
        {    
            EditMaskFactory.getDateTimeMask(ems);
            assertEquals (exp,ems.getEditMask().toString());
            assertEquals (3,ems.getDayStart());
            assertEquals (0,ems.getMonthStart());
            assertEquals (6,ems.getYearStart());
            assertEquals (0,ems.getHourStart()+ems.getMinuteStart()+ems.getSecondStart());
            
            ems.setMaskStyle(EditMaskFactory.STYLE_DATE_TIME);
            exp = exp+EditMaskFactory.ESCAPE+" DD"+EditMaskFactory.ESCAPE+":DD"+EditMaskFactory.ESCAPE+":DD";
            
            EditMaskFactory.getDateTimeMask(ems);
            assertEquals (exp,ems.getEditMask().toString());
            assertEquals (3,ems.getDayStart());
            assertEquals (0,ems.getMonthStart());
            assertEquals (6,ems.getYearStart());
            assertEquals (11,ems.getHourStart());
            assertEquals (14,ems.getMinuteStart());
            assertEquals (17,ems.getSecondStart());     
            
            ems.setDateInput(DateFormat.MEDIUM);
            exp="ULL"+EditMaskFactory.ESCAPE+" DD"+EditMaskFactory.ESCAPE+","+EditMaskFactory.ESCAPE+" DDDD"+
                    EditMaskFactory.ESCAPE+" DD"+EditMaskFactory.ESCAPE+":DD"+
                    EditMaskFactory.ESCAPE+":DD";
            EditMaskFactory.getDateTimeMask(ems);
            assertEquals (exp,ems.getEditMask().toString());
            assertEquals (4,ems.getDayStart());
            assertEquals (0,ems.getMonthStart());
            assertEquals (8,ems.getYearStart());
            assertEquals (13,ems.getHourStart());
            assertEquals (16,ems.getMinuteStart());
            assertEquals (19,ems.getSecondStart());                         
            
            
            
        }
        catch (Exception x)
        {
            assertEquals ("",x.toString());
        }    
        
        
    }   
    
    //Irish (Ireland)Short: 25/04/2012 Medium: 25 Feabh 2012 Pattern: dd MMM yyyy Noll
    @Test
    public void testGetDateTimeMask_IE ()
    {
        Locale l = new Locale ("GA", "IE");
        EditMaskSpecification ems = new EditMaskSpecification (l,EditMaskFactory.STYLE_DATE);
        ems.setForceFourDigitYears(true);
        
        String exp = "DD"+EditMaskFactory.ESCAPE+"/DD"+EditMaskFactory.ESCAPE+"/DDDD";
        try
        {    
            EditMaskFactory.getDateTimeMask(ems);
            assertEquals (exp,ems.getEditMask().toString());
            assertEquals (0,ems.getDayStart());
            assertEquals (3,ems.getMonthStart());
            assertEquals (6,ems.getYearStart());
            assertEquals (0,ems.getHourStart()+ems.getMinuteStart()+ems.getSecondStart());
            
            ems.setMaskStyle(EditMaskFactory.STYLE_DATE_TIME);
            exp = exp+EditMaskFactory.ESCAPE+" DD"+EditMaskFactory.ESCAPE+":DD"+EditMaskFactory.ESCAPE+":DD";
            
            EditMaskFactory.getDateTimeMask(ems);
            assertEquals (exp,ems.getEditMask().toString());
            assertEquals (0,ems.getDayStart());
            assertEquals (3,ems.getMonthStart());
            assertEquals (6,ems.getYearStart());
            assertEquals (11,ems.getHourStart());
            assertEquals (14,ems.getMinuteStart());
            assertEquals (17,ems.getSecondStart());     
            
            ems.setDateInput(DateFormat.MEDIUM);
            exp="DD"+EditMaskFactory.ESCAPE+" ULLLL"+EditMaskFactory.ESCAPE+" DDDD"+
                    EditMaskFactory.ESCAPE+" DD"+EditMaskFactory.ESCAPE+":DD"+
                    EditMaskFactory.ESCAPE+":DD";
            EditMaskFactory.getDateTimeMask(ems);
            assertEquals (exp,ems.getEditMask().toString());
            assertEquals (0,ems.getDayStart());
            assertEquals (3,ems.getMonthStart());
            assertEquals (9,ems.getYearStart());
            assertEquals (14,ems.getHourStart());
            assertEquals (17,ems.getMinuteStart());
            assertEquals (20,ems.getSecondStart());                         
            
            
            
        }
        catch (Exception x)
        {
            assertEquals ("",x.toString());
        }    
        
        
    }       
}
