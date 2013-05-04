# Splash creator

Example:

```

    <plugin>
        <groupId>org.bitstrings.maven.plugins</groupId>
        <artifactId>splasher-maven-plugin</artifactId>
        <version>1.0.0-SNAPSHOT</version>
        <configuration>

            <outputFile>image.png</outputFile>

            <canvas>
                <backgroundImageFile>src/it/resources/splashA.png</backgroundImageFile>
            </canvas>

            <fonts>
                <font>
                    <fontFile>src/it/resources/EurostileLTStd.otf</fontFile>
                    <alias>font1</alias>
                </font>
            </fonts>

            <drawables>
                <drawText>
                    <text>Version 3.0</text>
                    <fontName>font1</fontName>
                    <fontSize>13</fontSize>
                    <antialias>true</antialias>
                    <x>203</x>
                    <y>86</y>
                    <color>#222222</color>
                </drawText>

                <drawImage>
                    <imageFile>src/it/resources/serv_black_24.png</imageFile>
                    <x>372</x>
                    <y>5</y>
                </drawImage>
            </drawables>

        </configuration>
    </plugin>

```
