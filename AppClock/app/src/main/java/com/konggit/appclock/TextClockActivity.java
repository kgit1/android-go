package com.konggit.appclock;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class TextClockActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_clock);
    }
}
/*

TextClock https://developer.android.com/reference/android/widget/TextClock.html
public class TextClock
extends TextView

java.lang.Object
   ↳	android.view.View
 	   ↳	android.widget.TextView
 	 	   ↳	android.widget.TextClock

TextClock can display the current date and/or time as a formatted string.

This view honors the 24-hour format system setting. As such, it is possible and recommended
to provide two different formatting patterns: one to display the date/time in 24-hour mode and
one to display the date/time in 12-hour mode. Most callers will want to use the defaults, though,
which will be appropriate for the user's locale.

It is possible to determine whether the system is currently in 24-hour mode by calling is24HourModeEnabled().

The rules used by this widget to decide how to format the date and time are the following:

In 24-hour mode:
Use the value returned by getFormat24Hour() when non-null
Otherwise, use the value returned by getFormat12Hour() when non-null
Otherwise, use a default value appropriate for the user's locale, such as h:mm a
In 12-hour mode:
Use the value returned by getFormat12Hour() when non-null
Otherwise, use the value returned by getFormat24Hour() when non-null
Otherwise, use a default value appropriate for the user's locale, such as HH:mm
The CharSequence instances used as formatting patterns when calling either setFormat24Hour(CharSequence)
or setFormat12Hour(CharSequence) can contain styling information. To do so, use a Spanned object.
Note that if you customize these strings, it is your responsibility to supply strings appropriate
for formatting dates and/or times in the user's locale.

Summary
XML attributes
android:format12Hour	Specifies the formatting pattern used to show the time and/or date in 12-hour mode.
android:format24Hour	Specifies the formatting pattern used to show the time and/or date in 24-hour mode.
android:timeZone	Specifies the time zone to use.
Inherited XML attributes
  From class android.widget.TextView
  From class android.view.View
Inherited constants
  From class android.widget.TextView
  From class android.view.View
Fields
public static final CharSequence	DEFAULT_FORMAT_12_HOUR
This field was deprecated in API level 18. Let the system use locale-appropriate defaults instead.
public static final CharSequence	DEFAULT_FORMAT_24_HOUR
This field was deprecated in API level 18. Let the system use locale-appropriate defaults instead.
Inherited fields
  From class android.view.View
Public constructors
TextClock(Context context)
Creates a new clock using the default patterns for the current locale.
TextClock(Context context, AttributeSet attrs)
Creates a new clock inflated from XML.
TextClock(Context context, AttributeSet attrs, int defStyleAttr)
Creates a new clock inflated from XML.
TextClock(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes)
Public methods
CharSequence	getFormat12Hour()
Returns the formatting pattern used to display the date and/or time in 12-hour mode.
CharSequence	getFormat24Hour()
Returns the formatting pattern used to display the date and/or time in 24-hour mode.
String	getTimeZone()
Indicates which time zone is currently used by this view.
boolean	is24HourModeEnabled()
Indicates whether the system is currently using the 24-hour mode.
void	onVisibilityAggregated(boolean isVisible)
Called when the user-visibility of this View is potentially affected by a change to this view
itself, an ancestor view or the window this view is attached to.
void	setFormat12Hour(CharSequence format)
Specifies the formatting pattern used to display the date and/or time in 12-hour mode.
void	setFormat24Hour(CharSequence format)
Specifies the formatting pattern used to display the date and/or time in 24-hour mode.
void	setTimeZone(String timeZone)
Sets the specified time zone to use in this clock.
Protected methods
void	onAttachedToWindow()
This is called when the view is attached to a window.
void	onDetachedFromWindow()
This is called when the view is detached from a window.
Inherited methods
  From class android.widget.TextView
  From class android.view.View
  From class java.lang.Object
  From interface android.view.ViewTreeObserver.OnPreDrawListener
  From interface android.graphics.drawable.Drawable.Callback
  From interface android.view.KeyEvent.Callback
  From interface android.view.accessibility.AccessibilityEventSource
XML attributes
android:format12Hour

Specifies the formatting pattern used to show the time and/or date in 12-hour mode. Please refer
to DateFormat for a complete description of accepted formatting patterns. The default pattern is
a locale-appropriate equivalent of "h:mm a".

May be a string value, using '\\;' to escape characters such as '\\n' or '\\uxxxx' for a unicode character;

Related methods:

setFormat12Hour(CharSequence)
android:format24Hour

Specifies the formatting pattern used to show the time and/or date in 24-hour mode. Please
refer to DateFormat for a complete description of accepted formatting patterns. The default pattern
is a locale-appropriate equivalent of "H:mm".

May be a string value, using '\\;' to escape characters such as '\\n' or '\\uxxxx' for a unicode character;

Related methods:

setFormat24Hour(CharSequence)
android:timeZone

Specifies the time zone to use. When this attribute is specified, the TextClock will ignore
the time zone of the system. To use the user's time zone, do not specify this attribute.
The default value is the user's time zone. Please refer to TimeZone for more information
about time zone ids.

May be a string value, using '\\;' to escape characters such as '\\n' or '\\uxxxx' for a unicode character;

Related methods:

setTimeZone(String)
Fields
DEFAULT_FORMAT_12_HOUR

added in API level 17
CharSequence DEFAULT_FORMAT_12_HOUR
This field was deprecated in API level 18.
Let the system use locale-appropriate defaults instead.

The default formatting pattern in 12-hour mode. This pattern is used if setFormat12Hour(CharSequence)
is called with a null pattern or if no pattern was specified when creating an instance of this class.
This default pattern shows only the time, hours and minutes, and an am/pm indicator.

See also:

setFormat12Hour(CharSequence)
getFormat12Hour()
DEFAULT_FORMAT_24_HOUR

added in API level 17
CharSequence DEFAULT_FORMAT_24_HOUR
This field was deprecated in API level 18.
Let the system use locale-appropriate defaults instead.

The default formatting pattern in 24-hour mode. This pattern is used if
setFormat24Hour(CharSequence) is called with a null pattern or if no pattern was specified when
creating an instance of this class. This default pattern shows only the time, hours and minutes.

See also:

setFormat24Hour(CharSequence)
getFormat24Hour()
Public constructors
TextClock

added in API level 17
TextClock (Context context)
Creates a new clock using the default patterns for the current locale.

Parameters
context	Context: The Context the view is running in, through which it can access the current theme,
resources, etc.
TextClock

added in API level 17
TextClock (Context context,
                AttributeSet attrs)
Creates a new clock inflated from XML. This object's properties are intialized from the attributes
specified in XML. This constructor uses a default style of 0, so the only attribute values applied
are those in the Context's Theme and the given AttributeSet.

Parameters
context	Context: The Context the view is running in, through which it can access the current theme,
resources, etc.
attrs	AttributeSet: The attributes of the XML tag that is inflating the view
TextClock

added in API level 17
TextClock (Context context,
                AttributeSet attrs,
                int defStyleAttr)
Creates a new clock inflated from XML. This object's properties are intialized from the attributes
specified in XML.

Parameters
context	Context: The Context the view is running in, through which it can access the current theme,
 resources, etc.
attrs	AttributeSet: The attributes of the XML tag that is inflating the view
defStyleAttr	int: An attribute in the current theme that contains a reference to a style resource
that supplies default values for the view. Can be 0 to not look for defaults.
TextClock

added in API level 21
TextClock (Context context,
                AttributeSet attrs,
                int defStyleAttr,
                int defStyleRes)
Parameters
context	Context
attrs	AttributeSet
defStyleAttr	int
defStyleRes	int
Public methods
getFormat12Hour

added in API level 17
CharSequence getFormat12Hour ()
Returns the formatting pattern used to display the date and/or time in 12-hour mode.
The formatting pattern syntax is described in DateFormat.

Returns
CharSequence	A CharSequence or null.
See also:

setFormat12Hour(CharSequence)
is24HourModeEnabled()
getFormat24Hour

added in API level 17
CharSequence getFormat24Hour ()
Returns the formatting pattern used to display the date and/or time in 24-hour mode.
The formatting pattern syntax is described in DateFormat.

Returns
CharSequence	A CharSequence or null.
See also:

setFormat24Hour(CharSequence)
is24HourModeEnabled()
getTimeZone

added in API level 17
String getTimeZone ()
Indicates which time zone is currently used by this view.

Returns
String	The ID of the current time zone or null if the default time zone, as set by the user,
must be used
See also:

TimeZone
getAvailableIDs()
setTimeZone(String)
is24HourModeEnabled

added in API level 17
boolean is24HourModeEnabled ()
Indicates whether the system is currently using the 24-hour mode. When the system is in 24-hour mode,
this view will use the pattern returned by getFormat24Hour(). In 12-hour mode, the pattern returned by getFormat12Hour() is used instead. If either one of the formats is null, the other format is used. If both formats are null, the default formats for the current locale are used.

Returns
boolean	true if time should be displayed in 24-hour format, false if it should be displayed in 12-hour format.
See also:

setFormat12Hour(CharSequence)
getFormat12Hour()
setFormat24Hour(CharSequence)
getFormat24Hour()
onVisibilityAggregated

added in API level 24
void onVisibilityAggregated (boolean isVisible)
Called when the user-visibility of this View is potentially affected by a change to this view itself,
an ancestor view or the window this view is attached to.

Parameters
isVisible	boolean: true if this view and all of its ancestors are VISIBLE and this view's window
is also visible
setFormat12Hour

added in API level 17
void setFormat12Hour (CharSequence format)
Specifies the formatting pattern used to display the date and/or time in 12-hour mode.
The formatting pattern syntax is described in DateFormat.

If this pattern is set to null, getFormat24Hour() will be used even in 12-hour mode.
If both 24-hour and 12-hour formatting patterns are set to null, the default pattern for
the current locale will be used instead.

Note: if styling is not needed, it is highly recommended you supply a format string generated
by getBestDateTimePattern(java.util.Locale, String). This method takes care of generating
a format string adapted to the desired locale.

Related XML Attributes:

android:format12Hour
Parameters
format	CharSequence: A date/time formatting pattern as described in DateFormat
See also:

getFormat12Hour()
is24HourModeEnabled()
getBestDateTimePattern(java.util.Locale, String)
DateFormat
setFormat24Hour

added in API level 17
void setFormat24Hour (CharSequence format)
Specifies the formatting pattern used to display the date and/or time in 24-hour mode.
The formatting pattern syntax is described in DateFormat.

If this pattern is set to null, getFormat24Hour() will be used even in 12-hour mode.
If both 24-hour and 12-hour formatting patterns are set to null, the default pattern for the current locale will be used instead.

Note: if styling is not needed, it is highly recommended you supply a format string generated
by getBestDateTimePattern(java.util.Locale, String). This method takes care of generating
a format string adapted to the desired locale.

Related XML Attributes:

android:format24Hour
Parameters
format	CharSequence: A date/time formatting pattern as described in DateFormat
See also:

getFormat24Hour()
is24HourModeEnabled()
getBestDateTimePattern(java.util.Locale, String)
DateFormat
setTimeZone

added in API level 17
void setTimeZone (String timeZone)
Sets the specified time zone to use in this clock. When the time zone is set through this method,
system time zone changes (when the user sets the time zone in settings for instance) will be ignored.

Related XML Attributes:

android:timeZone
Parameters
timeZone	String: The desired time zone's ID as specified in TimeZone or null to user
the time zone specified by the user (system time zone)
See also:

getTimeZone()
getAvailableIDs()
getTimeZone(String)
Protected methods
onAttachedToWindow

added in API level 17
void onAttachedToWindow ()
This is called when the view is attached to a window. At this point it has a Surface and
will start drawing. Note that this function is guaranteed to be called before
onDraw(android.graphics.Canvas), however it may be called any time before the first onDraw --
including before or after onMeasure(int, int).

onDetachedFromWindow

added in API level 17
void onDetachedFromWindow ()
This is called when the view is detached from a window. At this point it no longer has a surface for drawing.

 */