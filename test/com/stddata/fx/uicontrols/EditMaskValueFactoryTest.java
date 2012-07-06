package com.stddata.fx.uicontrols;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Locale;
import org.junit.AfterClass;
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
public class EditMaskValueFactoryTest
{
    protected static EditMaskSpecification moneys;
    protected static EditMaskSpecification ints;
    protected static EditMaskSpecification dateTimes;
    protected static EditMaskSpecification dateTime_USs;    
    protected static EditMaskSpecification dates;
    protected static EditMaskSpecification date_USs;     
    protected static EditMaskSpecification times;
    public EditMaskValueFactoryTest()
    {
    }

    @BeforeClass
    public static void setUpClass() throws Exception
    {
       moneys = new EditMaskSpecification (18,2,".",",",EditMaskFactory.NEG_FMT_BRACKETS);   
       dateTimes = new EditMaskSpecification(Locale.UK,EditMaskFactory.STYLE_DATE_TIME);
       dateTimes.setDateInput(DateFormat.SHORT);
       dateTime_USs = new EditMaskSpecification(Locale.US,EditMaskFactory.STYLE_DATE_TIME);
       dateTime_USs.setDateInput(DateFormat.SHORT);       
       dates = new EditMaskSpecification(Locale.UK,EditMaskFactory.STYLE_DATE);
       EditMaskFactory.getMask(dates);
       dates.setDateInput(DateFormat.SHORT);
       date_USs = new EditMaskSpecification(Locale.US,EditMaskFactory.STYLE_DATE);
       date_USs.setDateInput(DateFormat.SHORT);              
       ints = new EditMaskSpecification (10,EditMaskFactory.D_DIGIT);
       EditMaskFactory.getMask(ints);       
       times = new EditMaskSpecification (Locale.UK,EditMaskFactory.STYLE_TIME);
       EditMaskFactory.getMask(times);
       
    }

    @AfterClass
    public static void tearDownClass() throws Exception
    {
    }

    @Test
    public void testFormatToSpecification()
    {
        BigDecimal bd = new BigDecimal (1234567.66);
        String result = EditMaskValueFactory.formatToSpecification(bd, moneys,false);
        String exp = "+           1234567.66";
        assertEquals (exp,result);
        
        bd = bd.multiply(new BigDecimal (-1));
        result = EditMaskValueFactory.formatToSpecification(bd, moneys,false);
        exp = "-           1234567.66";
        assertEquals (exp,result);    
        
        
        bd = BigDecimal.ZERO;
        result = EditMaskValueFactory.formatToSpecification(bd, moneys,false);
        exp = "+                 0.00";
        assertEquals (exp,result);            
        
        result = EditMaskValueFactory.formatToSpecification(32678, ints,false);
        exp = "+     32678";
        assertEquals (exp,result);            
        
        Calendar c = Calendar.getInstance();
        c.set(2012, 0, 28, 13, 12, 59);
        //nglish (United Kingdom)Short: 28/04/12 Medium: 28-Jan-2012 Pattern: dd-MMM-yyyy
        result = EditMaskValueFactory.formatToSpecification(c, dateTimes,true);
        exp = "28/01/2012 13:12:59";
        assertEquals (exp,result);
        
        result = EditMaskValueFactory.formatToSpecification(c, dateTime_USs,true);
        exp = "01/28/2012 13:12:59";
        assertEquals (exp,result);        
        
        result = EditMaskValueFactory.formatToSpecification(c, dates,true);
        exp = "28/01/2012";
        assertEquals (exp,result);
        
        result = EditMaskValueFactory.formatToSpecification(c, date_USs,true);
        exp = "01/28/2012";
        assertEquals (exp,result);    
        
        c.set(2012, 2, 13, 7, 3, 28);
        result = EditMaskValueFactory.formatToSpecification(c, dates,true);
        exp = "13/03/2012";
        assertEquals (exp,result);
        
        result = EditMaskValueFactory.formatToSpecification(c, date_USs,true);
        exp = "03/13/2012";
        assertEquals (exp,result);            

    }
    
    @Test
    public void testParseValue ()
    {
        BigDecimal exp = new BigDecimal ("1234567.66");
        exp.setScale(2);
        
        String ti = "          1,234,567.66";
        BigDecimal result = (BigDecimal)EditMaskValueFactory.parseValue(ti, moneys);
        assertEquals (exp.toPlainString(),result.toPlainString());     
        
        exp= new BigDecimal ("-4567854.89");
        exp.setScale(2);
        ti = "(         4,567,854.89)";
        result = (BigDecimal)EditMaskValueFactory.parseValue(ti, moneys);
        assertEquals (exp.toPlainString(),result.toPlainString());             
        
        Integer expi  = new Integer (23467);
        ti = "23,467";
        result = (BigDecimal)EditMaskValueFactory.parseValue(ti, ints);
        assertEquals (expi.toString(),Integer.toString(result.intValue()));                     
        
        expi  = new Integer (7);
        ti = "         7";
        result = (BigDecimal)EditMaskValueFactory.parseValue(ti, ints);
        assertEquals (expi.toString(),Integer.toString(result.intValue()));                             

        Calendar expc = Calendar.getInstance();
        expc.set(Calendar.MILLISECOND,0);
        expc.set(2012, 0, 28, 13, 12, 59);
        ti = "28/01/2012 13:12:59";        
        //English (United Kingdom)Short: 28/04/12 Medium: 28-Jan-2012 Pattern: dd-MMM-yyyy
        Calendar resultc = (Calendar)EditMaskValueFactory.parseValue(ti, dateTimes);
        assertEquals (expc.getTimeInMillis(),resultc.getTimeInMillis());
        
        ti = "01/28/2012 13:12:59";
        resultc = (Calendar)EditMaskValueFactory.parseValue(ti, dateTime_USs);
        assertEquals (expc.getTimeInMillis(),resultc.getTimeInMillis());    
        
        expc.set(2012, 2, 13, 0, 0, 0);
        expc.set(Calendar.MILLISECOND,0);
        ti= "13/03/2012";        
        resultc = (Calendar)EditMaskValueFactory.parseValue(ti, dates);
        assertEquals (expc.getTimeInMillis(),resultc.getTimeInMillis());
        
        ti = "03/13/2012";
        resultc = (Calendar)EditMaskValueFactory.parseValue(ti, date_USs);
        assertEquals (expc.getTimeInMillis(),resultc.getTimeInMillis());    
        
        ti = " 3/13/2012";
        resultc = (Calendar)EditMaskValueFactory.parseValue(ti, date_USs);
        assertEquals (expc.getTimeInMillis(),resultc.getTimeInMillis());            
        
        expc.set (1970, 0, 1, 7, 3, 28);
        ti = "07:03:28";
        resultc = (Calendar)EditMaskValueFactory.parseValue(ti, times);
        assertEquals (expc.get(Calendar.HOUR_OF_DAY),resultc.get(Calendar.HOUR_OF_DAY));
        assertEquals (expc.get(Calendar.MINUTE),resultc.get(Calendar.MINUTE));
        assertEquals (expc.get(Calendar.SECOND),resultc.get(Calendar.SECOND));        
        
    }     
    
    @Test
    public void testTimeSpinDateTimeFieldSection ()
    {
        StringBuilder text = new StringBuilder ("18:23:23");
        StringBuilder exp = new StringBuilder ("19:23:23");
        EditMaskValueFactory.spinDateTimeFieldSection(text, 1, times, true);
        assertEquals (exp.toString(),text.toString());
        
        text = new StringBuilder ("18:23:23");        
        exp = new StringBuilder ("18:22:23");
        EditMaskValueFactory.spinDateTimeFieldSection(text, 3, times, false);
        assertEquals (exp.toString(),text.toString());
        
        text = new StringBuilder ("18:23:23");        
        exp = new StringBuilder ("18:23:24");
        EditMaskValueFactory.spinDateTimeFieldSection(text, 6, times, true);
        assertEquals (exp.toString(),text.toString());        
        
        text = new StringBuilder ("18:23:23");        
        exp = new StringBuilder ("18:23:23");
        EditMaskValueFactory.spinDateTimeFieldSection(text, 2, times, true);
        assertEquals (exp.toString(),text.toString());       
        
        text = new StringBuilder (" 1:23:23");        
        exp = new StringBuilder ("02:23:23");
        EditMaskValueFactory.spinDateTimeFieldSection(text, 0, times, true);
        assertEquals (exp.toString(),text.toString());               
        
        
        text = new StringBuilder ("23:23:23");        
        exp = new StringBuilder ("00:23:23");
        EditMaskValueFactory.spinDateTimeFieldSection(text, 0, times, true);
        assertEquals (exp.toString(),text.toString());               

        text = new StringBuilder ("00:23:23");        
        exp = new StringBuilder ("23:23:23");
        EditMaskValueFactory.spinDateTimeFieldSection(text, 0, times, false);
        assertEquals (exp.toString(),text.toString());                       
        
        text = new StringBuilder ("23:59:23");        
        exp = new StringBuilder ("23:00:23");
        EditMaskValueFactory.spinDateTimeFieldSection(text, 3, times, true);
        assertEquals (exp.toString(),text.toString());               

        text = new StringBuilder ("23:00:23");        
        exp = new StringBuilder ("23:59:23");
        EditMaskValueFactory.spinDateTimeFieldSection(text, 4, times, false);
        assertEquals (exp.toString(),text.toString());                               
        
        
        text = new StringBuilder ("23:59:59");        
        exp = new StringBuilder ("23:59:00");
        EditMaskValueFactory.spinDateTimeFieldSection(text, 6, times, true);
        assertEquals (exp.toString(),text.toString());               

        text = new StringBuilder ("23:59:00");        
        exp = new StringBuilder ("23:59:59");
        EditMaskValueFactory.spinDateTimeFieldSection(text, 7, times, false);
        assertEquals (exp.toString(),text.toString());                                       
        
    }        
    
    @Test
    public void testDateSpinDateTimeFieldSection ()
    {
        StringBuilder text = new StringBuilder ("15/04/2012");
        StringBuilder exp = new StringBuilder ("16/04/2012");
        EditMaskValueFactory.spinDateTimeFieldSection(text, 1, dates, true);
        assertEquals (exp.toString(),text.toString());        
        
        text = new StringBuilder ("15/04/2012");
        exp = new StringBuilder ("14/04/2012");
        EditMaskValueFactory.spinDateTimeFieldSection(text, 0, dates, false);
        assertEquals (exp.toString(),text.toString());                
        
        text = new StringBuilder ("31/04/2012");
        exp = new StringBuilder ("01/04/2012");
        EditMaskValueFactory.spinDateTimeFieldSection(text, 1, dates, true);
        assertEquals (exp.toString(),text.toString());                        
        
        text = new StringBuilder (" 1/04/2012");
        exp = new StringBuilder ("31/04/2012");
        EditMaskValueFactory.spinDateTimeFieldSection(text, 0, dates, false);
        assertEquals (exp.toString(),text.toString());                                
        
        text = new StringBuilder ("10/11/2012");
        exp = new StringBuilder ("10/12/2012");
        EditMaskValueFactory.spinDateTimeFieldSection(text, 3, dates, true);
        assertEquals (exp.toString(),text.toString());        
        
        text = new StringBuilder ("15/5 /2012");
        exp = new StringBuilder ("15/04/2012");
        EditMaskValueFactory.spinDateTimeFieldSection(text, 4, dates, false);
        assertEquals (exp.toString(),text.toString());                
        
        text = new StringBuilder ("31/12/2012");
        exp = new StringBuilder ("31/01/2012");
        EditMaskValueFactory.spinDateTimeFieldSection(text, 3, dates, true);
        assertEquals (exp.toString(),text.toString());                        
        
        text = new StringBuilder ("31/01/2012");
        exp = new StringBuilder ("31/12/2012");
        EditMaskValueFactory.spinDateTimeFieldSection(text, 4, dates, false);
        assertEquals (exp.toString(),text.toString());                                        
        
        
        text = new StringBuilder ("10/11/2012");
        exp = new StringBuilder ("10/11/2013");
        EditMaskValueFactory.spinDateTimeFieldSection(text, 6, dates, true);
        assertEquals (exp.toString(),text.toString());        
        
        text = new StringBuilder ("15/04/2012");
        exp = new StringBuilder ("15/04/2011");
        EditMaskValueFactory.spinDateTimeFieldSection(text, 7, dates, false);
        assertEquals (exp.toString(),text.toString());                
        
        text = new StringBuilder ("31/12/2200");
        exp = new StringBuilder ("31/12/1800");
        EditMaskValueFactory.spinDateTimeFieldSection(text, 8, dates, true);
        assertEquals (exp.toString(),text.toString());                        
        
        text = new StringBuilder ("31/01/1800");
        exp = new StringBuilder ("31/01/2200");
        EditMaskValueFactory.spinDateTimeFieldSection(text, 9, dates, false);
        assertEquals (exp.toString(),text.toString());                   
        
        
    }        
    
    @Test
    public void testDateTextMonthSpinDateTimeFieldSection ()
    {    
        EditMaskSpecification mDates = new EditMaskSpecification(Locale.UK,EditMaskFactory.STYLE_DATE);
        try
        {    
            mDates.setDateInput(DateFormat.MEDIUM); 
            EditMaskFactory.getMask(mDates);    
        }
        catch (Exception x)
        {
           fail (x.toString());
        }   
       
        StringBuilder text = new StringBuilder ("10-Nov-2012");
        StringBuilder exp = new StringBuilder ("10-Dec-2012");
        EditMaskValueFactory.spinDateTimeFieldSection(text, 3, mDates, true);
        assertEquals (exp.toString(),text.toString());        
        
        text = new StringBuilder ("15-May-2012");
        exp = new StringBuilder ("15-Apr-2012");
        EditMaskValueFactory.spinDateTimeFieldSection(text, 4, mDates, false);
        assertEquals (exp.toString(),text.toString());                
        
        text = new StringBuilder ("31-Dec-2012");
        exp = new StringBuilder ("31-Jan-2012");
        EditMaskValueFactory.spinDateTimeFieldSection(text, 3, mDates, true);
        assertEquals (exp.toString(),text.toString());                        
        
        text = new StringBuilder ("31-Jan-2012");
        exp = new StringBuilder ("31-Dec-2012");
        EditMaskValueFactory.spinDateTimeFieldSection(text, 4, mDates, false);
        assertEquals (exp.toString(),text.toString());                                               
       
       
    }
    
    /***
     * This test is limited to some value checking
     */
    
    @Test
    public void testTimeFieldSection ()
    {
        String text = " 1: 1:1 ";
        Calendar expc = Calendar.getInstance();
        expc.set(1970, 0, 1, 1, 1, 10);
                
        Calendar resultc = Calendar.getInstance();
        resultc = (Calendar)EditMaskValueFactory.parseValue(text, times,true);
        assertEquals (expc.get(Calendar.HOUR_OF_DAY),resultc.get(Calendar.HOUR_OF_DAY));
        assertEquals (expc.get(Calendar.MINUTE),resultc.get(Calendar.MINUTE));
        assertEquals (expc.get(Calendar.SECOND),resultc.get(Calendar.SECOND)); 
        
        text = "45:56:78";
        resultc = (Calendar)EditMaskValueFactory.parseValue(text, times,true);
        assertEquals (null,resultc);
        
    }        
}
