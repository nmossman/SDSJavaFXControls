package com.stddata.fx.uicontrols;


import java.util.ArrayList;

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

public class EditMaskHelper
{
  /***
   * Checks the position in the mask and returns true if the offset is a fixed lateral
   * The caret position should be adjusted prior to making this call refer to getMaskPositionFromCaret.
   * @param offset to check in the mask. 
   * @return true if a literal position
   */
  protected synchronized static boolean isLiteral (StringBuilder editMask, int offset)
  {
    boolean result = false;
    
    if (offset > -1 && offset < editMask.length())
    {
      if (editMask.charAt(offset)==EditMaskFactory.ESCAPE)
        result = true;
      else
      if (offset > 0 && 
          editMask.charAt(offset-1)==EditMaskFactory.ESCAPE)
      {
          result = true;
      }   


      
    }
    return result;
  }    
  
  /***
   * Checks the position in the mask and returns true if the offset is an optional lateral
   * The caret position should be adjusted prior to making this call refer to getMaskPositionFromCaret.
   * @param offset to check in the mask. 
   * @return true if a literal position
   */  
  
  protected synchronized static boolean isOptionalLiteral (StringBuilder editMask, int offset)
  {
    boolean result = false;
    
    if (offset > -1 && offset < editMask.length())
    {
      if (editMask.charAt(offset)==EditMaskFactory.OPTIONAL_ESCAPE)
        result = true;
      else
      if (offset > 0 && 
          editMask.charAt(offset-1)==EditMaskFactory.OPTIONAL_ESCAPE)
      {
          result = true;
      }   
      
    }
    return result;
  }      
  
    /***
   * Checks the position in the mask and returns true if the offset is a fixed or optional lateral
   * The caret position should be adjusted prior to making this call refer to getMaskPositionFromCaret.
   * @param offset to check in the mask. 
   * @return true if a literal position
   */
  
  protected synchronized static boolean isAnyLiteral (StringBuilder editMask, int offset)
  {
    boolean result = isLiteral (editMask,offset);
    
    if (!result)
        result = isOptionalLiteral (editMask, offset);

    return result;
  }        
  
  
  public synchronized static int getMaskPositionFromCaret (StringBuilder editMask, int caret)
  {
      int result = 0;
      int lc = 0;
      while (result < editMask.length() && lc < caret)
      {
          if ((editMask.charAt(result)==EditMaskFactory.ESCAPE ||
              editMask.charAt(result)==EditMaskFactory.OPTIONAL_ESCAPE))
             result +=1;
          else
          {    
             ++lc; 
             ++result;
          }
      }    
      
      return result;
  }        
  
 /*******
  * 
  * @param editMask editMask of inputfield
  * @param keypress the character to be tested
  * @param offset the position in the mask, not the caret position
  * @return 0 if the character is not valid else the valid character.  Lowercase characters will be 
  * converted to uppercase when the mask stipulates this.
  */
  
  public synchronized static char isValidForPosition (StringBuilder editMask, char keypress, int offset)
  {
    char result = 0;
    
    if (offset > -1 && offset < editMask.length())
    {
    
      switch (editMask.charAt(offset))
      {
        case EditMaskFactory.U_CASE_LETTER:
        {
          if (Character.isLowerCase(keypress))
            keypress = Character.toUpperCase(keypress);
          if (Character.isUpperCase(keypress))
            result = keypress;
          break;
        }
        case EditMaskFactory.L_CASE_LETTER:
        {
          if (Character.isUpperCase(keypress))
            keypress = Character.toLowerCase(keypress);
          if (Character.isLowerCase(keypress))
            result = keypress;
          break;
        }        
        case EditMaskFactory.D_DIGIT:
        {
          if (Character.isDigit(keypress) || Character.isSpaceChar(keypress))
            result = keypress;
          break;          
        }
        case EditMaskFactory.ANY_KEY:
          result = keypress;
          break;
        case EditMaskFactory.ANY_KEY_FORCED_UPPER:
        {
          if (Character.isLowerCase(keypress))
            result = Character.toUpperCase(keypress);          
          else
            result = keypress;
          break;
        }
        case EditMaskFactory.ANY_KEY_FORCED_LOWER:        
        {
          if (Character.isUpperCase(keypress))
            result = Character.toLowerCase(keypress);          
          else
            result = keypress;
          break;
        } 
        case EditMaskFactory.ALPHA_NUMERIC_LTDPUNC_ANYCASE:          
        case EditMaskFactory.ALPHA_NUMERIC_ANYCASE:
        {
          if (Character.isDigit(keypress) || Character.isLetter(keypress) || 
              Character.isSpaceChar(keypress))
             result = keypress;
          
          if (result ==0 && 
              editMask.charAt(offset)==EditMaskFactory.ALPHA_NUMERIC_LTDPUNC_ANYCASE &&
              isLtdPunc(keypress))
              result = keypress;           
          
          break;          
          
        }
        case EditMaskFactory.ALPHA_NUMERIC_LTDPUNC_FORCEUPPERCASE:        
        case EditMaskFactory.ALPHA_NUMERIC_FORCEUPPERCASE:   
        {
          if (Character.isDigit(keypress) || Character.isLetter(keypress) ||
              Character.isSpaceChar(keypress))
          {
            if (Character.isLowerCase(keypress))
              result = Character.toUpperCase(keypress);
            else
              result = keypress;
          }
          
          if (result ==0 && 
              editMask.charAt(offset)==EditMaskFactory.ALPHA_NUMERIC_LTDPUNC_FORCEUPPERCASE &&
              isLtdPunc(keypress))
              result = keypress;          
          
          break;          
        }
        case EditMaskFactory.ALPHA_NUMERIC_LTDPUNC_UPPERCASE:        
        case EditMaskFactory.ALPHA_NUMERIC_UPPERCASE:
        {
          if (Character.isDigit(keypress) || Character.isLetter(keypress))
          {
            if (Character.isDigit(keypress) || Character.isUpperCase(keypress))
              result = keypress;
          }
          
          if (result ==0 && 
              editMask.charAt(offset)==EditMaskFactory.ALPHA_NUMERIC_LTDPUNC_UPPERCASE &&
              isLtdPunc(keypress))
              result = keypress;
          break;
        }
        case EditMaskFactory.ALPHA_NUMERIC_LTDPUNC_LOWERCASE:        
        case EditMaskFactory.ALPHA_NUMERIC_LOWERCASE:
        {
          if (Character.isDigit(keypress) || Character.isLetter(keypress))
          {
            if (Character.isDigit(keypress) || Character.isLowerCase(keypress))
              result = keypress;
          }
          
         if (result ==0 && 
              editMask.charAt(offset)==EditMaskFactory.ALPHA_NUMERIC_LTDPUNC_LOWERCASE &&
              isLtdPunc(keypress))
              result = keypress;            
          
          break;
        }        
        case EditMaskFactory.ALPHA_NUMERIC_LTDPUNC_FORCELOWERCASE:        
        case EditMaskFactory.ALPHA_NUMERIC_FORCELOWERCASE:                
        {
          if (Character.isDigit(keypress) || Character.isLetter(keypress))
          {
            if (Character.isUpperCase(keypress))
              result = Character.toLowerCase(keypress);
            else
              result = keypress;
          }        
          
          if (result ==0 && 
              editMask.charAt(offset)==EditMaskFactory.ALPHA_NUMERIC_LTDPUNC_FORCELOWERCASE &&
              isLtdPunc(keypress))
              result = keypress;  
          
          break;
        }
        case EditMaskFactory.TELEPHONE_NUMBER:
        {
           if (Character.isDigit(keypress) ||
               keypress == ' ' || keypress =='-')
              result = keypress;
           break;
        }
        
      }
    }
    return result;
  }  
  
  public synchronized static boolean isLtdPunc (char ch)
  {
      
      String s = new String (new char [] {ch});
      String list = new String (EditMaskFactory.LTDPUNC);
      if (list.indexOf(s) > -1)
         return true;
      else
          return false;
  }  
  
  public static int getFirstInputPosition (StringBuilder editMask)
  {
    int result = getNextInputPosition (editMask, -1);
    
    return result;
  }
  
  
  /***
   * 
   * @param editMask an editMask
   * @param pos an actual caret position
   * @return the next caret position 
   */
  
  public synchronized static int getNextInputPosition (StringBuilder editMask, int pos)
  {
    int result = -1;
    int caret = 0;
    int count = 0;
    while (count < editMask.length ())
    {
        
        if (editMask.charAt(count)==EditMaskFactory.ESCAPE ||
            editMask.charAt(count)==EditMaskFactory.OPTIONAL_ESCAPE)
        {
            count +=2;
            ++caret;
        }   
        else
        {
          ++count;  
          if (caret <= pos)  
              ++caret;
          else
          {
              result = caret;
              break;
          }
        }

    }
    
    if (result == -1 && pos != 0)
        getFirstInputPosition (editMask);
    
    return result;
  }
  
  /**
   * Used to determine if an editmask only allows numbers to be input
   * @param mask the mask to be inspected.
   * @return returns true if only digits are found.
   */
  
  protected synchronized static boolean isNumberOnlyInput (StringBuilder mask)
  {
    boolean result = true;
    ArrayList <Character> allowedLiterals = new ArrayList () {{
         add (',');
         add ('.');
         add ('(');
         add (')');
         add ('-');
      }};             
    
    if (mask.toString().trim().isEmpty())
        result = false;
    
    int count = 0;
    while (result && count < mask.length())
    {
      if (!isAnyLiteral(mask,count))
      {
        if (mask.charAt(count)==EditMaskFactory.D_DIGIT)
          result = true;
        else
        {
          result = false;
        }
        ++count;
      }
      else
      if (allowedLiterals.contains(mask.charAt(count+1)))
           count +=2;
      else
           result = false;    
    }
    
    return result;    
  }
  /**
   * 
   * @param editMask
   * @return caret position of the last field the user can type into.
   */
  
  public synchronized static int getLastInputPosition (StringBuilder editMask)
  {
      int result = -1;
      StringBuilder sb = makeEmptyField (editMask,true);
      result = sb.lastIndexOf(" ");
      
      return result;
  }        
  
  public synchronized static StringBuilder makeEmptyField (StringBuilder editMask)
  {
      return makeEmptyField (editMask, false);
  }        
  
  /***
   * 
   * @param editMask
   * @param includeOptionalLiterals true if optional literals are to be displayed
   * @return A string with Fixed as well as optional literals
   */
  
  public synchronized static StringBuilder makeEmptyField (StringBuilder editMask,boolean includeOptionalLiterals)
  {
        StringBuilder data = new StringBuilder ("");
        int count = 0;
        int m = 0;

        while (m < editMask.length())
        {   

            if (isLiteral(editMask, m))
            {
                ++m;
                data.append(editMask.charAt(m));
            }
            else
            if (isOptionalLiteral (editMask, m))
            {
                ++m;
                if (includeOptionalLiterals)
                    data.append(editMask.charAt(m));    
                else
                    data.append(" ");
            }    
            else
               data.append(" ") ;
            ++ m;
            ++count;
        }
        
        return data;

    }          
  
  /****
   * 
   * @param editMask
   * @param sought the literal character
   * @param caret the current caret position
   * @return  -1 if no position is available otherwise the next input position after
   * the literal.  
   */
  
  public synchronized static int nextLiteral (StringBuilder editMask, char sought, int caret)
  {
    int result = -1;
    
    String template = makeEmptyField(editMask).toString();
    
    if (template.charAt(caret)==sought)
    {
       result = template.indexOf(' ',caret+1);
       if (result > -1)
          return result; 
    }    
    
    if (caret > -1 && caret < template.length()-1)
    {
      result = template.indexOf(sought,caret+1);
      if (result > -1)
          result = getNextInputPosition (editMask, result);
    }
    
    return result;
  }
  
  
  /***
   * Takes the editMask and the text from the control and applies optional literals
   * where a character appears to one side or the other of the optional literal.
   * @param editMask
   * @param text the source text to be checked.
   * @return String showing any optional and standard literals.
   */
  
  public synchronized static String showOptionalLiterals (StringBuilder editMask,StringBuilder text)
  {
        StringBuilder ed = makeEmptyField(editMask,true);
        StringBuilder std = makeEmptyField (editMask, false);
        StringBuilder result = new StringBuilder (text);
        int count = 0;
        
        for (count =1; count < text.length()-1;++count)
        {
          if (ed.charAt(count) != ' ' && std.charAt(count) == ' ')
          {
             if (result.charAt(count-1)!=' ' && result.charAt(count+1)!=' ')
                 result.setCharAt(count, ed.charAt(count));
             else
                result.setCharAt(count,' ');
          }
        }
        
        return result.toString();
  }        
 
  
  public synchronized static int[] getLiteralLocations (StringBuilder editMask)
  {
      int[] result = new int[0];
      int pos = 0;
      int count = 0;
      StringBuilder ef = makeEmptyField (editMask,true);
      
      ArrayList <Integer> al = new ArrayList <> ();
      
      while (count < ef.length())
      {
         if (ef.charAt(count)!= ' ')   
         {
             al.add(count);
         }
         ++count;
      }
      
      if (!al.isEmpty())
      {
        result = new int[al.size()];
        
        for (count =0; count < al.size(); ++ count)
        {
            result[count]=al.get(count);
        }
      }
      
      return result;
  }  
  
  /*****
   * Applies a sign to a textfield.  This routine performs a simple check so 
   * is not fool proof, the calling code must have made the correct decision to 
   * call this method.
   * @param editMask
   * @param text
   * @param sign Only + or - are valid
   * @return StringBuffer containing the text with the negative symbol(s) added or removed as necessary
   */
  
  public synchronized static StringBuilder processSign (StringBuilder editMask, StringBuilder text, char sign)
  {
      
      if (editMask.charAt(1)=='(' || editMask.charAt(1)=='-' || editMask.charAt(editMask.length()-1)=='-')
      {
          if (editMask.charAt(1)=='(' || editMask.charAt(1)=='-')
          {
              if (sign =='+')
                  text.setCharAt(0, ' ');
              else
              if (sign == '-')    
                  text.setCharAt(0, editMask.charAt(1));
          }    
          
          if (editMask.charAt(editMask.length()-1)=='-' || editMask.charAt(editMask.length()-1)==')')
          {
              if (sign =='+')
                  text.setCharAt(text.length()-1, ' ');
              else
              if (sign == '-')    
                  text.setCharAt(text.length()-1, editMask.charAt(editMask.length()-1));
          }        
                    
      }    
      
      
      return text;
  }        
  
  /*****
   * 
   * @param editMask
   * @param literalCharLocation based on the position in the text (i.e. caret position)
   * @param text
   * @param style the style of the editMask, used for special processing support
   * @return 
   */
  
  public synchronized static  StringBuilder tidyLiteralInput (StringBuilder editMask, int literalCharLocation, 
                       StringBuilder text, char style)
  {
    
    int lits[] = getLiteralLocations(editMask);
    int maskLoc = getMaskPositionFromCaret (editMask,literalCharLocation);
    StringBuilder section = new StringBuilder (text.substring(0, literalCharLocation));
    
    boolean negativeNumber = false;
    if (style==EditMaskFactory.STYLE_NUMERIC) 
    {
        if (editMask.charAt(1)!=EditMaskFactory.D_DIGIT && text.charAt(0)!=' ')
            negativeNumber = true;
        else
        if (editMask.charAt(editMask.length()-1)!=EditMaskFactory.D_DIGIT && text.charAt(text.length()-1)!= ' ')
            negativeNumber = true;
    }    
    
    
    int count = 0;
    for (count=0; count < lits.length; ++ count)
    {
        if (lits[count]<literalCharLocation)
            section.setCharAt(lits[count], ' ');
    }
    String data = section.toString().replaceAll("( )", "").trim();
            
    count = maskLoc-1;
    int slot = literalCharLocation-1;
    int pos = data.length()-1;
    while (count > -1 && slot > -1)
    {
        if (count > 0 && 
                (editMask.charAt(count-1)==EditMaskFactory.ESCAPE || 
                 editMask.charAt(count-1)==EditMaskFactory.OPTIONAL_ESCAPE))
        {
            --count;
        }    
        
        if (!isAnyLiteral(editMask, count))
        {
             
            char ch = ' ';
            if (pos > -1)
              ch = data.charAt(pos);
            
            if (isValidForPosition(editMask, ch,count)!=0  ||
                ch == ' ')
            {
                //is valid for position
                text.setCharAt(slot, ch);
                --pos;
            }
            else
               text.setCharAt(slot, ' '); 
        }
        --slot;
        --count;
    }
    
    if (negativeNumber)
    {
        if (editMask.charAt(1)!=EditMaskFactory.D_DIGIT)
            text.setCharAt(0, editMask.charAt(1));

        if (editMask.charAt(editMask.length()-1)!=EditMaskFactory.D_DIGIT)
            text.setCharAt(text.length()-1,editMask.charAt(editMask.length()-1));
    }    
    
    text = new StringBuilder (showOptionalLiterals (editMask, text));
    
    return text;
    
  }  
  
  /****
   * Called once focus has been lost to tidy the input field up.  This was designed
   * for use with numeric fields and will tidy both sides of a decimal point.
   * @param editMask Editmask to check against
   * @param text Text from buffer.  This is amended during the process
   * @param spec A specification where information about the editMask can be obtained.
   */
  
  public synchronized static StringBuilder tidyPostInput (StringBuilder editMask, StringBuilder text,
            EditMaskSpecification spec)
  {
    if (spec.getMaskStyle()==EditMaskFactory.STYLE_DATE || 
        spec.getMaskStyle()==EditMaskFactory.STYLE_DATE_TIME || 
        spec.getMaskStyle()==EditMaskFactory.STYLE_TIME)
    {
        return tidyDateTimeTextPostInput (editMask, text,spec);
    }    
      
    StringBuilder result = new StringBuilder (text)  ;
    StringBuilder ef = makeEmptyField (editMask,true);
    int lits[] = getLiteralLocations(editMask);    
    int caret = 1+ef.lastIndexOf(" ");
    
    if (ef.toString().trim().equals("") || caret == -1)
        caret = ef.length()-1;
    else    
    if (spec.getMaskStyle()==EditMaskFactory.STYLE_NUMERIC && spec.getDecimalPlaces()> 0)
    {
        caret = ef.lastIndexOf(spec.getDecimalSeparator());
    }        
    else
    if (lits.length > 0 && lits[lits.length-1] == result.length())
    {
        caret = lits[lits.length-1];
    }    
    


    int maskLoc = getMaskPositionFromCaret (editMask,caret);
    StringBuilder section = new StringBuilder (result.substring(0, caret));
    
    boolean negativeNumber = false;
    if (spec.getMaskStyle()==EditMaskFactory.STYLE_NUMERIC) 
    {
        if (editMask.charAt(1)!=EditMaskFactory.D_DIGIT && result.charAt(0)!=' ')
            negativeNumber = true;
        else
        if (editMask.charAt(editMask.length()-1)!=EditMaskFactory.D_DIGIT && result.charAt(result.length()-1)!= ' ')
            negativeNumber = true;
    }    
    
    
    int count = 0;
    for (count=0; count < lits.length; ++ count)
    {
        if (lits[count]<caret)
            section.setCharAt(lits[count], ' ');
    }
    String data = section.toString().replaceAll("( )", "").trim();
            
    count = maskLoc-1;
    int slot = caret-1;
    int pos = data.length()-1;
    while (count > -1 && slot > -1)
    {
        if (count > 0 && 
                (editMask.charAt(count-1)==EditMaskFactory.ESCAPE || 
                 editMask.charAt(count-1)==EditMaskFactory.OPTIONAL_ESCAPE))
        {
            --count;
        }    
        
        if (!isAnyLiteral(editMask, count))
        {
             
            char ch = ' ';
            if (pos > -1)
              ch = data.charAt(pos);
            
            if (isValidForPosition(editMask, ch,count)!=0  ||
                ch == ' ')
            {
                //is valid for position
                result.setCharAt(slot, ch);
                --pos;
            }
            else
               result.setCharAt(slot, ' '); 
        }
        else
        if (!isLiteral(editMask,count)) //over wright optional literals
        {
            result.setCharAt(slot, ' ');
        }    
        --slot;
        --count;
    }
    
    if (spec.getMaskStyle()==EditMaskFactory.STYLE_NUMERIC && spec.getDecimalPlaces()> 0)
    {
        caret = ef.lastIndexOf(spec.getDecimalSeparator());
        maskLoc = getMaskPositionFromCaret (editMask,caret);
        if(caret > -1)
        {
            if (result.charAt(caret-1)==' ')
                result.setCharAt(caret-1, '0');
            
            ++caret;
            maskLoc+=2;
            while (caret < result.length())
            {    
                if (result.charAt(caret)==' ' && !isAnyLiteral (editMask,maskLoc))
                {
                    result.setCharAt(caret, '0');
                }   
                else
                if (isAnyLiteral(editMask, maskLoc))
                    ++maskLoc;
                ++caret;
                ++maskLoc;
            }
        }    
    }    
    
    result = new StringBuilder(showOptionalLiterals (editMask,result));
    
    if (negativeNumber)
    {
        if (editMask.charAt(1)!=EditMaskFactory.D_DIGIT)
            result.setCharAt(0, editMask.charAt(1));

        if (editMask.charAt(editMask.length()-1)!=EditMaskFactory.D_DIGIT)
            result.setCharAt(result.length()-1,editMask.charAt(editMask.length()-1));
    }    
    
    return result;
    
  }  
  
  protected static synchronized StringBuilder tidyDateTimeTextPostInput (StringBuilder editMask, StringBuilder text,
            EditMaskSpecification spec)
  {
      StringBuilder result = new StringBuilder (text);
      for (int count = 0; count < result.length(); ++ count)
      {
          if (result.charAt(count)== ' ')
          {
              int m = getMaskPositionFromCaret (editMask,count);
              if (editMask.charAt(m)==EditMaskFactory.D_DIGIT)
                result.setCharAt(count, '0');
          }    
      }  
      
      return result;
  }        
  
  public static synchronized  void deleteSelectedText(int start, 
                int end,StringBuilder text, StringBuilder editMask)
  {
    int pos = getMaskPositionFromCaret (editMask,start);
    while (start < text.length() && start <= end && pos < editMask.length())
    {
        if (EditMaskHelper.isAnyLiteral(editMask,  pos))
        {
            ++start;
            pos+=2;
        }    
        else
        {
            text.setCharAt(start, ' ');
            ++start;
            ++pos;
        }    
     }          
  }   
  
  public synchronized  static void debugFieldAndMask (StringBuilder editMask, StringBuilder text)
  {
      char tab = '\t';
      for (int count =0; count < editMask.length(); ++ count)
      {
          System.out.print(count+"\t");
      }    
      System.out.println ();
      
      for (int count =0; count < editMask.length(); ++ count)
      {
          System.out.print(editMask.charAt(count)+"\t");
      }          
      
      System.out.println ();
      
      for (int count =0; count < text.length(); ++ count)
      {
          System.out.print(text.charAt(count)+"\t");
      }                
      System.out.println ();      
      
      
  }        
          
  /****
   * Used to find the number of characters in a subfield.  Works by
   * comparing the mask at the start of a subfield and looking for the 
   * end of the mask or first non matching character.  Must useful in 
   * date/time etc
   * @param start Caret position, or field start in user text
   * @param editMask editMask for the field
   * @param pos mask position which equates to start
   * @return the end of the current subfield in text
   */
  
  public synchronized static int getEndOfSubField (int start,StringBuilder editMask,
            int pos)  
  {
      int result = start;
      int maskStart = getMaskPositionFromCaret(editMask, start);
      int end = maskStart;
      while (end < editMask.length() && 
            editMask.charAt(end) != EditMaskFactory.ESCAPE &&
            editMask.charAt(end) != EditMaskFactory.OPTIONAL_ESCAPE)
      {
        ++end;
      }        
      
      result = (end-maskStart)+start;
      
      return result;
  }     
  
  /****
   * Checks to see if a mask is valid for use with the software
   * @param editMask Editmask to be checked
   * @return true if the mask is valid or false as soon as the first invalid mask
   * character is detected
   */
  
  public synchronized static boolean isValidMask (StringBuilder editMask)
  {
      boolean result = true;
           
      ArrayList <Character> allowedMask = new ArrayList () {{
        add (EditMaskFactory.U_CASE_LETTER);
        add (EditMaskFactory.L_CASE_LETTER);
        add (EditMaskFactory.D_DIGIT);
        add (EditMaskFactory.ANY_KEY);
        add (EditMaskFactory.ANY_KEY_FORCED_UPPER);
        add (EditMaskFactory.ANY_KEY_FORCED_LOWER);
        add (EditMaskFactory.ALPHA_NUMERIC_UPPERCASE);
        add (EditMaskFactory.ALPHA_NUMERIC_LOWERCASE);
        add (EditMaskFactory.ALPHA_NUMERIC_FORCEUPPERCASE);
        add (EditMaskFactory.ALPHA_NUMERIC_FORCELOWERCASE);
        add (EditMaskFactory.ALPHA_NUMERIC_LTDPUNC_UPPERCASE);
        add (EditMaskFactory.ALPHA_NUMERIC_LTDPUNC_LOWERCASE);
        add (EditMaskFactory.ALPHA_NUMERIC_LTDPUNC_FORCEUPPERCASE);
        add (EditMaskFactory.ALPHA_NUMERIC_LTDPUNC_FORCELOWERCASE);
        add (EditMaskFactory.ALPHA_NUMERIC_LTDPUNC_ANYCASE);
        add (EditMaskFactory.TELEPHONE_NUMBER);
        add (EditMaskFactory.ALPHA_NUMERIC_ANYCASE);
        add (EditMaskFactory.NO_INPUT);}};

      if (editMask == null || editMask.toString().trim().isEmpty())
          result = false;
      
      int count = 0;
      while (result && count < editMask.length() )
      {    
        if (isAnyLiteral (editMask,count))
        {    
            count +=2;
        }
        else
        {
            if (allowedMask.contains(editMask.charAt(count)))
                ++ count;
            else
            {
                result = false;
            }    
        }    
      }
      
      
      return result;
  }        
}
