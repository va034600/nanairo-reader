nanairo-reader
==============
[![Build Status](https://travis-ci.org/va034600/nanairo-reader.png?branch=master)](https://travis-ci.org/va034600/nanairo-reader)

#開発環境
	eclipse
		m2e-android
		m2eclipse

#パッケージング

```.m2/settings.xml に以下を追加

	&lt;profiles&gt;
		&lt;profile&gt;
			&lt;id&gt;sign&lt;/id&gt;
			&lt;properties&gt;
				&lt;keystore.path&gt;/Users/test/nanairo.keystore&lt;/keystore.path&gt;
				&lt;keystore.store.password&gt;password&lt;/keystore.store.password&gt;
				&lt;keystore.key.password&gt;password&lt;/keystore.key.password&gt;
				&lt;keystore.alias&gt;abcalias&lt;/keystore.alias&gt;
			&lt;/properties&gt;
			&lt;activation&gt;
				&lt;activeByDefault&gt;true&lt;/activeByDefault&gt;
				&lt;property&gt;
					&lt;name&gt;performRelease&lt;/name&gt;
					&lt;value&gt;true&lt;/value&gt;
				&lt;/property&gt;
			&lt;/activation&gt;
		&lt;/profile&gt;
	&lt;/profiles&gt;
```

#コマンド
 mvn -Psign clean package



