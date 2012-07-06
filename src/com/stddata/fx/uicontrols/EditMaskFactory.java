package com.stddata.fx.uicontrols;



import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;



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
 * Copyright 2006-2012 Standard Data Systems Limited
 * @author nigelm
 */

public class EditMaskFactory
{

  public static final int GENERAL_INTRASTAT_CODE = 16;


  public static final String NEG_FMT_NONE = "";
  public static final String NEG_FMT_LEADING = "-1.1";
  public static final String NEG_FMT_TRAILING = "1.1-";  
  public static final String NEG_FMT_BRACKETS = "(1.1)";
  
  public static final char U_CASE_LETTER = 'U';
  public static final char L_CASE_LETTER = 'L';
  public static final char D_DIGIT = 'D';
  public static final char ANY_KEY = '*';
  public static final char ANY_KEY_FORCED_UPPER = 'u';
  public static final char ANY_KEY_FORCED_LOWER = 'l';
  public static final char ESCAPE = '\\';
  public static final char OPTIONAL_ESCAPE = '¬';  
  public static final char D_SIGN_MINUS      = '-';
  public static final char D_SIGN_MINUS_LEFT = '(';
  public static final char D_SIGN_MINUS_RIGHT= ')';
  public static final char ALPHA_NUMERIC_UPPERCASE='A';
  public static final char ALPHA_NUMERIC_LOWERCASE='a';
  public static final char ALPHA_NUMERIC_FORCEUPPERCASE='F';
  public static final char ALPHA_NUMERIC_FORCELOWERCASE='f';
  public static final char ALPHA_NUMERIC_LTDPUNC_UPPERCASE='X';
  public static final char ALPHA_NUMERIC_LTDPUNC_LOWERCASE='x';
  public static final char ALPHA_NUMERIC_LTDPUNC_FORCEUPPERCASE='Y';
  public static final char ALPHA_NUMERIC_LTDPUNC_FORCELOWERCASE='y';
  public static final char ALPHA_NUMERIC_LTDPUNC_ANYCASE='Z';     
  public static final char ALPHA_NUMERIC_ANYCASE='C';
  public static final char TELEPHONE_NUMBER='P';
  public static final char NO_INPUT=' ';
  protected static char[] LTDPUNC = new char[] {'&','*','!','.'};  
  
  public static final char STYLE_GENERAL='g';
  public static final char STYLE_DATE='d';
  public static final char STYLE_TIME='t';
  public static final char STYLE_DATE_TIME='e';
  public static final char STYLE_NUMERIC='n';
  public static final char STYLE_SPECIAL='s';
  public static final char[] STYLES = new char [] {STYLE_GENERAL,STYLE_DATE,
                                                   STYLE_TIME,STYLE_DATE_TIME,
                                                   STYLE_NUMERIC,STYLE_SPECIAL};
  
  protected static int[] specialFmtData = new int [8];
  protected static char specialcase = 'a';


  public synchronized static void getNumberMask(EditMaskSpecification specification)  
  {
    
    StringBuilder inputMask = makeMask (D_DIGIT,
                  specification.getDigits(),specification.getDecimalPlaces(),
                  specification.getDecimalSeparator().charAt(0),specification.getThousandSeparator().charAt(0),
                  specification.getNegativeFormat());
    specification.setEditMask(inputMask);   
    
  }

 
 
  
  public static synchronized void getMask (EditMaskSpecification specification) throws Exception
  {
      if (specification.getMaskStyle()==STYLE_NUMERIC)
      {
         StringBuilder inputMask = makeMask (D_DIGIT,
                  specification.getDigits(),specification.getDecimalPlaces(),
                  specification.getDecimalSeparator().charAt(0),specification.getThousandSeparator().charAt(0),
                  specification.getNegativeFormat());
          specification.setEditMask(inputMask);    
      }    
      else
      if (specification.getMaskStyle()==STYLE_DATE || 
          specification.getMaskStyle()==STYLE_TIME ||
          specification.getMaskStyle()==STYLE_DATE_TIME)
      {   
          getDateTimeMask (specification);
      }
      else
      if (specification.getMaskStyle()==STYLE_GENERAL)
      {
          StringBuilder editMask = makeMaskGeneral (specification);
          specification.setEditMask(editMask);
          
          if (EditMaskHelper.isNumberOnlyInput(specification.getEditMask()))
          {
              specification.setMaskStyle(STYLE_NUMERIC);
          }    
          
      }   
      else
      if (specification.getMaskStyle()==STYLE_SPECIAL)    
      {
          StringBuilder editMask = specification.getEditMask();
          if (editMask == null || 
              editMask.toString().trim().isEmpty())
                 editMask = makeMaskSpecial (specification);
          
          if (EditMaskHelper.isValidMask(editMask))
            specification.setEditMask(editMask);
          else
            throw new Exception ("Invalid Editmask format - check for illegal characters");
      }    
  }
  
  public synchronized static void getDateTimeMask (EditMaskSpecification specification)throws Exception
  {
      if (specification.getMaskStyle()!=STYLE_DATE && specification.getMaskStyle()!=STYLE_TIME &&
          specification.getMaskStyle()!=STYLE_DATE_TIME)
          throw new Exception ("Specification Mask Style not a date or time constant");
      
      if (specification.getDateInput() != DateFormat.SHORT && specification.getDateInput() != DateFormat.MEDIUM &&
          specification.getDateInput() != DateFormat.LONG)
          throw new Exception ("Specification Mask DateInput is not SHORT, MEDIUM or LONG");
      
      if (specification.getDateDisplay() != DateFormat.SHORT && specification.getDateDisplay() != DateFormat.MEDIUM &&
          specification.getDateDisplay() != DateFormat.LONG)
          throw new Exception ("Specification Mask DateDisplay is not SHORT, MEDIUM or LONG");      
      
      DateFormat sd = DateFormat.getDateInstance(specification.getDateInput(), specification.getLocale());
      specification.setEditMask(makeDateTimeMaskProcess (specification,sd,true));            
      
      sd = DateFormat.getDateInstance(specification.getDateDisplay(), specification.getLocale());
      specification.setDateTimeDisplayMask(makeDateTimeMaskProcess (specification,sd,false));                  
  }        
  

  
  protected synchronized static StringBuilder makeMaskGeneral (EditMaskSpecification spec)
  {
    int size = spec.getMaskLength();
    char maskChar = spec.getMaskChar ();
    
    StringBuilder result = null;
    if (size < 1)
      size = 10;
    
    try
    {

      result = makeMask(maskChar,size);
      
  /*    switch (size)
      {    

          case GENERAL_INTRASTAT_CODE:
              size = 10;
              result = new StringBuilder ();
              result.append(makeMask (D_DIGIT,4));
              result.append(ESCAPE).append(" ");
              result.append(makeMask (D_DIGIT,2));
              result.append(ESCAPE).append(" ");
              result.append(makeMask (D_DIGIT,2));
              break;
         default:
              result = makeMask (ANY_KEY,size);
              break;                            
      }*/
    
    }
    catch (Exception x)
    {

    }  
    
    return result;
  }
  
  protected synchronized static StringBuilder makeMaskSpecial (EditMaskSpecification spec)
  {
       StringBuilder work = null;
       StringBuilder result = new StringBuilder ();
       char acctcase = spec.getSpecialSectionCase();
       
       for (int count = 0;count < spec.getSpecialSectionsSizes().length; ++ count)
       {
            work = makeMask (acctcase,spec.getSpecialSectionsSizes()[count]);
            if (count > 0)
                result.append(ESCAPE).append(spec.getSpecialSectionSeparator()).append(work);
            else
                result.append (work);
       }
       
       return result;
  }        
  
  protected synchronized static StringBuilder makeMask (char maskChar, int size)
  {
    StringBuilder result = new StringBuilder ("");
    for (int count = 0; count < size;  ++count)
      result.append (maskChar);
      
    return result;
  }
  
  protected synchronized static StringBuilder makeMask (char maskChar, int size, int decimals, char decimalSep)  
  {
    StringBuilder result = new StringBuilder ("");
    result = makeMask (maskChar,size);
    result.append (decimalSep);
    result.append (makeMask (maskChar,decimals));
    return result;
  }
  
 protected synchronized static StringBuffer adjustMask (StringBuffer inputMask, 
                             StringBuffer displayMask, char maskChar, int inSize, 
                            int decimals, char decimalSep, char thouSep, String negFmt)   
{
    int ins = 0;
    if (negFmt.startsWith("(") || negFmt.startsWith("-"))
        ins = 1;
    while (inputMask.length() < displayMask.length())
    {
      inputMask.insert(ins, new char[] {'\\',' '});
    }
    return inputMask;
}
  
  protected synchronized static StringBuilder makeMask (char maskChar, int inSize, 
                            int decimals, char decimalSep, char thouSep, String negFmt)  
  {
    StringBuilder result = new StringBuilder ("");
    int size = inSize;
    int base = 0;
    int work = 0;
    
    if (thouSep != ' ')
    {
        work = size;
                
        if (work > 3)
        {
           ++base;
           work = (work - 4)/3;
           base+= work;
        }
        size += base*2;  //*2 allows for escape chars
    }
    
    result = makeMask (maskChar,size);    
    
    if (base > 0)
    {
        base = size-1;
        work = 0;
        while (base > 1)
        {
            ++ work; 
            if (work == 4)
            {
              work = 0;
              result.setCharAt(base, thouSep);
              --base;
              result.setCharAt(base,OPTIONAL_ESCAPE);
              --base;
            }
            else
              --base;  
        }
    }
    
    if (decimals > 0)
    {
        result.append (ESCAPE).append(decimalSep).append(makeMask (maskChar,decimals));
    }
        switch (negFmt)
        {
            case "-1.1":
                result = new StringBuilder (OPTIONAL_ESCAPE+"-"+result.toString());
                break;
            case "1.1-":
                result.append(OPTIONAL_ESCAPE).append ("-");
                break;
            case "(1.1)":
                result = new StringBuilder (OPTIONAL_ESCAPE+"("+result.toString()+OPTIONAL_ESCAPE+")");
                break;
        }

    
    return result;
  }  
  
  
  
  protected static synchronized StringBuilder makeDateTimeMaskProcess (EditMaskSpecification spec, DateFormat md,
            boolean isInput)
  {
      StringBuilder result = new StringBuilder ();
      int yearStart=0;
      int monthStart=0;
      int dayStart = 0;
      int hourStart = 0;
      int minuteStart = 0;
      int secondStart = 0;
      int escapeCount = 0;
       

      Calendar c = Calendar.getInstance();
      String ld = md.format(c.getTime());      
      String pattern = ((SimpleDateFormat) md).toPattern();
      int mth = c.get(Calendar.MONTH);
      int count = 0;
      String mths[] = ((SimpleDateFormat) md).getDateFormatSymbols().getShortMonths(); 
      int longestMonth = 0;
      
      for (count = 0; count < mths.length;++count)
      {
          if (mths[count].length() > longestMonth)
              longestMonth = count;
      }    
      if (longestMonth == 0)
          longestMonth = mth;
      
      count = 0;
      
      
      if (spec.getMaskStyle()==STYLE_DATE || spec.getMaskStyle()==STYLE_DATE_TIME)
      {    
        while (count < pattern.length())
        {
            boolean lastChar = false;
            if (count == pattern.length()-1)
                lastChar = true;
            switch (pattern.toLowerCase().charAt(count))
            {    
                case 'd':
                {
                    dayStart = result.length()-escapeCount;                    
                    if (!lastChar && pattern.toLowerCase().charAt(count+1)=='d')
                        ++count;
                    result.append (D_DIGIT).append(D_DIGIT);
                    break;
                }  
                case 'm':
                {
                    int slot = pattern.toLowerCase().indexOf("mmm",count);
                    monthStart = result.length()-escapeCount;
                    if (slot > -1)
                    {
                        count+=2;
                        for (int cx=0; cx < mths[longestMonth].length(); ++ cx)
                        {
                            if (Character.isUpperCase(mths[longestMonth].charAt(cx)))
                                result.append(U_CASE_LETTER);
                            else
                                result.append(L_CASE_LETTER);
                        }    
                    }   
                    else
                    {
                        if (!lastChar && pattern.toLowerCase().charAt(count+1)=='m')
                            ++count;
                        result.append (D_DIGIT).append(D_DIGIT);
                    }  
                    break;
                }   
                case 'y':
                {
                    /***
                    * There seems to be a pattern where a single y in the Year
                    * means 4 digits
                    */ 
                    yearStart = result.length()-escapeCount;
                    int year = 2;
                    if (pattern.toLowerCase().indexOf("yyyy",count) > -1)
                    {    
                        year = 4;
                        count+=3;
                    }    
                    else
                    if (pattern.toLowerCase().indexOf("yy",count) > -1)
                    {    
                        if (!spec.isForceFourDigitYears())
                            year = 2; 
                        else
                            year = 4;
                        count+=1;
                    }    
                    else //asumed to be one
                        year = 4; 

                    for (int cx=0; cx < year; ++cx)
                        result.append (D_DIGIT);
                    break;
                }   
                default:
                {
                    escapeCount+=1;
                    result = result.append(ESCAPE).append(pattern.charAt(count));
                    break;
                }    
            }    


            ++count;
        } 
      }
      
      if (spec.getMaskStyle()==STYLE_TIME || spec.getMaskStyle()==STYLE_DATE_TIME)
      {
          if (spec.getMaskStyle()==STYLE_DATE_TIME)
          {    
              result =result.append(ESCAPE).append(" ");
              ++escapeCount;
          }
          
          hourStart = result.length()-escapeCount;
          minuteStart = hourStart+3;
          secondStart = minuteStart+3;
          result = result.append(D_DIGIT).append(D_DIGIT).
                  append(ESCAPE).append(':').append(D_DIGIT).append(D_DIGIT).
                  append(ESCAPE).append(':').append(D_DIGIT).append(D_DIGIT);

          
      }    
      
      if (isInput)
      {    
        spec.setDateTimeMaskFieldLocations (yearStart, monthStart,dayStart, 
                                          hourStart, minuteStart,secondStart);
      }
      
      return result;      
  }        
  
  /**
   * getFieldSizeBasedOnMask used to determine the actual size of the field
   * on screen.
   * @param mask
   * @return the size of the field
   */
  public synchronized static int getFieldSizeBasedOnMask (StringBuilder mask)
  {
      int result = mask.length();
      
      int count = 0;
      int slot  = 0;
      String seek = new String (new char[]{ESCAPE});
      while (slot != -1)
      {
          slot = mask.indexOf(seek,slot);
          if (slot != -1)
          {
              ++count;
              ++slot;
          }
      }    
      
      result -= count;
      
      count = 0;
      slot  = 0;
      seek = new String (new char[]{OPTIONAL_ESCAPE});      
      while (slot != -1)
      {
          slot = mask.indexOf(seek,slot);
          if (slot != -1)
          {
              ++count;
              ++slot;
          }
      }        
      
      result -= count;
      
      return result;
  }
  

}
