# Rosetta [![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.ahmedjazzar.rosetta/rosetta/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.ahmedjazzar.rosetta/rosetta/badge.svg) [![Android Gems](http://www.android-gems.com/badge/ahmedaljazzar/rosetta.svg?branch=master)](http://www.android-gems.com/lib/ahmedaljazzar/rosetta)


Rosetta is an Android library that helps Android apps support multiple languages. Using Rosetta, switching between languages won't cause any headaches!

![First screenshot](https://s3-us-west-2.amazonaws.com/ahmedjazzar-ahmedjazzarcom/libs/rosetta-v1.0.png)

## How to use

#### 1. Add these lines to your *build.gradle*
You can use *Jcenter()* or *mavenCentral()*

```JAVA
repositories {
    mavenCentral()
}

dependencies {
  compile "com.ahmedjazzar.rosetta:rosetta:1.0.1"
}
```

#### 2. Identify your locales, **or let the library do it for you**!

At the launch activity or **MainApplication.java**, the library offers two ways to identify
and set the supported locales:

* For the first way, you need to know what languages your app is going to use. This method is predictable, easy to use, and more efficient than the second method. You can use it if your app is closed source or has specific supported locales:

```JAVA

// This is the locale that you wanna your app to launch with.
Locale firstLaunchLocale = new Locale("ar");

// You can use a HashSet<String> instead and call 'setSupportedStringLocales()' :)
HashSet<Locale> supportedLocales = new HashSet<>();
supportedLocales.add(Locale.US);
supportedLocales.add(Locale.CHINA);
supportedLocales.add(firstLaunchLocale);

// You can make the following object static so you can use the same reference in all app's
// classes. static is much stable.
LanguageSwitcher ls = new LanguageSwitcher(this, firstLaunchLocale);
ls.setSupportedLocales(supportedLocales);

```

* The second way is asking the library to do its magic and search inside the app for available locales. *May fail!* *:

```JAVA

// This is the locale that you wanna your app to launch with.
Locale firstLaunchLocale = new Locale("ar");

// IMPORTANT: this is the locale of the main strings.xml file. -- most developers write it
// in English, so if you wrote it in another locale specify it here.
Locale baseLocale = Locale.ENGLISH;

// stringId: the id of a string that's occurred in every locale with a different character.
// For instance if you have 3 locales: US, UK, and Arabic the perfect fit would be the word
// 'color' -if exist for sure- because it has different form in each locale:
// Locale.US: color, Locale.UK: colour, ar: لون
int stringId = R.string.nice_string;

LanguageSwitcher ls = new LanguageSwitcher(this, firstLaunchLocale, baseLocale);
ls.setSupportedLocales(stringId);

```

The second methodology fetches the available locales in your app, then sets them as the support locales (since there are no supported locales provided).
by executing the following command you will see what locales will be fetched before setting them:

	```JAVA
	ls.fetchAvailableLocales(stringId);
	```

	the return value is a **HashSet<Locale>** of the detected locales.
* * It may fail because even if you have a folder named **values-ar** it doesn't mean you have any resources there. If you have **values-ar** and you provide the **LanguageSwitcher** with a string that is not listed, or has the same characters in the same order then your locale will not be detected (i.e *nice* in US and *nice* in the UK). 

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
    	// If you have a static declared switcher You can call it using the
    	// following line:
    	// MainApplication.languageSwitcher.showChangeLanguageDialog(this);
        new LanguageSwitcher(getActivity()).showChangeLanguageDialog();
    }
});

// The following 3 lines are extra and not needed :)
TextView language_tv = (TextView) layout.findViewById(R.id.current_language);
Locale currentLocale = getResources().getConfiguration().locale;
language_tv.setText(currentLocale.getDisplayName(currentLocale));
```

#### 5. Cheers!
Yes! You did it. Your application now supports all Android supported locales.


## Extra features

1. To get a HashSet of the supported locales: `ls.getLocales()`
2. To set the supported locales using a `HashSet<String>` instead of `HashSet<Locale>`:
`ls.setSupportedStringLocales(supportedLocales)`
3. To return to your launch locale (helpful when you have non-localized views): `ls.switchToLaunch(SomeActivity.this)`
4. To use your own `DialogFragment` with your custom design:
	1. Extend `LanguagesListDialogFragment` and override `onCreateDialog`. For example:
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
	2. In **positive button** OnClickListener call: `onPositiveClick();`
	3. In **OnItemClickListener** use `onLanguageSelected(position);` or if you want to localize the dialog title, positive button, and negative button texts, call `onLanguageSelectedLocalized(position, titleTextView, positiveBtn, negativeBtn);`
	4. You can use other methods available in the parent class, such as:
		1. `getLanguages()` to get a display-ready languages.
		2. `getCurrentLocaleIndex()` to get the index of the locale that your app is displaying right now.

## What is Rosetta?

Rosetta is the first name of Rosetta Stone. Rosetta Stone is a granodiorite stele inscribed with a decree issued at Memphis, Egypt, in 196 BC on behalf of King Ptolemy V. The decree appears in three scripts: The upper text is Ancient Egyptian hieroglyphs, the middle portion is a Demotic script, and the lowest is Ancient Greek. The stone provided the key to the modern understanding of Egyptian hieroglyphs because it presented the same text in all three scripts (with some minor differences among them). Continue reading [here](https://en.wikipedia.org/wiki/Rosetta_Stone).

## License
The MIT License (MIT)
Copyright (c) 2016 Ahmed Jazzar <me@ahmedjazzar.com>


*NOTE: This library does not translate your application localized strings, it's just helping you switch between them.*
