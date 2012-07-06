package com.stddata.fx.uicontrols;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
public class EditMaskValueFactory
{
    /***
     * Converts a value to a string according to the Specification
     * @param valueIn
     * @param spec
     * @param useInput applies only to datetime fields, determines which format to use
     * @return String value that can be passed to setText in the control.
     */
    
    public synchronized static String formatToSpecification (Object valueIn, EditMaskSpecification spec, boolean useInput)
    {
        String result = "";
        
        if (valueIn instanceof BigDecimal)
        {
            BigDecimal value = (BigDecimal)valueIn;
            result = formatValue (value, spec);
        }   
        else
        if (valueIn instanceof Double)    
        {
            BigDecimal value = new BigDecimal ((Double)valueIn);
            value.setScale(spec.getDecimalPlaces(), RoundingMode.HALF_UP);
            result = formatValue (value, spec);
        }    
        else
        if (valueIn instanceof Float)    
        {
            BigDecimal value = new BigDecimal ((Float)valueIn);
            value.setScale(spec.getDecimalPlaces(), RoundingMode.HALF_UP);
            result = formatValue (value, spec);
        }            
        else
        if (valueIn instanceof Integer)    
        {
            BigDecimal value = new BigDecimal ((Integer)valueIn);
            value.setScale(0, RoundingMode.HALF_UP);
            result = formatValue (value, spec);
        }            
        else
        if (valueIn instanceof Long)    
        {
            BigDecimal value = new BigDecimal ((Long)valueIn);
            value.setScale(0, RoundingMode.HALF_UP);
            result = formatValue (value, spec);
        }                    
        else
        if (valueIn instanceof Calendar)    
        {
            result = formatDateTime ((Calendar)valueIn,spec,useInput);
        }    
        else
        if (valueIn instanceof Date)    
        {
            Calendar c = Calendar.getInstance();
            c.setTime((Date)valueIn);
            result = formatDateTime (c,spec,useInput);
        }  
        else
           result = valueIn.toString();  
            
        
        return result;
    } 
    
    protected static synchronized String formatValue (BigDecimal value, EditMaskSpecification spec)
    {
        boolean minus = false;
        
        if (value.compareTo(BigDecimal.ZERO) < 0)
        {
            minus = true;
            value = value.abs();
        }

        String fmt = getFormat(1,spec);

        String result = String.format(fmt,value);
        if (!spec.getNegativeFormat().equals(EditMaskFactory.NEG_FMT_NONE))
        {

            if (minus)
                result = "-"+result;
            else
                result = "+"+result;
        }


        result =result.replaceAll("\\.",spec.getDecimalSeparator());     
        return result;
    }        
    
    protected static synchronized String formatDateTime (Calendar c, EditMaskSpecification spec,boolean useInput)
    {
          String pattern = getDateTimePattern (spec,useInput);
          SimpleDateFormat sdf = new SimpleDateFormat (pattern,spec.getLocale());
          String result = sdf.format(c.getTime());
              
          return result;    

    }        
    
    protected static synchronized String getDateTimePattern (EditMaskSpecification spec, boolean useInput)
    {
         
          DateFormat fm  = DateFormat.getDateInstance(spec.getDateInput(), 
                  spec.getLocale());
          if (!useInput) 
              fm = DateFormat.getDateInstance(spec.getDateDisplay(),spec.getLocale());
          String pattern = "";
          
          if (spec.getMaskStyle()==EditMaskFactory.STYLE_DATE || spec.getMaskStyle()==EditMaskFactory.STYLE_DATE_TIME)
          {    
             SimpleDateFormat sdf = (SimpleDateFormat)fm;
             StringBuilder x = new StringBuilder (sdf.toPattern());

             if (spec.isForceFourDigitYears())
             {
                 while (x.toString().toLowerCase().indexOf("yyyy") < 0)
                 {
                     int i = x.toString().toLowerCase().indexOf("y");
                     x.insert(i,x.charAt(i));
                 }  
             }
             
             //we also need to force two digit months and days
             while (x.toString().toLowerCase().indexOf("mm") < 0)
             {
                 int i = x.toString().toLowerCase().indexOf("m");
                 x.insert(i,x.charAt(i));
             }  

             while (x.toString().toLowerCase().indexOf("dd") < 0)
             {
                 int i = x.toString().toLowerCase().indexOf("d");
                 x.insert(i,x.charAt(i));
             }                   

             if (spec.getMaskStyle()==EditMaskFactory.STYLE_DATE)
                 pattern = x.toString();
             else
                 pattern = x.toString()+" HH:mm:ss";
          }   
          
          if (spec.getMaskStyle()==EditMaskFactory.STYLE_TIME)
          {    
              pattern="HH:mm:ss";
          }      
          
          return pattern;
                  
    }        
    
    protected static synchronized String getFormat (int arg, EditMaskSpecification spec)
    {
      return getFormat (arg,"f",spec);
    }

    protected static synchronized String getFormat (int arg, String numberType, EditMaskSpecification spec)
    {
        StringBuilder result = new StringBuilder ();

        if (arg > 0)
        {
            result.append("%").append (Integer.toString(arg).trim());
        }

        int overall = spec.getDigits();
        if (spec.getDecimalPlaces() > 0)
        {
            overall += spec.getDecimalPlaces()+1;
        }

        if (numberType.equals("d"))
        {
            result.append("$").append(Integer.toString(overall).trim()).append (numberType);
        }
        else
            result.append("$").append(Integer.toString(overall).trim()).append(".").
                    append(Integer.toString(spec.getDecimalPlaces()).trim()).
                    append (numberType);

        return result.toString();
    }    
    
    public synchronized static Object parseValue (String value, EditMaskSpecification specification)
    {
        return parseValue (value,specification,false,true);
    }
    
    public synchronized static Object parseValue (String value, EditMaskSpecification specification, boolean useInput)
    {
        return parseValue (value,specification,false,useInput);
    }
    
    /****
     * Converts the input field Value back to a String, Number, Calendar Item etc
     * TidyInputText must have been called before attempting to parse the value.
     * For numbers all separators are stripped and spaces removed before attempting
     * to parse the value.  A BigDecimal is returned an the calling code should 
     * convert this back to long, int etc value as required.
     * 
     * @param value Text from edited string.
     * @param specification
     * @param includeLiterals applies only to String based fields, true returns string with literals included, false without
     * @param useInput applies only to date/time fields determines if input or display mask is used
     * @return 
     */
    
        
    
    public synchronized static Object parseValue (String value, EditMaskSpecification specification,
                                                  boolean includeLiterals,boolean useInput)
    {
        Object result = null;
        
        switch (specification.getMaskStyle())
        {        
            case EditMaskFactory.STYLE_NUMERIC:
            {
                result = getNumericValue (value, specification);
                break;
            }    
            case EditMaskFactory.STYLE_DATE:
            case EditMaskFactory.STYLE_DATE_TIME:
            {
                result = getDateTime (value,specification,useInput);
                break;
            }    
            case EditMaskFactory.STYLE_TIME:
            {
                result = getTime (value,specification,useInput);
                break;
            }   
            case EditMaskFactory.STYLE_GENERAL:
            case EditMaskFactory.STYLE_SPECIAL:
            {
               if (!includeLiterals)  
                   result = value;
               else
                   result = getStringNoLiterals (value, specification);
               break;
            }   
            default:
                result = value;
        }
        
        return result;
    }   
    
    protected synchronized static BigDecimal getNumericValue (String textValue, EditMaskSpecification specification)
    {
        BigDecimal result = new BigDecimal (0);

        try
        {
            String decimalSeparator = specification.getDecimalSeparator();    
            String v = textValue;

            if (!decimalSeparator.trim().isEmpty() && !decimalSeparator.trim().equals("."))
            {
                v = v.replaceAll(decimalSeparator,".");
            }

            boolean negValue = false;
            int bracket = v.indexOf("(");

            if ((bracket == 0) || (bracket >0))
            {
                negValue = true;
                v = v.replace('(',' ');
                v = v.replace(')', ' ');
            }
            else
            if (v.indexOf("-") > -1)
            {
                negValue = true;
                v = v.replaceAll("(-)"," ");
            }

            StringBuilder s = new StringBuilder (v);
            int count = 0;
            while (count < s.length())
            {
                if (Character.isDigit(s.charAt(count)) || s.charAt(count)== '.')
                    ++count;
                else
                    s.deleteCharAt(count);
            }
            v = s.toString();
            if (v.charAt(v.length()-1 )==' ')
                v = v.substring(0, v.length()-1);  //removes trailing space character
            v = v.replaceAll("( )","0").trim(); //replace any space chars with "0"

            if (negValue)
                result = new BigDecimal ("-"+v);
            else
                result = new BigDecimal (v);


        }
        catch (Exception x)
        {
            result = null;
        }

        return result;        
    }  
    
    protected synchronized static Calendar getDateTime (String textValue, EditMaskSpecification specification,
            boolean useInput)
    {
        Calendar result = Calendar.getInstance(specification.getLocale());
        
        try
        {
            String pattern = getDateTimePattern (specification,useInput);
            
            if (specification.getMaskStyle()==EditMaskFactory.STYLE_TIME ||
                specification.getMaskStyle()==EditMaskFactory.STYLE_DATE_TIME)
            {
                int fs = pattern.toLowerCase().indexOf("hh");
                int end = pattern.toLowerCase().indexOf("ss");
                
                if (end == -1)
                    end = pattern.indexOf("mm",fs);
                
                if (end > -1)
                    end+=1;
                
                
              /*  if (fs > -1)
                {    
                    String sep = pattern.substring(fs+2, fs+3);
                    end = pattern.lastIndexOf(sep,fs);
                    end +=2;
                }*/
                    
                
                StringBuilder vt = new StringBuilder (textValue);

                do
                {    
                    
                   fs = vt.indexOf(" ", fs);
                    if (fs > end)
                        fs = -1;
                    
                    if (fs > -1)
                        vt.setCharAt(fs, '0');
                }
                while (fs > -1);
                textValue = vt.toString();
            }    
                

            SimpleDateFormat sdf = new SimpleDateFormat (pattern, specification.getLocale());
            sdf.setLenient(false);
            result.setTime(sdf.parse(textValue.trim()));

            
            if (specification.getMaskStyle()==EditMaskFactory.STYLE_DATE)
            {
                result.set(Calendar.HOUR_OF_DAY,0);
                result.set(Calendar.MINUTE,0);
                result.set(Calendar.SECOND,0);
            }   
            else
            if (specification.getMaskStyle()==EditMaskFactory.STYLE_DATE_TIME)    
            {
                int i = pattern.lastIndexOf(" ");
                if (i > -1)
                {    
                    String tpattern =pattern.substring(i+1);
                    sdf = new SimpleDateFormat (tpattern, specification.getLocale());
                    sdf.setLenient(false);
                    Calendar tr = Calendar.getInstance();
                    Date d = sdf.parse(textValue.substring(i+1));
                    tr.set(result.get(Calendar.YEAR), result.get(Calendar.MONTH), result.get(Calendar.DAY_OF_MONTH),
                            d.getHours(),d.getMinutes(),d.getSeconds());
                    result = tr;

                }    
            }    
            result.set(Calendar.MILLISECOND,0);
        }   
        catch (Exception x)
        {
            result = null;
        }
        
        return result;
    }     

    /***
     * 
     * @param textValue
     * @param specification
     * @return time but the date element will be set to the Epoc of 1st Jan 1970
     */
    
    protected synchronized static Calendar getTime (String textValue, EditMaskSpecification specification,boolean useInput)
    {
        return getDateTime (textValue,specification,useInput);

    }     
    
    protected synchronized static String getStringNoLiterals (String textValue, EditMaskSpecification specification)
    {
         StringBuilder emptyMask = EditMaskHelper.makeEmptyField(specification.getEditMask(),true);
         
         StringBuilder result = new StringBuilder ("");
         
         for (int count = 0; count < textValue.length(); ++ count)
         {
             if (count >= emptyMask.length())
                 break;
             
             if (emptyMask.charAt(count)!= ' ')
             {
                 result.append(' ');
             }    
             else
             {
                 result.append (textValue.charAt(count));
             }    
         }    
         
         return result.toString();
    }    
    
    public synchronized static void spinDateTimeFieldSection (StringBuilder text, 
            int caret, EditMaskSpecification specification, boolean add)
    {
        if (specification.getMaskStyle() != EditMaskFactory.STYLE_DATE &&
            specification.getMaskStyle() != EditMaskFactory.STYLE_DATE_TIME && 
            specification.getMaskStyle() != EditMaskFactory.STYLE_TIME)
            return;
        
        //get field section start end
        int pos = EditMaskHelper.getMaskPositionFromCaret(specification.getEditMask(), caret);
        if (EditMaskHelper.isAnyLiteral(specification.getEditMask(), pos))
            return;
        
        int st = 0;
        int end = 0;
        String pattern = getDateTimePattern (specification,true);
        
        
        switch (pattern.charAt(caret))
        {
            case 'd':
                st = specification.getDayStart();
                end = EditMaskHelper.getEndOfSubField(st, specification.getEditMask(), pos);
                addValue (specification.getEditMask(),st,end,31,1,text,add);
                break;
            case 'M':
                st = specification.getMonthStart();
                end = EditMaskHelper.getEndOfSubField(st, specification.getEditMask(), pos);
                if ((end - st) > 2)
                {
                    String m = text.substring(st,end).toLowerCase().trim();
                    DateFormat df = DateFormat.getDateInstance(specification.getDateInput(), specification.getLocale());
                    if (df instanceof SimpleDateFormat)
                    {
                        SimpleDateFormat sdf = (SimpleDateFormat)df;
                        DateFormatSymbols sym = sdf.getDateFormatSymbols();
                        int slot = 0;
                        while (slot < sym.getShortMonths().length && 
                              !sym.getShortMonths()[slot].toLowerCase().equals(m))
                        {
                            ++slot;
                        }    
                        
                        if (add)
                            ++ slot;
                        else
                            --slot;
                        
                        int arLen = sym.getShortMonths().length - 1;
                        //For the uk the array has 13 slots, the last slot is 
                        //an empty string
                        if (sym.getShortMonths()[arLen].isEmpty())
                            --arLen;
                        
                        if (slot > arLen)
                            slot = 0;
                        else
                        if (slot < 0)    
                            slot = arLen;
                        
                        text.delete(st, end);
                        text.insert(st, sym.getShortMonths()[slot]);

                    }    
                }   
                else
                  addValue (specification.getEditMask(),st,end,12,1,text,add);                                      
                break;
            case 'y':
            case 'Y':
                st = specification.getYearStart();
                end = EditMaskHelper.getEndOfSubField(st, specification.getEditMask(), pos);
                
                if ((end - st) < 4)
                    addValue (specification.getEditMask(),st,end,99,0,text,add);                                    
                else
                    addValue (specification.getEditMask(),st,end,2200,1800,text,add);                
                break;

            case 'H':
            case 'h':
                st = specification.getHourStart();
                addValue (specification.getEditMask(),':',st,23,0,text,add);
                break;
            case 'm':
                st = specification.getMinuteStart();
                addValue (specification.getEditMask(),':',st,59,0,text,add);                
                break;                
            case 's':
                st = specification.getSecondStart();
                addValue (specification.getEditMask(),':',st,59,0,text,add);                
                break;
            default:
                break;
                
        }    
        
    }     
    
    protected synchronized static void addValue (StringBuilder editMask, char separator,
              int start, int maxValue, int minValue, StringBuilder text, boolean 
               add)    
    {
        int end = EditMaskHelper.nextLiteral(editMask, separator, start);
        if (end == -1)
            end =  text.length(); 
        else
            end--;
        
        addValue (editMask,start,end,maxValue,minValue,text,add);
        
    }
    protected synchronized static void addValue (StringBuilder editMask, 
              int start, int end, int maxValue, int minValue, StringBuilder text, 
              boolean add)
    {

        
        try
        {     
            int val = Integer.parseInt(text.substring(start,end).trim());
            if (val >= maxValue && add)
                val = minValue;
            else
            if (val < maxValue && add)
                ++val;
            else
            if (val > minValue && !add)    
                --val;
            else
            if (val <= minValue && !add)    
                val = maxValue;
            else    
                val = -1;

            if (val > -1)
            {
                String v= String.format("%02d",val);
                text.replace(start, end, v);
            }    

        }
        catch (Exception x)
        {

        }            
        
    }        
    
    
    /****
     * For datetime fields after an F4 Lookup key to enable easy conversion to the input format
     * @param src  Text date/time
     * @param specification EditMaskSpecification for control
     * @param useInput true for input format, false for display format
     * @return If sucessful, the formatted string,else returns the src text as the result
     */
    
    public static String checkAndConvertDateTimeToInputFormat (String src, EditMaskSpecification specification, boolean useInput)
    {
        String result = src;
        
        Calendar c = (Calendar)parseValue (src,specification,true);
        if (c==null)
        {
            c = (Calendar)parseValue (src,specification,false);
        }    
        
        if (c!=null)
            result = formatToSpecification(c,specification,useInput);
        
        
        return result;
    }        
    
}
