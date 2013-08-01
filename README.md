nanairo-reader
==============
[![Build Status](https://travis-ci.org/va034600/nanairo-reader.png?branch=master)](https://travis-ci.org/va034600/nanairo-reader)

#開発環境
	eclipse
		m2e-android
		m2eclipse

#パッケージング

```.m2/settings.xml に以下を追加
	<profiles>
			<profile>
			<id>sign</id>
			<properties>
				<keystore.path>/Users/test/nanairo.keystore</keystore.path>
				<keystore.store.password>password</keystore.store.password>
				<keystore.key.password>password</keystore.key.password>
				<keystore.alias>abcalias</keystore.alias>
			</properties>
			<activation>
				<activeByDefault>true</activeByDefault>
				<property>
					<name>performRelease</name>
					<value>true</value>
				</property>
			</activation>
		</profile>
	</profiles>
```

#コマンド
 mvn -Psign clean package



