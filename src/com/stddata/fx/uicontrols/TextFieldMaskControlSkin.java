package com.stddata.fx.uicontrols;



import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.IndexRange;
import javafx.scene.control.Skin;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

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
public class TextFieldMaskControlSkin extends TextField implements Skin<TextFieldMaskControl>
{

    private TextFieldMaskControl control;
    private ChangeListener textFieldFocusListener;
    private InvalidationListener moneyFieldStyleClassListener;    
    private String textOnEntry;
    private boolean debug = false;
    
    public TextFieldMaskControlSkin (final TextFieldMaskControl control)
    {
        this.control = control;
           

        focusedProperty().addListener(textFieldFocusListener = new ChangeListener (){

            @Override
            public void changed(ObservableValue arg0, Object arg1, Object arg2)
            {
                if (!isFocused())
                    focusLost ();
                else 
                    focusGained ();

            }
        });
        
        addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() 
        {
       
            @Override
            public void handle(KeyEvent event) 
            {
                
                if (event.isAltDown() || event.isControlDown() || event.isMetaDown() ||
                    event.isShiftDown() || event.isShortcutDown() || event.isConsumed())
                    return;
                
                switch (control.getSpecification().getMaskStyle())
                {
                    case EditMaskFactory.STYLE_DATE:
                    case EditMaskFactory.STYLE_DATE_TIME:
                    case EditMaskFactory.STYLE_TIME:
                       if (event.getCode().getName().equals("Up") ||
                           event.getCode().getName().equals("Down"))  
                       {
                           boolean add= true;
                           if (event.getCode().getName().equals("Down"))
                               add = false;
                           event.consume();
                           StringBuilder txt = new StringBuilder (getText());
                           int caret = getCaretPosition();
                           EditMaskValueFactory.spinDateTimeFieldSection(txt, 
                                   caret,
                                   control.getSpecification(), add);
                           setText(txt.toString());
                           positionCaret(caret);
                       }    
                       break;
                    default:
                       break; 
                }    
                
                if (event.getCode().getName().equals ("F4") && control.isLookupEnabled())
                {
                    event.consume();

                    TextFieldMaskLookupCallBack cb = control.getLookupCallback();
                    if (cb != null)
                    {    
                        int caret = getCaretPosition();
                        cb.call(control,getText(),true);
                        positionCaret (caret);
                    }    

                }    
            }
        });
        
        addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>(){

            @Override
            public void handle(MouseEvent arg0)
            {
                if (control.isEditable() && !control.isFocused())
                {    
                    control.requestFocus();
                    arg0.consume();
                }    
            }
        });
        
        setId("text-field-mask");
        getStyleClass().setAll(control.getStyleClass());
        control.getStyleClass().addListener(moneyFieldStyleClassListener = new InvalidationListener() 
        {

            @Override
            public void invalidated(javafx.beans.Observable arg0)
            {
                getStyleClass().setAll(control.getStyleClass());
            }
        });


        editableProperty().bind(control.editableProperty());
       

        // Whenever the text of the textField changes, we may need to
        // update the value.
        setText (control.getText());        
        control.getTextProperty().addListener(new InvalidationListener() {

            @Override
            public void invalidated(javafx.beans.Observable arg0)
            {
                
                if (arg0 instanceof TextMaskProperty)
                {
                    
                    positionCaret (0);
                    TextMaskProperty tm = (TextMaskProperty)arg0;
                    String t = tm.get();
                    
                    
                    /***
                     * For DateTime fields the new text may match display but the
                     * field could be input (after F4 lookup key).  We need to determine
                     * if the formats match!
                     */
                    
                    if (control.getSpecification().getMaskStyle()==EditMaskFactory.STYLE_DATE_TIME ||
                        control.getSpecification().getMaskStyle()==EditMaskFactory.STYLE_DATE)
                    {
                        t = EditMaskValueFactory.checkAndConvertDateTimeToInputFormat
                                (t,  control.getSpecification(),isFocused());            
                    }   
                    
                    skinReplaceText (0,t);
                }    
            }
        });

        setFocusTraversable(false);  

    }


    @Override 
    public void replaceText(int start, int end, String text) 
    {
        skinReplaceText (start,text);
    }
            

    @Override 
    public void replaceSelection(String text) 
    {

        int start = Math.min(getAnchor(), getCaretPosition());
        int end = Math.max(getAnchor(), getCaretPosition());
        if (text.trim().isEmpty())
            deleteText (start,end);
        else 
            skinReplaceText (start,text);
    }

    @Override
    public void insertText (int start, String text)
    {
        skinReplaceText (start,text);                
    }        


    @Override
    public void deleteText(int arg0, int arg1)
    {
        StringBuilder editMask = control.getEditMask();                                
        int caret = arg0;
        StringBuilder txt = new StringBuilder (getText());

        EditMaskHelper.deleteSelectedText(caret, arg1,txt,editMask);

        setText(EditMaskHelper.showOptionalLiterals(editMask,txt));                
    }
            
    protected void skinReplaceText(int arg0, String arg1)
    {
        int count =0;
        int caret = getCaretPosition();
        
        
        IndexRange ir = this.getSelection();
        if (ir.getLength() > 1 && caret > 0)                
        {
            --caret;
        }    

        StringBuilder editMask = control.getEditMask();    
        if (!this.isFocused() && 
                (control.getSpecification().getMaskStyle()==EditMaskFactory.STYLE_DATE || 
                 control.getSpecification().getMaskStyle()==EditMaskFactory.STYLE_DATE_TIME))
        {
            editMask = control.getSpecification().getDateTimeDisplayMask();  
        }    
        
        int lastInputCaret = EditMaskHelper.getLastInputPosition(editMask);        
        if (caret > lastInputCaret)
        {
            positionCaret (0);
            caret = 0;
        }    
        
        int pos = EditMaskHelper.getMaskPositionFromCaret(editMask,caret);

        StringBuilder txt = new StringBuilder (getText());
        int caretCount = 0;

        boolean adjustCaret = true;
        if (debug)
            System.out.println ("PreLoop!!! count:" +count+" pos:"+pos+
                    " Caret: "+caret+" cc:"+caretCount+" Text::"+arg1+"::"+arg0);

        while (count < arg1.length() && pos < editMask.length())
        {
                adjustCaret = true;
                if (caret > lastInputCaret)
                    caret = EditMaskHelper.getFirstInputPosition(editMask);

                if (control.getSpecification().getMaskStyle()==EditMaskFactory.STYLE_NUMERIC && (
                    arg1.charAt(count)=='+' || arg1.charAt(count)=='-'))
                {
                    adjustCaret = false;
                    ++count;
                    EditMaskHelper.processSign(editMask, txt, arg1.charAt(count-1));
                    continue;
                }    

                if (debug)
                    System.out.println ("count:" +count+" pos:"+pos+" Caret: "+
                            caret+" cc:"+caretCount+" Text::"+arg1+"::"+arg0+
                            "::"+editMask.charAt(pos));
                
                if (EditMaskHelper.isAnyLiteral(editMask,  pos))
                {
                    if (editMask.charAt(pos+1)==arg1.charAt(count))
                        ++count;
                    ++caret;
                    pos+=2;
                    continue;
                }

                char ch = EditMaskHelper.isValidForPosition(editMask, arg1.charAt(count), pos);
                if (ch != 0)
                {
                    if (ir.getLength() > 1)
                    {
                        EditMaskHelper.deleteSelectedText(ir.getStart(), ir.getEnd()-1, txt, editMask);
                    }    
                    txt.setCharAt(caret, ch);
                    ++count;
                    if (arg1.length() > 1)
                        ++caret;
                }  
                else
                {
                    int next = EditMaskHelper.nextLiteral(editMask,arg1.charAt(count), arg0+caretCount);
                    if (debug)
                        System.out.println ("Next...:"+next);
                    
                    if (next > -1)
                    {
                        adjustCaret = false;
                        ++count;
                        caret=next;
                        pos = EditMaskHelper.getMaskPositionFromCaret(editMask,caret);

                        if (control.getSpecification().getMaskStyle()==EditMaskFactory.STYLE_NUMERIC &&
                            arg1.charAt(count-1)==control.getSpecification().getDecimalSeparator().charAt(0))
                        {
                            EditMaskHelper.tidyLiteralInput(editMask, caret, txt, control.getSpecification().getMaskStyle());
                        }    

                        continue;
                    } 
                    else
                    {    
                        adjustCaret = false;  //don't move if the user entered rubbish  
                        ++count;  //ignore input
                    }    
                }    
                ++pos;

        }    

        setText(EditMaskHelper.showOptionalLiterals(editMask,txt));                
        if (adjustCaret)
        {    
            caret=EditMaskHelper.getNextInputPosition(editMask, caret);
        }    
        positionCaret(caret);
    }                
            

    @Override 
    public TextFieldMaskControl getSkinnable() 
    {
        return control;
    }

    
    protected void focusGained ()
    {
        textOnEntry = getText();
        if (control.getSpecification().getMaskStyle()==EditMaskFactory.STYLE_DATE ||
            control.getSpecification().getMaskStyle()==EditMaskFactory.STYLE_DATE_TIME ||
            control.getSpecification().getMaskStyle()==EditMaskFactory.STYLE_TIME)            
        {

            Object result = EditMaskValueFactory.parseValue(getText(), 
                    control.getSpecification(), false);

            if (result == null)
                result = control.getValueOnEntry();
            setText(EditMaskValueFactory.formatDateTime((Calendar)result, 
                    control.getSpecification(), true));
        }     
        

        if (control.isFocused())
            requestFocus();
    }        
    
    /****
     * For numeric fields input is tided up on focus lost
     * For DateTime fields, input is tided up (which should not be necessary)
     * The value in the text field is converted back to a native object and
     * reformatted in the display value.
     */
    
    protected void focusLost ()
    {
        if (!control.isFocused())
        {
            if (control.getSpecification().getMaskStyle()==EditMaskFactory.STYLE_NUMERIC)
            {
                StringBuilder txt = new StringBuilder (getText());
                StringBuilder editMask = control.getEditMask();
                setText(EditMaskHelper.tidyPostInput(editMask,  
                        txt, control.getSpecification()).toString());
                Object result = EditMaskValueFactory.parseValue(getText(), control.getSpecification(),false);                
                if (result != null)
                    control.setValueOnEntry(result);
            }    
            else
            if (control.getSpecification().getMaskStyle()==EditMaskFactory.STYLE_DATE ||
                control.getSpecification().getMaskStyle()==EditMaskFactory.STYLE_DATE_TIME ||
                control.getSpecification().getMaskStyle()==EditMaskFactory.STYLE_TIME)            
            {
                StringBuilder txt = new StringBuilder (getText());
                StringBuilder editMask = control.getEditMask();
                setText(EditMaskHelper.tidyPostInput(editMask,  
                        txt, control.getSpecification()).toString());
                Object result = EditMaskValueFactory.parseValue(getText(), 
                        control.getSpecification(), true);
                
                if (result == null)
                {
                    setText(EditMaskHelper.tidyPostInput(editMask,  
                        new StringBuilder (textOnEntry),
                        control.getSpecification()).toString());
                    result = EditMaskValueFactory.parseValue(getText(), 
                        control.getSpecification(), true);                    
                }   
                if (result == null)
                    result = control.getValueOnEntry();
                else
                if (control.getValueOnEntry() instanceof Date)    
                {
                    if (result instanceof Date)
                        control.setValueOnEntry(result);
                    else
                    if (result instanceof Calendar)
                    {   
                        Calendar c = (Calendar)result;
                        control.setValueOnEntry(c.getTime());
                    } 
                }    
                else
                if (control.getValueOnEntry() instanceof Calendar)    
                {
                    if (result instanceof Calendar)
                        control.setValueOnEntry(result);
                    else
                    if (result instanceof Date)    
                    {
                        Calendar c = Calendar.getInstance(control.getSpecification().getLocale());
                        c.setTime((Date)result);
                    }    
                }    
                
                setText(EditMaskValueFactory.formatDateTime((Calendar)result, 
                        control.getSpecification(), false));
            }    
            else
            {

                String result = new String (getText().getBytes());
                        //EditMaskValueFactory.parseValue(getText(), control.getSpecification(),false);                

                if (result != null)
                    control.setValueOnEntry(result);  

                //control.getTextProperty().set(getText());
            }    
        } 
    }        


    @Override
    public void dispose() 
    {
        control.getStyleClass().removeListener(moneyFieldStyleClassListener);
        focusedProperty().removeListener(textFieldFocusListener);
    }

    @Override
    public Node getNode()
    {
        return this;
    }

    /***
     * This method is primarily offered to allow test tools to find and 
     * check control data.  It is not intended for general use!
     * @return the control linked to this skin
     */
    
    public TextFieldMaskControl getTextFieldMaskControl ()
    {
        return control;
    }        
    
}
