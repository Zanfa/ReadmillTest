package com.example.Readmill;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.PowerMock;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(PowerMockRunner.class)
public class UserPreferencesTest {

    private static final int[] VALID_FONT_SIZES = new int[] {10, 12, 14};
    private static final int[] VALID_MARGINS = new int[] {4, 6, 8};

    private Context context;
    private UserPreferences preferences;

    @Before
    public void setUp() throws Exception {
        context = PowerMock.createMock(Context.class);

        // Mock Resources
        Resources resources = PowerMock.createMock(Resources.class);

        EasyMock.expect(context.getResources()).andReturn(resources);
        EasyMock.expect(resources.getIntArray(R.array.valid_font_sizes)).andReturn(VALID_FONT_SIZES);
        EasyMock.expect(resources.getIntArray(R.array.valid_margins)).andReturn(VALID_MARGINS);

        // Mock the SharedPreferences interface
        SharedPreferences sharedPreferences = PowerMock.createMock(SharedPreferences.class);

        EasyMock.expect(context.getSharedPreferences(UserPreferences.USER_PREFERENCES, Context.MODE_PRIVATE))
                .andReturn(sharedPreferences);
        EasyMock.expect(sharedPreferences.getInt(UserPreferences.FONT_SIZE, UserPreferences.DEFAULT_FONT_SIZE))
                .andReturn(UserPreferences.DEFAULT_FONT_SIZE);
        EasyMock.expect(sharedPreferences.getInt(UserPreferences.MARGIN, UserPreferences.DEFAULT_MARGIN))
                .andReturn(UserPreferences.DEFAULT_MARGIN);
        EasyMock.expect(sharedPreferences.getFloat(UserPreferences.BRIGHTNESS, UserPreferences.DEFAULT_BRIGHTNESS))
                .andReturn(UserPreferences.DEFAULT_BRIGHTNESS);
        EasyMock.expect(sharedPreferences.getBoolean(UserPreferences.NIGHT_MODE, UserPreferences.DEFAULT_NIGHT_MODE))
                .andReturn(UserPreferences.DEFAULT_NIGHT_MODE);

        PowerMock.replayAll();

        preferences = new UserPreferences(context);

        PowerMock.verifyAll();
    }

    @Test
    public void testSetFontSize() throws Exception {
        // Value exists in VALID_FONT_SIZES
        assertThat(preferences.setFontSize(10), equalTo(true));
        assertThat(preferences.setFontSize(12), equalTo(true));
        assertThat(preferences.setFontSize(14), equalTo(true));

        // Check the last valid size
        assertThat(preferences.getFontSize(), equalTo(14));

        // Value doesn't exist in VALID_FONT_SIZES
        assertThat(preferences.setFontSize(6), equalTo(false));
        assertThat(preferences.setFontSize(11), equalTo(false));
        assertThat(preferences.setFontSize(13), equalTo(false));
        assertThat(preferences.setFontSize(16), equalTo(false));

        // Still the last valid size
        assertThat(preferences.getFontSize(), equalTo(14));
    }

    @Test
    public void testSetMargin() throws Exception {
        // Value exists in VALID_MARGINS
        assertThat(preferences.setMargin(4), equalTo(true));
        assertThat(preferences.setMargin(6), equalTo(true));
        assertThat(preferences.setMargin(8), equalTo(true));

        // Check the last valid value
        assertThat(preferences.getMargin(), equalTo(8));

        // Value doesn't exist in VALID_MARGINS
        assertThat(preferences.setMargin(7), equalTo(false));
        assertThat(preferences.setMargin(10), equalTo(false));

        // Check the last valid value
        assertThat(preferences.getMargin(), equalTo(8));
    }

    @Test
    public void testSetBrightness() throws Exception {
        // Value between 0.0 - 1.0
        assertThat(preferences.setBrightness(0.5f), equalTo(true));

        // Value over 1.0
        assertThat(preferences.setBrightness(1.2f), equalTo(false));

        // Value below 0.0
        assertThat(preferences.setBrightness(-0.5f), equalTo(false));

        // Check the last valid value
        assertThat(preferences.getBrightness(), equalTo(0.5f));
    }

    @Test
    public void testSave() throws Exception {
        PowerMock.resetAll();

        SharedPreferences sharedPreferences = PowerMock.createMock(SharedPreferences.class);

        // Mock the Editor interface
        SharedPreferences.Editor editor = PowerMock.createMock(SharedPreferences.Editor.class);

        EasyMock.expect(editor.putInt(UserPreferences.FONT_SIZE, UserPreferences.DEFAULT_FONT_SIZE)).andReturn(editor);
        EasyMock.expect(editor.putInt(UserPreferences.MARGIN, UserPreferences.DEFAULT_MARGIN)).andReturn(editor);
        EasyMock.expect(editor.putFloat(UserPreferences.BRIGHTNESS, UserPreferences.DEFAULT_BRIGHTNESS)).andReturn(editor);
        EasyMock.expect(editor.putBoolean(UserPreferences.NIGHT_MODE, UserPreferences.DEFAULT_NIGHT_MODE)).andReturn(editor);
        EasyMock.expect(editor.commit()).andReturn(true);

        EasyMock.expect(context.getSharedPreferences(UserPreferences.USER_PREFERENCES, Context.MODE_PRIVATE))
                .andReturn(sharedPreferences);
        EasyMock.expect(sharedPreferences.edit()).andReturn(editor);

        PowerMock.replayAll();

        // Check that default values were saved successfully
        assertThat(preferences.save(context), equalTo(true));

        PowerMock.verifyAll();
    }

}
