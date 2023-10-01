package st.far.indonesiagul

import android.inputmethodservice.InputMethodService
import android.inputmethodservice.Keyboard
import android.inputmethodservice.KeyboardView
import android.view.View
import android.view.inputmethod.InputConnection

class MyKeyboardService : InputMethodService() {

    private lateinit var keyboardView: KeyboardView
    private lateinit var keyboard: Keyboard

    override fun onCreateInputView(): View {
        keyboardView = layoutInflater.inflate(R.layout.keyboard_view, null) as KeyboardView
        keyboard = Keyboard(this, R.xml.keyboard_layout)
        keyboardView.keyboard = keyboard
        keyboardView.setOnKeyboardActionListener(MyKeyboardActionListener())
        return keyboardView
    }

    private inner class MyKeyboardActionListener : KeyboardView.OnKeyboardActionListener {

        override fun onPress(primaryCode: Int) {
            // Handle key press (e.g., show a key preview popup)
        }

        override fun onRelease(primaryCode: Int) {
            // Handle key release (e.g., hide the key preview popup)
        }

        override fun onKey(primaryCode: Int, keyCodes: IntArray?) {
            val inputConnection: InputConnection = currentInputConnection ?: return
            when (primaryCode) {
                Keyboard.KEYCODE_DELETE -> {
                    val selectedText = inputConnection.getSelectedText(0)
                    if (selectedText != null && selectedText.isNotEmpty()) {
                        // Delete the selected text
                        inputConnection.commitText("", 1)
                    } else {
                        // No text selected, delete previous character
                        inputConnection.deleteSurroundingText(1, 0)
                    }
                }
                Keyboard.KEYCODE_DONE -> {
                    // Handle the DONE key
                    requestHideSelf(0)
                }
                else -> {
                    // Insert the character
                    val code = Character.toString(primaryCode.toChar())
                    inputConnection.commitText(code, 1)
                }
            }
        }

        override fun onText(text: CharSequence?) {
            // Handle text input (e.g., from a key with a text label)
            val inputConnection: InputConnection = currentInputConnection ?: return
            inputConnection.commitText(text, 1)
        }

        override fun swipeLeft() {
            // Handle left swipe
        }

        override fun swipeRight() {
            // Handle right swipe
        }

        override fun swipeDown() {
            // Handle down swipe
        }

        override fun swipeUp() {
            // Handle up swipe
        }
    }
}
