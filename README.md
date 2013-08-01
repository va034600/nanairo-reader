nanairo-reader
==============
[![Build Status](https://travis-ci.org/va034600/nanairo-reader.png?branch=master)](https://travis-ci.org/va034600/nanairo-reader)

#開発環境
	eclipse
		m2e-android
		m2eclipse

#パッケージング

```.m2/settings.xml に以下を追加
&lt;profiles>
		&lt;profile>
		&lt;id>sign&lt;/id>
		&lt;properties>
			&lt;keystore.path>/Users/test/nanairo.keystore&lt;/keystore.path>
			&lt;keystore.store.password>password&lt;/keystore.store.password>
			&lt;keystore.key.password>password&lt;/keystore.key.password>
			&lt;keystore.alias>abcalias&lt;/keystore.alias>
		&lt;/properties>
		&lt;activation>
			&lt;activeByDefault>true&lt;/activeByDefault>
			&lt;property>
				&lt;name>performRelease&lt;/name>
				&lt;value>true&lt;/value>
			&lt;/property>
		&lt;/activation>
	&lt;/profile>
&lt;/profiles>
```

#コマンド
 mvn -Psign clean package



