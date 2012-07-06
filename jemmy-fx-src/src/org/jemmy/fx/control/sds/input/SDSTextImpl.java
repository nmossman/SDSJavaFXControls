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
import org.jemmy.action.Action;
import org.jemmy.control.Wrap;
import org.jemmy.interfaces.Focusable;
import org.jemmy.interfaces.Keyboard;
import org.jemmy.interfaces.Keyboard.KeyboardButtons;
import org.jemmy.interfaces.Keyboard.KeyboardModifiers;
import org.jemmy.interfaces.Text;
import org.jemmy.timing.State;
import static org.jemmy.interfaces.Keyboard.KeyboardButtons.*;


public abstract class SDSTextImpl implements Text {

    private static final int DEFAULT_SELECT_ALL_CLICK_COUNT = 3;
    private Wrap<?> target;
    private int selectAllClickCount = DEFAULT_SELECT_ALL_CLICK_COUNT;
    boolean keyboardSelection;
    private boolean inputComparison = true;

    /**
     *
     * @param target
     * @param keyboardSelection
     */
    protected SDSTextImpl(Wrap<?> target, boolean keyboardSelection) {
        this.target = target;
        this.keyboardSelection = keyboardSelection;
    }

    /**
     *
     * @param target
     */
    protected SDSTextImpl(Wrap<?> target) {
        this(target, false);
    }

    /**
     *
     * @return
     */
    public Wrap<?> getWrap() {
        return target;
    }

    /**
     * Types text into the control. Wrap may implement Focusable.
     * @see Focusable
     * @param newText
     */
    @Override
    public void type(final String newText) {
        target.getEnvironment().getExecutor().execute(target.getEnvironment(), false, new Action() {

            @Override
            public void run(Object... parameters) {
                if (target.is(Focusable.class)) {
                    target.as(Focusable.class).focuser().focus();
                }
                char[] chars = newText.toCharArray();
                Keyboard kb = target.keyboard();
                for (char c : chars) {
                    kb.typeChar(c);
                }
                target.getEnvironment().getWaiter(Wrap.WAIT_STATE_TIMEOUT.getName()).ensureState(new State<Object>() {
                    
                    @Override
                    public Object reached() {
                        if (!inputComparison)
                            return "";
                        else
                            return text().contains(newText) ? "" : null;     
                    }   
                    
                    @Override
                    public String toString() {
                        return "text() equals '" + newText + "', text() = '" + text() + "'";
                    }
                });
            }

            @Override
            public String toString() {
                return "typing text \"" + newText + "\"";
            }
        });
    }

    /**
     * Selects all text within component by clicking 3 times on it or using
     * keyboard depending on the second argument passed to {@linkplain
     * #TextImpl(org.jemmy.control.Wrap, boolean) constructor}. Override if
     * needed.<p>
     *
     * <b>Warning!</b> In Java keyboard selection doesn't work with
     * NumLock turned On due to CR 4966137 'Robot presses Numpad del key
     * instead of normal Del key'.
     */
    protected void selectAll() {
        if (!keyboardSelection) {
            target.mouse().click(selectAllClickCount, target.getClickPoint());
        } else {
            Keyboard kbrd = target.keyboard();
            kbrd.pushKey(KeyboardButtons.HOME);
            kbrd.pushKey(KeyboardButtons.END, KeyboardModifiers.SHIFT_DOWN_MASK);
            kbrd.pushKey(KeyboardButtons.DELETE);
        }
    }

    /**
     * Clears text by pressing End and then Delete and Backspace until the text
     * is cleared.
     */
    @Override
    public void clear() {
        target.getEnvironment().getExecutor().execute(target.getEnvironment(),
                false, new Action() {

            @Override
            public void run(Object... parameters) {
                if (target.is(Focusable.class)) {
                    target.as(Focusable.class).focuser().focus();
                }
                target.keyboard().pushKey(END);
                while(text().length() > 0 && withinAllowedTime()) {
                    target.keyboard().pushKey(BACK_SPACE);
                    target.keyboard().pushKey(DELETE);
                }
            }

            @Override
            public String toString() {
                return "clearing text";
            }
        });
    }
    
    public void setInputComparison (boolean turnOn)
    {
        inputComparison=turnOn;
    }        
}

