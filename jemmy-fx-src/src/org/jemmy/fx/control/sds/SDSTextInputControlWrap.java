package org.jemmy.fx.control.sds;

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



import org.jemmy.fx.control.ControlWrap;
import javafx.scene.control.TextInputControl;
import org.jemmy.action.GetAction;
import org.jemmy.fx.ByText;
import org.jemmy.control.ControlInterfaces;
import org.jemmy.control.ControlType;
import org.jemmy.control.Property;
import org.jemmy.dock.ObjectLookup;
import org.jemmy.env.Environment;
import org.jemmy.fx.control.sds.input.SDSSelectionText;
import org.jemmy.input.ClickFocus;
import org.jemmy.input.SelectionText;
import org.jemmy.interfaces.ControlInterface;
import org.jemmy.interfaces.Focus;
import org.jemmy.interfaces.Focusable;
import org.jemmy.interfaces.IntervalSelectable;
import org.jemmy.interfaces.IntervalSelector;
import org.jemmy.interfaces.Keyboard.KeyboardButtons;
import org.jemmy.interfaces.Text;
import org.jemmy.lookup.LookupCriteria;
import org.jemmy.resources.StringComparePolicy;

@ControlType(TextInputControl.class)
@ControlInterfaces({SDSSelectionText.class})
public class SDSTextInputControlWrap<T extends TextInputControl> extends ControlWrap<T> implements Text, IntervalSelectable, Focusable {

    @ObjectLookup("text and comparison policy")
    public static <B extends TextInputControl> LookupCriteria<B> textLookup(Class<B> tp, String id, StringComparePolicy policy) {
        return new ByText<>(id, policy);
    }

    public static final String IS_FOCUSED_PROP_NAME = "isFocused";
    SDSSelectionText inputter;
    SDSTextInputControlWrap.TextInputFocus focus = null;

    /**
     *
     * @param scene
     * @param nd
     */
    public SDSTextInputControlWrap(Environment env, T node) {
        super(env, node);
        inputter = new SDSSelectionText(this) {

            @Override
            public String text() {
                return org.jemmy.fx.control.sds.SDSTextInputControlWrap.this.text();
            }

            @Override
            public double position() {
                return org.jemmy.fx.control.sds.SDSTextInputControlWrap.this.dot();
            }

            @Override
            public double anchor() {
                return org.jemmy.fx.control.sds.SDSTextInputControlWrap.this.mark();
            }
        };
        //huh?
        //TBD why there's not get$multiline() ?
        if(text().contains("\n")) {
            inputter.addNavKeys(KeyboardButtons.UP, KeyboardButtons.DOWN);
        }
    }

    /**
     *
     * @return
     */
    @Property(TEXT_PROP_NAME)
    @Override
    public String text() {
        return new GetAction<String>() {

            @Override
            public void run(Object... parameters) {
                setResult(getControl().getText());
            }

            @Override
            public String toString() {
                return "Getting text of " + getControl();
            }

        }.dispatch(getEnvironment());
    }

    /**
     *
     * @return
     */
    @Property(POSITION_PROP_NAME)
    public int dot() {
        return new GetAction<Integer>() {

            @Override
            public void run(Object... parameters) {
                setResult(getControl().getSelection().getEnd());
            }

            @Override
            public String toString() {
                return "Getting position of " + getControl();
            }

        }.dispatch(getEnvironment());
    }

    /**
     *
     * @return
     */
    public int mark() {
        return new GetAction<Integer>() {

            @Override
            public void run(Object... parameters) {
                setResult(getControl().getSelection().getStart());
            }

            @Override
            public String toString() {
                return "Getting selection position of " + getControl();
            }

        }.dispatch(getEnvironment());
    }

    /**
     *
     * @param arg0
     */
    @Override
    public void type(String arg0) {
        inputter.type(arg0);
    }

    /**
     *
     */
    @Override
    public void clear() {
        inputter.clear();
    }

    @Override
    public double anchor() {
        return inputter.anchor();
    }

    @Override
    public IntervalSelector caret() {
        return inputter.caret();
    }

    @Override
    public double position() {
        return inputter.position();
    }

    @Override
    public void to(double position) {
        inputter.to(position);
    }

    /**
     * An instance of SelectionText which is actually used to perform input
     * @return
     */
    protected SDSSelectionText input() {
        return inputter;
    }

    /**
     * Selects interval.
     * @param start
     * @param end
     */
    public void select(int start, int end) {
        caret().to(start);
        caret().selectTo(end);
    }

    /**
     * Selects <code>index</code>'th occurance of the regex.
     * @param regex
     * @param index
     */
    public void select(String regex, int index) {
        inputter.select(regex, index);
    }

    /**
     * Selects first occurance of the regex.
     * @param regex
     */
    public void select(String regex) {
        inputter.select(regex);
    }

    /**
     * Retuns the selection portion of the text.
     * @return
     */
    public String selection() {
        return inputter.selection();
    }

    @Override
    public Focus focuser() {
        if(focus == null) {
            focus = new SDSTextInputControlWrap.TextInputFocus();
        }
        return focus;
    }

    @Override
    public <INTERFACE extends ControlInterface> boolean is(Class<INTERFACE> interfaceClass) {
        if(Text.class.isAssignableFrom(interfaceClass) || interfaceClass.isAssignableFrom(SelectionText.class)) return true;
        return super.is(interfaceClass);
    }

    @Override
    public <INTERFACE extends ControlInterface> INTERFACE as(Class<INTERFACE> interfaceClass) {
        if(Text.class.isAssignableFrom(interfaceClass) || interfaceClass.isAssignableFrom(SelectionText.class))
            return (INTERFACE) inputter;
        return super.as(interfaceClass);
    }

    private class TextInputFocus extends ClickFocus {

        public TextInputFocus() {
            super(org.jemmy.fx.control.sds.SDSTextInputControlWrap.this);
        }

        @Override
        public void focus() {
            if(!getProperty(Boolean.class, IS_FOCUSED_PROP_NAME)) {
                super.focus();
            }
            waitProperty(IS_FOCUSED_PROP_NAME, true);
        }

    }
}