package com.stddata.fx.uicontrols;

import java.util.Locale;
import org.junit.Test;
import static org.junit.Assert.*;

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
public class EditMaskHelperTest
{
    @Test
    public void testMakeEmptyField ()
    {
       try
       {    
            EditMaskSpecification glAccts = new EditMaskSpecification ('u',"-",3,3,2);
            EditMaskFactory.getMask(glAccts);
            StringBuilder r = EditMaskHelper.makeEmptyField(glAccts.getEditMask());
            assertEquals ("   -   -  ".toString(),r.toString());

            EditMaskSpecification moneys = new EditMaskSpecification (18,2,".",",",EditMaskFactory.NEG_FMT_BRACKETS); 
            EditMaskFactory.getNumberMask(moneys);             
            r = EditMaskHelper.makeEmptyField(moneys.getEditMask());
            String exp = "                        .   ";
            assertEquals (exp.length(),r.toString().length());
            assertEquals (exp,r.toString());
        }
        catch (Exception x)
        {
            fail(x.toString());
        }           
        
        
    }        

    @Test
    public void testGetMaskPositionFromCaret()
    {
       try
       {     
            EditMaskSpecification glAccts = new EditMaskSpecification ('u',"-",3,3,2);    
            EditMaskFactory.getMask (glAccts);
            int r = EditMaskHelper.getMaskPositionFromCaret(glAccts.getEditMask(), 0);
            assertEquals ("First Position",0,r);

            r = EditMaskHelper.getMaskPositionFromCaret(glAccts.getEditMask(), 3);
            assertEquals (3,r);

            r = EditMaskHelper.getMaskPositionFromCaret(glAccts.getEditMask(), 4);
            assertEquals (5,r);        

            r = EditMaskHelper.getMaskPositionFromCaret(glAccts.getEditMask(), 9);
            assertEquals (11,r);   
            
            EditMaskSpecification moneys = new EditMaskSpecification (18, 2, ".", ",", EditMaskFactory.NEG_FMT_BRACKETS);
            EditMaskFactory.getMask(moneys);
            StringBuilder editText = new StringBuilder ("          66,443,322.67)");
            r = EditMaskHelper.getMaskPositionFromCaret(moneys.getEditMask(), 17);
            assertEquals (22,r);
            
            
        }
        catch (Exception x)
        {
            fail(x.toString());
        }           
    }

    @Test
    public void testIsValidForPosition()
    {
        EditMaskSpecification glAccts = new EditMaskSpecification ('u',"-",3,3,2);        
        
        assertEquals (1,1);
    }

    @Test
    public void testIsLtdPunc()
    {
        assertEquals (1,1);        
    }

    @Test
    public void testGetFirstInputPosition()
    {
        try
        {    
            EditMaskSpecification glAccts = new EditMaskSpecification ('u',"-",3,3,2); 
            EditMaskFactory.getMask (glAccts);
            int r = EditMaskHelper.getFirstInputPosition(glAccts.getEditMask());
            assertEquals (0,r);  
        }
        catch (Exception x)
        {
            fail(x.toString());
        }            
    }

    @Test
    public void testGetNextInputPosition()
    {
        try
        {    
            EditMaskSpecification glAccts = new EditMaskSpecification ('u',"-",3,3,2);   
            EditMaskFactory.getMask (glAccts);
            int r = EditMaskHelper.getNextInputPosition(glAccts.getEditMask(), 1);
            assertEquals (2,r);
        
            r = EditMaskHelper.getNextInputPosition(glAccts.getEditMask(), 2);
            assertEquals (4,r);        
            r = EditMaskHelper.getNextInputPosition(glAccts.getEditMask(), 6);
            assertEquals (8,r);                
        }
        catch (Exception x)
        {
            fail(x.toString());
        }    

    }
    
    @Test
    public void testNextLiteral ()
    {
        try
        {    
            EditMaskSpecification glAccts = new EditMaskSpecification ('u',"-",3,3,2); 
            EditMaskFactory.getMask(glAccts);
            int r = EditMaskHelper.nextLiteral(glAccts.getEditMask(), '.', 0);
            assertEquals (-1,r);

            r = EditMaskHelper.nextLiteral(glAccts.getEditMask(), '-', 1);
            assertEquals (4,r);

            r = EditMaskHelper.nextLiteral(glAccts.getEditMask(), '-', 3);
            assertEquals (4,r);        

            r = EditMaskHelper.nextLiteral(glAccts.getEditMask(), '-', 6);
            assertEquals (8,r);        
        }
        catch (Exception x)
        {
            fail(x.toString());
        }           
    }     
    
    @Test
    public void testTidyLiteralInput ()
    {
        EditMaskSpecification moneys = new EditMaskSpecification (18,2,".",",",EditMaskFactory.NEG_FMT_BRACKETS);                
        EditMaskFactory.getNumberMask(moneys);     
        
        StringBuilder text = new StringBuilder (" 1  ,4          ,456    .   ");
        String exp  = "                  14,456.   ";
        StringBuilder r = EditMaskHelper.tidyLiteralInput(moneys.getEditMask(), 24, text,EditMaskFactory.STYLE_NUMERIC);
        assertEquals (exp.length(),r.toString().length());
        assertEquals (exp,r.toString());        
        
        exp  = "(                 14,456.  )";        
        text = new StringBuilder ("(1  ,4          ,456    .  )");        
        r = EditMaskHelper.tidyLiteralInput(moneys.getEditMask(), 24, text,EditMaskFactory.STYLE_NUMERIC);        
        assertEquals (exp.length(),r.toString().length());
        assertEquals (exp,r.toString());                
        
    }      
    
    @Test
    public void testProcessSign ()
    {
        EditMaskSpecification moneys = new EditMaskSpecification (18,2,".",",",EditMaskFactory.NEG_FMT_BRACKETS);                
        EditMaskFactory.getNumberMask(moneys);     
        
        StringBuilder text = new StringBuilder (" 1  ,4          ,456    .   ");
        StringBuilder exp  = new StringBuilder ("(1  ,4          ,456    .  )");
        StringBuilder r = EditMaskHelper.processSign(moneys.getEditMask(), text, '-');
        
        assertEquals (exp.length(),r.toString().length());
        assertEquals (exp.toString(),r.toString());        
        
        r = EditMaskHelper.processSign(moneys.getEditMask(), text, '-');        
        assertEquals (exp.length(),r.toString().length());
        assertEquals (exp.toString(),r.toString());        
        
        exp = new StringBuilder (" 1  ,4          ,456    .   ");
        r = EditMaskHelper.processSign(moneys.getEditMask(), text, '+');        
        assertEquals (exp.length(),r.toString().length());
        assertEquals (exp.toString(),r.toString());                
        
        
    }      
    
    @Test
    public void testTidyPostInput ()
    {
        EditMaskSpecification moneys = new EditMaskSpecification (18,2,".",",",EditMaskFactory.NEG_FMT_BRACKETS);                
        EditMaskFactory.getNumberMask(moneys);     
        
        StringBuilder text = new StringBuilder (" 1  ,4          ,456    .   ");
        String exp  = "                  14,456.00 ";
        StringBuilder r = EditMaskHelper.tidyPostInput(moneys.getEditMask(), text,moneys);
        assertEquals (exp.length(),r.toString().length());
        assertEquals (exp,r.toString());        
        
        exp  = "(                 14,456.00)";        
        text = new StringBuilder ("(1  ,4          ,456    .  )");        
        r = EditMaskHelper.tidyPostInput(moneys.getEditMask(), text,moneys);
        assertEquals (exp.length(),r.toString().length());
        assertEquals (exp,r.toString());  
        
        exp  = "(                 14,456.02)";        
        text = new StringBuilder ("(1  ,4          ,456    . 2)");        
        r = EditMaskHelper.tidyPostInput(moneys.getEditMask(), text,moneys);
        assertEquals (exp.length(),r.toString().length());
        assertEquals (exp,r.toString());          
        
        moneys = new EditMaskSpecification (18,2,".",",",EditMaskFactory.NEG_FMT_TRAILING);  
        EditMaskFactory.getNumberMask(moneys);     
        exp  = "                 14,456.02-";        
        text = new StringBuilder ("1  ,4          ,456    . 2-");        
        r = EditMaskHelper.tidyPostInput(moneys.getEditMask(), text,moneys);
        assertEquals (exp.length(),r.toString().length());
        assertEquals (exp,r.toString());                  
        
        exp  = "                      0.00 ";        
        text = new StringBuilder ("                       .   ");        
        r = EditMaskHelper.tidyPostInput(moneys.getEditMask(), text,moneys);
        assertEquals (exp.length(),r.toString().length());
        assertEquals (exp,r.toString());                          
        
        
        moneys = new EditMaskSpecification (18,0,".",",",EditMaskFactory.NEG_FMT_BRACKETS);                
        EditMaskFactory.getNumberMask(moneys);     
                                //"(DDD,DDD,DDD,DDD,DDD,DDD)"  
        text = new StringBuilder (" 1  ,4          ,456     ");
        exp  =                    "                  14,456 ";
        r = EditMaskHelper.tidyPostInput(moneys.getEditMask(), text,moneys);
        assertEquals (exp.length(),r.toString().length());
        assertEquals (exp,r.toString());                
                                //"(DDD,DDD,DDD,DDD,DDD,DDD)"  
        text = new StringBuilder ("( 1 ,4          ,456    )");
        exp  =                    "(                 14,456)";
        r = EditMaskHelper.tidyPostInput(moneys.getEditMask(), text,moneys);
        assertEquals (exp.length(),r.toString().length());
        assertEquals (exp,r.toString());                        
        

        moneys = new EditMaskSpecification (18,0,".",",",EditMaskFactory.NEG_FMT_TRAILING);                
        EditMaskFactory.getNumberMask(moneys);     
        
                                //"DDD,DDD,DDD,DDD,DDD,DDD)"      
        text = new StringBuilder (" 1 ,4          ,456     ");
        exp  =                    "                 14,456 ";
        r = EditMaskHelper.tidyPostInput(moneys.getEditMask(), text,moneys);
        assertEquals (exp.length(),r.toString().length());
        assertEquals (exp,r.toString());                        
                                //"DDD,DDD,DDD,DDD,DDD,DDD)"      
        text = new StringBuilder (" 1 ,4          ,456    -");
        exp  =                    "                 14,456-";
        r = EditMaskHelper.tidyPostInput(moneys.getEditMask(), text,moneys);
        assertEquals (exp.length(),r.toString().length());
        assertEquals (exp,r.toString());                                
        
        
        moneys = new EditMaskSpecification (18,0,".",",",EditMaskFactory.NEG_FMT_LEADING);                
        EditMaskFactory.getNumberMask(moneys);     
        
                                //"-DDD,DDD,DDD,DDD,DDD,DDD"      
        text = new StringBuilder ("  1 ,4          ,456    ");
        exp  =                    "                  14,456";
        r = EditMaskHelper.tidyPostInput(moneys.getEditMask(), text,moneys);
        assertEquals (exp.length(),r.toString().length());
        assertEquals (exp,r.toString());                        
                                //"-DDD,DDD,DDD,DDD,DDD,DDD"      
        text = new StringBuilder ("- 1 ,4          ,456    ");
        exp  =                    "-                 14,456";
        r = EditMaskHelper.tidyPostInput(moneys.getEditMask(), text,moneys);
        assertEquals (exp.length(),r.toString().length());
        assertEquals (exp,r.toString());                                        
        
        EditMaskSpecification times = new EditMaskSpecification (Locale.ENGLISH, EditMaskFactory.STYLE_TIME); 
        try
        {    
            EditMaskFactory.getMask(times);
        }
        catch (Exception x)
        {
            fail (x.getMessage());
        }    
        
        text = new StringBuilder (" 1: 1:1 ");
        exp = "01:01:10";
        r = EditMaskHelper.tidyPostInput(times.getEditMask(),text,times);
        assertEquals (exp,r.toString());
        
    }        
            
    @Test
    public void testTidyPostInputPerfectValues ()
    {
        EditMaskSpecification moneys = new EditMaskSpecification (18,2,".",",",EditMaskFactory.NEG_FMT_BRACKETS);                
        EditMaskFactory.getNumberMask(moneys);     
        
        StringBuilder text = new StringBuilder ("                  14,456.00 ");
        String exp  =                           "                  14,456.00 ";
        StringBuilder r = EditMaskHelper.tidyPostInput(moneys.getEditMask(), text,moneys);
        assertEquals (exp.length(),r.toString().length());
        assertEquals (exp,r.toString());        
        
        exp  = "(                 14,456.00)";        
        text = new StringBuilder ("(                 14,456.00)");        
        r = EditMaskHelper.tidyPostInput(moneys.getEditMask(), text,moneys);
        assertEquals (exp.length(),r.toString().length());
        assertEquals (exp,r.toString());  
        
        exp  = "(                 14,456.02)";        
        text = new StringBuilder ("(                 14,456.02)");        
        r = EditMaskHelper.tidyPostInput(moneys.getEditMask(), text,moneys);
        assertEquals (exp.length(),r.toString().length());
        assertEquals (exp,r.toString());          
        
        moneys = new EditMaskSpecification (18,2,".",",",EditMaskFactory.NEG_FMT_TRAILING);  
        EditMaskFactory.getNumberMask(moneys);             
        exp  = "                 14,456.02-";        
        text = new StringBuilder ("                 14,456.02-");        
        r = EditMaskHelper.tidyPostInput(moneys.getEditMask(), text,moneys);
        assertEquals (exp.length(),r.toString().length());
        assertEquals (exp,r.toString());                  
        
        moneys = new EditMaskSpecification (18,0,".",",",EditMaskFactory.NEG_FMT_BRACKETS);                
        EditMaskFactory.getNumberMask(moneys);     
                                //"(DDD,DDD,DDD,DDD,DDD,DDD)"  
        text = new StringBuilder ("                  14,456 ");
        exp  =                    "                  14,456 ";
        r = EditMaskHelper.tidyPostInput(moneys.getEditMask(), text,moneys);
        assertEquals (exp.length(),r.toString().length());
        assertEquals (exp,r.toString());                
                                //"(DDD,DDD,DDD,DDD,DDD,DDD)"  
        text = new StringBuilder ("(                 14,456)");
        exp  =                    "(                 14,456)";
        r = EditMaskHelper.tidyPostInput(moneys.getEditMask(), text,moneys);
        assertEquals (exp.length(),r.toString().length());
        assertEquals (exp,r.toString());                        
        
        
        moneys = new EditMaskSpecification (18,0,".",",",EditMaskFactory.NEG_FMT_TRAILING);                
        EditMaskFactory.getNumberMask(moneys);     
        
                                //"DDD,DDD,DDD,DDD,DDD,DDD)"      
        text = new StringBuilder ("                 14,456 ");
        exp  =                    "                 14,456 ";
        r = EditMaskHelper.tidyPostInput(moneys.getEditMask(), text,moneys);
        assertEquals (exp.length(),r.toString().length());
        assertEquals (exp,r.toString());                        
                                //"DDD,DDD,DDD,DDD,DDD,DDD)"      
        text = new StringBuilder ("                 14,456-");
        exp  =                    "                 14,456-";
        r = EditMaskHelper.tidyPostInput(moneys.getEditMask(), text,moneys);
        assertEquals (exp.length(),r.toString().length());
        assertEquals (exp,r.toString());                                
        
        
        moneys = new EditMaskSpecification (18,0,".",",",EditMaskFactory.NEG_FMT_LEADING);                
        EditMaskFactory.getNumberMask(moneys);             
                                //"-DDD,DDD,DDD,DDD,DDD,DDD"      
        text = new StringBuilder ("                  14,456");
        exp  =                    "                  14,456";
        r = EditMaskHelper.tidyPostInput(moneys.getEditMask(), text,moneys);
        assertEquals (exp.length(),r.toString().length());
        assertEquals (exp,r.toString());                        
                                //"-DDD,DDD,DDD,DDD,DDD,DDD"      
        text = new StringBuilder ("-                 14,456");
        exp  =                    "-                 14,456";
        r = EditMaskHelper.tidyPostInput(moneys.getEditMask(), text,moneys);
        assertEquals (exp.length(),r.toString().length());
        assertEquals (exp,r.toString());                                        
        
        
    }            
    
    @Test
    public void testIsValidMask ()
    {
              //INTRASTAT_CODE:
        int size = 10;
        StringBuilder mask = new StringBuilder ();
        mask.append("DDDD").append(EditMaskFactory.ESCAPE).append(" ").append("DD").append(EditMaskFactory.ESCAPE).append(" ").append("DD");

        boolean result = EditMaskHelper.isValidMask(mask);
        assertEquals (true, result);
        
        mask.setCharAt(2, 'w');
        result = EditMaskHelper.isValidMask(mask);
        assertEquals (false, result);        

    }        
    
    @Test
    public void testIsNumericInputMask ()
    {
        StringBuilder mask = new StringBuilder ("DDDD");
        
        boolean result = EditMaskHelper.isNumberOnlyInput(mask);
        assertEquals (true,result);
        
        mask.append (EditMaskFactory.ESCAPE).append('.').append ("DD");
        result = EditMaskHelper.isNumberOnlyInput(mask);
        assertEquals (true,result);        
        
        mask = new StringBuilder ("DD,DD");
        result = EditMaskHelper.isNumberOnlyInput(mask);
        assertEquals (false,result);                
        
        mask = new StringBuilder ("DDDA");
        result = EditMaskHelper.isNumberOnlyInput(mask);
        assertEquals (false,result);                        
    }        
    
}
