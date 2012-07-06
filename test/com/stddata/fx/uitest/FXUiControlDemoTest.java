package com.stddata.fx.uitest;

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




import com.stddata.fx.uicontrols.DatePickerDialog;
import com.stddata.fx.uicontrols.SDSDateChooserPanel;
import com.stddata.fx.uicontrols.TextFieldMaskControlSkin;
import java.util.Calendar;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import org.jemmy.control.Wrap;
import org.jemmy.fx.AppExecutor;
import org.jemmy.fx.Root;
import org.jemmy.fx.control.TableViewWrap;
import org.jemmy.fx.control.TextInputControlWrap;
import org.jemmy.input.KeyboardImpl;
import org.jemmy.interfaces.*;
import org.jemmy.lookup.LookupCriteria;
import org.junit.*;


public class FXUiControlDemoTest {

    public Wrap<? extends TextInputControl> standard;
    public Wrap<? extends TextFieldMaskControlSkin> basic;
    public Wrap<? extends TextFieldMaskControlSkin> glcode;
    public Wrap<? extends TextFieldMaskControlSkin> money;
    public Wrap<? extends TextFieldMaskControlSkin> maskdate;
    public Wrap<? extends TextFieldMaskControlSkin> maskFRdate;    
    public Wrap<? extends TextFieldMaskControlSkin> maskTime;        
    public Wrap<? extends TextFieldMaskControlSkin> maskLookup;            
    public Wrap<? extends Button> OK;
    
    public FXUiControlDemoTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        AppExecutor.executeNoBlock(com.stddata.fx.uitest.FXUiControlDemo.class);
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() 
    {

        Parent<Node> parent = Root.ROOT.lookup().as(Parent.class, Node.class);
        LookupCriteria standardTextFieldLookup  = new LookupCriteria () {

            @Override
            public boolean check(Object cntrl)
            {
                boolean result = false;
                if (cntrl instanceof TextField)
                {
                    TextField tv = (TextField)cntrl;
                    if (tv.getUserData() != null && tv.getUserData().toString().equals("Normal"))
                        result = true;
                }    
                return result;
            }
        };
        
        LookupCriteria buttonLookup  = new LookupCriteria () {

            @Override
            public boolean check(Object cntrl)
            {
                boolean result = false;
                if (cntrl instanceof javafx.scene.control.Button)
                {
                    result = true;
                }    
                return result;
            }
        };        
        
        SDSTextFieldLookup sdsTextFieldLookup = new SDSTextFieldLookup ();
        standard=  parent.lookup(TextInputControl.class,standardTextFieldLookup).wrap(); 
        
        OK=  parent.lookup(javafx.scene.control.Button.class,buttonLookup).wrap();         
        
        
        sdsTextFieldLookup.search = "Basic";
        basic =  parent.lookup(TextFieldMaskControlSkin.class,sdsTextFieldLookup).wrap(); 
        basic = this.setTextComparisionOff(basic);  
        
        sdsTextFieldLookup.search = "GL Code";
        glcode = parent.lookup(TextFieldMaskControlSkin.class,sdsTextFieldLookup).wrap(); 
        glcode = this.setTextComparisionOff(glcode);  
        
        sdsTextFieldLookup.search = "Money";
        money = parent.lookup(TextFieldMaskControlSkin.class,sdsTextFieldLookup).wrap(); 
        money = this.setTextComparisionOff(money);        
        
        sdsTextFieldLookup.search = "Mask Date";
        maskdate = parent.lookup(TextFieldMaskControlSkin.class,sdsTextFieldLookup).wrap(); 
        maskdate = this.setTextComparisionOff(maskdate);                
        
        sdsTextFieldLookup.search = "FR Mask Date";
        maskFRdate = parent.lookup(TextFieldMaskControlSkin.class,sdsTextFieldLookup).wrap(); 
        maskFRdate = this.setTextComparisionOff(maskFRdate);                        
        
        sdsTextFieldLookup.search = "Mask Time";
        maskTime = parent.lookup(TextFieldMaskControlSkin.class,sdsTextFieldLookup).wrap(); 
        maskTime = this.setTextComparisionOff(maskTime);                                
        
        sdsTextFieldLookup.search = "MaskLookup";
        maskLookup = parent.lookup(TextFieldMaskControlSkin.class,sdsTextFieldLookup).wrap(); 
        maskLookup = this.setTextComparisionOff(maskLookup);                                        
    }

    @After
    public void tearDown() {
    }

    public Wrap setTextComparisionOff (Wrap ticw)
    {
        if (ticw instanceof TextInputControlWrap)
        {
            TextInputControlWrap ti = (TextInputControlWrap)ticw;
            ti.input().setInputComparison(false);
        }  
        
        return ticw;
    }        
    
    
    @Test
    public void normalTextField() throws InterruptedException 
    {
        if (standard ==null)
            Assert.assertEquals(null, standard);

        standard.as(Text.class).type("Type Me In");
        String text = standard.getControl().getText();     
        Assert.assertEquals("Type Me In", text);

        standard.keyboard().pressKey(KeyboardImpl.KeyboardButtons.TAB);
    }
    
    
    @Test
    public void basicTextField() throws InterruptedException
    {    

        //Basic field allows up to 20 characters of anything you like


        basic.as(Text.class).type("The slow Fox Jumped out");

        String text = basic.getControl().getText();
        Assert.assertEquals ("out slow Fox Jumped ",text);
        
        
        basic.getControl().selectAll();
        basic.keyboard().pressKey(KeyboardImpl.KeyboardButtons.DELETE);
        text = basic.getControl().getText();
        Assert.assertEquals (20,text.length());
        Assert.assertEquals ("",text.trim());

    }    
    
    @Test
    public void glcodeTextField () throws InterruptedException
    {
        glcode.getControl().selectAll();
        glcode.keyboard().pressKey(KeyboardImpl.KeyboardButtons.DELETE);
        String text = glcode.getControl().getText();
        Assert.assertEquals (10,text.length());
        Assert.assertEquals ("   -   -  ",text);        
        
        glcode.as(Text.class).type("BcstaPOL");        
        text = glcode.getControl().getText();
        Assert.assertEquals (10,text.length());
        Assert.assertEquals ("BCS-TAP-OL",text); 
        
        glcode.as(Text.class).type("X");        
        glcode.as(Text.class).type("-");                
        glcode.as(Text.class).type("cop");        
        text = glcode.getControl().getText();        
        Assert.assertEquals (10,text.length());
        Assert.assertEquals ("XCS-COP-OL",text); 
        
        glcode.as(Text.class).type("-");                
        glcode.as(Text.class).type("J");                
        text = glcode.getControl().getText();        
        Assert.assertEquals (10,text.length());
        Assert.assertEquals ("XCS-COP-JL",text);         
        
        glcode.keyboard().pressKey(KeyboardImpl.KeyboardButtons.LEFT);
        glcode.keyboard().pressKey(KeyboardImpl.KeyboardButtons.LEFT);
        glcode.keyboard().pressKey(KeyboardImpl.KeyboardButtons.LEFT);        
        glcode.as(Text.class).type("e"); 
        text = glcode.getControl().getText();                
        Assert.assertEquals (10,text.length());
        Assert.assertEquals ("XCS-COE-JL",text);                 
        
        glcode.keyboard().pressKey(KeyboardImpl.KeyboardButtons.LEFT);
        glcode.keyboard().pressKey(KeyboardImpl.KeyboardButtons.LEFT);        
        glcode.keyboard().pressKey(KeyboardImpl.KeyboardButtons.LEFT);                
        glcode.as(Text.class).type(" ");             
        text = glcode.getControl().getText();                
        Assert.assertEquals (10,text.length());
        Assert.assertEquals ("XCS-C E-JL",text);                         
        
        String junk = "!\"\\$%^&*()_+)[]}:@?><,.";
        for (int count = 0; count < junk.length(); ++ count)
        {
            glcode.as(Text.class).type(junk.substring(count, count+1));
        }    
        text = glcode.getControl().getText();                
        Assert.assertEquals (10,text.length());
        Assert.assertEquals ("XCS-C E-JL",text);  
        
        glcode.getControl().getTextFieldMaskControl().clear();
        text = glcode.getControl().getText();                
        Assert.assertEquals (10,text.length());
        Assert.assertEquals ("   -   -  ",text);          
        
    }        
    
    @Test
    public void moneyTextField () throws InterruptedException
    {
        money.getControl().requestFocus();
        money.keyboard().pressKey(KeyboardImpl.KeyboardButtons.DELETE);
        
        String text = money.getControl().getText();
        Assert.assertEquals (28,text.length());
        Assert.assertEquals ("                        .   ",text); 
        
        money.as(Text.class).type("12345+");
        text = money.getControl().getText();
        Assert.assertEquals (28,text.length());
        Assert.assertEquals (" 123,45                 .   ",text);         
        
        money.as(Text.class).type(".123");
        text = money.getControl().getText();
        Assert.assertEquals (28,text.length());
        Assert.assertEquals (" 3                12,345.12 ",text);   
        
        basic.getControl().requestFocus();
        
        text = money.getControl().getText();
        Assert.assertEquals (28,text.length());
        Assert.assertEquals ("                 312,345.12 ",text);   
        
        money.getControl().requestFocus();
        money.as(Text.class).type(")");
        text = money.getControl().getText();
        Assert.assertEquals (28,text.length());
        Assert.assertEquals ("                 312,345.12 ",text);           
        
        money.as(Text.class).type("-");
        text = money.getControl().getText();
        Assert.assertEquals (28,text.length());
        Assert.assertEquals ("(                312,345.12)",text);                   
        
        money.as(Text.class).type("+");
        text = money.getControl().getText();
        Assert.assertEquals (28,text.length());
        Assert.assertEquals ("                 312,345.12 ",text);                           
        
        //test for invalid characters
        money.as(Text.class).type("adc!$%^&*`[");
        text = money.getControl().getText();
        Assert.assertEquals (28,text.length());
        Assert.assertEquals ("                 312,345.12 ",text);                                   
        

        money.keyboard().pressKey(KeyboardImpl.KeyboardButtons.A,KeyboardImpl.KeyboardModifiers.CTRL_DOWN_MASK);
        money.keyboard().releaseKey(KeyboardImpl.KeyboardButtons.A,KeyboardImpl.KeyboardModifiers.CTRL_DOWN_MASK);        
        money.keyboard().pressKey(KeyboardImpl.KeyboardButtons.DELETE);        
        money.as(Text.class).type("2048.50");
        text = money.getControl().getText();
        Assert.assertEquals (28,text.length());
        Assert.assertEquals ("                   2,048.50 ",text);                                           

    }
    
    @Test
    public void testDateField () throws InterruptedException
    {
        maskdate.getControl().requestFocus();
        maskdate.keyboard().pressKey(KeyboardImpl.KeyboardButtons.DELETE);
        
        maskdate.as(Text.class).type ("17112007");
        basic.getControl().requestFocus();
        
        String text = maskdate.getControl().getText();
        Assert.assertEquals ("17-Nov-2007",text);   
        
        maskdate.getControl().requestFocus();
        maskdate.keyboard().pressKey(KeyboardImpl.KeyboardButtons.F4);        
        
        Parent<Node> parent = Root.ROOT.lookup().as(Parent.class, Node.class);
        LookupCriteria popupWindowLookup  = new LookupCriteria () {

            @Override
            public boolean check(Object cntrl)
            {
                boolean result = false;
                if (cntrl instanceof SDSDateChooserPanel)
                {
                    result = true;
                }    
                return result;
            }
        };   
        Wrap<? extends Node> datePanel =  parent.lookup(Node.class,popupWindowLookup).wrap();    
        
        Assert.assertNotNull(datePanel); 
        
        datePanel.keyboard().pressKey(KeyboardImpl.KeyboardButtons.ESCAPE);
        
        maskdate.keyboard().pressKey(KeyboardImpl.KeyboardButtons.DELETE);
        maskdate.keyboard().pressKey(KeyboardImpl.KeyboardButtons.RIGHT);
        maskdate.as(Text.class).type("1112007");
        basic.getControl().requestFocus();
        
        text = maskdate.getControl().getText();
        Assert.assertEquals ("01-Nov-2007",text);           
        
        //check only valid characters are allowed
        maskdate.getControl().requestFocus();       
        int caret = maskdate.getControl().getCaretPosition();
        maskdate.as(Text.class).type("!\"$%^&*()_+=abcdefg");
        Assert.assertEquals(caret, maskdate.getControl().getCaretPosition());
        text = maskdate.getControl().getText();
        Assert.assertEquals ("01/11/2007",text); 
        
        //check that we get back a start date if the users enters rubbish and tabs on
        maskdate.keyboard().pressKey(KeyboardImpl.KeyboardButtons.HOME);        
        maskdate.as(Text.class).type("34132013");        
        basic.getControl().requestFocus();
        text = maskdate.getControl().getText();
        Assert.assertEquals ("01-Nov-2007",text);                   
        
    }        
    
    @Test
    public void testFrenchDateField () throws InterruptedException
    {
        maskFRdate.getControl().requestFocus();
        maskFRdate.keyboard().pressKey(KeyboardImpl.KeyboardButtons.DELETE);
        
        maskFRdate.as(Text.class).type ("17062007");
        basic.getControl().requestFocus();
        
        String text = maskFRdate.getControl().getText();
        Assert.assertEquals ("17 juin 2007",text);   
        
        maskFRdate.getControl().requestFocus();
        maskFRdate.keyboard().pressKey(KeyboardImpl.KeyboardButtons.F4);        
        
        Parent<Node> parent = Root.ROOT.lookup().as(Parent.class, Node.class);
        LookupCriteria popupWindowLookup  = new LookupCriteria () {

            @Override
            public boolean check(Object cntrl)
            {
                boolean result = false;
                if (cntrl instanceof SDSDateChooserPanel)
                {
                    result = true;
                }    
                return result;
            }
        };   
        Wrap<? extends Node> datePanel =  parent.lookup(Node.class,popupWindowLookup).wrap();    
        
        Assert.assertNotNull(datePanel); 
        
        datePanel.keyboard().pressKey(KeyboardImpl.KeyboardButtons.ESCAPE);
        
        maskFRdate.keyboard().pressKey(KeyboardImpl.KeyboardButtons.DELETE);
        maskFRdate.keyboard().pressKey(KeyboardImpl.KeyboardButtons.RIGHT);        
        maskFRdate.as(Text.class).type("1082007");
        basic.getControl().requestFocus();
        
        text = maskFRdate.getControl().getText();
        Assert.assertEquals ("01 août 2007",text);           
        
        //check only valid characters are allowed
        maskFRdate.getControl().requestFocus();        
        maskFRdate.as(Text.class).type("!\"$%^&*()_+=abcdefg");        
        text = maskFRdate.getControl().getText();
        Assert.assertEquals ("01/08/2007",text); 
        
        //check that we get back a start date if the users enters rubbish and tabs on
        maskFRdate.keyboard().pressKey(KeyboardImpl.KeyboardButtons.HOME);        
        maskFRdate.as(Text.class).type("34122014");        
        basic.getControl().requestFocus();
        text = maskFRdate.getControl().getText();
        Assert.assertEquals ("01 août 2007",text);                   
        
    }            
    
    @Test
    public void testTime () throws InterruptedException
    {
        maskTime.getControl().requestFocus();
        maskTime.keyboard().pressKey(KeyboardImpl.KeyboardButtons.DELETE);
        
        maskTime.as(Text.class).type ("235959");
        basic.getControl().requestFocus();
        
        String text = maskTime.getControl().getText();
        Assert.assertEquals ("23:59:59",text);   
        
        maskTime.getControl().requestFocus();
        maskTime.keyboard().pressKey(KeyboardImpl.KeyboardButtons.F4);        
       
        
        maskTime.keyboard().pressKey(KeyboardImpl.KeyboardButtons.DELETE);
        maskTime.keyboard().pressKey(KeyboardImpl.KeyboardButtons.RIGHT);                        
        maskTime.as(Text.class).type("1");
        maskTime.keyboard().pressKey(KeyboardImpl.KeyboardButtons.RIGHT);                                
        maskTime.as(Text.class).type("11");
        basic.getControl().requestFocus();
        
        text = maskTime.getControl().getText();
        Assert.assertEquals ("01:01:10",text);           
        
        //check only valid characters are allowed
        maskTime.getControl().requestFocus();        
        maskTime.as(Text.class).type("!.\"$%^&*()_+=abcdefg");        
        text = maskTime.getControl().getText();
        Assert.assertEquals ("01:01:10",text); 
        
        //check that we get back a start date if the users enters rubbish and tabs on
        maskTime.keyboard().pressKey(KeyboardImpl.KeyboardButtons.HOME);        
        maskTime.as(Text.class).type("507080");        
        basic.getControl().requestFocus();
        text = maskTime.getControl().getText();
        Assert.assertEquals ("01:01:10",text);                   
        
    }   
    
    @Test
    public void testMaskLookup ()
    {
        //This test is to confirm that the selection list window 
        //interface works and returns a value.  The pop up box is not
        //the test target.
        maskLookup.getControl().requestFocus();
        maskLookup.keyboard().pressKey(KeyboardImpl.KeyboardButtons.F4);        
        
        Wrap<? extends TableView> tableView;        
        Wrap<? extends Button> selectBtn;
        Parent<Node> parent = Root.ROOT.lookup().as(Parent.class, Node.class);
        
        TableViewLookup tableViewLookup = new TableViewLookup ();
        tableViewLookup.search="LookupTable";
        tableView=  parent.lookup(TableView.class,tableViewLookup).wrap();         
        
        ButtonLookup buttonLookup = new ButtonLookup ();
        buttonLookup.search="SelectBtn";
        selectBtn =  parent.lookup(Button.class,buttonLookup).wrap();                 

        tableView.getControl().requestFocus();
        tableView.mouse().click();
        tableView.as(TableViewWrap.class).keyboard().pressKey(KeyboardImpl.KeyboardButtons.DOWN);
        
        selectBtn.mouse().click();
        Calendar c = Calendar.getInstance();
        boolean timeout = false;
        while (!timeout)
        {
            String text = maskLookup.getControl().getText().trim();
            if (!text.isEmpty())
            {    
                Assert.assertEquals("Edward Heath", text);
                break;
            }
            
            Calendar d = Calendar.getInstance();
            if (d.getTimeInMillis()-c.getTimeInMillis() > 60000)
                timeout = true;
                
        }
        
        if (timeout)
            Assert.fail("Timeouted on testMaskLookup");
    }        
    
    public class SDSTextFieldLookup implements LookupCriteria
    {

            public String search = "";
            @Override
            public boolean check(Object cntrl)
            {
                boolean result = false;


                if (cntrl instanceof TextFieldMaskControlSkin)
                {
                    TextFieldMaskControlSkin tv = (TextFieldMaskControlSkin)cntrl;
                    
                    if (tv.getTextFieldMaskControl().getUserData() != null && 
                        tv.getTextFieldMaskControl().getUserData().toString().equals(search))
                    {    
                        result = true;
                    }    
                }    
                return result;
            }
    }        
    
    public class TableViewLookup implements LookupCriteria
    {

            public String search = "";
            @Override
            public boolean check(Object cntrl)
            {
                boolean result = false;

                if (cntrl instanceof TableView)
                {
                    TableView tv = (TableView)cntrl;
                    
                    if (tv.getUserData() != null && 
                        tv.getUserData().toString().equals(search))
                    {    
                        result = true;
                    }    
                }    
                return result;
            }
    }            
    public class ButtonLookup implements LookupCriteria
    {

            public String search = "";
            @Override
            public boolean check(Object cntrl)
            {
                boolean result = false;


                if (cntrl instanceof Button)
                {
                    Button btn = (Button)cntrl;
                    
                    if (btn.getUserData() != null && 
                        btn.getUserData().toString().equals(search))
                    {    
                        result = true;
                    }    
                }    
                return result;
            }
    }                
}


