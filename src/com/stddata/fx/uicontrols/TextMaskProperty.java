package com.stddata.fx.uicontrols;

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
import com.sun.javafx.binding.ExpressionHelper;
import javafx.beans.InvalidationListener;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class TextMaskProperty extends StringProperty
{
    
    
    public TextMaskProperty (TextFieldMaskControl control)
    {
        super();        
        this.control = control;
        observable = null;
        listener = null;
        helper = null;
        value = "";
    }

    @Override
    public String get()
    {
        return value;
    }

    @Override
    public void set(String s)
    {
        if(isBound())
        {
            throw new RuntimeException("A bound value cannot be set.");
        } else
        {
            doSet(s);
            markInvalid();
        }
    }

    private void invalidate()
    {
        markInvalid();
    }

    @Override
    public void bind(ObservableValue observablevalue)
    {
        if(observablevalue == null)
            throw new NullPointerException("Cannot bind to null");
        if(!observablevalue.equals(observable))
        {
            unbind();
            observable = observablevalue;
            observable.addListener(listener);
            markInvalid();
            doSet((String)observablevalue.getValue());
        }
    }

    @Override
    public void unbind()
    {
        if(observable != null)
        {
            doSet((String)observable.getValue());
            observable.removeListener(listener);
            observable = null;
        }
    }

    @Override
    public boolean isBound()
    {
        return observable != null;
    }

    @Override
    public void addListener(InvalidationListener invalidationlistener)
    {
        helper = ExpressionHelper.addListener(helper, this, invalidationlistener);
    }

    @Override
    public void removeListener(InvalidationListener invalidationlistener)
    {
        helper = ExpressionHelper.removeListener(helper, invalidationlistener);
    }

    @Override
    public void addListener(ChangeListener changelistener)
    {
        helper = ExpressionHelper.addListener(helper, this, changelistener);
    }

    @Override
    public void removeListener(ChangeListener changelistener)
    {
        helper = ExpressionHelper.removeListener(helper, changelistener);
    }

    @Override
    public Object getBean()
    {
        return this;
    }

    @Override
    public String getName()
    {
        return "text";
    }

    private void fireValueChangedEvent()
    {
        ExpressionHelper.fireValueChangedEvent(helper);
    }

    private void markInvalid()
    {
        fireValueChangedEvent();
    }

    private void doSet(String s)
    {
        if(s == null)
            s = "";
        value = s;

    }


    protected ObservableValue observable;
    protected InvalidationListener listener;
    protected ExpressionHelper helper;
    protected final TextFieldMaskControl control;
    private String value;    
    
}
