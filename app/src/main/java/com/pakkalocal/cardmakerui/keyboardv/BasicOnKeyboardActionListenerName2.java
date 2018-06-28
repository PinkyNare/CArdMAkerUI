package com.pakkalocal.cardmakerui.keyboardv;

import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.view.KeyEvent;
import android.widget.EditText;

import com.pakkalocal.cardmakerui.R;
import com.pakkalocal.cardmakerui.activities.VenueSettingsAct;

public class BasicOnKeyboardActionListenerName2 implements KeyboardView.OnKeyboardActionListener {

    EditText editText;
    CustomKeyboardView displayKeyboardView;
    private VenueSettingsAct mTargetActivity;

    public BasicOnKeyboardActionListenerName2(VenueSettingsAct targetActivity, EditText editText,
                                              CustomKeyboardView
                                                      displayKeyboardView) {
        mTargetActivity = targetActivity;
        this.editText = editText;
        this.displayKeyboardView = displayKeyboardView;
    }

    @Override
    public void swipeUp() {
        // TODO Auto-generated method stub

    }

    @Override
    public void swipeRight() {
        // TODO Auto-generated method stub

    }

    @Override
    public void swipeLeft() {
        // TODO Auto-generated method stub

    }

    @Override
    public void swipeDown() {
        // TODO Auto-generated method stub

    }

    @Override
    public void onText(CharSequence text) {
        // TODO Auto-generated method stub
        int cursorPosition = editText.getSelectionEnd();
        String before = editText.getText().toString().substring(0, cursorPosition);
        String after = editText.getText().toString().substring(cursorPosition);
        editText.setText(before + text + after);
        editText.setSelection(cursorPosition + 1);


    }

    @Override
    public void onRelease(int primaryCode) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onPress(int primaryCode) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onKey(int primaryCode, int[] keyCodes) {
        switch (primaryCode) {
            case 66:

                try {
                    if (!editText.getText().toString().equalsIgnoreCase("")) {


                        int cursorPositionvv = editText.getSelectionEnd();
                        String beforegfdg = editText.getText().toString().substring(0, cursorPositionvv);
                        String aftergg = editText.getText().toString().substring(cursorPositionvv);
                        editText.setText(beforegfdg + "\n" + aftergg);
                        editText.setSelection(cursorPositionvv + 1);
                    }
                } catch (Exception e) {

                }

                break;
            case 67:

                try {
                    if (!editText.getText().toString().equalsIgnoreCase("")) {

                        int cursorPosition = editText.getSelectionEnd();
                        String after = editText.getText().toString().substring(0, cursorPosition - 1);
                        editText.setText(after);
                        editText.setSelection(cursorPosition - 1);

                    }
                } catch (Exception e) {
                }

                break;

          /*  case 66:
            case 67:
                long eventTime = System.currentTimeMillis();
                KeyEvent event =
                        new KeyEvent(eventTime, eventTime, KeyEvent.ACTION_DOWN, primaryCode, 0, 0, 0, 0,
                                KeyEvent.FLAG_SOFT_KEYBOARD | KeyEvent.FLAG_KEEP_TOUCH_MODE);

                mTargetActivity.dispatchKeyEvent(event);
                break;*/
            case -106:
                displayKeyboardView
                        .setKeyboard(new Keyboard(mTargetActivity, R.xml.kbd_hin2));
                break;
            case -107:
                displayKeyboardView
                        .setKeyboard(new Keyboard(mTargetActivity, R.xml.kbd_hin1));
                break;
            case -108:
                displayKeyboardView
                        .setKeyboard(new Keyboard(mTargetActivity, R.xml.kbd_guj2));
                break;
            case -109:
                displayKeyboardView.setKeyboard(new Keyboard(mTargetActivity, R.xml.kbd_guj1));
                break;
            case -110:
                displayKeyboardView.setKeyboard(new Keyboard(mTargetActivity, R.xml.kbd_mar2));
                break;
            case -111:
                displayKeyboardView.setKeyboard(new Keyboard(mTargetActivity, R.xml.kbd_mar1));
                break;
            case -112:
                displayKeyboardView.setKeyboard(new Keyboard(mTargetActivity, R.xml.kbd_knd2));
                break;
            case -113:
                displayKeyboardView.setKeyboard(new Keyboard(mTargetActivity, R.xml.kbd_knd1));
                break;
            case -114:
                displayKeyboardView.setKeyboard(new Keyboard(mTargetActivity, R.xml.kbd_tam2));
                break;
            case -115:
                displayKeyboardView.setKeyboard(new Keyboard(mTargetActivity, R.xml.kbd_tam1));
                break;
            case -116:
                displayKeyboardView.setKeyboard(new Keyboard(mTargetActivity, R.xml.kbd_punj2));
                break;
            case -117:
                displayKeyboardView.setKeyboard(new Keyboard(mTargetActivity, R.xml.kbd_punj1));
                break;

            case -118:
                displayKeyboardView.setKeyboard(new Keyboard(mTargetActivity, R.xml.kbd_tel2));
                break;
            case -119:
                displayKeyboardView.setKeyboard(new Keyboard(mTargetActivity, R.xml.kbd_tel1));
                break;
            case -120:
                displayKeyboardView.setKeyboard(new Keyboard(mTargetActivity, R.xml.kbd_eng1));
                break;
            case -121:
                displayKeyboardView.setKeyboard(new Keyboard(mTargetActivity, R.xml.kbd_eng2));
                break;


            case -122:
                mTargetActivity.show_alertdialogue(editText);
                break;
            case -123:
                mTargetActivity.show_alertdialogueFonts(editText);
                break;

            default:
                break;
        }
    }
}
