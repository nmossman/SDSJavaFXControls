
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
import org.jemmy.env.Environment;
import org.jemmy.input.AbstractCaretOwner;
import org.jemmy.input.RegexCaretDirection;
import org.jemmy.input.TextCaret;
import org.jemmy.interfaces.Keyboard.KeyboardButton;
import org.jemmy.interfaces.Keyboard.KeyboardModifier;
import org.jemmy.interfaces.Text;


public abstract class SDSCaretText extends AbstractCaretOwner implements Text {

    static {
        Environment.getEnvironment().setPropertyIfNotSet(RegexCaretDirection.REGEX_FLAGS, 0);
    }

    TextCaret caret;
    SDSTextImpl text;
    Wrap<?> wrap;
    /**
     *
     * @param wrap
     */
    public SDSCaretText(Wrap<?> wrap) {
        this.wrap = wrap;
        caret = new TextCaret(wrap, this);
        text = new SDSTextImpl(wrap) {

            @Override
            public String text() {
                return org.jemmy.fx.control.sds.input.SDSCaretText.this.text();
            }
        };
    }

    @Override
    public TextCaret caret() {
        return caret;
    }

    /**
     *
     * @return
     */
    protected int getFlags() {
        return (Integer)wrap.getEnvironment().
                getProperty(RegexCaretDirection.REGEX_FLAGS, 0);
    }

    @Override
    public void type(String newText) {
        text.type(newText);
    }

    @Override
    public void clear() {
        text.clear();
    }

    /**
     * Moves caret to a beginning/end of an <code>index</code>'th occurance of the regex.
     * @param regex
     * @param front
     * @param index
     */
    public void to(String regex, boolean front, int index) {
        caret().to(new RegexCaretDirection(this, this, regex, getFlags(), front, index));
    }

    /**
     * Moves caret to a beginning/end of the first occurance of the regex.
     * @param regex
     * @param front
     */
    public void to(String regex, boolean front) {
        to(regex, front, 0);
    }

    /**
     * Moves caret to a beginning the first occurance of the regex.
     * @param regex
     */
    public void to(String regex) {
        to(regex, true);
    }

    /**
     *
     * @param left
     * @param leftMods
     * @param right
     * @param rightMods
     */
    public void addNavKeys(KeyboardButton left, KeyboardModifier[] leftMods,
            KeyboardButton right, KeyboardModifier[] rightMods) {
        caret.addNavKeys(left, leftMods, right, rightMods);
    }

    /**
     *
     * @param left
     * @param right
     */
    public void addNavKeys(KeyboardButton left, KeyboardButton right) {
        addNavKeys(left, new KeyboardModifier[0], right, new KeyboardModifier[0]);
    }
    
    public void setInputComparison (boolean turnOn)
    {
        
    }        
}

