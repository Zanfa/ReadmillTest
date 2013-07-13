package com.example.Readmill;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserPreferences {

    public static final String USER_PREFERENCES = "user_preferences";
    public static final String FONT_SIZE = "font_size";
    public static final String MARGIN = "margin";
    public static final String BRIGHTNESS = "brightness";
    public static final String NIGHT_MODE = "night_mode";

    public static final int DEFAULT_FONT_SIZE = 12;
    public static final int DEFAULT_MARGIN = 10;
    public static final float DEFAULT_BRIGHTNESS = 1.0f;
    public static final boolean DEFAULT_NIGHT_MODE = false;

    private int fontSize = DEFAULT_FONT_SIZE;
    private int margin = DEFAULT_MARGIN;
    private float brightness = DEFAULT_BRIGHTNESS;
    private boolean nightMode = DEFAULT_NIGHT_MODE;

    private int[] validFontSizes;
    private int[] validMargins;

    public UserPreferences(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(USER_PREFERENCES, Context.MODE_PRIVATE);

        fontSize = preferences.getInt(FONT_SIZE, DEFAULT_FONT_SIZE);
        margin = preferences.getInt(MARGIN, DEFAULT_MARGIN);
        brightness = preferences.getFloat(BRIGHTNESS, DEFAULT_BRIGHTNESS);
        nightMode = preferences.getBoolean(NIGHT_MODE, DEFAULT_NIGHT_MODE);

        Resources resources = context.getResources();

        validFontSizes = resources.getIntArray(R.array.valid_font_sizes);
        validMargins = resources.getIntArray(R.array.valid_margins);
    }

    /**
     * Persist the current UserPreferences into SharedPreferences. Should be called in the
     * onPause method of the containing Fragment or Activity
     *
     * @param context Activity or Context, which will be used to retrieve UserPreferences
     */
    public boolean save(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(USER_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putInt(FONT_SIZE, fontSize);
        editor.putInt(MARGIN, margin);
        editor.putFloat(BRIGHTNESS, brightness);
        editor.putBoolean(NIGHT_MODE, nightMode);

        return editor.commit();
    }

    /**
     * Validate the user chosen font size and save the value if it's among the given valid values
     *
     * @param fontSize Font size to be validated and saved
     * @return True if margin is valid and saved, False if value is invalid and not saved
     */
    public boolean setFontSize(int fontSize) {
        for (int validFontSize: validFontSizes) {
            if (fontSize == validFontSize) {
                this.fontSize = fontSize;
                return true;
            }
        }

        return false;
    }

    /**
     * Validate the user chosen margin and save the value if it's among the given valid values
     *
     * @param margin Margin value to be validated and saved
     * @return True if font size is valid and saved, False if value is invalid and not saved
     */
    public boolean setMargin(int margin) {
        for (int validMarginSize: validMargins) {
            if (margin == validMarginSize) {
                this.margin = margin;
                return true;
            }
        }

        return false;
    }

    /**
     * Validate the brightness value (must be between 0.0 - 1.0) and save the value it it's valid
     *
     * @param brightness Brightness value to be validated and saved
     * @return True if brightness is valid and saved, False if value is invalid and not saved
     */
    public boolean setBrightness(float brightness) {
        if (brightness >= 0.0f && brightness <= 1.0f) {
            this.brightness = brightness;
            return true;
        }

        return false;
    }

    public int getFontSize() {
        return fontSize;
    }

    public int getMargin() {
        return margin;
    }

    public float getBrightness() {
        return brightness;
    }

    public boolean isNightMode() {
        return nightMode;
    }

    public void setNightMode(boolean nightMode) {
        this.nightMode = nightMode;
    }
}
