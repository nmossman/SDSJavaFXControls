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
public interface TextFieldMaskLookupCallBack
{
    /***
     * Allows a call to a look up window to take place
     * @param control  Control making the call, use setValue for return values
     * @param editText The current text typed by the user, this is in the shadow 
     *                 textfield, this value will be different to the main control
     * @param focusCall  true if F4 key pressed, false if lookup button pressed
     */
    public void call (TextFieldMaskControl control, String editText, boolean focusCall);
}
