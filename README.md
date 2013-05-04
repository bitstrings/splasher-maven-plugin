# Splash Screen Generator

In the event you need to generate a splash screen using some build time info, this plugin can help.

Example 1:

```

    <plugin>
        <groupId>org.bitstrings.maven.plugins</groupId>
        <artifactId>splasher-maven-plugin</artifactId>
        <version>1.0.0-SNAPSHOT</version>
        <configuration>
            <outputFile>image.png</outputFile>

            <canvas>
                <width>640</width>
                <height>480</height>
                <color>#E0FFE0</color>
            </canvas>

            <draw>
                <drawText>
                    <text>--&gt; Center &lt;--</text>
                    <fontName>monospace</fontName>
                    <fontStyle>bold</fontStyle>
                    <fontSize>48</fontSize>
                    <antialias>true</antialias>
                    <color>#000000</color>
                    <position>center,center</position>
                </drawText>
                <drawText>
                    <text>:Top Left:</text>
                    <fontName>serif</fontName>
                    <fontStyle>bold</fontStyle>
                    <fontSize>32</fontSize>
                    <antialias>true</antialias>
                    <color>#000077</color>
                    <position>left,top</position>
                </drawText>
                <drawText>
                    <text>:Bottom Right:</text>
                    <fontName>serif</fontName>
                    <fontStyle>bold</fontStyle>
                    <fontSize>32</fontSize>
                    <antialias>true</antialias>
                    <color>#000077</color>
                    <position>right,bottom</position>
                </drawText>
            </draw>
        </configuration>
    </plugin>

```

Example 2:

```

    <plugin>
        <groupId>org.bitstrings.maven.plugins</groupId>
        <artifactId>splasher-maven-plugin</artifactId>
        <version>1.0.0-SNAPSHOT</version>
        <configuration>
            <outputFile>image.png</outputFile>

            <canvas>
                <backgroundImageFile>src/it/resources/splash.png</backgroundImageFile>
            </canvas>

            <draw>
                <drawImage>
                    <imageFile>src/it/resources/tux.png</imageFile>
                    <position>center,64</position>
                </drawImage>
                <drawImage>
                    <imageFile>src/it/resources/red-indicator.png</imageFile>
                    <position>left,top</position>
                </drawImage>
                <drawImage>
                    <imageFile>src/it/resources/red-indicator.png</imageFile>
                    <position>right,bottom</position>
                </drawImage>
            </draw>
        </configuration>
    </plugin>

```
