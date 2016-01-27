# Rosetta
Rosetta is an Android library that helps your app to support multiple languages and switching between them without causing any headaches for you as a developer.

![First screenshot](https://s3.amazonaws.com/ahmedjazzar-personalsite/libraries/all.png)

## How to use

#### 1. Add these lines to your *build.gradle*

```JAVA
repositories {
    mavenCentral()
}

dependencies {
  compile "com.ahmedjazzar.rosetta:rosetta:0.9.0"
}
```

#### 2. Identify your locales, **or let the library do it for you**!

At your launch activity or **MainApplication.java** -if exists-, the Library offers two ways to identify
and set the supported locales:

* The first one requires you to know what languages your app gonna use. It's more efficient, predictable, and easy to use.
You can use this method if you have a closed source app, or your app has a specific supported locales:

```JAVA

// This is the locale that you wanna your app to launch with.
Locale firstLaunchLocale = new Locale("ar");

// You can use a HashSet<String> instead and call 'setSupportedStringLocales()'
// and it'll work perfectly :)
HashSet<Locale> supportedLocales = new HashSet<>();
supportedLocales.add(Locale.US);
supportedLocales.add(Locale.CHINA);
supportedLocales.add(new Locale("ar"));

LanguageSwitcher ls = new LanguageSwitcher(this, firstLaunchLocale);
ls.setSupportedLocales(supportedLocales);

```

* The second one asking the library to do its magic and searches inside the app for available locale. *May fail!* *:

```JAVA

// This is the locale that you wanna your app to launch with.
Locale firstLaunchLocale = new Locale("ar");

// IMPORTANT: this is the locale of the main strings.xml file. -- most developers write it
// in English, so if you wrote it in another locale specify it here.
Locale baseLocale = Locale.ENGLISH;

// stringId: the id of a string that's occurred in every locale with a different characters.
// For instance if you have 3 locales: US, UK, and Arabic the perfect fit would be the word
// 'color' -if exist for sure- because it has different form in each locale:
// Locale.US: color, Locale.UK: colour, ar: لون
int stringId = R.string.nice_string;

LanguageSwitcher ls = new LanguageSwitcher(this, firstLaunchLocale, baseLocale);
ls.setSupportedLocales(stringId);

```

* The second methodology is gonna fetch the available locales in your app then set them as the support locales since there's no supported locales provided.
* You can see what locales gonna be fetched before setting them by calling:

	```JAVA
	ls.fetchAvailableLocales(stringId);
	```

	and it's gonna return a **HashSet<Locale>** of the detected locales.
* * It may fails because even if you have a folder named **values-ar** it doesn't mean you have any resources there, so if you have **values-ar** and you provided the **LanguageSwitcher** with a string that's not listed in, or it has the same characters in the same order; i.e *nice* in US and *nice* in UK, then your locale, unfortunately, will not detected.

#### 3. Add a tab in your **activity_settings.xml**

Using this tab the user will be able to change the application language

```XML
<LinearLayout
    android:id="@id/language_layout"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:orientation="vertical">

    <TextView
        android:id="@id/languageText"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="Language"
        android:textSize="13sp" />

    <TextView
        android:id="@id/current_language"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:textSize="11sp"
        tools:text="English" />
</LinearLayout>
```

#### 4. Add the following functionality to your **SettingsActivity.java**

```JAVA

LinearLayout languageView = (LinearLayout) layout.findViewById(R.id.language_layout);
languageView.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        new LanguageSwitcher(getActivity()).showChangeLanguageDialog();
    }
});

// The following 3 lines are extra and not needed :)
TextView language_tv = (TextView) layout.findViewById(R.id.current_language);
Locale currentLocale = getResources().getConfiguration().locale;
language_tv.setText(currentLocale.getDisplayName(currentLocale));
```

#### 5. Cheers!
Yes! You did it. Your application supports now every locale supported by android.


## Extra features

1. To get a HashSet of the supported locales: `ls.getLocales()`.
2. To set the supported locales using a `HashSet<String>` instead of `HashSet<Locale>`:
`ls.setSupportedStringLocales(supportedLocales)`.
3. To use your own `DialogFragment` with your custom design:
	1. Extend `LanguagesListDialogFragment` and override `onCreateDialog`! For example:
	```JAVA
	public class ChangeLanguageDialogFragment extends LanguagesListDialogFragment	{
		.
		.
		.
	    @Override
	    public Dialog onCreateDialog(Bundle savedInstanceState) {
	        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	        .
	        .
	        .
	        return builder.create();
	    }

		.
		.
		.
	}
	```
	2. In **positive button** on click listener call: `onPositiveClick();`
	3. In **OnItemClickListener** use `onLanguageSelected(position);`, or if you wanna localize the dialog title, positive button, and negative button texts call `onLanguageSelectedLocalized(position, titleTextView, positiveBtn, negativeBtn);`.
	4. You can use other helpful methods available in the parent class like:
		1. `getLanguages()` to get a display-ready languages.
		2. `getCurrentLocaleIndex()` to get the index of the locale that your app is displaying right now.

## What is Rosetta?*

The Rosetta Stone is a granodiorite stele inscribed with a decree issued at Memphis, Egypt, in 196 BC on behalf of King Ptolemy V. The decree appears in three scripts: the upper text is Ancient Egyptian hieroglyphs, the middle portion Demotic script, and the lowest Ancient Greek. Because it presents essentially the same text in all three scripts (with some minor differences among them), the stone provided the key to the modern understanding of Egyptian hieroglyphs.
* Continue reading [here](https://en.wikipedia.org/wiki/Rosetta_Stone)

## License
The MIT License (MIT)
Copyright (c) 2016 Ahmed Jazzar <me@ahmedjazzar.com>


*NOTE: This library does not translate your application localized strings, it's just switching between them.*
