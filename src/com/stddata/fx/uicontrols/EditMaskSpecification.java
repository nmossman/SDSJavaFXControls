package com.stddata.fx.uicontrols;

import java.text.DateFormat;
import java.util.Locale;

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
public class EditMaskSpecification
{
    protected String decimalSeparator="";
    protected String thousandSeparator="";
    protected String negativeFormat=EditMaskFactory.NEG_FMT_BRACKETS;
    protected int decimalPlaces=0;
    protected char maskStyle;
    protected char maskChar = EditMaskFactory.ALPHA_NUMERIC_ANYCASE;
    protected int dateDisplay=DateFormat.MEDIUM;
    protected int dateInput= DateFormat.SHORT;
    protected boolean forceFourDigitYears = true;
    protected StringBuilder editMask=null;
    protected StringBuilder dateTimeDisplayMask=null;
    protected int maskLength;
    protected Locale locale;
    protected int yearStart=0;
    protected int monthStart=0;
    protected int dayStart = 0;
    protected int hourStart = 0;
    protected int minuteStart = 0;
    protected int secondStart = 0;
    protected int [] specialSectionsSizes = new int []{3};
    protected String specialSectionSeparator="";
    protected char specialSectionCase = EditMaskFactory.ALPHA_NUMERIC_FORCEUPPERCASE;
   
    
    public EditMaskSpecification (Locale locale, char maskStyle)
    {
        this.maskStyle=maskStyle;
        this.locale=locale;
    }        
    
    public EditMaskSpecification (int digits, int decimalPlaces, String decimalSeparator, 
                String thousandSeparator, String negativeFormat) 
    {
        this.decimalPlaces=decimalPlaces;
        this.decimalSeparator=decimalSeparator;
        this.thousandSeparator=thousandSeparator;
        this.negativeFormat=negativeFormat;
        this.maskLength=digits;
        maskStyle = EditMaskFactory.STYLE_NUMERIC;
    }
    
    public EditMaskSpecification ()
    {
        this (20,EditMaskFactory.ANY_KEY);
    }        
    
    public EditMaskSpecification (int maskLength, char maskChar)
    {
        this.maskLength=maskLength;
        this.maskChar = maskChar;
        maskStyle = EditMaskFactory.STYLE_GENERAL;
    }

    public EditMaskSpecification (char specialSectionCase,
                String specialSectionSeparator, int ... sectionSizes)
    {
        maskStyle = EditMaskFactory.STYLE_SPECIAL;
        this.specialSectionSeparator=specialSectionSeparator;
        this.specialSectionsSizes=sectionSizes;
        this.specialSectionCase=specialSectionCase;
        int total = 0;
        for (int count = 0; count < sectionSizes.length; ++ count)
        {
            total += sectionSizes[count];
            if (count > 0)
                total += specialSectionSeparator.length();
        }    
        maskLength = total;
    }    
    
    public EditMaskSpecification (String editMask) throws Exception
    {
        this (new StringBuilder (editMask));
    }        
    
    public EditMaskSpecification (StringBuilder editMask) throws Exception
    {
        maskStyle = EditMaskFactory.STYLE_SPECIAL;
        if (EditMaskHelper.isValidMask(editMask))
        {
            this.editMask = editMask;
            maskLength = editMask.length();
        }   
        else
            throw new Exception ("Invalid EditMask");

    }        
    
    /**
     * @return the decimalSeparator
     */
    public String getDecimalSeparator()
    {
        return decimalSeparator;
    }

    /**
     * @param decimalSeparator the decimalSeparator to set
     */
    public void setDecimalSeparator(String decimalSeparator)
    {
        this.decimalSeparator = decimalSeparator;
    }

    /**
     * @return the thousandSeparator
     */
    public String getThousandSeparator()
    {
        return thousandSeparator;
    }

    /**
     * @param thousandSeparator the thousandSeparator to set
     */
    public void setThousandSeparator(String thousandSeparator)
    {
        this.thousandSeparator = thousandSeparator;
    }

    /**
     * @return the negativeFormat
     */
    public String getNegativeFormat()
    {
        return negativeFormat;
    }

    /**
     * @param negativeFormat the negativeFormat to set
     */
    public void setNegativeFormat(String negativeFormat)
    {
        this.negativeFormat = negativeFormat;
    }

    /**
     * @return the decimalPlaces
     */
    public int getDecimalPlaces()
    {
        return decimalPlaces;
    }

    /**
     * @param decimalPlaces the decimalPlaces to set
     */
    public void setDecimalPlaces(int decimalPlaces)
    {
        this.decimalPlaces = decimalPlaces;
    }

    /**
     * @return the maskStyle
     */
    public char getMaskStyle()
    {
        return maskStyle;
    }

    /**
     * @param maskStyle the maskStyle to set
     * the maskStyle must be one of the STYLE found in the STYLES Array
     */
    public void setMaskStyle(char maskStyle)
    {
        this.maskStyle = EditMaskFactory.STYLE_GENERAL;
        for (int count = 0;count < EditMaskFactory.STYLES.length;++count)
        {
            if (maskStyle==EditMaskFactory.STYLES[count])
            {    
                this.maskStyle = maskStyle;
                break;
            }
        }    
    }

       

    /**
     * @return the editMask
     */
    public StringBuilder getEditMask()
    {
        return editMask;
    }

    /**
     * @param editMask the editMask to set
     */
    public void setEditMask(StringBuilder editMask)
    {
        this.editMask = editMask;
    }

    /**
     * @return the maskLength
     */
    public int getMaskLength()
    {
        return maskLength;
    }



    /**
     * @return the digits
     */
    public int getDigits()
    {
        return getMaskLength();
    }


    /**
     * @return the locale
     */
    public Locale getLocale()
    {
        return locale;
    }

    /**
     * @param locale the locale to set
     */
    public void setLocale(Locale locale)
    {
        this.locale = locale;
    }

    /**
     * @return the dateDisplay
     */
    public int getDateDisplay()
    {
        return dateDisplay;
    }

    /**
     * @param dateDisplay the dateDisplay to set
     */
    public void setDateDisplay(int dateDisplay)
    {
        this.dateDisplay = dateDisplay;
    }

    /**
     * @return the dateInput
     */
    public int getDateInput()
    {
        return dateInput;
    }

    /**
     * @param dateInput the dateInput to set
     */
    public void setDateInput(int dateInput)
    {
        this.dateInput = dateInput;
    }

    /**
     * @return the forceFourDigitYears
     */
    public boolean isForceFourDigitYears()
    {
        return forceFourDigitYears;
    }

    /**
     * @param forceFourDigitYears the forceFourDigitYears to set
     */
    public void setForceFourDigitYears(boolean forceFourDigitYears)
    {
        this.forceFourDigitYears = forceFourDigitYears;
    }

    /**
     * @return the yearStart
     */
    public int getYearStart()
    {
        return yearStart;
    }

    /**
     * @param yearStart the yearStart to set
     */
    public void setYearStart(int yearStart)
    {
        this.yearStart = yearStart;
    }

    /**
     * @return the monthStart
     */
    public int getMonthStart()
    {
        return monthStart;
    }

    /**
     * @param monthStart the monthStart to set
     */
    public void setMonthStart(int monthStart)
    {
        this.monthStart = monthStart;
    }

    /**
     * @return the dayStart
     */
    public int getDayStart()
    {
        return dayStart;
    }

    /**
     * @param dayStart the dayStart to set
     */
    public void setDayStart(int dayStart)
    {
        this.dayStart = dayStart;
    }

    /**
     * @return the hourStart
     */
    public int getHourStart()
    {
        return hourStart;
    }

    /**
     * @param hourStart the hourStart to set
     */
    public void setHourStart(int hourStart)
    {
        this.hourStart = hourStart;
    }

    /**
     * @return the minuteStart
     */
    public int getMinuteStart()
    {
        return minuteStart;
    }

    /**
     * @param minuteStart the minuteStart to set
     */
    public void setMinuteStart(int minuteStart)
    {
        this.minuteStart = minuteStart;
    }

    /**
     * @return the secondStart
     */
    public int getSecondStart()
    {
        return secondStart;
    }

    /**
     * @param secondStart the secondStart to set
     */
    public void setSecondStart(int secondStart)
    {
        this.secondStart = secondStart;
    }
    
    public void setDateTimeMaskFieldLocations (int yearStart, int monthStart, 
                                               int dayStart,  
                                               int hourStart, int minuteStart,
                                               int secondStart)
    {
       setYearStart (yearStart) ;
       setMonthStart (monthStart);
       setDayStart (dayStart);
       setHourStart (hourStart);
       setMinuteStart (minuteStart);
       setSecondStart (secondStart);
    }

    /**
     * @return the specialSectionsSizes
     */
    public int[] getSpecialSectionsSizes()
    {
        return specialSectionsSizes;
    }

    /**
     * @param specialSectionsSizes the specialSectionsSizes to set
     */
    public void setSpecialSectionsSizes(int[] specialSectionsSizes)
    {
        this.specialSectionsSizes = specialSectionsSizes;
    }

    /**
     * @return the specialSectionSeparator
     */
    public String getSpecialSectionSeparator()
    {
        return specialSectionSeparator;
    }

    /**
     * @param specialSectionSeparator the specialSectionSeparator to set
     */
    public void setSpecialSectionSeparator(String specialSectionSeparator)
    {
        this.specialSectionSeparator = specialSectionSeparator;
    }

    /**
     * @return the specialSectionCase
     */
    public char getSpecialSectionCase()
    {
        return specialSectionCase;
    }

    /**
     * @param specialSectionCase the specialSectionCase to set
     */
    public void setSpecialSectionCase(char specialSectionCase)
    {
        this.specialSectionCase = specialSectionCase;
    }

    /**
     * @return the dateTimeDisplayMask
     */
    public StringBuilder getDateTimeDisplayMask()
    {
        if (dateTimeDisplayMask==null)
            dateTimeDisplayMask=editMask;
        
        return dateTimeDisplayMask;
    }

    /**
     * @param dateTimeDisplayMask the dateTimeDisplayMask to set
     */
    public void setDateTimeDisplayMask(StringBuilder dateTimeDisplayMask)
    {
        this.dateTimeDisplayMask = dateTimeDisplayMask;
    }
    
    public char getMaskChar ()
    {
        return maskChar;
    }        
            
}
