package org.jemmy.fx.control.sds.input;

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
  * @author nigelm
 */
import org.jemmy.control.Wrap;
import org.jemmy.input.RegexCaretDirection;
import org.jemmy.interfaces.IntervalSelectable;


public abstract class SDSSelectionText extends SDSCaretText implements IntervalSelectable {

    /**
     *
     * @param wrap
     */
    public SDSSelectionText(Wrap<?> wrap) {
        super(wrap);
    }

    /**
     * Selects <code>index</code>'th occurance of the regex.
     * @param regex
     * @param index
     */
    public void select(String regex, int index) {
        to(regex, true, index);
        caret().selectTo(new RegexCaretDirection(this, this, regex, getFlags(), false, index));
    }

    /**
     * Selects first occurance of the regex.
     * @param regex
     */
    public void select(String regex) {
        select(regex, 0);
    }

    /**
     * Retuns the selection portion of the text.
     * @return
     */
    public String selection() {
        int a = (int) anchor(); int p = (int) position();
        int start = (a < p) ? a : p;
        int end = (a < p) ? p : a;
        return text().substring(start, end);
    }
    
    public void setInputComparison (boolean turnOn)
    {
        text.setInputComparison(turnOn);
    }        
}

