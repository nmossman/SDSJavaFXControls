package com.stddata.fx.uicontrols;

import com.sun.javafx.Utils;
import java.text.BreakIterator;
import java.util.Iterator;
import javafx.beans.property.*;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.control.IndexRange;

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
public class TextFieldMaskControl extends Control
{
    public TextFieldMaskControl()
    {
        super ();
        specification = new EditMaskSpecification ();
        text = new TextMaskProperty (this);
        try
        {    
            EditMaskFactory.getMask(specification);
        }
        catch (Exception x)
        {
            System.out.println(x);
        }    

        length = new ReadOnlyIntegerWrapper(this, "length");
        editable = new SimpleBooleanProperty(this, "editable", true);
        selection = new ReadOnlyObjectWrapper(this, "selection", new IndexRange(0, 0));
        selectedText = new ReadOnlyStringWrapper(this, "selectedText");
        anchor = new ReadOnlyIntegerWrapper(this, "anchor", 0);
        caretPosition = new ReadOnlyIntegerWrapper(this, "caretPosition", 0);
        doNotAdjustCaret = false;  
        valueOnEntry = null;
        lookupEnabled=false;
        text.set (EditMaskHelper.makeEmptyField(specification.getEditMask()).toString()); 
        length.set(text.get().length());
        getStyleClass().setAll("field-mask");        
    }
    
    public TextFieldMaskControl (EditMaskSpecification specification)
    {
        super ();
        this.specification=specification;
        text = new TextMaskProperty (this);        
        try
        {   
            if (this.specification.getEditMask() == null)
                EditMaskFactory.getMask(this.specification);
        }
        catch (Exception x)
        {
            System.out.println(x);
        }            

        length = new ReadOnlyIntegerWrapper(this, "length");
        editable = new SimpleBooleanProperty(this, "editable", true);
        selection = new ReadOnlyObjectWrapper(this, "selection", new IndexRange(0, 0));
        selectedText = new ReadOnlyStringWrapper(this, "selectedText");
        anchor = new ReadOnlyIntegerWrapper(this, "anchor", 0);
        caretPosition = new ReadOnlyIntegerWrapper(this, "caretPosition", 0);
        doNotAdjustCaret = false;  
        valueOnEntry = null;
        lookupEnabled=false;
        if (specification.getMaskStyle()==EditMaskFactory.STYLE_DATE || 
            specification.getMaskStyle()==EditMaskFactory.STYLE_DATE_TIME)
        {
            lookupEnabled = true;
            text.set (EditMaskHelper.makeEmptyField(specification.getDateTimeDisplayMask()).toString());
        }    
        else
            text.set (EditMaskHelper.makeEmptyField(specification.getEditMask()).toString());  
        
        length.set(text.get().length());        
        getStyleClass().setAll("field-mask");        
    }        
    
    protected void setSpecification (EditMaskSpecification specification)
    {
        this.specification=specification;
    }   
    
    public EditMaskSpecification getSpecification ()
    {
        return specification;
    }        
 
    public StringBuilder getEditMask ()
    {
        return specification.getEditMask();
    }
    
    public final String getText()
    {
        return text.get();
    }

    public final void setText(String s)
    {
        if(s == null)
            s = "";
        
        if (getValueOnEntry() == null)
            setValueOnEntry(s);
        

        //deleteText(0, getLength());
        insertText(0, s);
        if(!isFocused())
        {
            selectRange(0, 0);
        }
    }
    
    public final TextMaskProperty getTextProperty ()
    {
        return text;
    }        
    
    @Override
    public void requestFocus ()
    {

        TextFieldMaskControlSkin tfms = null;        
        ObservableList <Node> ol = getChildren();
        Iterator it = ol.iterator();
        while (it.hasNext())
        {
            Object o = it.next();
            if (o instanceof TextFieldMaskControlSkin)
               tfms = (TextFieldMaskControlSkin)o;
        } 
        if (tfms != null)
            tfms.requestFocus();
        else
            super.requestFocus();
    }        
    
    public final void setValue (Object value)
    {
        if (getValueOnEntry() == null)
            setValueOnEntry(value);
        if (specification.getMaskStyle()==EditMaskFactory.STYLE_DATE_TIME ||
            specification.getMaskStyle()==EditMaskFactory.STYLE_DATE ||
            specification.getMaskStyle()==EditMaskFactory.STYLE_TIME)
        {
            setText (EditMaskValueFactory.formatToSpecification(value, specification,false));            
        }   
        else
            setText (EditMaskValueFactory.formatToSpecification(value, specification,false));
    }  
    
    public final Object getValue ()
    {
        Object result = getValueOnEntry();
        StringBuilder parse = EditMaskHelper.tidyPostInput(specification.getEditMask(), new StringBuilder(this.getText()), specification);
        result = EditMaskValueFactory.parseValue(parse.toString(), specification,false);
        if (result == null)
            result = getValueOnEntry();
        
        return result;
    }        



    public final int getLength()
    {
        return length.get();
    }

    public final ReadOnlyIntegerProperty lengthProperty()
    {
        return length.getReadOnlyProperty();
    }

    public final boolean isEditable()
    {
        return editable.getValue().booleanValue();
    }

    public final void setEditable(boolean flag)
    {
        editable.setValue(Boolean.valueOf(flag));
    }

    public final BooleanProperty editableProperty()
    {
        return editable;
    }

    public final IndexRange getSelection()
    {
        return (IndexRange)selection.getValue();
    }

    public final ReadOnlyObjectProperty selectionProperty()
    {
        return selection.getReadOnlyProperty();
    }

    public final String getSelectedText()
    {
        return selectedText.get();
    }

    public final ReadOnlyStringProperty selectedTextProperty()
    {
        return selectedText.getReadOnlyProperty();
    }

    public final int getAnchor()
    {
        return anchor.get();
    }

    public final ReadOnlyIntegerProperty anchorProperty()
    {
        return anchor.getReadOnlyProperty();
    }

    public final int getCaretPosition()
    {
        return caretPosition.get();
    }

    public final ReadOnlyIntegerProperty caretPositionProperty()
    {
        return caretPosition.getReadOnlyProperty();
    }

    public String getText(int i, int j)
    {
        if(i > j)
            throw new IllegalArgumentException("The start must be <= the end");
        if(i < 0 || j > getLength())
            throw new IndexOutOfBoundsException();
        else
            return text.get().substring(i, j);
    }

    public void appendText(String s)
    {
        insertText(getLength(), s);
    }

    public void insertText(int i, String s)
    {
        replaceText(i, i, s);
    }

    public void deleteText(IndexRange indexrange)
    {
        replaceText(indexrange, "");
    }

    public void deleteText(int i, int j)
    {
        replaceText(i, j, "");
    }

    public void replaceText(IndexRange indexrange, String s)
    {
        if(indexrange == null)
        {
            throw new NullPointerException();
        } else
        {
            int i = indexrange.getStart();
            int j = i + indexrange.getLength();
            replaceText(i, j, s);
        }
    }

    public void replaceText(int i, int j, String s)
    {

        if(i > j)
            throw new IllegalArgumentException();
        if(s == null)
            throw new NullPointerException();
        if(i < 0 || j > getLength())
            throw new IndexOutOfBoundsException();


        insert(i, s);
          
        if (!isFocused())
        {    
            i += s.length();
            selectRange(i, i);
        }    
                    

    }

    public void selectRange(int i, int j)
    {
        caretPosition.set(Utils.clamp(0, j, getLength()));
        anchor.set(Utils.clamp(0, i, getLength()));
        selection.set(IndexRange.normalize(getAnchor(), getCaretPosition()));
    }

    public void extendSelection(int i)
    {
        int j = Utils.clamp(0, i, getLength());
        int k = getCaretPosition();
        int l = getAnchor();
        int i1 = Math.min(k, l);
        int j1 = Math.max(k, l);
        if(j < i1)
            selectRange(j1, j);
        else
            selectRange(i1, j);
    }

    public void clear()
    {
        deselect();
        setText(EditMaskHelper.makeEmptyField(specification.getEditMask()).toString());
    }

    public void deselect()
    {
        selectRange(getCaretPosition(), getCaretPosition());
    }

    public void replaceSelection(String s)
    {
/*        if(text.isBound())
            return;*/
        int i = getCaretPosition();
        int j = getAnchor();
        int k = Math.min(i, j);
        int l = Math.max(i, j);
        int i1 = i;
        if(getLength() == 0)
        {
            doNotAdjustCaret = true;
            setText(s);
            selectRange(getLength(), getLength());
            doNotAdjustCaret = false;
        } else
        {
            deselect();
            doNotAdjustCaret = true;
            if(k != l)
                deleteText(k, l >= getLength() ? getLength() : l);
            int j1 = getLength();
            insert(k, s);
            int k1 = (k + getLength()) - j1;
            selectRange(k1, k1);
            doNotAdjustCaret = false;
        }
    }

    protected void insert(int arg0, String arg1)
    {
        int count =0;
        int caret = arg0;
        StringBuilder editMask = specification.getEditMask();
        if (!isFocused() && (specification.getMaskStyle()==EditMaskFactory.STYLE_DATE_TIME ||
                             specification.getMaskStyle()==EditMaskFactory.STYLE_DATE))
            editMask = specification.getDateTimeDisplayMask();
            
        int pos = EditMaskHelper.getMaskPositionFromCaret(editMask,arg0);

        StringBuilder txt = new StringBuilder (text.get());
        StringBuilder empty = EditMaskHelper.makeEmptyField(editMask);

        while (txt.length() < empty.length())
        {    
            txt.append (" ");
        }

        int caretCount = 0;
        boolean adjustCaret = true;


        while (count < arg1.length() && pos < editMask.length())
        {
                adjustCaret = true;
                    
                if (specification.getMaskStyle()==EditMaskFactory.STYLE_NUMERIC && (
                    arg1.charAt(count)=='+' || arg1.charAt(count)=='-'))
                {
                    adjustCaret = false;
                    ++count;
                    EditMaskHelper.processSign(editMask, txt, arg1.charAt(count-1));
                    continue;
                }    

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
                    txt.setCharAt(caret, ch);
                    ++count;
                    ++caret;
                }  
                else
                {
                    int next = EditMaskHelper.nextLiteral(editMask,arg1.charAt(count), arg0+caretCount);
                    if (next > -1)
                    {
                        adjustCaret = false;
                        ++count;
                        caret=next;
                        pos = EditMaskHelper.getMaskPositionFromCaret(editMask,caret);

                        if (specification.getMaskStyle()==EditMaskFactory.STYLE_NUMERIC &&
                            arg1.charAt(count-1)==specification.getDecimalSeparator().charAt(0))
                        {
                            EditMaskHelper.tidyLiteralInput(editMask, caret, txt, specification.getMaskStyle());
                        }    

                        continue;
                    } 
                    else
                        adjustCaret = false;  //don't move if the user entered rubbish  
                }    
                ++pos;

        }    

        text.set(EditMaskHelper.showOptionalLiterals(editMask,txt)); 
    }


    protected void delete(int arg0, int arg1)
    {
        int count = arg0;
        StringBuilder data = new StringBuilder (text.get());
        
        StringBuilder empty = EditMaskHelper.makeEmptyField(specification.getEditMask());
        if (!isFocused() && 
             (specification.getMaskStyle()==EditMaskFactory.STYLE_DATE || 
              specification.getMaskStyle()==EditMaskFactory.STYLE_DATE_TIME))
        {
            empty = EditMaskHelper.makeEmptyField(specification.getDateTimeDisplayMask());
        }        
        
        while (count < arg1 && count < empty.length())
        {
            if (empty.charAt(count)== ' ')
                data.setCharAt(count, ' ');

            ++count;
        }  

        String s = new String (data);
        text.set (s);

    }

    public int length()
    {
        return EditMaskFactory.getFieldSizeBasedOnMask(specification.getEditMask());
    }    
    
    public TextFieldMask getParentTextFieldMask ()
    {
        TextFieldMask result = null;
        Node p = getParent();
        do
        {
            if (p instanceof TextFieldMask) 
            {
                result = (TextFieldMask)p;
                break;
            }    
            else
             p = p.getParent();   
        }
        while (p != null);
        
        return result;
    }        

    @Override 
    protected String getUserAgentStylesheet() {
        return getClass().getResource("TextFieldMaskControl.css").toExternalForm();
    }    
    
    /**
     * @return the lookupEnabled
     */
    public boolean isLookupEnabled()
    {
        return lookupEnabled;
    }

    /**
     * @param lookupEnabled the lookupEnabled to set
     */
    public void setLookupEnabled(boolean lookupEnabled)
    {
        this.lookupEnabled = lookupEnabled;
    }

    /**
     * @return the lookupCallback
     */
    public TextFieldMaskLookupCallBack getLookupCallback()
    {
        if (lookupCallback == null)
            return null;
        else            
            return (TextFieldMaskLookupCallBack)lookupCallback.get();
    }

    /**
     * @param lookupCallback the lookupCallback to set
     */
    public void setLookupCallback(TextFieldMaskLookupCallBack arg0 )
    {
        if (lookupCallback == null)
        {
            lookupCallback = new SimpleObjectProperty (this, "lookCallBackProperty");
        }    
        lookupEnabled = true;
        this.lookupCallback.set(arg0);
    }

    public ObjectProperty lookCallBackProperty ()
    {
        return lookupCallback;
    }   
    
    /**
     * @return the valueOnEntry
     */
    public Object getValueOnEntry()
    {
        return valueOnEntry;
    }

    /**
     * @param valueOnEntry the valueOnEntry to set
     */
    public void setValueOnEntry(Object valueOnEntry)
    {
        this.valueOnEntry = valueOnEntry;
    }
    
    protected Object valueOnEntry;
    protected TextMaskProperty text;
    protected ReadOnlyIntegerWrapper length;
    protected BooleanProperty editable;
    protected ReadOnlyObjectWrapper selection;
    protected ReadOnlyStringWrapper selectedText;
    protected ReadOnlyIntegerWrapper anchor;
    protected ReadOnlyIntegerWrapper caretPosition;
    protected boolean doNotAdjustCaret;
    protected BreakIterator breakIterator;
    protected EditMaskSpecification specification;
    protected boolean lookupEnabled;
    protected ObjectProperty lookupCallback;

   

    


    
}
